package src;

import dao.conexionSalesforce;
import izziImpl.GetAPIQuerySOQL;
import dao.conexionDB;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    
  Properties properties = new Properties();
  
    public static void main(String[] args) throws IOException, SQLException, Exception {
    Properties properties = new Properties();
    properties.load(new FileInputStream(new File("SFBRM.properties")));
    conexionSalesforce connSF = new conexionSalesforce();
    conexionDB connDB = new conexionDB(properties.get("ODBCBRM").toString(),properties.get("USERBRM").toString() ,properties.get("PWDBRM").toString() );
    connSF.getToken();
    //String tokenSF = connSF.getLoginAccessToken();
    //GetAPIQuerySOQL APIQrySOQL= new GetAPIQuerySOQL(tokenSF,connDB);
    //APIQrySOQL.queryAPI();
    connDB.CloseDB();
    
    
   /* jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
     Strin Id = APIQrySOQL.getResult();
    System.out.println("Total: " + Total);
   hola hola hola
    */
        
        
    }
}

