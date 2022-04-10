/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import BASEDATO.EvenConexion;
//import Config_JSON.json_config;
import Evento.Mensaje.EvenMensajeJoptionpane;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Digno
 */
public class PosImprimir_Funcio_Recibo {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
//    private static json_config config = new json_config();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static String tk_idrecibo = "0";
    private static String tk_fecha = "0";
    private static String tk_descripcion = "0";
    private static String tk_funcionario = "0";
    private static String tk_monto_recibo = "0";
    private static String tk_monto_letra = "0";
    private static String tk_nombre_empresa = "PELUQUERIA";
    private static String tk_ruta_archivo = "ticket_recibo.txt";
    private static FileInputStream inputStream = null;
    private static String nombre_ticket = "RECIBO";

    private void cargar_datos_funcionario_recibo(Connection conn, int idfuncionario_recibo) {
        String titulo = "cargar_datos_funcionario_recibo";
        String sql = "select fr.idfuncionario_recibo as idfr,\n"
                + "(f.nombre||' '||f.apellido)  as funcionario,\n"
                + "fr.descripcion,\n"
                + "to_char(fr.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "fr.estado,fr.monto_recibo as monto,\n"
                + "to_char(fr.monto_recibo,'999G999G999') as monto_recibo,fr.monto_letra \n"
                + "from funcionario_recibo fr ,funcionario f \n"
                + "where fr.fk_idfuncionario=f.idfuncionario \n"
                + "and fr.idfuncionario_recibo=" + idfuncionario_recibo;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                tk_idrecibo = rs.getString("idfr");
                tk_fecha = rs.getString("fecha");
                tk_descripcion = rs.getString("descripcion");
                tk_funcionario = rs.getString("funcionario");
                tk_monto_recibo = rs.getString("monto_recibo");
                tk_monto_letra = rs.getString("monto_letra");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        mensaje_impresora = mensaje_impresora + "=======" + "" + nombre_ticket + "========" + saltolinea;
        mensaje_impresora = mensaje_impresora + "ID:" + tk_idrecibo + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA: " + tk_fecha + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "DESCRIP: " + tk_descripcion + saltolinea;
        mensaje_impresora = mensaje_impresora + "FUNCIO.: " + tk_funcionario + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL :" + tk_monto_recibo + saltolinea;
        mensaje_impresora = mensaje_impresora + "LETRA: " + tk_monto_letra + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = 48;
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = 13;
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, 1, totalColumna, "=============" + nombre_ticket + "=============");
        printer.printTextWrap(2 + tempfila, 2, 1, totalColumna, "ID:" + tk_idrecibo);
        printer.printTextWrap(2 + tempfila, 2, 20, totalColumna, "FECHA: " + tk_fecha);
        printer.printTextWrap(4 + tempfila, 4, 1, totalColumna, "==========================================");
        printer.printTextWrap(5 + tempfila, 5, 1, 200, "DESCRIP: " + tk_descripcion);
        printer.printTextWrap(6 + tempfila, 6, 1, 100, "FUNCIO.: " + tk_funcionario);
        printer.printTextWrap(7 + tempfila, 7, 1, totalColumna, "==========================================");
        printer.printTextWrap(8 + tempfila, 8, 25, totalColumna, "TOTAL :" + tk_monto_recibo);
        printer.printTextWrap(9 + tempfila, 9, 1, totalColumna, "LETRA: " + tk_monto_letra);
        printer.toFile(tk_ruta_archivo);
        try {
            inputStream = new FileInputStream(tk_ruta_archivo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }
        if (inputStream == null) {
            return;
        }

    }

    void crear_archivo_enviar_impresora() {
        String titulo = "crear_archivo_enviar_impresora";
        try {
            crear_archivo_texto_impresion();
            pos.setInputStream(inputStream);
            pos.imprimir_ticket_Pos();
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
    }

    private void crear_mensaje_textarea_y_confirmar() {
        JTextArea ta = new JTextArea(20, 30);
        ta.setText(cargar_datos_para_mensaje_textarea());
        System.out.println(cargar_datos_para_mensaje_textarea());
        Object[] opciones = {"IMPRIMIR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), nombre_ticket,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            crear_archivo_enviar_impresora();
        }
    }

    public void boton_imprimir_pos_funcionario_recibo(Connection conn, int idfuncionario_recibo) {
        cargar_datos_funcionario_recibo(conn, idfuncionario_recibo);
        crear_mensaje_textarea_y_confirmar();
    }
}
