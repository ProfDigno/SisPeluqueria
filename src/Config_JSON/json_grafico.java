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
public class json_grafico {

    private String ruta_json = "JSON\\json_grafico.json";
    private EvenFecha evefec = new EvenFecha();
    private static String top_producto_vendido;
    private json_config_json jsoncf=new json_config_json();

    public void cargar_jsom_grafico() {
        String titulo="cargar_jsom_grafico";
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(ruta_json));
            JSONObject jsonObject = (JSONObject) obj;
            String top_producto_vendido = (String) jsonObject.get("top_producto_vendido");
            setTop_producto_vendido((top_producto_vendido));
            
            System.out.println(titulo + jsonObject);
        } catch (Exception ex) {
            System.err.println(titulo+"\nError: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString(),titulo,JOptionPane.ERROR_MESSAGE);
        } finally {
            System.out.println(titulo + ruta_json);
        }
    }
    public void abrir_config_grafico(){
        jsoncf.abrirArchivo(ruta_json);
    }

    public static String getTop_producto_vendido() {
        return top_producto_vendido;
    }

    public static void setTop_producto_vendido(String top_producto_vendido) {
        json_grafico.top_producto_vendido = top_producto_vendido;
    }

    
    
}
