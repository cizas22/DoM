package Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiService {
  private final CloseableHttpClient httpClient = HttpClients.createDefault();
  private final Gson gson = new Gson();

  public JsonArray fetchGet(String urlToFetch) {
    HttpGet request = new HttpGet(urlToFetch);

    try (CloseableHttpResponse response = httpClient.execute(request)) {
      HttpEntity entity = response.getEntity();

      if (entity != null) {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
            || response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
          return gson.fromJson(EntityUtils.toString(response.getEntity()), JsonArray.class);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public JsonObject fetchPost(String urlToFetch, JsonObject requestBody) {
    HttpPost post = new HttpPost(urlToFetch);
    if (requestBody != null) {
      try {
        post.setEntity(new UrlEncodedFormEntity(buildRequestBody(requestBody)));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }

    try (CloseableHttpResponse response = httpClient.execute(post)) {
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
          || response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
        String responseBody = EntityUtils.toString(response.getEntity());
        return gson.fromJson(responseBody, JsonObject.class);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  private List<NameValuePair> buildRequestBody(JsonObject requestBody) {
    List<NameValuePair> urlParameters = new ArrayList<>();
    Iterator<String> i = requestBody.keySet().iterator();
    while (i.hasNext()) {
      String key = i.next();
      urlParameters.add(new BasicNameValuePair(key, requestBody.get(key).toString()));
    }
    return urlParameters;
  }

  private boolean checkIfJsonObject(String body) {
    return body != null && !body.isEmpty() && body.charAt(0) == '{';
  }
}
