/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Grafico;

import BASEDATO.EvenConexion;
import CONFIGURACION.Global_datos;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JTable;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Digno
 */
public class EvenDataSetPelu {

    private EvenConexion eveconn = new EvenConexion();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private Global_datos gda = new Global_datos();

    public DefaultCategoryDataset createDataset_producto_mas_vendido(Connection conn, String campo, String filtro, String top) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String titulo = "createDataset_producto_mas_vendido";
        String sql = "select p.nombre,sum(" + campo + ") as valor  \n"
                + "from venta v,item_venta iv,producto p \n"
                + "where v.idventa=iv.fk_idventa \n"
                + "and iv.fk_idproducto=p.idproducto \n"
                + "and (v.estado='" + gda.getEstado_emitido() + "' or v.estado='" + gda.getEstado_comision() + "')\n"
                + "and iv.es_producto=true\n " + filtro
                + " group by 1\n"
                + "order by 2 desc limit " + top + ";";
        String seriedt = "VENTA PRODUCTO";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(2);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }

    public DefaultCategoryDataset createDataset_servicio_mas_vendido(Connection conn, String campo, String filtro, String top) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String titulo = "createDataset_producto_mas_vendido";
        String sql = "select s.nombre,sum(" + campo + ") as valor  \n"
                + "from venta v,item_venta iv,servicio s \n"
                + "where v.idventa=iv.fk_idventa \n"
                + "and iv.fk_idservicio=s.idservicio  \n"
                + "and (v.estado='" + gda.getEstado_emitido() + "' or v.estado='" + gda.getEstado_comision() + "')\n"
                + "and iv.es_servicio=true\n " + filtro
                + " group by 1\n"
                + "order by 2 desc limit " + top + ";";
        String seriedt = "VENTA SERVICIO";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(2);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }

    public DefaultCategoryDataset createDataset_venta_dia_semana(Connection conn,String calendario,String campo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String titulo = "createDataset_venta_esta_semana";
        String sql = "select date(v.fecha_creado) as fecha,\n"
                + "CASE  \n"
                + "WHEN date_part('dow', v.fecha_creado)=0 THEN date_part('day',v.fecha_creado)||'-DOM' \n"
                + "WHEN date_part('dow', v.fecha_creado)=1 THEN date_part('day',v.fecha_creado)||'-LUN' \n"
                + "WHEN date_part('dow', v.fecha_creado)=2 THEN date_part('day',v.fecha_creado)||'-MAR' \n"
                + "WHEN date_part('dow', v.fecha_creado)=3 THEN date_part('day',v.fecha_creado)||'-MIE' \n"
                + "WHEN date_part('dow', v.fecha_creado)=4 THEN date_part('day',v.fecha_creado)||'-JUE' \n"
                + "WHEN date_part('dow', v.fecha_creado)=5 THEN date_part('day',v.fecha_creado)||'-VIE' \n"
                + "WHEN date_part('dow', v.fecha_creado)=6 THEN date_part('day',v.fecha_creado)||'-SAB' \n"
                + "ELSE 'sin dia' END as dia,\n"
                + "sum(v.monto_pagado) as s_venta \n"
                + "from venta v \n"
                + "where  date_part('year',v.fecha_creado)=date_part('year',current_date)\n"
                + "and date_part('"+calendario+"',v.fecha_creado)=date_part('"+calendario+"',current_date "+campo+") \n"
                + "and (v.estado='" + gda.getEstado_emitido() + "' or v.estado='" + gda.getEstado_comision() + "')\n"
                + "group by 1,2 order by 1 asc;";
        String seriedt = "TOTAL VENTA";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(2));
                double valor = rs.getDouble(3);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }
}
