package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.proveedor;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_proveedor {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PROVEEDOR GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PROVEEDOR MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO proveedor(idproveedor,fecha_creado,creado_por,orden,activo,"
            + "razon_social,nombre,ruc,direccion,telefono) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE proveedor SET fecha_creado=?,creado_por=?,orden=?,activo=?,"
            + "razon_social=?,nombre=?,ruc=?,direccion=?,telefono=? WHERE idproveedor=?;";
    private String sql_select = "SELECT idproveedor as id,"
            + "razon_social,nombre,direccion,ruc,telefono FROM proveedor order by 1 desc;";//5,30,20,25,10,10
    private String sql_cargar = "SELECT idproveedor,fecha_creado,creado_por,orden,activo,"
            + "razon_social,nombre,ruc,direccion,telefono FROM proveedor WHERE idproveedor=";

    public void insertar_proveedor(Connection conn, proveedor prov) {
        prov.setC1idproveedor(eveconn.getInt_ultimoID_mas_uno(conn, prov.getTb_proveedor(), prov.getId_idproveedor()));
        String titulo = "insertar_proveedor";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prov.getC1idproveedor());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, prov.getC3creado_por());
            pst.setInt(4, prov.getC4orden());
            pst.setBoolean(5, prov.getC5activo());
            pst.setString(6, prov.getC6razon_social());
            pst.setString(7, prov.getC7nombre());
            pst.setString(8, prov.getC8ruc());
            pst.setString(9, prov.getC9direccion());
            pst.setString(10, prov.getC10telefono());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prov.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prov.toString(), titulo);
        }
    }

    public void update_proveedor(Connection conn, proveedor prov) {
        String titulo = "update_proveedor";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, prov.getC3creado_por());
            pst.setInt(3, prov.getC4orden());
            pst.setBoolean(4, prov.getC5activo());
            pst.setString(5, prov.getC6razon_social());
            pst.setString(6, prov.getC7nombre());
            pst.setString(7, prov.getC8ruc());
            pst.setString(8, prov.getC9direccion());
            pst.setString(9, prov.getC10telefono());
            pst.setInt(10, prov.getC1idproveedor());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prov.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prov.toString(), titulo);
        }
    }

    public void cargar_proveedor(Connection conn, proveedor prov, int idproveedor) {
        String titulo = "Cargar_proveedor";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproveedor, titulo);
            if (rs.next()) {
                prov.setC1idproveedor(rs.getInt(1));
                prov.setC2fecha_creado(rs.getString(2));
                prov.setC3creado_por(rs.getString(3));
                prov.setC4orden(rs.getInt(4));
                prov.setC5activo(rs.getBoolean(5));
                prov.setC6razon_social(rs.getString(6));
                prov.setC7nombre(rs.getString(7));
                prov.setC8ruc(rs.getString(8));
                prov.setC9direccion(rs.getString(9));
                prov.setC10telefono(rs.getString(10));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prov.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prov.toString(), titulo);
        }
    }

    public void actualizar_tabla_proveedor(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_proveedor(tbltabla);
    }

    public void ancho_tabla_proveedor(JTable tbltabla) {
        int Ancho[] = {5,30,20,25,10,10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
