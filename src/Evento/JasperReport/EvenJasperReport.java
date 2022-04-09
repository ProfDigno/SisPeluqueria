/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.JasperReport;

import Evento.Mensaje.EvenMensajeJoptionpane;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvMetadataExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Digno
 */
public class EvenJasperReport {

    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    public void imprimir_jasper_o_pdf(Connection conexion, String sql, String titulonota, String direccion, String rutatemp){
        String mensaje="SELECCIONAR EL FORMATO DE IMPRECION";
        String titulo="IMPRIMIR";
        String btnjasper="-JASPER-";
        String btnpdf="-PDF-";
        String btnword="-WORD-";
        String btncancelar="CANCELAR";
        Object[] opciones = {btnjasper,btnpdf,btnword,btncancelar};
        int eleccion = JOptionPane.showOptionDialog(null, mensaje, titulo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, btnjasper);
        if (eleccion == 0) {
            imprimirjasper(conexion, sql, titulonota, direccion);
        }
        if (eleccion == 1) {
            imprimirPDF(conexion, sql, direccion, rutatemp);
        }
        if (eleccion == 2) {
            imprimirWord(conexion, sql, direccion, rutatemp);
        }
    }
    public void imprimirjasper(Connection conexion, String sql, String titulonota, String direccion) {
        String titulo = "imprimirjasper";
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
            jviewer.setTitle(titulonota);
            jviewer.setVisible(true);
            evemen.Imprimir_serial_sql(sql + "\n", titulo);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }

    }
    public void imprimirPDF(Connection conexion, String sql, String direccion, String rutatemp) {
        String titulo = "imprimirPDF";
        String carpeta="src/REPORTE_PDF/";
        String formato=".pdf";
        String ruta=carpeta+rutatemp+formato;
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            JasperExportManager.exportReportToPdfFile(jasperPrint,ruta);// exportacion PDF
            abrirArchivo(ruta);
            evemen.Imprimir_serial_sql(sql + "\n", titulo);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }

    }
//    /**
//     * Imprimir en word tener en cuenta la ruta si esta creada
//     *
//     * @param conexion
//     */
    public void imprimirWord(Connection conexion, String sql, String direccion, String rutatemp) {
        String titulo = "imprimirWord";
        String carpeta="src/REPORTE_WORD/";
        String formato=".docx";
        String ruta=carpeta+rutatemp+formato;
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            JRExporter exp = new JRDocxExporter();
            exp.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exp.setParameter(JRExporterParameter.OUTPUT_FILE, new File(ruta));
            exp.exportReport();
            abrirArchivo(ruta);
            evemen.Imprimir_serial_sql(sql + "\n", titulo);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }

    }

//    public void imprimirExcel(Connection conexion) {
//        String rutatemp = "" + rutaWord + "\\expExcel.xlsx ";
//        try {
//            JasperDesign jasperDesign = JRXmlLoader.load(rv.getDireccion());
//            JRDesignQuery newQuery = new JRDesignQuery();
//            newQuery.setText(rv.getSql());
//            jasperDesign.setQuery(newQuery);
//            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
//            JRExporter exp = new JRXlsxExporter();
//            exp.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exp.setParameter(JRExporterParameter.OUTPUT_FILE, new File(rutatemp));
//            exp.exportReport();
//            abrirArchivo(rutatemp);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//
//    }

//    public void imprimirText(Connection conexion) {
//        String rutatemp = "" + rutaWord + "\\expTEXT.XML ";
//        try {
//            JasperDesign jasperDesign = JRXmlLoader.load(rv.getDireccion());
//            JRDesignQuery newQuery = new JRDesignQuery();
//            newQuery.setText(rv.getSql());
//            jasperDesign.setQuery(newQuery);
//            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
//            JRExporter exp = new JRXmlExporter();
//            exp.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exp.setParameter(JRExporterParameter.OUTPUT_FILE, new File(rutatemp));
//            exp.exportReport();
//            abrirArchivo(rutatemp);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//
//    }

   

//    public void imprimirHTML(Connection conexion) {
//        String rutatemp = "" + rutaWord + "\\expHTML.html";
//        try {
//            JasperDesign jasperDesign = JRXmlLoader.load(rv.getDireccion());
//            JRDesignQuery newQuery = new JRDesignQuery();
//            newQuery.setText(rv.getSql());
//            jasperDesign.setQuery(newQuery);
//            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
//            JasperExportManager.exportReportToHtmlFile(jasperPrint, rutatemp);
//            abrirArchivo(rutatemp);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//
//    }

    private void abrirArchivo(String ruta) {
        try {
            File file = new File(ruta);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
