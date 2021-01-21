import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;

/**
 * @ClassName HttpClientØ
 * @Date 2021/1/20 23:02Ø
 * @Description TODO
 */
public class HttpExample {
  public static void main(String[] args) {
    httpGet();
  }

  public static void httpGet() {
    HttpClient httpClient = HttpClients.createDefault();
    String url = "http://localhost:8088/api/hello";
    HttpGet httpGet = new HttpGet(url);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    try {
      String responseBody = httpClient.execute(httpGet, responseHandler);
      System.out.println(responseBody);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
