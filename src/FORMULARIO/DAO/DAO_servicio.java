package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.servicio;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_servicio {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "SERVICIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "SERVICIO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO servicio(idservicio,fecha_creado,creado_por,orden,activo,cod_barra,"
            + "nombre,descripcion,precio_venta,precio_compra,porcentaje_comision,duracion_hora,duracion_minuto"
            + ",fk_idservicio_categoria,fk_idfuncionario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE servicio SET fecha_creado=?,creado_por=?,orden=?,activo=?,cod_barra=?,"
            + "nombre=?,descripcion=?,precio_venta=?,precio_compra=?,porcentaje_comision=?,duracion_hora=?,duracion_minuto=?,"
            + "fk_idservicio_categoria=?,fk_idfuncionario=? WHERE idservicio=?;";
    private String sql_select = "select s.idservicio as ids,s.orden as ord,\n"
            + "f.nombre as funcionario,sc.nombre as categoria,s.nombre as servicio,"
            + "TRIM(to_char(s.precio_venta,'999G999G999')) as precio,\n"
            + "TRIM(to_char(s.porcentaje_comision,'99%')) as por,\n"
            + "TRIM(to_char(((s.porcentaje_comision*s.precio_venta)/100),'999G999G999')) as comision,\n"
            + "TRIM(to_char((s.precio_venta-((s.porcentaje_comision*s.precio_venta)/100)),'999G999G999')) as ganancia\n"
            + "from servicio s,servicio_categoria sc,funcionario f \n"
            + "where s.fk_idservicio_categoria=sc.idservicio_categoria "
            + "and s.fk_idfuncionario=f.idfuncionario ";
    private String sql_select_orden = "order by 1 desc;";
    private String sql_cargar = "SELECT idservicio,fecha_creado,creado_por,orden,activo,cod_barra,"
            + "nombre,descripcion,precio_venta,precio_compra,porcentaje_comision,duracion_hora,duracion_minuto,"
            + "fk_idservicio_categoria,fk_idfuncionario FROM servicio WHERE idservicio=";
    private String sql_select_venta = "select s.idservicio as ids,(sc.nombre||'-'||s.nombre) as servicio,"
            + "TRIM(to_char(s.porcentaje_comision,'99')) as \"%\","
            + "f.nombre as funcionario,"
            + "TRIM(to_char(s.precio_venta,'999G999G999')) as precio,f.idfuncionario,s.precio_compra \n"
            + "from servicio s,servicio_categoria sc,funcionario f  \n"
            + "where s.fk_idservicio_categoria=sc.idservicio_categoria \n"
            + "and s.fk_idfuncionario=f.idfuncionario \n"
            + "and s.activo=true \n";
    private String sql_select_ord = " order by s.orden desc;";

    public void insertar_servicio(Connection conn, servicio serv) {
        serv.setC1idservicio(eveconn.getInt_ultimoID_mas_uno(conn, serv.getTb_servicio(), serv.getId_idservicio()));
        String titulo = "insertar_servicio";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, serv.getC1idservicio());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, serv.getC3creado_por());
            pst.setInt(4, serv.getC4orden());
            pst.setBoolean(5, serv.getC5activo());
            pst.setString(6, serv.getC6cod_barra());
            pst.setString(7, serv.getC7nombre());
            pst.setString(8, serv.getC8descripcion());
            pst.setDouble(9, serv.getC9precio_venta());
            pst.setDouble(10, serv.getC10precio_compra());
            pst.setDouble(11, serv.getC11porcentaje_comision());
            pst.setInt(12, serv.getC12duracion_hora());
            pst.setInt(13, serv.getC13duracion_minuto());
            pst.setInt(14, serv.getC14fk_idservicio_categoria());
            pst.setInt(15, serv.getC15fk_idfuncionario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + serv.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + serv.toString(), titulo);
        }
    }

    public void update_servicio(Connection conn, servicio serv) {
        String titulo = "update_servicio";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, serv.getC3creado_por());
            pst.setInt(3, serv.getC4orden());
            pst.setBoolean(4, serv.getC5activo());
            pst.setString(5, serv.getC6cod_barra());
            pst.setString(6, serv.getC7nombre());
            pst.setString(7, serv.getC8descripcion());
            pst.setDouble(8, serv.getC9precio_venta());
            pst.setDouble(9, serv.getC10precio_compra());
            pst.setDouble(10, serv.getC11porcentaje_comision());
            pst.setInt(11, serv.getC12duracion_hora());
            pst.setInt(12, serv.getC13duracion_minuto());
            pst.setInt(13, serv.getC14fk_idservicio_categoria());
            pst.setInt(14, serv.getC15fk_idfuncionario());
            pst.setInt(15, serv.getC1idservicio());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + serv.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + serv.toString(), titulo);
        }
    }

    public void cargar_servicio(Connection conn, servicio serv, int idservicio) {
        String titulo = "Cargar_servicio";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idservicio, titulo);
            if (rs.next()) {
                serv.setC1idservicio(rs.getInt(1));
                serv.setC2fecha_creado(rs.getString(2));
                serv.setC3creado_por(rs.getString(3));
                serv.setC4orden(rs.getInt(4));
                serv.setC5activo(rs.getBoolean(5));
                serv.setC6cod_barra(rs.getString(6));
                serv.setC7nombre(rs.getString(7));
                serv.setC8descripcion(rs.getString(8));
                serv.setC9precio_venta(rs.getDouble(9));
                serv.setC10precio_compra(rs.getDouble(10));
                serv.setC11porcentaje_comision(rs.getDouble(11));
                serv.setC12duracion_hora(rs.getInt(12));
                serv.setC13duracion_minuto(rs.getInt(13));
                serv.setC14fk_idservicio_categoria(rs.getInt(14));
                serv.setC15fk_idfuncionario(rs.getInt(15));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + serv.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + serv.toString(), titulo);
        }
    }

    public void actualizar_tabla_servicio(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select + sql_select_orden, tbltabla);
        ancho_tabla_servicio(tbltabla);
    }

    public void ancho_tabla_servicio(JTable tbltabla) {
        int Ancho[] = {5, 5, 20,12, 20, 10, 8, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void buscar_tabla_servicio(Connection conn, JTable tbltabla, JTextField txtbuscar, String columna) {
        if (txtbuscar.getText().trim().length() > 1) {
            String buscar = txtbuscar.getText();
            String sql = sql_select
                    + " and " + columna + " ilike'%" + buscar + "%' "
                    + sql_select_orden;
            eveconn.Select_cargar_jtable(conn, sql, tbltabla);
            ancho_tabla_servicio(tbltabla);
        }
    }

    public void actualizar_tabla_servicio_venta(Connection conn, JTable tbltabla,String filtro) {
        String sql = sql_select_venta
                + filtro
                + sql_select_ord;
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_servicio_venta(tbltabla);
    }

    public void ancho_tabla_servicio_venta(JTable tbltabla) {
        int Ancho[] = {5,50,5,25, 15,1,1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 5);
        evejt.ocultar_columna(tbltabla, 6);
    }
}
