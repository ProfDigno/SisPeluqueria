package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_categoria;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_producto_categoria {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_CATEGORIA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_CATEGORIA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_categoria(idproducto_categoria,fecha_creado,creado_por,orden,activo,nombre) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto_categoria SET fecha_creado=?,creado_por=?,orden=?,activo=?,nombre=? WHERE idproducto_categoria=?;";
    private String sql_select = "SELECT idproducto_categoria as idpc,orden as ord,nombre,activo FROM producto_categoria order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_categoria,fecha_creado,creado_por,orden,activo,nombre FROM producto_categoria WHERE idproducto_categoria=";
    private String sql_select_venta = "SELECT idproducto_categoria as idsc,nombre as categoria "
            + "FROM producto_categoria where activo=true order by orden desc;";
    public void insertar_producto_categoria(Connection conn, producto_categoria pcat) {
        pcat.setC1idproducto_categoria(eveconn.getInt_ultimoID_mas_uno(conn, pcat.getTb_producto_categoria(), pcat.getId_idproducto_categoria()));
        String titulo = "insertar_producto_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, pcat.getC1idproducto_categoria());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, pcat.getC3creado_por());
            pst.setInt(4, pcat.getC4orden());
            pst.setBoolean(5, pcat.getC5activo());
            pst.setString(6, pcat.getC6nombre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + pcat.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + pcat.toString(), titulo);
        }
    }

    public void update_producto_categoria(Connection conn, producto_categoria pcat) {
        String titulo = "update_producto_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, pcat.getC3creado_por());
            pst.setInt(3, pcat.getC4orden());
            pst.setBoolean(4, pcat.getC5activo());
            pst.setString(5, pcat.getC6nombre());
            pst.setInt(6, pcat.getC1idproducto_categoria());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + pcat.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + pcat.toString(), titulo);
        }
    }

    public void cargar_producto_categoria(Connection conn, producto_categoria pcat, int idproducto_categoria) {
        String titulo = "Cargar_producto_categoria";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_categoria, titulo);
            if (rs.next()) {
                pcat.setC1idproducto_categoria(rs.getInt(1));
                pcat.setC2fecha_creado(rs.getString(2));
                pcat.setC3creado_por(rs.getString(3));
                pcat.setC4orden(rs.getInt(4));
                pcat.setC5activo(rs.getBoolean(5));
                pcat.setC6nombre(rs.getString(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + pcat.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + pcat.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_categoria(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_categoria(tbltabla);
    }

    public void ancho_tabla_producto_categoria(JTable tbltabla) {
        int Ancho[] = {15, 10, 65, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void buscar_tabla_producto_categoria(Connection conn, JTable tbltabla, JTextField txtbuscar) {
        if (txtbuscar.getText().trim().length() > 1) {
            String buscar = txtbuscar.getText();
            String sql = "SELECT idproducto_categoria as idpc,orden as ord,nombre,activo "
                    + "FROM producto_categoria "
                    + "where nombre ilike'%" + buscar + "%' "
                    + "order by 1 desc;";
            eveconn.Select_cargar_jtable(conn, sql, tbltabla);
            ancho_tabla_producto_categoria(tbltabla);
        }
    }
    public void actualizar_tabla_producto_categoria_venta(Connection conn, JTable tblproducto_categoria) {
        eveconn.Select_cargar_jtable(conn, sql_select_venta, tblproducto_categoria);
        ancho_tabla_producto_categoria_venta(tblproducto_categoria);
        evejt.ocultar_columna(tblproducto_categoria, 0);
    }

    public void ancho_tabla_producto_categoria_venta(JTable tblproducto_categoria) {
        int Ancho[] = {5,95};
        evejt.setAnchoColumnaJtable(tblproducto_categoria, Ancho);
    }
}
