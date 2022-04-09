package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.servicio_categoria;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_servicio_categoria {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "SERVICIO_CATEGORIA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "SERVICIO_CATEGORIA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO servicio_categoria(idservicio_categoria,fecha_creado,creado_por,orden,activo,nombre,ruta_icono) VALUES (?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE servicio_categoria SET fecha_creado=?,creado_por=?,orden=?,activo=?,nombre=?,ruta_icono=? WHERE idservicio_categoria=?;";
    private String sql_select = "SELECT idservicio_categoria as idsc,orden,nombre,activo "
            + "FROM servicio_categoria "; //10,15,60,15
    private String sql_select_orden = "order by 1 desc;";
    private String sql_cargar = "SELECT idservicio_categoria,fecha_creado,creado_por,orden,activo,nombre,ruta_icono FROM servicio_categoria WHERE idservicio_categoria=";
    private String sql_select_venta = "SELECT idservicio_categoria as idsc,nombre as categoria "
            + "FROM servicio_categoria where activo=true order by orden desc;"; //10,15,60,15
    public void insertar_servicio_categoria(Connection conn, servicio_categoria secat) {
        secat.setC1idservicio_categoria(eveconn.getInt_ultimoID_mas_uno(conn, secat.getTb_servicio_categoria(), secat.getId_idservicio_categoria()));
        String titulo = "insertar_servicio_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, secat.getC1idservicio_categoria());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, secat.getC3creado_por());
            pst.setInt(4, secat.getC4orden());
            pst.setBoolean(5, secat.getC5activo());
            pst.setString(6, secat.getC6nombre());
            pst.setString(7, secat.getC7ruta_icono());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + secat.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + secat.toString(), titulo);
        }
    }

    public void update_servicio_categoria(Connection conn, servicio_categoria secat) {
        String titulo = "update_servicio_categoria";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, secat.getC3creado_por());
            pst.setInt(3, secat.getC4orden());
            pst.setBoolean(4, secat.getC5activo());
            pst.setString(5, secat.getC6nombre());
            pst.setString(6, secat.getC7ruta_icono());
            pst.setInt(7, secat.getC1idservicio_categoria());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + secat.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + secat.toString(), titulo);
        }
    }

    public void cargar_servicio_categoria(Connection conn, servicio_categoria secat, int idservicio_categoria) {
        String titulo = "Cargar_servicio_categoria";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idservicio_categoria, titulo);
            if (rs.next()) {
                secat.setC1idservicio_categoria(rs.getInt(1));
                secat.setC2fecha_creado(rs.getString(2));
                secat.setC3creado_por(rs.getString(3));
                secat.setC4orden(rs.getInt(4));
                secat.setC5activo(rs.getBoolean(5));
                secat.setC6nombre(rs.getString(6));
                secat.setC7ruta_icono(rs.getString(7));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + secat.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + secat.toString(), titulo);
        }
    }

    public void actualizar_tabla_servicio_categoria(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select + sql_select_orden, tbltabla);
        ancho_tabla_servicio_categoria(tbltabla);
    }

    public void ancho_tabla_servicio_categoria(JTable tbltabla) {
        int Ancho[] = {10, 15, 60, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void buscar_tabla_servicio_categoria(Connection conn, JTable tbltabla, JTextField txtbuscar) {
        if (txtbuscar.getText().trim().length() > 1) {
            String buscar = txtbuscar.getText();
            String sql = sql_select
                    + " where nombre ilike'%" + buscar + "%' "
                    + sql_select_orden;
            eveconn.Select_cargar_jtable(conn, sql, tbltabla);
            ancho_tabla_servicio_categoria(tbltabla);
        }
    }
    public void actualizar_tabla_servicio_categoria_venta(Connection conn, JTable tblservicio_categoria) {
        eveconn.Select_cargar_jtable(conn, sql_select_venta, tblservicio_categoria);
        ancho_tabla_servicio_categoria_venta(tblservicio_categoria);
        evejt.ocultar_columna(tblservicio_categoria, 0);
    }

    public void ancho_tabla_servicio_categoria_venta(JTable tblservicio_categoria) {
        int Ancho[] = {5,95};
        evejt.setAnchoColumnaJtable(tblservicio_categoria, Ancho);
    }
}
