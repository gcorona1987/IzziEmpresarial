package izziImpl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class  APIUpdate {


    static final String LOGINURL     = "https://bestel--brm.my.salesforce.com";
    static String GRANTSERVICE = "/services/data/v52.0/sobjects/Account/";
    static String account;
    static String sessionId;
    //String sessionId = "00D780000004dwZ!AQwAQFow0Kk4yuc_lSoejCcUn9VpWynS8IWk3_5NFOAqTA2s1JnGWj483TZFGhGOc9SGvD3m.qr_k1uUIPHRRE2D6nwKBE2E";


    public APIUpdate(String account,String sessionId) {
        APIUpdate.account = account;
        APIUpdate.sessionId = sessionId;
        GRANTSERVICE = GRANTSERVICE+account;
    }

    public  void updateAPI() throws UnsupportedEncodingException {

        HttpClient httpclient = HttpClientBuilder.create().build();
        // Assemble the login request URL
        String loginURL = LOGINURL +
                GRANTSERVICE;

        // Login requests must be POSTs
        HttpPatch httpPatch = new HttpPatch(loginURL);

        JSONObject obj = new JSONObject();
        obj.put( "Description","Prueba50");

        StringEntity params = new StringEntity(obj.toString());


        httpPatch.setHeader("Authorization", "Bearer " + sessionId);
        httpPatch.setHeader("Content-Type", "application/json");
        httpPatch.setEntity(params);


        HttpResponse response = null;

        try {
            // Execute the login POST request
            response = httpclient.execute(httpPatch);

        } catch (IOException cpException) {
            cpException.printStackTrace();
        }

        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("API Rest Update: Successful\r\n" +
                           "Satuts Code: "+statusCode);


        // release connection
        httpPatch.releaseConnection();

    }


}

