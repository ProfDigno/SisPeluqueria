package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.cliente;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_cliente {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "CLIENTE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CLIENTE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO cliente(idcliente,fecha_creado,creado_por,orden,activo,"
            + "nombre,apellido,ruc,cedula,direccion,telefono,"
            + "tiene_puntaje,total_puntaje,fk_idconfiguracion_puntaje) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE cliente SET fecha_creado=?,creado_por=?,orden=?,activo=?,"
            + "nombre=?,apellido=?,ruc=?,cedula=?,direccion=?,telefono=?,"
            + "tiene_puntaje=?,total_puntaje=?,fk_idconfiguracion_puntaje=? WHERE idcliente=?;";
    private String sql_select = "SELECT idcliente as idc,"
            + "(nombre||'-'||apellido) as nombre,direccion,ruc,telefono,"
            + "total_puntaje as punto FROM cliente "; //5,35,30,10,10,10
    private String sql_select_ord = " desc;";
    private String sql_cargar = "SELECT idcliente,fecha_creado,creado_por,orden,activo,"
            + "nombre,apellido,ruc,cedula,direccion,telefono,"
            + "tiene_puntaje,total_puntaje,fk_idconfiguracion_puntaje FROM cliente WHERE idcliente=";

    public void insertar_cliente(Connection conn, cliente cli) {
        cli.setC1idcliente(eveconn.getInt_ultimoID_mas_uno(conn, cli.getTb_cliente(), cli.getId_idcliente()));
        String titulo = "insertar_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, cli.getC1idcliente());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, cli.getC3creado_por());
            pst.setInt(4, cli.getC4orden());
            pst.setBoolean(5, cli.getC5activo());
            pst.setString(6, cli.getC6nombre());
            pst.setString(7, cli.getC7apellido());
            pst.setString(8, cli.getC8ruc());
            pst.setString(9, cli.getC9cedula());
            pst.setString(10, cli.getC10direccion());
            pst.setString(11, cli.getC11telefono());
            pst.setBoolean(12, cli.getC12tiene_puntaje());
            pst.setDouble(13, cli.getC13total_puntaje());
            pst.setInt(14, cli.getC14fk_idconfiguracion_puntaje());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + cli.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + cli.toString(), titulo);
        }
    }

    public void update_cliente(Connection conn, cliente cli) {
        String titulo = "update_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, cli.getC3creado_por());
            pst.setInt(3, cli.getC4orden());
            pst.setBoolean(4, cli.getC5activo());
            pst.setString(5, cli.getC6nombre());
            pst.setString(6, cli.getC7apellido());
            pst.setString(7, cli.getC8ruc());
            pst.setString(8, cli.getC9cedula());
            pst.setString(9, cli.getC10direccion());
            pst.setString(10, cli.getC11telefono());
            pst.setBoolean(11, cli.getC12tiene_puntaje());
            pst.setDouble(12, cli.getC13total_puntaje());
            pst.setInt(13, cli.getC14fk_idconfiguracion_puntaje());
            pst.setInt(14, cli.getC1idcliente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cli.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cli.toString(), titulo);
        }
    }

    public void cargar_cliente(Connection conn, cliente cli, int idcliente) {
        String titulo = "Cargar_cliente";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcliente, titulo);
            if (rs.next()) {
                cli.setC1idcliente(rs.getInt(1));
                cli.setC2fecha_creado(rs.getString(2));
                cli.setC3creado_por(rs.getString(3));
                cli.setC4orden(rs.getInt(4));
                cli.setC5activo(rs.getBoolean(5));
                cli.setC6nombre(rs.getString(6));
                cli.setC7apellido(rs.getString(7));
                cli.setC8ruc(rs.getString(8));
                cli.setC9cedula(rs.getString(9));
                cli.setC10direccion(rs.getString(10));
                cli.setC11telefono(rs.getString(11));
                cli.setC12tiene_puntaje(rs.getBoolean(12));
                cli.setC13total_puntaje(rs.getDouble(13));
                cli.setC14fk_idconfiguracion_puntaje(rs.getInt(14));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + cli.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cli.toString(), titulo);
        }
    }

    public void actualizar_tabla_cliente(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select+"order by 1 "+sql_select_ord, tbltabla);
        ancho_tabla_cliente(tbltabla);
    }

    public void ancho_tabla_cliente(JTable tbltabla) {
        int Ancho[] = {5,35,30,10,10,10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
