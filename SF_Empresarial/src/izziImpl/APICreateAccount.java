package izziImpl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class APICreateAccount {

    //static final String USERNAME     = "figutierreza@izzi.mx.brm";
    //static final String PASSWORD     = "Lemondesk820*FNUaBv9f0c4TDZJ8Q0Yzplqw";
    static final String LOGINURL     = "https://bestel--brm.my.salesforce.com";
    static final String GRANTSERVICEACCOUNT = "/services/data/v52.0/sobjects/Account/";
    //static final String CLIENTID     = "3MVG9qEXqmIutu_QzDus6m9MqCYQCG2VqhvesLfyEP0QQiqL252G58DvTaaSE6bC.hRnLSH8jyUmkrpplMb43";
    //static final String CLIENTSECRET = "085B4E36C457498EA5E3586C3122AFE1BBCD04FF6D3C4762D6786FBA67115F68";
    //static String loginAccessToken = null;
    //static String loginInstanceUrl = null;
    String sessionId = "00D780000004dwZ!AQwAQH1.er8.qszbfsRkXUdlvEzBhDDq6jQ.J.8RBbD2w4YEerjm476s2smy2B6K0kPDzNwjgT0FCLxyGCB.k8gTyzJxSdlK";
    String result;



    public  void insretRow() throws UnsupportedEncodingException {
        //sessionId = this.sessionId;
        HttpClient httpclient = HttpClientBuilder.create().build();

        // Assemble the login request URL
        String loginURL = LOGINURL +
                          GRANTSERVICEACCOUNT;

        // Login requests must be POSTs
        HttpPost httpPost = new HttpPost(loginURL);

        JSONObject obj = new JSONObject();
        obj.put( "Name","IzziTest22");
        obj.put( "Segmento_Bestel__c","BESTEL");
        obj.put( "Compania__c","Bestel");
        obj.put( "Subsidiaria__c","BestelUSA");
        obj.put( "CurrencyIsoCode","MXN");
        obj.put( "Segmento_SCenter__c","Iniciativa Privada");
        obj.put( "Giro__c","MXN");
        StringEntity params = new StringEntity(obj.toString());

        httpPost.setHeader("Authorization", "Bearer " + sessionId);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(params);

        HttpResponse response = null;

        try
        {
            // Execute the login POST request
            response = httpclient.execute(httpPost);

        }
        catch (IOException cpException)
        {
            cpException.printStackTrace();
        }

        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();


        String getResult = null;
        try
        {
            getResult = EntityUtils.toString(response.getEntity());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

        JSONObject jsonObject = null;

        try
        {
            assert getResult != null;
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            result = (String) jsonObject.get("id");

        }
        catch (JSONException jsonException)
        {
            jsonException.printStackTrace();
        }


        System.out.println("Id: "+ result);
        System.out.println("Status Code: "+ statusCode);
        //System.out.println("Successful Holaa");

        // release connection
        httpPost.releaseConnection();
    }
}

