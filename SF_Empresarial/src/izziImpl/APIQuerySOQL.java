package izziImpl;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author figutierreza
 */
public class APIQuerySOQL {
    
    static final String LOGINURL     = "https://bestel--brm.my.salesforce.com";
    static final String QUERY= "/services/data/v52.0/query/?q=";
    public JSONObject jsonObject = null;
    public String getResult = null;
    public String loginURL=null;
    public String Authorization=null;
    public int qrytotalSize;

    public int getQrytotalSize() {
        return qrytotalSize;
    }

    public void setQrytotalSize(int qrytotalSize) {
        this.qrytotalSize = qrytotalSize;
    }
    
    
      public void getConsulta(String qrySF, String tokenSF)  throws IOException  {

        HttpClient httpclient = HttpClientBuilder.create().build();

        // Assemble the login request URL
        //https://bestel--brm.my.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG9qEXqmIutu_QzDus6m9MqCYQCG2VqhvesLfyEP0QQiqL252G58DvTaaSE6bC.hRnLSH8jyUmkrpplMb43&client_secret=085B4E36C457498EA5E3586C3122AFE1BBCD04FF6D3C4762D6786FBA67115F68&username=figutierreza@izzi.mx.brm&password=Lemondesk820*FNUaBv9f0c4TDZJ8Q0Yzplqw
        loginURL = LOGINURL + QUERY +qrySF;
        
        Authorization ="Bearer " + tokenSF;
        
        // Login requests must be GET
        HttpGet httpGet = new HttpGet(loginURL);
        httpGet.addHeader("Authorization",Authorization);

        HttpResponse response = null;

        try {
            // Execute the login POST request
            response = httpclient.execute(httpGet);
        } catch (ClientProtocolException cpException) {
            System.out.println(cpException.getMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
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
             qrytotalSize = jsonObject.getInt("totalSize");
            // qrydone = jsonObject.getString("done");
             
             JSONArray arr = jsonObject.getJSONArray("records"); // notice that `"posts": [...]`
             for (int i = 0; i < arr.length(); i++)
             {
                String Name = arr.getJSONObject(i).getString("Name");
                String Id = arr.getJSONObject(i).getString("Id");
                System.out.println("Cuenta: " +Name+ "    Id: "+ Id);
             }

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }


        // release connection
        httpGet.releaseConnection();
    }
}
