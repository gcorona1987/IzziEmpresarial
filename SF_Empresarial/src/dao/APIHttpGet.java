package dao;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *
 * @author figutierreza
 */
public class APIHttpGet {

    HttpResponse response = null;
    HttpGet httpGet = null;
    HttpClient httpclient = HttpClientBuilder.create().build();

    public String getResult(String loginURL, String token) throws IOException {
        try {

            httpGet = new HttpGet(loginURL);
            httpGet.addHeader("Authorization", "Bearer " + token);
            // Execute the login get request
            response = httpclient.execute(httpGet);

            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.out.println("Error authenticating to Force.com: " + statusCode);
                // Error is in EntityUtils.toString(response.getEntity())
                return null;
            }

            String getResult = null;
            getResult = EntityUtils.toString(response.getEntity());
            httpGet.releaseConnection();

            return getResult;
        } catch (ClientProtocolException e) {
            System.out.println("Error" + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
            return null;
        }
    }

}
