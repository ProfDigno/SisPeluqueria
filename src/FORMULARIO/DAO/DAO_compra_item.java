package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import CONFIGURACION.Global_datos;
import FORMULARIO.ENTIDAD.compra_item;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.compra;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_compra_item {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private Global_datos gda = new Global_datos();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "COMPRA_ITEM GUARDADO CORRECTAMENTE";
    private String mensaje_update = "COMPRA_ITEM MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO compra_item(idcompra_item,fecha_creado,creado_por,descripcion,precio_compra,cantidad,fk_idcompra,fk_idproducto) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE compra_item SET fecha_creado=?,creado_por=?,descripcion=?,precio_compra=?,cantidad=?,fk_idcompra=?,fk_idproducto=? WHERE idcompra_item=?;";
    private String sql_select = "SELECT idcompra_item,fecha_creado,creado_por,descripcion,precio_compra,cantidad,fk_idcompra,fk_idproducto FROM compra_item order by 1 desc;";
    private String sql_cargar = "SELECT idcompra_item,fecha_creado,creado_por,descripcion,precio_compra,cantidad,fk_idcompra,fk_idproducto FROM compra_item WHERE idcompra_item=";

    public void insertar_compra_item(Connection conn, compra_item comi) {
        comi.setC1idcompra_item(eveconn.getInt_ultimoID_mas_uno(conn, comi.getTb_compra_item(), comi.getId_idcompra_item()));
        String titulo = "insertar_compra_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, comi.getC1idcompra_item());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, comi.getC3creado_por());
            pst.setString(4, comi.getC4descripcion());
            pst.setDouble(5, comi.getC5precio_compra());
            pst.setDouble(6, comi.getC6cantidad());
            pst.setInt(7, comi.getC7fk_idcompra());
            pst.setInt(8, comi.getC8fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + comi.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + comi.toString(), titulo);
        }
    }

    public void update_compra_item(Connection conn, compra_item comi) {
        String titulo = "update_compra_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, comi.getC3creado_por());
            pst.setString(3, comi.getC4descripcion());
            pst.setDouble(4, comi.getC5precio_compra());
            pst.setDouble(5, comi.getC6cantidad());
            pst.setInt(6, comi.getC7fk_idcompra());
            pst.setInt(7, comi.getC8fk_idproducto());
            pst.setInt(8, comi.getC1idcompra_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + comi.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + comi.toString(), titulo);
        }
    }

    public void cargar_compra_item(Connection conn, compra_item comi, int idcompra_item) {
        String titulo = "Cargar_compra_item";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcompra_item, titulo);
            if (rs.next()) {
                comi.setC1idcompra_item(rs.getInt(1));
                comi.setC2fecha_creado(rs.getString(2));
                comi.setC3creado_por(rs.getString(3));
                comi.setC4descripcion(rs.getString(4));
                comi.setC5precio_compra(rs.getDouble(5));
                comi.setC6cantidad(rs.getDouble(6));
                comi.setC7fk_idcompra(rs.getInt(7));
                comi.setC8fk_idproducto(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + comi.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + comi.toString(), titulo);
        }
    }

    public void actualizar_tabla_compra_item(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_compra_item(tbltabla);
    }

    public void ancho_tabla_compra_item(JTable tbltabla) {
        int Ancho[] = {12, 12, 12, 12, 12, 12, 12, 12};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void insertar_item_compra_de_tabla(Connection conn, JTable tblitem_producto, compra com) {
        compra_item icom = new compra_item();
        DAO_producto DAOpro = new DAO_producto();
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String Sfk_idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String Sprecio_compra = ((tblitem_producto.getModel().getValueAt(row, 2).toString()));
            String Scantidad = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
//            String total = ((tblitem_producto.getModel().getValueAt(row, 6).toString()));

            System.out.println("Sfk_idproducto:" + Sfk_idproducto);
            System.out.println("descripcion:" + descripcion);
            System.out.println("Sprecio_compra:" + Sprecio_compra);
            System.out.println("Scantidad:" + Scantidad);
            double precio_compra = Double.parseDouble(Sprecio_compra);
            double cantidad = Double.parseDouble(Scantidad);
            int fk_idproducto = Integer.parseInt(Sfk_idproducto);
            try {
                icom.setC3creado_por(gda.getCreado_por());
                icom.setC4descripcion(descripcion);
                icom.setC5precio_compra(precio_compra);
                icom.setC6cantidad(cantidad);
                icom.setC7fk_idcompra(com.getC1idcompra());
                icom.setC8fk_idproducto(fk_idproducto);
                DAOpro.update_stock_actual_producto_aumentar(conn, Scantidad, Sfk_idproducto);
                insertar_compra_item(conn, icom);

            } catch (Exception e) {
                evemen.mensaje_error(e, "insertar_item_compra_de_tabla");
                break;
            }

        }
    }

    public void anular_item_compra(Connection conn, int fk_idcompra) {
        String sql = "update producto set stock_actual=(stock_actual-ci.cantidad)\n"
                + "from compra_item ci\n"
                + "where idproducto=ci.fk_idproducto  and ci.fk_idcompra =" + fk_idcompra;
        eveconn.SQL_execute_libre(conn, sql);
    }
}
