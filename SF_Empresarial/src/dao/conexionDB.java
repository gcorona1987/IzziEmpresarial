package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author Felipe Gutierrez
 */
//extends Ejecuta_Todo
public class conexionDB {

    public Connection conexion;  
    public Boolean validar = true;

    //private final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public conexionDB(String ODBC, String UsuarioDB, String pwdDB) throws FileNotFoundException, IOException, SQLException {

        try {
            conexion = DriverManager.getConnection(ODBC, UsuarioDB, pwdDB);
            DriverManager.registerDriver(new OracleDriver());
            System.out.println("Conectado a DB: ");
            validar = false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void CloseDB() throws SQLException {

        if (conexion != null) {
            if (!conexion.isClosed()) {
                conexion.close();
                System.out.println("DB Ambiente Base: Cerrado");
            }
        }

    }

    public void getHora() {
        try {
            Calendar now = Calendar.getInstance();

            System.out.println("Current full date time is : " + (now.get(Calendar.MONTH) + 1) + "-"
                    + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR) + " "
                    + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":"
                    + now.get(Calendar.SECOND) + "." + now.get(Calendar.MILLISECOND));
        } catch (Exception e) {
            throw e;
        }

        try {
            Calendar now = Calendar.getInstance();

            String hora = ("" + (now.get(Calendar.MONTH) + 1) + "-"
                    + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR) + " "
                    + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":"
                    + now.get(Calendar.SECOND) + "." + now.get(Calendar.MILLISECOND));
        } catch (Exception e) {
            throw e;
        }
    }

}
