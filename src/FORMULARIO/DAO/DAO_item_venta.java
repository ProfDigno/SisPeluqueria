package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import CONFIGURACION.Global_datos;
import FORMULARIO.ENTIDAD.item_venta;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.funcionario_comision;
import FORMULARIO.ENTIDAD.venta;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_item_venta {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private Global_datos gda = new Global_datos();
    private String mensaje_insert = "ITEM_VENTA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_VENTA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_venta(iditem_venta,fecha_creado,creado_por,orden,es_producto,es_servicio,"
            + "descripcion,precio_venta,precio_compra,cantidad,es_sum_punto,porcentaje_comision,"
            + "fk_idventa,fk_idservicio,fk_idproducto,fk_idfuncionario,monto_comision) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE item_venta SET fecha_creado=?,creado_por=?,orden=?,es_producto=?,es_servicio=?,"
            + "descripcion=?,precio_venta=?,precio_compra=?,cantidad=?,es_sum_punto=?,porcentaje_comision=?,"
            + "fk_idventa=?,fk_idservicio=?,fk_idproducto=?,fk_idfuncionario=?,monto_comision=? WHERE iditem_venta=?;";
    private String sql_select = "SELECT iditem_venta,fecha_creado,creado_por,orden,es_producto,es_servicio,"
            + "descripcion,precio_venta,precio_compra,cantidad,es_sum_punto,porcentaje_comision,"
            + "fk_idventa,fk_idservicio,fk_idproducto,fk_idfuncionario,monto_comision FROM item_venta order by 1 desc;";
    private String sql_cargar = "SELECT iditem_venta,fecha_creado,creado_por,orden,es_producto,es_servicio,"
            + "descripcion,precio_venta,precio_compra,cantidad,es_sum_punto,porcentaje_comision,"
            + "fk_idventa,fk_idservicio,fk_idproducto,fk_idfuncionario,monto_comision FROM item_venta WHERE iditem_venta=";

    public void insertar_item_venta(Connection conn, item_venta iven) {
        iven.setC1iditem_venta(eveconn.getInt_ultimoID_mas_uno(conn, iven.getTb_item_venta(), iven.getId_iditem_venta()));
        String titulo = "insertar_item_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, iven.getC1iditem_venta());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, iven.getC3creado_por());
            pst.setInt(4, iven.getC4orden());
            pst.setBoolean(5, iven.getC5es_producto());
            pst.setBoolean(6, iven.getC6es_servicio());
            pst.setString(7, iven.getC7descripcion());
            pst.setDouble(8, iven.getC8precio_venta());
            pst.setDouble(9, iven.getC9precio_compra());
            pst.setDouble(10, iven.getC10cantidad());
            pst.setBoolean(11, iven.getC11es_sum_punto());
            pst.setDouble(12, iven.getC12porcentaje_comision());
            pst.setInt(13, iven.getC13fk_idventa());
            pst.setInt(14, iven.getC14fk_idservicio());
            pst.setInt(15, iven.getC15fk_idproducto());
            pst.setInt(16, iven.getC16fk_idfuncionario());
            pst.setDouble(17, iven.getC17monto_comision());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + iven.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + iven.toString(), titulo);
        }
    }

    public void update_item_venta(Connection conn, item_venta iven) {
        String titulo = "update_item_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, iven.getC3creado_por());
            pst.setInt(3, iven.getC4orden());
            pst.setBoolean(4, iven.getC5es_producto());
            pst.setBoolean(5, iven.getC6es_servicio());
            pst.setString(6, iven.getC7descripcion());
            pst.setDouble(7, iven.getC8precio_venta());
            pst.setDouble(8, iven.getC9precio_compra());
            pst.setDouble(9, iven.getC10cantidad());
            pst.setBoolean(10, iven.getC11es_sum_punto());
            pst.setDouble(11, iven.getC12porcentaje_comision());
            pst.setInt(12, iven.getC13fk_idventa());
            pst.setInt(13, iven.getC14fk_idservicio());
            pst.setInt(14, iven.getC15fk_idproducto());
            pst.setInt(15, iven.getC16fk_idfuncionario());
            pst.setDouble(16, iven.getC17monto_comision());
            pst.setInt(17, iven.getC1iditem_venta());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + iven.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + iven.toString(), titulo);
        }
    }

    public void cargar_item_venta(Connection conn, item_venta iven, int iditem_venta) {
        String titulo = "Cargar_item_venta";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + iditem_venta, titulo);
            if (rs.next()) {
                iven.setC1iditem_venta(rs.getInt(1));
                iven.setC2fecha_creado(rs.getString(2));
                iven.setC3creado_por(rs.getString(3));
                iven.setC4orden(rs.getInt(4));
                iven.setC5es_producto(rs.getBoolean(5));
                iven.setC6es_servicio(rs.getBoolean(6));
                iven.setC7descripcion(rs.getString(7));
                iven.setC8precio_venta(rs.getDouble(8));
                iven.setC9precio_compra(rs.getDouble(9));
                iven.setC10cantidad(rs.getDouble(10));
                iven.setC11es_sum_punto(rs.getBoolean(11));
                iven.setC12porcentaje_comision(rs.getDouble(12));
                iven.setC13fk_idventa(rs.getInt(13));
                iven.setC14fk_idservicio(rs.getInt(14));
                iven.setC15fk_idproducto(rs.getInt(15));
                iven.setC16fk_idfuncionario(rs.getInt(16));
                iven.setC17monto_comision(rs.getDouble(17));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + iven.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + iven.toString(), titulo);
        }
    }

    public void actualizar_tabla_item_venta(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_item_venta(tbltabla);
    }

    public void ancho_tabla_item_venta(JTable tbltabla) {
        int Ancho[] = {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    private boolean getboo_convertir(String texto) {
        boolean convertir = false;
        if (texto.equals("T")) {
            convertir = true;
        }
        return convertir;
    }

    public void insertar_item_venta_de_tabla(Connection conn, JTable tblitem_producto, venta ven) {
        item_venta iven = new item_venta();
        funcionario_comision ENTfc = new funcionario_comision();
        DAO_funcionario_comision DAOfc = new DAO_funcionario_comision();
        DAO_funcionario_grupo_comision DAOfgc = new DAO_funcionario_grupo_comision();
        DAO_producto DAOpro = new DAO_producto();
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String ord = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String tipo = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String porcentaje = ((tblitem_producto.getModel().getValueAt(row, 2).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String precio_venta = ((tblitem_producto.getModel().getValueAt(row, 4).toString()));
            String cant = ((tblitem_producto.getModel().getValueAt(row, 5).toString()));
            String total = ((tblitem_producto.getModel().getValueAt(row, 6).toString()));
            String es_producto = ((tblitem_producto.getModel().getValueAt(row, 7).toString()));
            String es_servicio = ((tblitem_producto.getModel().getValueAt(row, 8).toString()));
            String es_sum_punto = ((tblitem_producto.getModel().getValueAt(row, 9).toString()));
            String fk_idventa = ((tblitem_producto.getModel().getValueAt(row, 10).toString()));
            String fk_idservicio = ((tblitem_producto.getModel().getValueAt(row, 11).toString()));
            String fk_idproducto = ((tblitem_producto.getModel().getValueAt(row, 12).toString()));
            String fk_idfuncionario = ((tblitem_producto.getModel().getValueAt(row, 13).toString()));
            String monto_comision = ((tblitem_producto.getModel().getValueAt(row, 14).toString()));
            String precio_compra = ((tblitem_producto.getModel().getValueAt(row, 15).toString()));
            String cliente = ((tblitem_producto.getModel().getValueAt(row, 16).toString()));
            int idfuncionario = Integer.parseInt(fk_idfuncionario);
            try {
                iven.setC3creado_por(gda.getCreado_por());
                iven.setC4orden(Integer.parseInt(ord));
                iven.setC5es_producto(getboo_convertir(es_producto));
                iven.setC6es_servicio(getboo_convertir(es_servicio));
                iven.setC7descripcion(descripcion);
                iven.setC8precio_venta(Double.parseDouble(precio_venta));
                iven.setC9precio_compra(Double.parseDouble(precio_compra));
                iven.setC10cantidad(Double.parseDouble(cant));
                iven.setC11es_sum_punto(getboo_convertir(es_sum_punto));
                iven.setC12porcentaje_comision(Double.parseDouble(porcentaje));
                iven.setC13fk_idventa(ven.getC1idventa());
                iven.setC14fk_idservicio(Integer.parseInt(fk_idservicio));
                iven.setC15fk_idproducto(Integer.parseInt(fk_idproducto));
                iven.setC16fk_idfuncionario(idfuncionario);
                iven.setC17monto_comision(Double.parseDouble(monto_comision));
                insertar_item_venta(conn, iven);
                DAOpro.update_stock_actual_producto_descontar(conn, cant, fk_idproducto);
                //**FUNCIONARIO COMISION
                int idfuncionario_grupo_comision = DAOfgc.getInt_id_abierto_funcionario_grupo_comision(conn, idfuncionario);
                ENTfc.setC3creado_por(gda.getCreado_por());
                ENTfc.setC5monto_comision(Double.parseDouble(monto_comision));
                ENTfc.setC6monto_pagado(0);
                ENTfc.setC7estado(gda.getEstado_abierto());
                ENTfc.setC8descripcion(descripcion + "=>Cli:" + cliente);
                ENTfc.setC9es_pagado(false);
                ENTfc.setC10fk_idfuncionario_grupo_comision(idfuncionario_grupo_comision);
                ENTfc.setC11fk_iditem_venta(iven.getC1iditem_venta());
                ENTfc.setC12fk_idfuncionario(idfuncionario);
                ENTfc.setC13fk_idventa(ven.getC1idventa());
                DAOfc.insertar_funcionario_comision(conn, ENTfc);
                DAOfc.update_total_comision_funcionario(conn, idfuncionario);

            } catch (Exception e) {
                evemen.mensaje_error(e, "insertar_item_venta_de_tabla");
                break;
            }

        }
    }

    public void anular_item_venta(Connection conn, int fk_idventa) {
        String sql = "update producto set stock_actual=(stock_actual+iv.cantidad)\n"
                + "from item_venta iv\n"
                + "where iv.es_producto=true and idproducto=iv.fk_idproducto and iv.fk_idventa="+fk_idventa;
        eveconn.SQL_execute_libre(conn, sql);
    }
}
