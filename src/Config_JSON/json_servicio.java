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
public class json_servicio {

    private String ruta_json = "JSON\\json_servicio.json";
    private EvenFecha evefec = new EvenFecha();
    private static int min_por_comision;
    private static int max_por_comision;
    private static String order_select_serv;
    private static String defauld_descripcion;
    private static String defauld_porcentaje;
    private static String defauld_hora;
    private static String defauld_minuto;
    private json_config_json jsoncf=new json_config_json();

    public void cargar_jsom_servicio() {
        String titulo="cargar_jsom_servicio";
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(ruta_json));
            JSONObject jsonObject = (JSONObject) obj;
            String min_por_comision = (String) jsonObject.get("min_por_comision");
            String max_por_comision = (String) jsonObject.get("max_por_comision");
            String order_select_serv = (String) jsonObject.get("order_select_prod");
            String defauld_descripcion = (String) jsonObject.get("defauld_descripcion");
            String defauld_porcentaje = (String) jsonObject.get("defauld_porcentaje");
            String defauld_hora = (String) jsonObject.get("defauld_hora");
            String defauld_minuto = (String) jsonObject.get("defauld_minuto");
            setMin_por_comision(Integer.parseInt(min_por_comision));
            setMax_por_comision(Integer.parseInt(max_por_comision));
            setOrder_select_serv(order_select_serv);
            setDefauld_descripcion(defauld_descripcion);
            setDefauld_porcentaje(defauld_porcentaje);
            setDefauld_hora(defauld_hora);
            setDefauld_minuto(defauld_minuto);
            System.out.println(titulo + jsonObject);
        } catch (Exception ex) {
            System.err.println(titulo+"\nError: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString(),titulo,JOptionPane.ERROR_MESSAGE);
        } finally {
            System.out.println(titulo + ruta_json);
        }
    }
    public void abrir_config_servicio(){
        jsoncf.abrirArchivo(ruta_json);
    }


    public static int getMin_por_comision() {
        return min_por_comision;
    }

    public static void setMin_por_comision(int min_por_comision) {
        json_servicio.min_por_comision = min_por_comision;
    }

    public static int getMax_por_comision() {
        return max_por_comision;
    }

    public static void setMax_por_comision(int max_por_comision) {
        json_servicio.max_por_comision = max_por_comision;
    }

    public static String getOrder_select_serv() {
        return order_select_serv;
    }

    public static void setOrder_select_serv(String order_select_serv) {
        json_servicio.order_select_serv = order_select_serv;
    }

    public static String getDefauld_descripcion() {
        return defauld_descripcion;
    }

    public static void setDefauld_descripcion(String defauld_descripcion) {
        json_servicio.defauld_descripcion = defauld_descripcion;
    }

    public static String getDefauld_porcentaje() {
        return defauld_porcentaje;
    }

    public static void setDefauld_porcentaje(String defauld_porcentaje) {
        json_servicio.defauld_porcentaje = defauld_porcentaje;
    }

    public static String getDefauld_hora() {
        return defauld_hora;
    }

    public static void setDefauld_hora(String defauld_hora) {
        json_servicio.defauld_hora = defauld_hora;
    }

    public static String getDefauld_minuto() {
        return defauld_minuto;
    }

    public static void setDefauld_minuto(String defauld_minuto) {
        json_servicio.defauld_minuto = defauld_minuto;
    }


    
}
