package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_unidad;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_producto_unidad {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_UNIDAD GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_UNIDAD MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_unidad(idproducto_unidad,fecha_creado,creado_por,activo,nombre) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE producto_unidad SET fecha_creado=?,creado_por=?,activo=?,nombre=? WHERE idproducto_unidad=?;";
    private String sql_select = "SELECT idproducto_unidad as idpu,nombre as unidad,activo FROM producto_unidad order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_unidad,fecha_creado,creado_por,activo,nombre FROM producto_unidad WHERE idproducto_unidad=";

    public void insertar_producto_unidad(Connection conn, producto_unidad puni) {
        puni.setC1idproducto_unidad(eveconn.getInt_ultimoID_mas_uno(conn, puni.getTb_producto_unidad(), puni.getId_idproducto_unidad()));
        String titulo = "insertar_producto_unidad";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, puni.getC1idproducto_unidad());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, puni.getC3creado_por());
            pst.setBoolean(4, puni.getC4activo());
            pst.setString(5, puni.getC5nombre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + puni.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + puni.toString(), titulo);
        }
    }

    public void update_producto_unidad(Connection conn, producto_unidad puni) {
        String titulo = "update_producto_unidad";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, puni.getC3creado_por());
            pst.setBoolean(3, puni.getC4activo());
            pst.setString(4, puni.getC5nombre());
            pst.setInt(5, puni.getC1idproducto_unidad());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + puni.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + puni.toString(), titulo);
        }
    }

    public void cargar_producto_unidad(Connection conn, producto_unidad puni, int idproducto_unidad) {
        String titulo = "Cargar_producto_unidad";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_unidad, titulo);
            if (rs.next()) {
                puni.setC1idproducto_unidad(rs.getInt(1));
                puni.setC2fecha_creado(rs.getString(2));
                puni.setC3creado_por(rs.getString(3));
                puni.setC4activo(rs.getBoolean(4));
                puni.setC5nombre(rs.getString(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + puni.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + puni.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_unidad(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_unidad(tbltabla);
    }

    public void ancho_tabla_producto_unidad(JTable tbltabla) {
        int Ancho[] = {15,75,10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void buscar_tabla_producto_unidad(Connection conn, JTable tbltabla, JTextField txtbuscar) {
        if (txtbuscar.getText().trim().length() > 1) {
            String buscar = txtbuscar.getText();
            String sql = "SELECT idproducto_unidad as idpu,nombre as unidad,activo "
                    + "FROM producto_unidad "
                    + "where nombre ilike'%" + buscar + "%' "
                    + "order by 1 desc;";
            eveconn.Select_cargar_jtable(conn, sql, tbltabla);
            ancho_tabla_producto_unidad(tbltabla);
        }
    }
}
