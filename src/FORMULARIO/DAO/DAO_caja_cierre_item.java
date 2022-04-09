package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.caja_cierre_item;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_caja_cierre_item {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "CAJA_CIERRE_ITEM GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CAJA_CIERRE_ITEM MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO caja_cierre_item(idcaja_cierre_item,fecha_creado,creado_por,fk_idcaja_cierre,fk_idcaja_detalle) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE caja_cierre_item SET fecha_creado=?,creado_por=?,fk_idcaja_cierre=?,fk_idcaja_detalle=? WHERE idcaja_cierre_item=?;";
    private String sql_select = "SELECT idcaja_cierre_item,fecha_creado,creado_por,fk_idcaja_cierre,fk_idcaja_detalle FROM caja_cierre_item order by 1 desc;";
    private String sql_cargar = "SELECT idcaja_cierre_item,fecha_creado,creado_por,fk_idcaja_cierre,fk_idcaja_detalle FROM caja_cierre_item WHERE idcaja_cierre_item=";

    public void insertar_caja_cierre_item(Connection conn, caja_cierre_item cjciei) {
        cjciei.setC1idcaja_cierre_item(eveconn.getInt_ultimoID_mas_uno(conn, cjciei.getTb_caja_cierre_item(), cjciei.getId_idcaja_cierre_item()));
        String titulo = "insertar_caja_cierre_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, cjciei.getC1idcaja_cierre_item());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, cjciei.getC3creado_por());
            pst.setInt(4, cjciei.getC4fk_idcaja_cierre());
            pst.setInt(5, cjciei.getC5fk_idcaja_detalle());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + cjciei.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + cjciei.toString(), titulo);
        }
    }

    public void update_caja_cierre_item(Connection conn, caja_cierre_item cjciei) {
        String titulo = "update_caja_cierre_item";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, cjciei.getC3creado_por());
            pst.setInt(3, cjciei.getC4fk_idcaja_cierre());
            pst.setInt(4, cjciei.getC5fk_idcaja_detalle());
            pst.setInt(5, cjciei.getC1idcaja_cierre_item());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cjciei.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cjciei.toString(), titulo);
        }
    }

    public void cargar_caja_cierre_item(Connection conn, caja_cierre_item cjciei, int idcaja_cierre_item) {
        String titulo = "Cargar_caja_cierre_item";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcaja_cierre_item, titulo);
            if (rs.next()) {
                cjciei.setC1idcaja_cierre_item(rs.getInt(1));
                cjciei.setC2fecha_creado(rs.getString(2));
                cjciei.setC3creado_por(rs.getString(3));
                cjciei.setC4fk_idcaja_cierre(rs.getInt(4));
                cjciei.setC5fk_idcaja_detalle(rs.getInt(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + cjciei.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cjciei.toString(), titulo);
        }
    }

    public void actualizar_tabla_caja_cierre_item(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_caja_cierre_item(tbltabla);
    }

    public void ancho_tabla_caja_cierre_item(JTable tbltabla) {
        int Ancho[] = {20, 20, 20, 20, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
