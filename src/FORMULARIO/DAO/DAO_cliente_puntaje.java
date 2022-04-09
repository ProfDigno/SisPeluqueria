package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.cliente_puntaje;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_cliente_puntaje {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "CLIENTE_PUNTAJE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CLIENTE_PUNTAJE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO cliente_puntaje(idcliente_puntaje,fecha_creado,creado_por,fecha_usado,puntaje_ingreso,puntaje_egreso,estado,descripcion,es_usado,dias_vencer,fk_idcliente_grupo_puntaje,fk_idcliente,fk_idventa) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE cliente_puntaje SET fecha_creado=?,creado_por=?,fecha_usado=?,puntaje_ingreso=?,puntaje_egreso=?,estado=?,descripcion=?,es_usado=?,dias_vencer=?,fk_idcliente_grupo_puntaje=?,fk_idcliente=?,fk_idventa=? WHERE idcliente_puntaje=?;";
    private String sql_select = "SELECT idcliente_puntaje,fecha_creado,creado_por,fecha_usado,puntaje_ingreso,puntaje_egreso,estado,descripcion,es_usado,dias_vencer,fk_idcliente_grupo_puntaje,fk_idcliente,fk_idventa FROM cliente_puntaje order by 1 desc;";
    private String sql_cargar = "SELECT idcliente_puntaje,fecha_creado,creado_por,fecha_usado,puntaje_ingreso,puntaje_egreso,estado,descripcion,es_usado,dias_vencer,fk_idcliente_grupo_puntaje,fk_idcliente,fk_idventa FROM cliente_puntaje WHERE idcliente_puntaje=";

    public void insertar_cliente_puntaje(Connection conn, cliente_puntaje clipu) {
        clipu.setC1idcliente_puntaje(eveconn.getInt_ultimoID_mas_uno(conn, clipu.getTb_cliente_puntaje(), clipu.getId_idcliente_puntaje()));
        String titulo = "insertar_cliente_puntaje";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, clipu.getC1idcliente_puntaje());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, clipu.getC3creado_por());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setDouble(5, clipu.getC5puntaje_ingreso());
            pst.setDouble(6, clipu.getC6puntaje_egreso());
            pst.setString(7, clipu.getC7estado());
            pst.setString(8, clipu.getC8descripcion());
            pst.setBoolean(9, clipu.getC9es_usado());
            pst.setInt(10, clipu.getC10dias_vencer());
            pst.setInt(11, clipu.getC11fk_idcliente_grupo_puntaje());
            pst.setInt(12, clipu.getC12fk_idcliente());
            pst.setInt(13, clipu.getC13fk_idventa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + clipu.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + clipu.toString(), titulo);
        }
    }

    public void update_cliente_puntaje(Connection conn, cliente_puntaje clipu) {
        String titulo = "update_cliente_puntaje";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, clipu.getC3creado_por());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setDouble(4, clipu.getC5puntaje_ingreso());
            pst.setDouble(5, clipu.getC6puntaje_egreso());
            pst.setString(6, clipu.getC7estado());
            pst.setString(7, clipu.getC8descripcion());
            pst.setBoolean(8, clipu.getC9es_usado());
            pst.setInt(9, clipu.getC10dias_vencer());
            pst.setInt(10, clipu.getC11fk_idcliente_grupo_puntaje());
            pst.setInt(11, clipu.getC12fk_idcliente());
            pst.setInt(12, clipu.getC13fk_idventa());
            pst.setInt(13, clipu.getC1idcliente_puntaje());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + clipu.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + clipu.toString(), titulo);
        }
    }

    public void cargar_cliente_puntaje(Connection conn, cliente_puntaje clipu, int idcliente_puntaje) {
        String titulo = "Cargar_cliente_puntaje";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcliente_puntaje, titulo);
            if (rs.next()) {
                clipu.setC1idcliente_puntaje(rs.getInt(1));
                clipu.setC2fecha_creado(rs.getString(2));
                clipu.setC3creado_por(rs.getString(3));
                clipu.setC4fecha_usado(rs.getString(4));
                clipu.setC5puntaje_ingreso(rs.getDouble(5));
                clipu.setC6puntaje_egreso(rs.getDouble(6));
                clipu.setC7estado(rs.getString(7));
                clipu.setC8descripcion(rs.getString(8));
                clipu.setC9es_usado(rs.getBoolean(9));
                clipu.setC10dias_vencer(rs.getInt(10));
                clipu.setC11fk_idcliente_grupo_puntaje(rs.getInt(11));
                clipu.setC12fk_idcliente(rs.getInt(12));
                clipu.setC13fk_idventa(rs.getInt(13));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + clipu.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + clipu.toString(), titulo);
        }
    }

    public void actualizar_tabla_cliente_puntaje(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_cliente_puntaje(tbltabla);
    }

    public void ancho_tabla_cliente_puntaje(JTable tbltabla) {
        int Ancho[] = {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
