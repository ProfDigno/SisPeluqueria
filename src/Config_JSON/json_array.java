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
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Digno
 */
public class json_array {

    private String ruta_json = "JSON\\json_array.json";
    EvenFecha evefec = new EvenFecha();
    private json_config_json jsoncf=new json_config_json();
    public boolean getBoo_cargar_jsom_array(String nombre_formulario, String nivel_formulario) {
        boolean abrir_formulario = false;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(ruta_json));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray frmArray = (JSONArray) jsonObject.get(nivel_formulario);
            Iterator<String> Frm_iterator = frmArray.iterator();
            boolean no_existe_formulario=true;
            while (Frm_iterator.hasNext()) {
                String formulario = String.valueOf(Frm_iterator.next());
                String[] parts = formulario.split("#");
                String nom_formulario = parts[0]; // 
                String Habilitar = parts[1]; // 
                if (nombre_formulario.equals(nom_formulario)) {
                    no_existe_formulario=false;
                    if (Habilitar.equals("si")) {
                        abrir_formulario = true;
                    }
                    if (Habilitar.equals("no")) {
                        abrir_formulario = false;
                    }
                    System.err.println("formulario: " + nom_formulario + " Habilitar:" + Habilitar);
                    break;
                }
            }
            if(no_existe_formulario){
                JOptionPane.showMessageDialog(null, "NO EXISTE ESTE FORMULARIO:\n"+ruta_json);
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString());
        } finally {

        }
        return abrir_formulario;
    }
public void abrir_config_formulario(){
        jsoncf.abrirArchivo(ruta_json);
    }
}
