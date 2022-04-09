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
public class json_producto {

    private String ruta_json = "JSON\\json_producto.json";
    private EvenFecha evefec = new EvenFecha();
    private static int min_por_comision;
    private static int max_por_comision;
    private static String order_select_prod;
    private static String defauld_stock_minimo;
    private static String defauld_stock_maximo;
    private static String defauld_porcentaje_comision;
    private json_config_json jsoncf=new json_config_json();

    public void cargar_jsom_producto() {
        String titulo="cargar_jsom_producto";
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(ruta_json));
            JSONObject jsonObject = (JSONObject) obj;
            String min_por_comision = (String) jsonObject.get("min_por_comision");
            String max_por_comision = (String) jsonObject.get("max_por_comision");
            String order_select_prod = (String) jsonObject.get("order_select_prod");
            String defauld_stock_minimo = (String) jsonObject.get("defauld_stock_minimo");
            String defauld_stock_maximo = (String) jsonObject.get("defauld_stock_maximo");
            String defauld_porcentaje_comision = (String) jsonObject.get("defauld_porcentaje_comision");
            setMin_por_comision(Integer.parseInt(min_por_comision));
            setMax_por_comision(Integer.parseInt(max_por_comision));
            setDefauld_Stock_minimo(defauld_stock_minimo);
            setDefauld_Stock_maximo(defauld_stock_maximo);
            setDefauld_Porcentaje_comision(defauld_porcentaje_comision);
            setOrder_select_prod(order_select_prod);
            System.out.println(titulo + jsonObject);
        } catch (Exception ex) {
            System.err.println(titulo+"\nError: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString(),titulo,JOptionPane.ERROR_MESSAGE);
        } finally {
            System.out.println(titulo + ruta_json);
        }
    }
    public void abrir_config_producto(){
        jsoncf.abrirArchivo(ruta_json);
    }


    public static int getMin_por_comision() {
        return min_por_comision;
    }

    public static void setMin_por_comision(int min_por_comision) {
        json_producto.min_por_comision = min_por_comision;
    }

    public static int getMax_por_comision() {
        return max_por_comision;
    }

    public static void setMax_por_comision(int max_por_comision) {
        json_producto.max_por_comision = max_por_comision;
    }

    public static String getOrder_select_prod() {
        return order_select_prod;
    }

    public static void setOrder_select_prod(String order_select_prod) {
        json_producto.order_select_prod = order_select_prod;
    }

    public static String getDefauld_Stock_minimo() {
        return defauld_stock_minimo;
    }

    public static void setDefauld_Stock_minimo(String defauld_stock_minimo) {
        json_producto.defauld_stock_minimo = defauld_stock_minimo;
    }

    public static String getDefauld_Stock_maximo() {
        return defauld_stock_maximo;
    }

    public static void setDefauld_Stock_maximo(String defauld_stock_maximo) {
        json_producto.defauld_stock_maximo = defauld_stock_maximo;
    }

    public static String getDefauld_Porcentaje_comision() {
        return defauld_porcentaje_comision;
    }

    public static void setDefauld_Porcentaje_comision(String defauld_porcentaje_comision) {
        json_producto.defauld_porcentaje_comision = defauld_porcentaje_comision;
    }
    
}
