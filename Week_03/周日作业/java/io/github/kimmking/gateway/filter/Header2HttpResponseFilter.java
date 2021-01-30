package io.github.kimmking.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @ClassName Header2HttpResponseFilter
 * @Date 2021/1/31 00:14
 * @Description TODO
 * @Status ISFINISH
 */
public class Header2HttpResponseFilter implements HttpResponseFilter{

  @Override
  public void filter(FullHttpResponse response) {
    response.headers().set("typ_out_test", "out typ");
  }
}
