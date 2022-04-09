/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import BASEDATO.EvenConexion;
//import Config_JSON.json_config;
import Config_JSON.json_imprimir_pos;
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
public class PosImprimir_caja_cierre {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
//    private static json_config config = new json_config();
    private static json_imprimir_pos jsprint = new json_imprimir_pos();
    ClaImpresoraPos pos = new ClaImpresoraPos();
//    private static String tk_idvale = "500";
//    private static String tk_fecha_emision = "08-03-2019";
//    private static String tk_descripcion = "la descripcion";
//    private static String tk_cliente = "el tipo de vale";
//    private static String tk_monto = "15.000";
    private static String tk_usuario = "digno";
    private static String tk_nombre_empresa = "";
    private static String tk_ruta_archivo = "ticket_caja_cierre.txt";
    private static FileInputStream inputStream = null;
    private static String nombre_ticket = "CIERRE-CAJA";
    private static String tk_idcaja_cierre = "000";
    private static String tk_inicio = "inicio";
    private static String tk_fin = "fin";
    private static String tk_in_Abrir = "0";
    private static String tk_in_venta = "0";
    private static String tk_eg_compra = "0";
    private static String tk_eg_gasto = "0";
    private static String tk_eg_vale = "0";
    private static String tk_sistema = "0";
    private static String tk_cierre = "0";
    private static String tk_diferencia = "0";

    private void cargar_datos_caja_cierre(Connection conn, int idcaja_cierre) {
        String titulo = "cargar_datos_caja_cierre";
        String sql = "select cc.idcaja_cierre,\n"
                + "to_char(cc.fecha_inicio ,'yyyy-MM-dd HH24:MI') as inicio,\n"
                + "to_char(cc.fecha_fin ,'yyyy-MM-dd HH24:MI') as fin,\n"
                + "TRIM(to_char(sum(cd.in_monto_apertura),'999G999G999')) as in_Abrir,\n"
                + "TRIM(to_char(sum(cd.in_monto_venta),'999G999G999')) as in_venta,\n"
                + "TRIM(to_char(sum(cd.eg_monto_compra),'999G999G999')) as eg_compra,\n"
                + "TRIM(to_char(sum(cd.eg_monto_gasto),'999G999G999')) as eg_gasto,\n"
                + "TRIM(to_char(sum(cd.eg_monto_recibo_funcionario),'999G999G999')) as eg_vale,\n"
                + "TRIM(to_char((sum(cd.in_monto_apertura +cd.in_monto_venta))-\n"
                + "(sum(cd.eg_monto_compra+cd.eg_monto_gasto+cd.eg_monto_recibo_funcionario)),'999G999G999')) as sistema,\n"
                + "TRIM(to_char(sum(cd.monto_cierre),'999G999G999')) as cierre,\n"
                + "TRIM(to_char((sum(cd.monto_cierre)-((sum(cd.in_monto_apertura +cd.in_monto_venta))-\n"
                + "(sum(cd.eg_monto_compra +cd.eg_monto_gasto +cd.eg_monto_recibo_funcionario)))),'999G999G999')) as diferencia\n"
                + "from caja_cierre cc,caja_cierre_item icc,caja_detalle cd\n"
                + "where cc.idcaja_cierre=icc.fk_idcaja_cierre\n"
                + "and cd.idcaja_detalle=icc.fk_idcaja_detalle\n"
                + "and cc.idcaja_cierre="+idcaja_cierre
                + " group by 1,2,3;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                tk_idcaja_cierre = rs.getString("idcaja_cierre");
                tk_inicio = rs.getString("inicio");
                tk_fin = rs.getString("fin");
                tk_in_Abrir = rs.getString("in_Abrir");
                tk_in_venta = rs.getString("in_venta");
                tk_eg_compra = rs.getString("eg_compra");
                tk_eg_gasto = rs.getString("eg_gasto");
                tk_eg_vale = rs.getString("eg_vale");
                tk_sistema = rs.getString("sistema");
                tk_cierre = rs.getString("cierre");
                tk_diferencia = rs.getString("diferencia");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "------->> ";
        mensaje_impresora = mensaje_impresora + "=======" + "" + nombre_ticket + "========" + saltolinea;
        mensaje_impresora = mensaje_impresora + "CODIGO:" + tk_idcaja_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + "INICIO: " + tk_inicio + saltolinea;
        mensaje_impresora = mensaje_impresora + "FIN: " + tk_fin + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + " CAJA ABRIR: " + tabular + tk_in_Abrir + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL VENTA: " + tabular + tk_in_venta + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + " TOTAL GASTO: " + tabular + tk_eg_gasto + saltolinea;
        mensaje_impresora = mensaje_impresora + "  TOTAL VALE: " + tabular + tk_eg_vale + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL COMPRA: " + tabular + tk_eg_compra + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "SAL.SISTEMA: " + tabular + tk_sistema + saltolinea;
        mensaje_impresora = mensaje_impresora + "     CIERRE: " + tabular + tk_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + " DIFERENCIA: " + tabular + tk_diferencia + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_cc();
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + nombre_ticket+ jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, 10, totalColumna, "CODIGO:" + tk_idcaja_cierre);
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, "INICIO:");
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_fecha(), totalColumna, tk_inicio);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "FIN:");
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_fecha(), totalColumna, tk_fin);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, "CAJA ABRIR: ");
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_numero(), totalColumna, tk_in_Abrir);
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, "TOTAL VENTA: ");
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_numero(), totalColumna, tk_in_venta);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_inicio(), totalColumna, "TOTAL GASTO:");
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_numero(), totalColumna, tk_eg_gasto);
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, "TOTAL VALE:");
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_numero(), totalColumna, tk_eg_vale);
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, "TOTAL COMPRA:");
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_numero(), totalColumna, tk_eg_compra);
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_inicio(), totalColumna, "SISTEMA:");
        printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_numero(), totalColumna, tk_sistema);
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_inicio(), totalColumna, "CIERRE:");
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_numero(), totalColumna, tk_cierre);
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_inicio(), totalColumna, "DIFERENCIA:");
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_numero(), totalColumna, "(" + tk_diferencia + ")");
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

    public void boton_imprimir_pos_caja_cierre(Connection conn, int idcaja_cierre) {
        cargar_datos_caja_cierre(conn, idcaja_cierre);
        crear_mensaje_textarea_y_confirmar();
    }
}
