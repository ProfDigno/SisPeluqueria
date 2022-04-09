package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.configuracion_puntaje;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_configuracion_puntaje {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "CONFIGURACION_PUNTAJE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CONFIGURACION_PUNTAJE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO configuracion_puntaje(idconfiguracion_puntaje,fecha_creado,creado_por,activo,nombre,porcentaje_del_total,unidad_por_punto,monto_por_punto,minimo_monto_generar,punto_maximo,dias_vencer) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE configuracion_puntaje SET fecha_creado=?,creado_por=?,activo=?,nombre=?,porcentaje_del_total=?,unidad_por_punto=?,monto_por_punto=?,minimo_monto_generar=?,punto_maximo=?,dias_vencer=? WHERE idconfiguracion_puntaje=?;";
    private String sql_select = "SELECT idconfiguracion_puntaje,fecha_creado,creado_por,activo,nombre,porcentaje_del_total,unidad_por_punto,monto_por_punto,minimo_monto_generar,punto_maximo,dias_vencer FROM configuracion_puntaje order by 1 desc;";
    private String sql_cargar = "SELECT idconfiguracion_puntaje,fecha_creado,creado_por,activo,nombre,porcentaje_del_total,unidad_por_punto,monto_por_punto,minimo_monto_generar,punto_maximo,dias_vencer FROM configuracion_puntaje WHERE idconfiguracion_puntaje=";

    public void insertar_configuracion_puntaje(Connection conn, configuracion_puntaje confpu) {
        confpu.setC1idconfiguracion_puntaje(eveconn.getInt_ultimoID_mas_uno(conn, confpu.getTb_configuracion_puntaje(), confpu.getId_idconfiguracion_puntaje()));
        String titulo = "insertar_configuracion_puntaje";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, confpu.getC1idconfiguracion_puntaje());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, confpu.getC3creado_por());
            pst.setBoolean(4, confpu.getC4activo());
            pst.setString(5, confpu.getC5nombre());
            pst.setDouble(6, confpu.getC6porcentaje_del_total());
            pst.setDouble(7, confpu.getC7unidad_por_punto());
            pst.setDouble(8, confpu.getC8monto_por_punto());
            pst.setDouble(9, confpu.getC9minimo_monto_generar());
            pst.setDouble(10, confpu.getC10punto_maximo());
            pst.setInt(11, confpu.getC11dias_vencer());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + confpu.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + confpu.toString(), titulo);
        }
    }

    public void update_configuracion_puntaje(Connection conn, configuracion_puntaje confpu) {
        String titulo = "update_configuracion_puntaje";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, confpu.getC3creado_por());
            pst.setBoolean(3, confpu.getC4activo());
            pst.setString(4, confpu.getC5nombre());
            pst.setDouble(5, confpu.getC6porcentaje_del_total());
            pst.setDouble(6, confpu.getC7unidad_por_punto());
            pst.setDouble(7, confpu.getC8monto_por_punto());
            pst.setDouble(8, confpu.getC9minimo_monto_generar());
            pst.setDouble(9, confpu.getC10punto_maximo());
            pst.setInt(10, confpu.getC11dias_vencer());
            pst.setInt(11, confpu.getC1idconfiguracion_puntaje());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + confpu.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + confpu.toString(), titulo);
        }
    }

    public void cargar_configuracion_puntaje(Connection conn, configuracion_puntaje confpu, int idconfiguracion_puntaje) {
        String titulo = "Cargar_configuracion_puntaje";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idconfiguracion_puntaje, titulo);
            if (rs.next()) {
                confpu.setC1idconfiguracion_puntaje(rs.getInt(1));
                confpu.setC2fecha_creado(rs.getString(2));
                confpu.setC3creado_por(rs.getString(3));
                confpu.setC4activo(rs.getBoolean(4));
                confpu.setC5nombre(rs.getString(5));
                confpu.setC6porcentaje_del_total(rs.getDouble(6));
                confpu.setC7unidad_por_punto(rs.getDouble(7));
                confpu.setC8monto_por_punto(rs.getDouble(8));
                confpu.setC9minimo_monto_generar(rs.getDouble(9));
                confpu.setC10punto_maximo(rs.getDouble(10));
                confpu.setC11dias_vencer(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + confpu.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + confpu.toString(), titulo);
        }
    }

    public void actualizar_tabla_configuracion_puntaje(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_configuracion_puntaje(tbltabla);
    }

    public void ancho_tabla_configuracion_puntaje(JTable tbltabla) {
        int Ancho[] = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
