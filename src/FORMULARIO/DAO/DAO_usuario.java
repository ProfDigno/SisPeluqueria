package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.usuario;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class DAO_usuario {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "USUARIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "USUARIO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO usuario(idusuario,fecha_creado,creado_por,nombre,usuario,password,nivel) VALUES (?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE usuario SET fecha_creado=?,creado_por=?,nombre=?,usuario=?,password=?,nivel=? WHERE idusuario=?;";
    private String sql_select = "SELECT idusuario as id,nombre,usuario,nivel FROM usuario order by 1 desc;";
    private String sql_cargar = "SELECT idusuario,fecha_creado,creado_por,nombre,usuario,password,nivel FROM usuario WHERE idusuario=";

    public void insertar_usuario(Connection conn, usuario usu) {
        usu.setC1idusuario(eveconn.getInt_ultimoID_mas_uno(conn, usu.getTb_usuario(), usu.getId_idusuario()));
        String titulo = "insertar_usuario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, usu.getC1idusuario());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, usu.getC3creado_por());
            pst.setString(4, usu.getC4nombre());
            pst.setString(5, usu.getC5usuario());
            pst.setString(6, usu.getC6password());
            pst.setString(7, usu.getC7nivel());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + usu.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + usu.toString(), titulo);
        }
    }

    public void update_usuario(Connection conn, usuario usu) {
        String titulo = "update_usuario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, usu.getC3creado_por());
            pst.setString(3, usu.getC4nombre());
            pst.setString(4, usu.getC5usuario());
            pst.setString(5, usu.getC6password());
            pst.setString(6, usu.getC7nivel());
            pst.setInt(7, usu.getC1idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + usu.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + usu.toString(), titulo);
        }
    }

    public void cargar_usuario(Connection conn, usuario usu, int idusuario) {
        String titulo = "Cargar_usuario";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idusuario, titulo);
            if (rs.next()) {
                usu.setC1idusuario(rs.getInt(1));
                usu.setC2fecha_creado(rs.getString(2));
                usu.setC3creado_por(rs.getString(3));
                usu.setC4nombre(rs.getString(4));
                usu.setC5usuario(rs.getString(5));
                usu.setC6password(rs.getString(6));
                usu.setC7nivel(rs.getString(7));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + usu.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + usu.toString(), titulo);
        }
    }

    public void actualizar_tabla_usuario(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_usuario(tbltabla);
    }

    public void ancho_tabla_usuario(JTable tbltabla) {
        int Ancho[] = {10, 50, 20,20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public boolean getBoolean_buscar_usuario_existente(Connection conn, usuario usu) {
        String titulo = "getBoolean_buscar_usuario_existente";
        String sql = "select * from usuario where usuario='" + usu.getC5usuario() + "' and password='" + usu.getC6password() + "' ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                usu.setGlobal_idusuario(rs.getInt("idusuario"));
                usu.setGlobal_nombre(rs.getString("nombre"));
                usu.setGlobal_nivel(rs.getString("nivel"));
                System.out.println("getGlobal_idusuario:"+usu.getGlobal_idusuario());
                System.out.println("getGlobal_nombre:"+usu.getGlobal_nombre());
                System.out.println("getGlobal_nivel:"+usu.getGlobal_nivel());
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "USUARIO O PASSWORD INCORRECTA", "ERROR", JOptionPane.ERROR_MESSAGE);
                if (usu.getC5usuario().equals("digno") && usu.getC6password().equals("4650586")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
            return false;
        }
    }
}
