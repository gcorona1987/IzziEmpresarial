/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author figutierreza
 */
public class conexionSalesforce {
    
    static final String USERNAME     = "sfbrmintegra@izzi.mx";
    static final String PASSWORD     = "SfBrm1nt3gr41zz1*Rc7SV6MyuMEhsrVC1TMJM3XX";
    static final String LOGINURL     = "https://bestel--brm.my.salesforce.com";
    static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
    static final String CLIENTID     = "3MVG9qEXqmIutu_QzDus6m9MqCYQCG2VqhvesLfyEP0QQiqL252G58DvTaaSE6bC.hRnLSH8jyUmkrpplMb43";
    static final String CLIENTSECRET = "085B4E36C457498EA5E3586C3122AFE1BBCD04FF6D3C4762D6786FBA67115F68";
    public JSONObject jsonObject = null;
    public String loginAccessToken = null;
    public String loginInstanceUrl = null;
    public String getResult = null;
    public String loginURL=null;


    public String getLoginAccessToken() {
        return loginAccessToken;
    }

    public void setLoginAccessToken(String loginAccessToken) {
        this.loginAccessToken = loginAccessToken;
    }

    public void getToken() {

        HttpClient httpclient = HttpClientBuilder.create().build();

        // Assemble the login request URL
        //https://bestel--brm.my.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG9qEXqmIutu_QzDus6m9MqCYQCG2VqhvesLfyEP0QQiqL252G58DvTaaSE6bC.hRnLSH8jyUmkrpplMb43&client_secret=085B4E36C457498EA5E3586C3122AFE1BBCD04FF6D3C4762D6786FBA67115F68&username=figutierreza@izzi.mx.brm&password=Lemondesk820*FNUaBv9f0c4TDZJ8Q0Yzplqw
        loginURL = LOGINURL +
                          GRANTSERVICE +
                          "&client_id=" + CLIENTID +
                          "&client_secret=" + CLIENTSECRET +
                          "&username=" + USERNAME +
                          "&password=" + PASSWORD;

        // Login requests must be POSTs
        HttpPost httpPost = new HttpPost(loginURL);
        HttpResponse response = null;

        try {
            // Execute the login POST request
            response = httpclient.execute(httpPost);
        } catch (ClientProtocolException cpException) {
            cpException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Error authenticating to Force.com: "+statusCode);
            // Error is in EntityUtils.toString(response.getEntity())
         return ;
        }

        try {
            getResult = EntityUtils.toString(response.getEntity());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        
        try {
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            loginAccessToken = jsonObject.getString("access_token");
            loginInstanceUrl = jsonObject.getString("instance_url");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        System.out.println(response.getStatusLine());
        System.out.println("Successful login");
        System.out.println("  instance URL: "+loginInstanceUrl);
        System.out.println("  access token/session ID: "+loginAccessToken);

        // release connection
        httpPost.releaseConnection();
    }
     


    
}
