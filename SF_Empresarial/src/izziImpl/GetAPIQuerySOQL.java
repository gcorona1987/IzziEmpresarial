package izziImpl;

/**
 *
 * @author figutierreza
 */
import dao.APIHttpGet;
import dao.conexionDB;
import java.io.File;
import java.io.FileInputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import objetos.Account;
import objetos.CFacturacion;
import org.json.JSONArray;
import java.util.Properties;
import objetos.Opportunity;

public class GetAPIQuerySOQL {

    Properties propgral = new Properties();
    Properties propdml = new Properties();
    static String token;
    static conexionDB connDBBRM;
    static String Id;
    static String Name;
    static String query;
    static String urlaccnt;
    static String urlcfact;
    static String urloportunity;
    static String insertbrmsfaccount;
    static String qryseqbrmsf;
    static String getResultAccnt;
    static String getResultCFact;
    static String getResultOpportunity;
    static String seqbrmsf;
    int qrytotalSizeAccnt;
    int qrytotalSizeCFact;
    int Registros, i, j;
    int size;
    Boolean conteo;   
    APIHttpGet apihhtget = new APIHttpGet();
    JSONObject jsonObject = null;
    Account accountObj = new Account();
    CFacturacion cfactObj = new CFacturacion();
    Opportunity opportObj = new Opportunity();

    public GetAPIQuerySOQL(String apitoken, conexionDB connDB) throws IOException, SQLException {
        try {
            propgral.load(new FileInputStream(new File("SFBRM.properties")));
            propdml.load(new FileInputStream(new File("SFBRMDML.properties")));
            urlaccnt = propgral.get("LOGINURLSF").toString() + propgral.get("QUERYSERVICE").toString() + propdml.get("QRYOBJACCOUNTSF").toString();
            urlcfact = propgral.get("LOGINURLSF").toString() + propgral.get("QUERYSERVICE").toString() + propdml.get("QRYOBJCONTRATOFACTURACION").toString();
            urloportunity=propgral.get("LOGINURLSF").toString() + propgral.get("QUERYSERVICE").toString() + propdml.get("QRYOBJOPORTUNITYSF").toString();
            insertbrmsfaccount=propdml.get("INSERTBRMSFACCOUNT").toString();
            qryseqbrmsf=propdml.get("QRYSEQBRMSF").toString();
            token = apitoken;
            connDBBRM = connDB;
            
            try (PreparedStatement ps = connDBBRM.conexion.prepareStatement(qryseqbrmsf); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
               seqbrmsf = rs.getString(1);
            }
            }
            catch(SQLException sqlexc){
                    System.out.println(sqlexc.getMessage());
                    }          
            
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public void queryAPI() throws IOException, Exception {
        List<Account> listAccount = new LinkedList();
        try {

            /**
             * **************** Obtiene Datos Cuenta Salesforce ***************
             */
            getResultAccnt = apihhtget.getResult(urlaccnt, token);
            jsonObject = (JSONObject) new JSONTokener(getResultAccnt).nextValue();

            qrytotalSizeAccnt = jsonObject.getInt("totalSize");

            JSONArray jarrAccnt = jsonObject.getJSONArray("records");

            for (i = 0; i < jarrAccnt.length(); i++) {
                System.out.println("Cuenta: " + jarrAccnt.getJSONObject(i).getString("Name"));

                accountObj.setName(jarrAccnt.getJSONObject(i).getString("Name"));
                accountObj.setId(jarrAccnt.getJSONObject(i).getString("Id"));
                accountObj.setSubsidiaria__c(jarrAccnt.getJSONObject(i).getString("Subsidiaria__c"));
                accountObj.setCurrencyIsoCode(jarrAccnt.getJSONObject(i).getString("CurrencyIsoCode"));

                if (jarrAccnt.getJSONObject(i).optString("Clave_de_Cliente__c", "nulo") != "nulo") {
                    accountObj.setClave_de_Cliente__c(jarrAccnt.getJSONObject(i).getString("Clave_de_Cliente__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Oracle_ID__c", "nulo") != "nulo") {
                    accountObj.setOracle_ID__c(jarrAccnt.getJSONObject(i).getString("Oracle_ID__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("ID_Cliente_SAP__c", "nulo") != "nulo") {
                    accountObj.setID_Cliente_SAP__c(jarrAccnt.getJSONObject(i).getString("ID_Cliente_SAP__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Razon_Social__c", "nulo") != "nulo") {
                    accountObj.setRazon_Social__c(jarrAccnt.getJSONObject(i).getString("Razon_Social__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Compania__c", "nulo") != "nulo") {
                    accountObj.setCompania__c(jarrAccnt.getJSONObject(i).getString("Compania__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Numero_de_Cuenta__c", "nulo") != "nulo") {
                    accountObj.setNumero_de_Cuenta__c(jarrAccnt.getJSONObject(i).getString("Numero_de_Cuenta__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("RFC__c", "nulo") != "nulo") {
                    accountObj.setRFC__c(jarrAccnt.getJSONObject(i).getString("RFC__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("accountStatus__c", "nulo") != "nulo") {
                    accountObj.setAccountStatus__c(jarrAccnt.getJSONObject(i).getString("accountStatus__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Organization__c", "nulo") != "nulo") {
                    accountObj.setOrganization__c(jarrAccnt.getJSONObject(i).getString("Organization__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("BRM_poidAccount__c", "nulo") != "nulo") {
                    accountObj.setBRM_poidAccount__c(jarrAccnt.getJSONObject(i).getString("BRM_poidAccount__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("BRM_poidAcctProfile__c", "nulo") != "nulo") {
                    accountObj.setBRM_poidAcctProfile__c(jarrAccnt.getJSONObject(i).getString("BRM_poidAcctProfile__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("BRM_poidPayInfo__c", "nulo") != "nulo") {
                    accountObj.setBRM_poidPayInfo__c(jarrAccnt.getJSONObject(i).getString("BRM_poidPayInfo__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("BRM_Poid_Account_Info__c", "nulo") != "nulo") {
                    accountObj.setBRM_Poid_Account_Info__c(jarrAccnt.getJSONObject(i).getString("BRM_Poid_Account_Info__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("addressType__c", "nulo") != "nulo") {
                    accountObj.setAddressType__c(jarrAccnt.getJSONObject(i).getString("addressType__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Segmento_SCenter__c", "nulo") != "nulo") {
                    accountObj.setSegmento_SCenter__c(jarrAccnt.getJSONObject(i).getString("Segmento_SCenter__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Contrato_Default__c", "nulo") != "nulo") {
                    accountObj.setContrato_Default__c(jarrAccnt.getJSONObject(i).getString("Contrato_Default__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("N_mero_de_cuenta_Siebel__c", "nulo") != "nulo") {
                    accountObj.setN_mero_de_cuenta_Siebel__c(jarrAccnt.getJSONObject(i).getString("N_mero_de_cuenta_Siebel__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Nombre_Contacto_IFT__c", "nulo") != "nulo") {
                    accountObj.setNombre_Contacto_IFT__c(jarrAccnt.getJSONObject(i).getString("Nombre_Contacto_IFT__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Segmento_Bestel__c", "nulo") != "nulo") {
                    accountObj.setSegmento_Bestel__c(jarrAccnt.getJSONObject(i).getString("Segmento_Bestel__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Giro__c", "nulo") != "nulo") {
                    accountObj.setOrganization__c(jarrAccnt.getJSONObject(i).getString("Giro__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Parent", "nulo") != "nulo") {
                    accountObj.setParentName(jarrAccnt.getJSONObject(i).getString("Parent"));
                }
                if (jarrAccnt.getJSONObject(i).optString("OwnerId", "nulo") != "nulo") {
                    accountObj.setOwnerId(jarrAccnt.getJSONObject(i).getString("OwnerId"));
                }
                if (jarrAccnt.getJSONObject(i).optString("ParentId", "nulo") != "nulo") {
                    accountObj.setParentId(jarrAccnt.getJSONObject(i).getString("ParentId"));
                }
                if (jarrAccnt.getJSONObject(i).optBoolean("Aprobacion_de_Cuenta__c", true) != true) {
                    accountObj.setAprobacion_de_Cuenta__c(jarrAccnt.getJSONObject(i).getBoolean("Aprobacion_de_Cuenta__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Asigna_propietario_SAC_SAO__c", "nulo") != "nulo") {
                    accountObj.setAsigna_propietario_SAC_SAO__c(jarrAccnt.getJSONObject(i).getString("Asigna_propietario_SAC_SAO__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("billCycle__c", "nulo") != "nulo") {
                    accountObj.setBillCycle__c(jarrAccnt.getJSONObject(i).getString("billCycle__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("billFrecuency__c", "nulo") != "nulo") {
                    accountObj.setBillFrecuency__c(jarrAccnt.getJSONObject(i).getString("billFrecuency__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("billingCode__c", "nulo") != "nulo") {
                    accountObj.setBillingCode__c(jarrAccnt.getJSONObject(i).getString("billingCode__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("billingSegment__c", "nulo") != "nulo") {
                    accountObj.setBillingSegment__c(jarrAccnt.getJSONObject(i).getString("billingSegment__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Due_o_de_Territorio__c", "nulo") != "nulo") {
                    accountObj.setDue_o_de_Territorio__c(jarrAccnt.getJSONObject(i).getString("Due_o_de_Territorio__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Grabar_IEPS__c", "nulo") != "nulo") {
                    accountObj.setGrabar_IEPS__c(jarrAccnt.getJSONObject(i).getString("Grabar_IEPS__c"));
                }
                if (jarrAccnt.getJSONObject(i).optBoolean("Guia_Funcionalidades_solo_Ucloud__c", true) != true) {
                    accountObj.setGuia_Funcionalidades_solo_Ucloud__c(jarrAccnt.getJSONObject(i).getBoolean("Guia_Funcionalidades_solo_Ucloud__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Identificador_Bestel__c", "nulo") != "nulo") {
                    accountObj.setIdentificador_Bestel__c(jarrAccnt.getJSONObject(i).getString("Identificador_Bestel__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Numero_C__c", "nulo") != "nulo") {
                    accountObj.setNumero_C__c(jarrAccnt.getJSONObject(i).getString("Numero_C__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Numero__c", "nulo") != "nulo") {
                    accountObj.setNumero__c(jarrAccnt.getJSONObject(i).getString("Numero__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Pais_C__c", "nulo") != "nulo") {
                    accountObj.setPais_C__c(jarrAccnt.getJSONObject(i).getString("Pais_C__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Particularidades_de_la_Cuenta__c", "nulo") != "nulo") {
                    accountObj.setParticularidades_de_la_Cuenta__c(jarrAccnt.getJSONObject(i).getString("Particularidades_de_la_Cuenta__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Pa_s__c", "nulo") != "nulo") {
                    accountObj.setPa_s__c(jarrAccnt.getJSONObject(i).getString("Pa_s__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Segmento_de_Cuenta__c", "nulo") != "nulo") {
                    accountObj.setSegmento_de_Cuenta__c(jarrAccnt.getJSONObject(i).getString("Segmento_de_Cuenta__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Segmento_ventas__c", "nulo") != "nulo") {
                    accountObj.setSegmento_ventas__c(jarrAccnt.getJSONObject(i).getString("Segmento_ventas__c"));
                }
                if (jarrAccnt.getJSONObject(i).optString("Type_bestel__c", "nulo") != "nulo") {
                    accountObj.setType_bestel__c(jarrAccnt.getJSONObject(i).getString("Type_bestel__c"));
                }

                
                
                InsertaBRM(accountObj);

                //accountObj.setCve_cliente ((String)jsonObject.get("Clave_de_Cliente__c"));
                Registros = listAccount.size();
                conteo = listAccount.isEmpty(); // Valida si la lista esta vacia
                System.out.println(listAccount);

            }
        } catch (JSONException jsonException) {
            System.out.println(jsonException.getMessage());
        }
    }
    
    public void getCfacturacion(Account accountObj) throws IOException{
        getResultCFact = apihhtget.getResult(urlcfact + accountObj.getId() + "'", token);
                jsonObject = (JSONObject) new JSONTokener(getResultCFact).nextValue();

                qrytotalSizeCFact = jsonObject.getInt("totalSize");

                JSONArray jarrCFact = jsonObject.getJSONArray("records");

                for (j = 0; j < jarrCFact.length(); j++) {
                    if (jarrCFact.getJSONObject(i).optString("Id", "nulo") != "nulo") {
                        cfactObj.setId(jarrCFact.getJSONObject(i).getString("Id"));
                    }
                    if (jarrCFact.getJSONObject(i).optString("AccountToBunit", "nulo") != "nulo") {
                        cfactObj.setAccountToBunit(jarrCFact.getJSONObject(i).getString("AccountToBunit"));
                    }
                    if (jarrCFact.getJSONObject(i).optString("BRM_bill_unit_name", "nulo") != "nulo") {
                        cfactObj.setBRM_bill_unit_name(jarrCFact.getJSONObject(i).getString("BRM_bill_unit_name"));
                    }
                    if (jarrCFact.getJSONObject(i).optString("Name", "nulo") != "nulo") {
                        cfactObj.setName(jarrCFact.getJSONObject(i).getString("Name"));
                    }
                    
                    
                }
    }
    
    public void getOpportunity(Account accountObj) throws IOException{
        getResultOpportunity = apihhtget.getResult(urloportunity + accountObj.getId() + "'", token);
                jsonObject = (JSONObject) new JSONTokener(getResultOpportunity).nextValue();

                qrytotalSizeCFact = jsonObject.getInt("totalSize");

                JSONArray jarrayopportunity = jsonObject.getJSONArray("records");

                for (j = 0; j < jarrayopportunity.length(); j++) {
                    if (jarrayopportunity.getJSONObject(i).optString("AccountId", "nulo") != "nulo") {
                        opportObj.setAccountId(jarrayopportunity.getJSONObject(i).getString("AccountId"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Aprobacion__c", "nulo") != "nulo") {
                        opportObj.setAprobacion__c(jarrayopportunity.getJSONObject(i).getString("Aprobacion__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("AccountId__c", "nulo") != "nulo") {
                        opportObj.setAccountId__c(jarrayopportunity.getJSONObject(i).getString("AccountId__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Canal_de_Venta__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Canal_de_Venta__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Compania__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Compania__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("CurrencyIsoCode", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("CurrencyIsoCode"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Estado_de_Contrato__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Estado_de_Contrato__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Estatus__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Estatus__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Fecha_de_Facturacion__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Fecha_de_Facturacion__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("ForecastCategory", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("ForecastCategory"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("ForecastCategoryName", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("ForecastCategoryName"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Forecast__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Forecast__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Id", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Id"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("IsClosed", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("IsClosed"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("IsDeleted", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("IsDeleted"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Name", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Name"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Numero_de_Oportunidad__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Numero_de_Oportunidad__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optDouble("Numero_de_Productos__c", 0) != 0) {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getDouble("Numero_de_Productos__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Oportunidad_a_la_que_se_relaciona__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Oportunidad_a_la_que_se_relaciona__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Oportunidad_Cerrada__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Oportunidad_Cerrada__c"));
                    }
                    if (!jarrayopportunity.getJSONObject(i).optBoolean("Oportunidad_con_Orden_de_Compra__c", true)) {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getBoolean("Oportunidad_con_Orden_de_Compra__c"));
                    }
                    if (!jarrayopportunity.getJSONObject(i).optBoolean("Oportunidad_en_Zona_Verde__c", true)) {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getBoolean("Oportunidad_en_Zona_Verde__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Oportunidad_Madre__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Oportunidad_Madre__c"));
                    }
                    if (!jarrayopportunity.getJSONObject(i).optBoolean("Oportunidad_migrada__c", true)) {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getBoolean("Oportunidad_migrada__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Oportunidad_tipo__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Oportunidad_tipo__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Op__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Op__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Orden_de_Compra__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Orden_de_Compra__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Orden_de_Venta_SR__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Orden_de_Venta_SR__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Orden_de_Venta__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Orden_de_Venta__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Probability", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Probability"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Sales_Center__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Sales_Center__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Segmento_BESTEL__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Segmento_BESTEL__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Segmento__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Segmento__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Servicios_migrados__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Servicios_migrados__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Subsidiaria__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Subsidiaria__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Subtipo_de_oportunidad__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Subtipo_de_oportunidad__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Tipo_de_oportunidad__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Tipo_de_oportunidad__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Tipo_de_Orden__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Tipo_de_Orden__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Tipo_de_Producto__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Tipo_de_Producto__c"));
                    }
                    if (jarrayopportunity.getJSONObject(i).optString("Vertical__c", "nulo") != "nulo") {
                        opportObj.setCanal_de_Venta__c(jarrayopportunity.getJSONObject(i).getString("Vertical__c"));
                    }
                    
                    
                    
                }
    }

     public void InsertaBRM(Opportunity opportObj) throws Exception {
                       
        try (PreparedStatement preparedStatement = connDBBRM.conexion.prepareStatement(insertbrmsfaccount)) {

            preparedStatement.setString(1,opportObj.getAccountId() );
            preparedStatement.setString(2,opportObj.getAccountId__c() );
            preparedStatement.setString(3,opportObj.getAprobacion__c() );
            preparedStatement.setString(4,opportObj.getCanal_de_Venta__c() );
            preparedStatement.setString(5,opportObj.getCompania__c() );
            preparedStatement.setString(6,opportObj.getCurrencyIsoCode() );
            preparedStatement.setString(7,opportObj.getEstado_de_Contrato__c() );
            preparedStatement.setString(8,opportObj.getEstatus__c());
            preparedStatement.setString(9,opportObj.getFecha_de_Facturacion__c() );
            preparedStatement.setString(10,opportObj.getForecast__c());
            preparedStatement.setString(11,opportObj.getForecastCategory());
            preparedStatement.setString(12,opportObj.getForecastCategoryName() );
            preparedStatement.setString(13,opportObj.getOrganization__c() );
            preparedStatement.setString(14,opportObj.getBRM_poidAccount__c() );
            preparedStatement.setString(15,opportObj.getBRM_poidAcctProfile__c() );
            preparedStatement.setString(16,opportObj.getBRM_poidPayInfo__c() );
            preparedStatement.setString(17,opportObj.getBRM_Poid_Account_Info__c() );
            preparedStatement.setString(18,opportObj.getAddressType__c() );
            preparedStatement.setString(19,opportObj.getSegmento_SCenter__c() );
            preparedStatement.setString(20,opportObj.getContrato_Default__c() );
            preparedStatement.setString(21,opportObj.getN_mero_de_cuenta_Siebel__c() );
            preparedStatement.setString(22,opportObj.getNombre_Contacto_IFT__c() );
            preparedStatement.setString(23,opportObj.getSegmento_Bestel__c() );
            preparedStatement.setString(24,opportObj.getGiro__c() );
            preparedStatement.setString(25,opportObj.getParentName() );
            preparedStatement.setString(26,opportObj.getOwnerId() );
            preparedStatement.setString(27,opportObj.getParentId() );
            preparedStatement.setBoolean(28,opportObj.getAprobacion_de_Cuenta__c() );
            preparedStatement.setString(29,opportObj.getAsigna_propietario_SAC_SAO__c() );
            preparedStatement.setString(30,opportObj.getBillCycle__c() );
            preparedStatement.setString(31,opportObj.getBillFrecuency__c() );
            preparedStatement.setString(32,opportObj.getBillingCode__c() );
            preparedStatement.setString(33,opportObj.getBillingSegment__c() );
            preparedStatement.setString(34,opportObj.getDue_o_de_Territorio__c() );
            preparedStatement.setString(35,opportObj.getGrabar_IEPS__c() );
            preparedStatement.setBoolean(36,opportObj.getGuia_Funcionalidades_solo_Ucloud__c() );
            preparedStatement.setString(37,opportObj.getIdentificador_Bestel__c() );
            preparedStatement.setString(38,opportObj.getNumero_C__c() );
            preparedStatement.setString(39,opportObj.getNumero__c() );
            preparedStatement.setString(40,opportObj.getPais_C__c() );
            preparedStatement.setString(41,opportObj.getParticularidades_de_la_Cuenta__c() );
            preparedStatement.setString(42,opportObj.getPa_s__c() );
            preparedStatement.setString(43,opportObj.getSegmento_de_Cuenta__c() );
            preparedStatement.setString(44,opportObj.getSegmento_ventas__c() );
            preparedStatement.setString(45,opportObj.getType_bestel__c() );
            preparedStatement.setString(46,seqbrmsf );
            int row = preparedStatement.executeUpdate();

            // rows affected
            System.out.println(row); //1

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void InsertaOppBRM(Account accountObj) throws Exception {

        try (PreparedStatement preparedStatement = connDBBRM.conexion.prepareStatement(insertbrmsfaccount)) {

            preparedStatement.setString(1,opportObj.getAccountId() );
            preparedStatement.setString(2,opportObj.getAccountId__c() );
            preparedStatement.setString(3,opportObj.getAprobacion__c() );
            preparedStatement.setString(4,opportObj.getCanal_de_Venta__c() );
            preparedStatement.setString(5,opportObj.getCompania__c() );
            preparedStatement.setString(6,opportObj.getCurrencyIsoCode() );
            preparedStatement.setString(7,opportObj.getEstado_de_Contrato__c() );
            preparedStatement.setString(8,opportObj.getEstatus__c() );
            preparedStatement.setString(9,opportObj.getFecha_de_Facturacion__c() );
            preparedStatement.setString(10,opportObj.getForecastCategory() );
            preparedStatement.setString(11,opportObj.getForecastCategoryName() );
            preparedStatement.setString(12,opportObj.getForecast__c() );
            preparedStatement.setString(13,opportObj.getId() );
            preparedStatement.setString(14,opportObj.getIsClosed() );
            preparedStatement.setString(15,opportObj.getIsDeleted() );
            preparedStatement.setString(16,opportObj.getName() );
            preparedStatement.setString(17,opportObj.getNumero_de_Oportunidad__c() );
            preparedStatement.setDouble(18,opportObj.getNumero_de_Productos__c() );
            preparedStatement.setString(19,opportObj.getN_mero_de_Contrato__c() );
            preparedStatement.setString(20,opportObj.getOportunidad_a_la_que_se_relaciona__c() );
            preparedStatement.setString(21,opportObj.getOportunidad_Cerrada__c() );
            preparedStatement.setBoolean(22,opportObj.getOportunidad_con_Orden_de_Compra__c() );
            preparedStatement.setBoolean(23,opportObj.getOportunidad_en_Zona_Verde__c() );
            preparedStatement.setString(24,opportObj.getOportunidad_Madre__c() );
            preparedStatement.setBoolean(25,opportObj.getOportunidad_migrada__c() );
            preparedStatement.setString(26,opportObj.getOportunidad_tipo__c() );
            preparedStatement.setString(27,opportObj.getOp__c() );
            preparedStatement.setString(28,opportObj.getOrden_de_Compra__c() );
            preparedStatement.setString(29,opportObj.getOrden_de_Venta_SR__c() );
            preparedStatement.setString(30,opportObj.getOrden_de_Venta__c() );
            preparedStatement.setString(31,opportObj.getProbability() );
            preparedStatement.setString(32,opportObj.getSales_Center__c() );
            preparedStatement.setString(33,opportObj.getSegmento_BESTEL__c() );
            preparedStatement.setString(34,opportObj.getSegmento__c() );
            preparedStatement.setString(35,opportObj.getServicios_migrados__c() );
            preparedStatement.setString(36,opportObj.getSubsidiaria__c() );
            preparedStatement.setString(37,opportObj.getSubtipo_de_oportunidad__c() );
            preparedStatement.setString(38,opportObj.getTipo_de_oportunidad__c() );
            preparedStatement.setString(39,opportObj.getTipo_de_Orden__c() );
            preparedStatement.setString(40,opportObj.getTipo_de_Producto__c() );
            preparedStatement.setString(41,opportObj.getVertical__c() );
            preparedStatement.setString(46,seqbrmsf );
            int row = preparedStatement.executeUpdate();

            // rows affected
            System.out.println(row); //1

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


     
    public void printAccount() {
        System.out.println("Id: " + accountObj.getId());
        System.out.println("Account: " + accountObj.getName() + "\r\n"
                + "Clave de Cliente: " + accountObj.getClave_de_Cliente__c() + "\r\n"
                + "Oracle Id: " + accountObj.getOracle_ID__c() + "\r\n"
                + "Id Cliente SAP: " + accountObj.getID_Cliente_SAP__c() + "\r\n"
                + "Razón Social: " + accountObj.getRazon_Social__c() + "\r\n"
                + "Compañia: " + accountObj.getCompania__c() + "\r\n"
                + "Subsidiaria Id: " + accountObj.getSubsidiaria__c() + "\r\n"
                + "Numero de cuenta: " + accountObj.getNumero_de_Cuenta__c() + "\r\n"
                + "RFC: " + accountObj.getRFC__c() + "\r\n"
                + "Courrency Code: " + accountObj.getCurrencyIsoCode() + "\r\n"
                + "Account Status: " + accountObj.getAccountStatus__c() + "\r\n"
                + "Organización: " + accountObj.getOrganization__c());
    }
}
