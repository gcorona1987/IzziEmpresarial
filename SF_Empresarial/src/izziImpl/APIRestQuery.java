package izziImpl;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import objetos.Account;


public class APIRestQuery {


    static final String LOGINURL     = "https://bestel--brm.my.salesforce.com";
    static String GRANTSERVICE = "/services/data/v52.0/sobjects/Account/";
    static String account;
    static String Id;
    static String Name;
    String sessionId = "00D780000004dwZ!AQwAQH1.er8.qszbfsRkXUdlvEzBhDDq6jQ.J.8RBbD2w4YEerjm476s2smy2B6K0kPDzNwjgT0FCLxyGCB.k8gTyzJxSdlK";
    int size;
    Account accountObj = new Account();

    public APIRestQuery(String account) {
        APIRestQuery.account = account;
        GRANTSERVICE = GRANTSERVICE+account;
    }

    public  void queryAPI() {

        HttpClient httpclient = HttpClientBuilder.create().build();
        // Assemble the login request URL
        String loginURL = LOGINURL +
                GRANTSERVICE;

        // Login requests must be POSTs
        HttpGet httpGet = new HttpGet(loginURL);

        httpGet.setHeader("Authorization", "Bearer " + sessionId);
        httpGet.setHeader("Content-Type", "application/json");


        HttpResponse response = null;

        try {
            // Execute the login POST request
            response = httpclient.execute(httpGet);

        } catch (IOException cpException) {
            cpException.printStackTrace();
        }

        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Error authenticating to Force.com: "+statusCode);
            // Error is in EntityUtils.toString(response.getEntity())
            return;
        }

        String getResult = null;
        try {
            getResult = EntityUtils.toString(response.getEntity());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        JSONObject jsonObject = null;


        try {
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            accountObj.setName((String)jsonObject.get("Name"));
            accountObj.setId((String)jsonObject.get("Id"));
            accountObj.setSubsidiaria__c((String)jsonObject.get("Subsidiaria__c"));
            accountObj.setCurrencyIsoCode((String)jsonObject.get("CurrencyIsoCode"));

            if(!jsonObject.isNull("Clave_de_Cliente__c")){
                accountObj.setClave_de_Cliente__c((String)jsonObject.get("Clave_de_Cliente__c"));
            }
            if(!jsonObject.isNull("Oracle_ID__c")){
                accountObj.setOracle_ID__c((String)jsonObject.get("Oracle_ID__c"));
            }
            if(!jsonObject.isNull("ID_Cliente_SAP__c")){
                accountObj.setID_Cliente_SAP__c((String)jsonObject.get("ID_Cliente_SAP__c"));
            }
            if(!jsonObject.isNull("Razon_Social__c")){
                accountObj.setRazon_Social__c((String)jsonObject.get("Razon_Social__c"));
            }
            if(!jsonObject.isNull("Compania__c")){
                accountObj.setCompania__c((String)jsonObject.get("Compania__c"));
            }
            if(!jsonObject.isNull("Numero_de_Cuenta__c")){
                accountObj.setNumero_de_Cuenta__c((String)jsonObject.get("Numero_de_Cuenta__c"));
            }
            if(!jsonObject.isNull("RFC__c")){
                accountObj.setRFC__c((String)jsonObject.get("RFC__c"));
            }
            if(!jsonObject.isNull("accountStatus__c")){
                accountObj.setAccountStatus__c((String)jsonObject.get("accountStatus__c"));
            }
            if(!jsonObject.isNull("Organization__c")){
                accountObj.setOrganization__c((String)jsonObject.get("Organization__c"));
            }


            //accountObj.setCve_cliente ((String)jsonObject.get("Clave_de_Cliente__c"));


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        // release connection
        httpGet.releaseConnection();


    }

    public void printAccount() {
        System.out.println("Id: " + accountObj.getId());
        System.out.println("Account: " + accountObj.getName() + "\r\n"
                + "Clave de Cliente: " + accountObj.getClave_de_Cliente__c()+ "\r\n"
                + "Oracle Id: " + accountObj.getOracle_ID__c()+ "\r\n"
                + "Id Cliente SAP: " + accountObj.getID_Cliente_SAP__c()+ "\r\n"
                + "Razón Social: " + accountObj.getRazon_Social__c() + "\r\n"
                + "Compañia: " + accountObj.getCompania__c() + "\r\n"
                + "Subsidiaria Id: " + accountObj.getSubsidiaria__c() + "\r\n"
                + "Numero de cuenta: " + accountObj.getNumero_de_Cuenta__c()+ "\r\n"
                + "RFC: " + accountObj.getRFC__c() + "\r\n"
                + "Courrency Code: " + accountObj.getCurrencyIsoCode() + "\r\n"
                + "Account Status: " + accountObj.getAccountStatus__c() + "\r\n"
                + "Organización: " + accountObj.getOrganization__c());
    }
}

