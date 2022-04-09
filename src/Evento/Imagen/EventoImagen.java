/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Imagen;

import Evento.Mensaje.EvenMensajeJoptionpane;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Digno
 */
public class EventoImagen {

    /**
     * @return the ultimaRutaScanner
     */
    public static String getUltimaRutaScanner() {
        return ultimaRutaScanner;
    }

    /**
     * @param aUltimaRutaScanner the ultimaRutaScanner to set
     */
    public static void setUltimaRutaScanner(String aUltimaRutaScanner) {
        ultimaRutaScanner = aUltimaRutaScanner;
    }

    /**
     * @return the hab_ultima_carpeta
     */
    public static boolean isHab_ultima_carpeta() {
        return hab_ultima_carpeta;
    }

    /**
     * @param aHab_ultima_carpeta the hab_ultima_carpeta to set
     */
    public static void setHab_ultima_carpeta(boolean aHab_ultima_carpeta) {
        hab_ultima_carpeta = aHab_ultima_carpeta;
    }
    private static String ultimaRutaScanner = "C:";
    private static boolean hab_ultima_carpeta = false;
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    public void boton_abrir_imagen(String formato_archivo, JTextField txtnombreArchivo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo de Imagen", formato_archivo));
        if (isHab_ultima_carpeta()) {
            fileChooser.setCurrentDirectory(new File(getUltimaRutaScanner()));
        } else {
            setHab_ultima_carpeta(true);
        }
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            //obtiene ruta y nombre del archivo
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            txtnombreArchivo.setText(ruta);
            try {
                URL mediaURL = fileChooser.getSelectedFile().toURL();
                String url = String.valueOf(mediaURL);
                String suburl = url.substring(6, url.length());
                setUltimaRutaScanner(fileChooser.getCurrentDirectory().toString());
            } catch (MalformedURLException ex) {
                evemen.mensaje_error(ex, ruta);
//                fm.mensaje_error_exce(ex, "ultima ruta guardado");
            }
        }
    }
}
