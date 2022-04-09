/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config_JSON;

import Evento.Fecha.EvenFecha;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Digno
 */
public class json_sql_actualizar {

    String ruta_json_sql = "JSON\\json_sql_actualizar.json";
    EvenFecha evefec = new EvenFecha();
    private static String ult_sql;
    private static String sql_ahora;

    public void cargar_jsom_sql_actualizar() {
        JSONParser parser = new JSONParser();
        setUlt_sql("");
        setSql_ahora("");
        try {
            Object obj = parser.parse(new FileReader(ruta_json_sql));
            JSONObject jsonObject = (JSONObject) obj;
            String ejecutar_sql = (String) jsonObject.get("ejecutar_sql");
            String ult_sql = " ";
            String sql_ahora = " ";
            if (ejecutar_sql.equals("SI")) {
                ult_sql = (String) jsonObject.get("sql" + evefec.getString_formato_fecha_hora_backup());
            }
            sql_ahora = (String) jsonObject.get("sql_ahora");
            setUlt_sql(ult_sql);
            setSql_ahora(sql_ahora);
            System.out.println("json json_sql_actualizar:" + jsonObject);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString());
        } finally {

        }
    }

    public static String getUlt_sql() {
        return ult_sql;
    }

    public static void setUlt_sql(String ult_sql) {
        json_sql_actualizar.ult_sql = ult_sql;
    }

    public static String getSql_ahora() {
        return sql_ahora;
    }

    public static void setSql_ahora(String sql_ahora) {
        json_sql_actualizar.sql_ahora = sql_ahora;
    }
    private void abrirArchivo(String ruta) {
        try {
            File file = new File(ruta);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
