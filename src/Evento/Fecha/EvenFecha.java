/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Fecha;

import BASEDATO.EvenConexion;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class EvenFecha {

    /**
     * @return the formato_fecha
     */
    public String getFormato_fecha() {
        return formato_fecha;
    }

    private String formato_fecha = "yyyy-MM-dd";
    String fecha_dia1 = "yyyy-MM-01";
    String formato_fechaHora = "yyyy-MM-dd HH:mm";
    private String formato_hora = "HH:mm:ss";
    String formato_fechaHoraZona = "yyyy-MM-dd HH:mm:ss.00";
    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();

    public String getString_validar_fecha(String fechaStr) {
        String Sfecha = "";
        try {
            SimpleDateFormat formato = new SimpleDateFormat(getFormato_fecha());
            java.util.Date fechaDate = formato.parse(fechaStr);
            Sfecha = String.valueOf(formato.format(fechaDate));
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            Sfecha = getString_formato_fecha();
        }
        return Sfecha;
    }

    public String getString_validar_fecha_hora(String fechaStr) {
        String Sfecha = "";
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHora);
            java.util.Date fechaDate = formato.parse(fechaStr);
            Sfecha = String.valueOf(formato.format(fechaDate));
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA HORA:MINUTO\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            Sfecha = getString_formato_fecha_hora();
        }
        return Sfecha;
    }

    public java.sql.Date getDateSQL_fecha_cargado_sinformat(String fechaStr) {
        java.sql.Date dateSql = null;
        try {
//            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHora);
//            java.sql.Date fromdate1=(java.sql.Date) formato.parse(fechaStr);
//            dateSql=fromdate1;
            dateSql = java.sql.Date.valueOf(fechaStr);
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n "
                    + "FORMATO: AñO-MES-DIA HORA:MINUTO\n"
                    + "FECHA INGRESADO:" + fechaStr + "\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR:getDateSQL_fecha_cargado_sinformat", JOptionPane.ERROR_MESSAGE);
            dateSql = java.sql.Date.valueOf(getString_formato_fecha());
        }
        return dateSql;
    }

    public java.sql.Date getDateSQL_sistema() {
        java.sql.Date fechaDate;
        try {
            fechaDate = new java.sql.Date(new java.util.Date().getTime());
            return fechaDate;
        } catch (Exception e) {
            evemen.mensaje_error(e, "getDateSQL_sistema");
            return null;
        }
    }

    public java.sql.Timestamp getTimestamp_fecha_cargado(String fechaStr) {
        java.sql.Timestamp dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHora);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Timestamp(dateUtil.getTime());
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA HORA:MINUTO\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR:getTimestamp_fecha_cargado", JOptionPane.ERROR_MESSAGE);
            dateSql = getTimestamp_sistema();
        }
        return dateSql;
    }

    /**
     * <H2>Formato fecha<H2/>
     *
     * @return dd-MM-yyyy
     */
    public String getString_formato_fecha() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(getFormato_fecha());
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_hora() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_hora);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public int getInt_segundos_ahora() {
        java.util.Date utilDate = new java.util.Date(); //fecha actual
        SimpleDateFormat sdf_hora = new SimpleDateFormat("HH");
        int hora = Integer.parseInt(sdf_hora.format(utilDate));
        SimpleDateFormat sdf_min = new SimpleDateFormat("mm");
        int min = Integer.parseInt(sdf_min.format(utilDate));
        SimpleDateFormat sdf_seg = new SimpleDateFormat("ss");
        int seg = Integer.parseInt(sdf_seg.format(utilDate));
        int resul = ((hora * 3600) + (min * 60) + seg);
        return resul;
    }

    public int getInt_diferencia_en_segundo(int tiempo_sql) {
        int resul = getInt_segundos_ahora() - tiempo_sql;
        return resul;
    }

    public String getString_convertir_segundo_hora(int num) {
        int hor, min, seg;
        hor = num / 3600;
        min = (num - (3600 * hor)) / 60;
        seg = num - ((hor * 3600) + (min * 60));
        String horaformada = hor + "h " + min + "m " + seg + "s";
        System.out.println(horaformada);
        return horaformada;
    }

    public String getString_fecha_dia1() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(fecha_dia1);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_fecha_hora() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_fechaHora);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_fecha_hora_backup() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM_dd");
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public java.util.Date getDate_sistema() {
        java.util.Date fechaDate;
        try {
            fechaDate = new java.util.Date();
            return fechaDate;
        } catch (Exception e) {
            evemen.mensaje_error(e, "getDate_sistema");
            return null;
        }
    }

    public String getSumarFecha(int tipofecha, int addtiempo) {
        String Sfecha = "";
        java.util.Date dateSistema = new java.util.Date();
        java.util.Date dateSumado;
        java.util.Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(getFormato_fecha());
            calendar.setTime(dateSistema);
            calendar.add(tipofecha, addtiempo);
            dateSumado = calendar.getTime();
            Sfecha = String.valueOf(formato.format(dateSumado));
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: AñO-MES-DIA\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error sumarDiasAFecha:" + e);
            Sfecha = getString_formato_fecha();
        }
        return Sfecha;
    }

    public Timestamp getTimestamp_sistema() {
        long timeNow = Calendar.getInstance().getTimeInMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
        return ts;
    }

    public boolean getValidar_fec_menos_de_hoy(Connection conn, String fecha) {
        boolean esMenos = false;
        int dia = 0;
        String sql = "select (date('" + fecha + "')-(date(now()))) as dias;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, "dias_para_hoy");
            if (rs.next()) {
                dia = (rs.getInt(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "El formato de fecha no es adecuado\n" + ex);
        }
        if (dia <= 0) {
            JOptionPane.showMessageDialog(null, "LA FECHA NO DEBE SER MENOR A HOY");
            esMenos = false;
        } else {
            esMenos = true;
        }
        return esMenos;
    }

    public String getString_formato_fecha_hora_zona() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_fechaHoraZona);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public void cargar_combobox_intervalo_fecha(JComboBox combo) {
        String fechas[] = {"HOY", "AYER",
            "ESTA SEMANA", "SEMANA  ANTERIOR",
            "ESTE MES", "MES ANTERIOR",
            "PRIMER TRIMESTRE", "SEGUNDO TRIMESTRE", "TERCER TRIMESTRE", "CUARTO TRIMESTRE", "TODO EL AÑO"};
        for (int i = 0; i < fechas.length; i++) {
            String fecha = fechas[i];
            combo.addItem(fecha);
        }
        combo.setSelectedIndex(4);
    }

    public String getIntervalo_fecha_combobox(JComboBox combo, String campofecha) {
        //date_part('year',(current_date - interval '1 year')) un ano menos

        String fecha = "";
        if (combo.getSelectedIndex() == 0) {//HOY
            fecha = "\n and date(" + campofecha + ")=date(current_date) ";
        }
        if (combo.getSelectedIndex() == 1) {//AYER
            fecha = "\n and date(" + campofecha + ")=date(current_date-1) ";
        }
        if (combo.getSelectedIndex() == 2) {//ESTA SEMANA
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('week'," + campofecha + ")=date_part('week',current_date) ";
        }
        if (combo.getSelectedIndex() == 3) {//SEMANA  ANTERIOR
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('week'," + campofecha + ")=date_part('week',(current_date - interval '1 week')) ";
        }
        if (combo.getSelectedIndex() == 4) {//ESTE MES
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('month'," + campofecha + ")=date_part('month',current_date) ";
        }
        if (combo.getSelectedIndex() == 5) {//MES ANTERIOR
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('month'," + campofecha + ")=date_part('month',(current_date - interval '1 month')) ";
        }
        if (combo.getSelectedIndex() == 6) {//PRIMER TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=1 ";
        }
        if (combo.getSelectedIndex() == 7) {//SEGUNDO TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=2 ";
        }
        if (combo.getSelectedIndex() == 8) {//TERCER TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=3 ";
        }
        if (combo.getSelectedIndex() == 9) {//CUARTO TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=4 ";
        }
        if (combo.getSelectedIndex() == 10) {//todo el año
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)";
        }
        return fecha;
    }

    public void cargar_combobox_mes(JComboBox combo) {
        String fechas[] = {"TODOS", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
        for (int i = 0; i < fechas.length; i++) {
            String fecha = fechas[i];
            combo.addItem(fecha);
        }
        combo.setSelectedIndex(esteMes());
    }

    public String getmes_combobox(JComboBox combo, String campofecha) {
        //date_part('year',(current_date - interval '1 year')) un ano menos

        String fecha = "";
        if (combo.getSelectedIndex() > 0) {//HOY
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('month'," + campofecha + ")=" + combo.getSelectedIndex();
        }
        return fecha;
    }

    int esteMes() {
        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month+1;
    }
}
