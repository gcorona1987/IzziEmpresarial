package izziImpl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class APIDelete {


    static final String LOGINURL     = "https://bestel--brm.my.salesforce.com";
    static String GRANTSERVICE = "/services/data/v52.0/sobjects/Account/";
    static String account;
    static String sessionId;
    //String sessionId = "00D780000004dwZ!AQwAQFow0Kk4yuc_lSoejCcUn9VpWynS8IWk3_5NFOAqTA2s1JnGWj483TZFGhGOc9SGvD3m.qr_k1uUIPHRRE2D6nwKBE2E";


    public APIDelete(String account, String sessionId) {
        APIDelete.account = account;
        APIDelete.sessionId = sessionId;
        GRANTSERVICE = GRANTSERVICE+account;
    }

    public  void deleteAPI() throws UnsupportedEncodingException {

        HttpClient httpclient = HttpClientBuilder.create().build();
        // Assemble the login request URL
        String loginURL = LOGINURL +
                GRANTSERVICE;

        // Login requests must be POSTs

        HttpDelete httpDelete = new HttpDelete(loginURL);

        JSONObject obj = new JSONObject();
        obj.put( "Description","Prueba50");

        StringEntity params = new StringEntity(obj.toString());


        httpDelete.setHeader("Authorization", "Bearer " + sessionId);
        httpDelete.setHeader("Content-Type", "application/json");

        HttpResponse response = null;

        try {
            // Execute the login POST request
            response = httpclient.execute(httpDelete);

        } catch (IOException cpException) {
            cpException.printStackTrace();
        }

        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("API Rest Delete: Successful\r\n" +
                "Satuts Code: "+statusCode);



        // release connection
        httpDelete.releaseConnection();

    }


}

