package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.LoadBalancer;
import io.kimmking.rpcfx.api.Router;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.MediaType;

public final class Rpcfx {

  static {
    ParserConfig.getGlobalInstance().addAccept("io.kimmking");
  }

  public static <T, filters> T createFromRegistry(final Class<T> serviceClass, final String zkUrl,
      Router router, LoadBalancer loadBalance, Filter filter) {

    // 加filte之一

    // curator Provider list from zk
    List<String> invokers = new ArrayList<>();
    // 1. 简单：从zk拿到服务提供的列表
    // 2. 挑战：监听zk的临时节点，根据事件更新这个list（注意，需要做个全局map保持每个服务的提供者List）

    List<String> urls = router.route(invokers);

    String url = loadBalance.select(urls); // router, loadbalance

    return (T) create(serviceClass, url, filter);

  }

  public static <T> T create(final Class<T> serviceClass, final String url, Filter... filters) {

    // 0. 替换动态代理 -> AOP
    return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass},
        new RpcfxInvocationHandler(serviceClass, url, filters));

  }

  public static class RpcfxInvocationHandler implements InvocationHandler {

    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

    private final Class<?> serviceClass;
    private final String url;
    private final Filter[] filters;

    public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url, Filter... filters) {
      this.serviceClass = serviceClass;
      this.url = url;
      this.filters = filters;
    }

    // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
    // int byte char float double long bool
    // [], data class

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {

      // 加filter地方之二
      // mock == true, new Student("hubao");

      RpcfxRequest request = new RpcfxRequest();
      request.setServiceClass(this.serviceClass.getName());
      request.setMethod(method.getName());
      request.setParams(params);

      if (null != filters) {
        for (Filter filter : filters) {
          if (!filter.filter(request)) {
            return null;
          }
        }
      }

      RpcfxResponse response = post(request, url);

      // 加filter地方之三
      // Student.setTeacher("cuijing");

      // 这里判断response.status，处理异常
      // 考虑封装一个全局的RpcfxException
      if (!response.isStatus()) {
        // 失败时，重试两次
        for (int i = 0; i < 2; i++) {
          Thread.sleep(500);
          System.out.println("i====" + i);
          response = post(request, url);
          if (response.isStatus()) {
            break;
          }
        }

        if (!response.isStatus()) {
          throw new Exception("服务器端发生异常，请稍后重试，谢谢", response.getException());
        }

      }

      return JSON.parse(response.getResult().toString());
    }

    private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
      String reqJson = JSON.toJSONString(req);
      System.out.println("req json: " + reqJson);

      // 2.尝试使用httpclient或者netty client
      String jsonResult = new HttpInboundServer(reqJson).bootStrapRun();
      return JSON.parseObject(jsonResult, RpcfxResponse.class);
    }
  }
}

class HttpInboundServer {

  private int port = 8081;
  private String reqJson;
  private List<String> proxyServers;

  public HttpInboundServer(String reqJson) {
    String proxyServers = "http://localhost:8081,http://localhost:8082";
    this.proxyServers = Arrays.asList(proxyServers.split(","));
    this.reqJson = reqJson;
  }

  public String bootStrapRun() {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    HttpClientHandler httpClientHandler = new HttpClientHandler();

    try {
      Bootstrap b = new Bootstrap();
      b.group(bossGroup).channel(NioSocketChannel.class)
          .option(ChannelOption.SO_KEEPALIVE, true)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline().addLast(new HttpRequestEncoder());// 客户端对发送的httpRequest进行编码
              socketChannel.pipeline()
                  .addLast(new HttpResponseDecoder());// 客户端需要对服务端返回的httpresopnse解码
              socketChannel.pipeline().addLast(httpClientHandler);
            }
          });

      Channel ch = b.connect("localhost", port).sync().channel();
      URI uri = new URI("/");
      FullHttpRequest fullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
          HttpMethod.POST, uri.toASCIIString(), Unpooled.wrappedBuffer(this.reqJson.getBytes()));

      fullHttpRequest.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
      fullHttpRequest.headers().add(HttpHeaderNames.HOST, "127.0.0.1");
      fullHttpRequest.headers()
          .add(HttpHeaderNames.CONTENT_LENGTH, fullHttpRequest.content().readableBytes());
      fullHttpRequest.headers().add(HttpHeaderNames.CONTENT_TYPE, "application/json");
      fullHttpRequest.headers().set("messageType", "normal");
      fullHttpRequest.headers().set("businessType", "testServerState");

      // send request
      ch.writeAndFlush(fullHttpRequest).sync();
      ch.closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
    }

    String jsonResult = httpClientHandler.getResult();
    return jsonResult;
  }
}

class HttpClientHandler extends SimpleChannelInboundHandler<HttpContent> {

  private String result;

  public String getResult() {
    return this.result;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("发送信息");
    super.channelActive(ctx);
  }

  @Override
  public void channelRead0(ChannelHandlerContext ctx, HttpContent msg)
      throws Exception {
    if (this.result == null || "".equals(this.result)) {
      HttpContent httpContent = (HttpContent) msg;
      ByteBuf content = httpContent.content();

      int length = content.readableBytes();
      byte[] array = new byte[length];
      ByteBuf bytes = content.getBytes(content.readerIndex(), array);
      this.result = convertByteBufToString(bytes);
    }
  }

  public String convertByteBufToString(ByteBuf buf) {
    String str;
    if (buf.hasArray()) { // 处理堆缓冲区
      str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
    } else { // 处理直接缓冲区以及复合缓冲区
      byte[] bytes = new byte[buf.readableBytes()];
      buf.getBytes(buf.readerIndex(), bytes);
      str = new String(bytes, 0, buf.readableBytes());
    }
    return str;
  }
}
