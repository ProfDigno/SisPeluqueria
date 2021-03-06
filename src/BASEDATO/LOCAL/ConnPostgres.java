/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BASEDATO.LOCAL;

//import ClaseUTIL.FunMensaje;
//import ClaseUTIL.SQLejecucion;
//import ClaseUTIL.SQLprepar;
import BASEDATO.EvenConexion;
import Evento.Fecha.EvenFecha;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.postgresql.Driver;

/**
 * Esta clase se conecta directamente y exclusivamente con postgres pero depende
 * de los datos proporcionado por la base de dato SQLite por eso es importante
 * que los datos de conexion en el SQLite esteen corectos
 *
 * @author Pc
 */
public class ConnPostgres {

    private static Connection connPostgres = null;
    public static String PsDriver;
    public static String PsConexion;
    public static String PsLocalhost;
    public static String PsPort;
    public static String PsNomBD;
    public static String PsUsuario;
    public static String PsContrasena;
    VariablesBD var = new VariablesBD();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    EvenConexion evconn = new EvenConexion();
    EvenFecha evefec = new EvenFecha();
    String ruta_json_conexion_leer = "JSON\\json_conexion.json";
//    String ruta_json_conexion_escribir = "src\\json_conexion_escribir.json";

    void cargar_jsom_conexion() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(ruta_json_conexion_leer));
            JSONObject jsonObject = (JSONObject) obj;
            String localhost = (String) jsonObject.get("localhost");
            String port = (String) jsonObject.get("port");
            String basedato = (String) jsonObject.get("basedato");
            String usuario = (String) jsonObject.get("usuario");
            String password = (String) jsonObject.get("password");
            String direc_dump = (String) jsonObject.get("direc_dump");
            String direc_backup = (String) jsonObject.get("direc_backup");
            String nombre_backup = (String) jsonObject.get("nombre_backup");
            String crear_backup = (String) jsonObject.get("crear_backup");
            var.setPsLocalhost(localhost);
            var.setPsPort(port);
            var.setPsNomBD(basedato);
            var.setPsUsuario(usuario);
            var.setPsContrasena(password);
            var.setPsdirec_dump(direc_dump);
            var.setPsdirec_backup(direc_backup);
            var.setPsnombre_backup(nombre_backup);
            var.setPsCrea_backup(crear_backup);
//            jsonObject.put("fecha_hoy", evefec.getFormato_fecha());
            System.out.println("Json Conexion:" + jsonObject);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString());
        } finally {

        }
    }


    void cargarVariables() {
        PsDriver = "org.postgresql.Driver";
        PsConexion = "jdbc:postgresql";
        PsLocalhost = var.getPsLocalhost();
        PsPort = var.getPsPort();
        PsNomBD = var.getPsNomBD();
        PsUsuario = var.getPsUsuario();
        PsContrasena = var.getPsContrasena();
        System.out.println("++++++++++++++++Carga de variable de JSON para la conexion postgres="
                + "\n" + PsDriver + "\n" + PsUsuario + "\n" + PsContrasena);
    }

    public String getDatos_conexion() {
        String dato = "";
        dato = "Puerto:" + var.getPsPort() + "/ Basedato:" + var.getPsNomBD() + " ";
        return dato;
    }

    public void ConnectDBpostgres(boolean msj) {
        cargar_jsom_conexion();
        cargarVariables();
        try {
            String connectString = "" + PsConexion + "://" + PsLocalhost + ":" + PsPort + "/" + PsNomBD + "";
            Class.forName(PsDriver);
            Connection connLocal = DriverManager.getConnection(connectString, PsUsuario, PsContrasena);
            if (connLocal != null) {
                System.out.println("++++++++++++++++Conection a posgrest suceso" + "\n" + PsDriver + "\n" + connectString + "\n" + PsUsuario + "\n" + PsContrasena);
                if (msj) {
                    JOptionPane.showMessageDialog(null, "++Conection a posgrest suceso++" + "\n" + PsDriver + "\n" + connectString + "\n" + PsUsuario);
                }
//                crear_jsom_escribir();
            }
            setConnPostgres(connLocal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error en la conexion con base de datos"
                    + "\nLocal Host: " + PsLocalhost
                    + "\nPuerto: " + PsPort
                    + "\nUsuario: " + PsUsuario
                    + "\nError: " + e.getMessage(), "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null, "EL SISTEMA SE VA CERRAR POR FALLA EN LA CONEXION", "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void cerrar_conexion() {
        try {
            getConnPosgres().close();
            System.out.println("CONEXION CERRADA");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CERRAR\n" + e);
        }
    }

    public static Connection getConnPosgres() {
        System.out.println("CONECTADO " + PsNomBD);
        return connPostgres;

    }

    public static void setConnPostgres(Connection aConnPostgres) {
        connPostgres = aConnPostgres;
    }

}
