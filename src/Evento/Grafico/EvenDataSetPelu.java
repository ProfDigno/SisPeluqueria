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
    public DefaultCategoryDataset createDataset_producto_mas_vendido(Connection conn,String campo, String filtro,String top) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String titulo = "createDataset_producto_mas_vendido";
        String sql = "select p.nombre,sum("+campo+") as valor  \n"
                + "from venta v,item_venta iv,producto p \n"
                + "where v.idventa=iv.fk_idventa \n"
                + "and iv.fk_idproducto=p.idproducto \n"
                + "and (v.estado='" + gda.getEstado_emitido() + "' or v.estado='" + gda.getEstado_comision() + "')\n"
                + "and iv.es_producto=true\n "+filtro
                + " group by 1\n"
                + "order by 2 desc limit "+top+";";
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
}
