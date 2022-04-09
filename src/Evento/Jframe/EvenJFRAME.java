/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Jframe;

import Config_JSON.json_array;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.VISTA.FrmMenuPelu;
//import FORMULARIO.VISTA.FrmMenuProba;
import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class EvenJFRAME {
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private json_array jsarr=new json_array();
    public void abrir_TablaJinternal(JInternalFrame formu) {
        String nivel_acceso="FRM_ADMIN";
        String nombre=formu.getClass().getSimpleName();
        System.out.println("MI FORMULARIO:"+nombre);
        if(jsarr.getBoo_cargar_jsom_array(nombre,nivel_acceso)){
            FrmMenuPelu.escritorio.add(formu);
            formu.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null,"NO TIENE PERMISO PARA ABRIR EL FORMULARIO: \n"+nombre+"\nNIVEL DE ACCESO:\n"+nivel_acceso);
        }
//        jsarr.getBoo_cargar_jsom_array_evento(nombre, nivel_acceso);
    }

    public  void centrar_formulario(javax.swing.JInternalFrame frame) {
        Dimension desktopSize = FrmMenuPelu.escritorio.getSize();
        Dimension jInternalFrameSize = frame.getSize();
        frame.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
        System.out.println("CENTRAR Jinternal: "+frame.getTitle());
    }
    public void maximizar_jinternal(JInternalFrame formu){
        try {
            formu.setMaximum(true);
        } catch (Exception e) {
            evemen.mensaje_error(e, "error","maximizar_jinternal");
        }
    }
    public  void centrar_formulario_internalframa(javax.swing.JInternalFrame frame) {
        Dimension desktopSize = FrmMenuPelu.escritorio.getSize();
        Dimension jInternalFrameSize = frame.getSize();
        frame.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
        System.out.println("CENTRAR Jinternal: "+frame.getTitle());
    }
}
