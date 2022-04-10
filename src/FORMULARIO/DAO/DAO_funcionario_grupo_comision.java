package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import CONFIGURACION.Global_datos;
import FORMULARIO.ENTIDAD.funcionario_grupo_comision;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class DAO_funcionario_grupo_comision {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private Global_datos gda = new Global_datos();
    private String mensaje_insert = "FUNCIONARIO_GRUPO_COMISION GUARDADO CORRECTAMENTE";
    private String mensaje_update = "FUNCIONARIO_GRUPO_COMISION MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO funcionario_grupo_comision(idfuncionario_grupo_comision,fecha_creado,creado_por,"
            + "fecha_inicio,fecha_fin,estado,es_abierto,fk_idfuncionario) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE funcionario_grupo_comision SET fecha_creado=?,creado_por=?,"
            + "fecha_inicio=?,fecha_fin=?,estado=?,es_abierto=?,fk_idfuncionario=? WHERE idfuncionario_grupo_comision=?;";
    private String sql_select = "SELECT idfuncionario_grupo_comision,fecha_creado,creado_por,"
            + "fecha_inicio,fecha_fin,estado,es_abierto,fk_idfuncionario FROM funcionario_grupo_comision order by 1 desc;";
    private String sql_cargar = "SELECT idfuncionario_grupo_comision,fecha_creado,creado_por,"
            + "fecha_inicio,fecha_fin,estado,es_abierto,fk_idfuncionario FROM funcionario_grupo_comision WHERE idfuncionario_grupo_comision=";

    private void crear_funcionario_grupo_comision_nuevo(Connection conn, int fk_idfuncionario) {
        funcionario_grupo_comision fungc = new funcionario_grupo_comision();
        fungc.setC3creado_por(gda.getCreado_por());
        fungc.setC6estado(gda.getEstado_abierto());
        fungc.setC7es_abierto(true);
        fungc.setC8fk_idfuncionario(fk_idfuncionario);
        insertar_funcionario_grupo_comision(conn, fungc);
    }

    public void insertar_funcionario_grupo_comision(Connection conn, funcionario_grupo_comision fungc) {
        fungc.setC1idfuncionario_grupo_comision(eveconn.getInt_ultimoID_mas_uno(conn, fungc.getTb_funcionario_grupo_comision(), fungc.getId_idfuncionario_grupo_comision()));
        String titulo = "insertar_funcionario_grupo_comision";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, fungc.getC1idfuncionario_grupo_comision());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, fungc.getC3creado_por());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setTimestamp(5, evefec.getTimestamp_sistema());
            pst.setString(6, fungc.getC6estado());
            pst.setBoolean(7, fungc.getC7es_abierto());
            pst.setInt(8, fungc.getC8fk_idfuncionario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + fungc.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + fungc.toString(), titulo);
        }
    }

    public void update_funcionario_grupo_comision(Connection conn, funcionario_grupo_comision fungc) {
        String titulo = "update_funcionario_grupo_comision";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, fungc.getC3creado_por());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setString(5, fungc.getC6estado());
            pst.setBoolean(6, fungc.getC7es_abierto());
            pst.setInt(7, fungc.getC8fk_idfuncionario());
            pst.setInt(8, fungc.getC1idfuncionario_grupo_comision());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + fungc.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + fungc.toString(), titulo);
        }
    }

    public void cargar_funcionario_grupo_comision(Connection conn, funcionario_grupo_comision fungc, int idfuncionario_grupo_comision) {
        String titulo = "Cargar_funcionario_grupo_comision";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idfuncionario_grupo_comision, titulo);
            if (rs.next()) {
                fungc.setC1idfuncionario_grupo_comision(rs.getInt(1));
                fungc.setC2fecha_creado(rs.getString(2));
                fungc.setC3creado_por(rs.getString(3));
                fungc.setC4fecha_inicio(rs.getString(4));
                fungc.setC5fecha_fin(rs.getString(5));
                fungc.setC6estado(rs.getString(6));
                fungc.setC7es_abierto(rs.getBoolean(7));
                fungc.setC8fk_idfuncionario(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + fungc.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + fungc.toString(), titulo);
        }
    }

    public void actualizar_tabla_funcionario_grupo_comision(Connection conn, JTable tbltabla, int fk_idfuncionario) {
        String sql = "select fgc.idfuncionario_grupo_comision as idg,\n"
                + "(f.nombre||' '||f.apellido) as funcionario,f.cedula, \n"
                + "to_char(fgc.fecha_inicio,'yyyy-MM-dd HH24:MI') as fec_inicio,\n"
                + "to_char(fgc.fecha_fin ,'yyyy-MM-dd HH24:MI') as fec_fin,\n"
                + "fgc.estado \n"
                + "from funcionario_grupo_comision fgc,funcionario f  \n"
                + "where fgc.fk_idfuncionario=f.idfuncionario \n"
                + "and fgc.fk_idfuncionario=" + fk_idfuncionario + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_funcionario_grupo_comision(tbltabla);
    }

    public void ancho_tabla_funcionario_grupo_comision(JTable tbltabla) {
        int Ancho[] = {10, 40, 10, 15, 15, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void crear_si_no_existe_funcionario_grupo_comision(Connection conn, int fk_idfuncionario) {
        String titulo = "Cargar_funcionario_grupo_comision";
        String sql = "select count(*) as cant \n"
                + "from funcionario_grupo_comision fgc \n"
                + "where fgc.fk_idfuncionario=" + fk_idfuncionario
                + " and fgc.es_abierto=true;";

        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                int cantidad = rs.getInt("cant");
                if (cantidad == 0) {
                    crear_funcionario_grupo_comision_nuevo(conn, fk_idfuncionario);
                }
                if (cantidad == 1) {

                }
                if (cantidad > 1) {
                    JOptionPane.showMessageDialog(null, "SE ENCONTRO MAS DE UNO funcionario_grupo_comision ABIERTO PARA ESTE FUNCIONARIO");
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n", titulo);
        }
    }

    public int getInt_id_abierto_funcionario_grupo_comision(Connection conn, int fk_idfuncionario) {
        int idfuncionario_grupo_comision = 0;
        if (fk_idfuncionario > 0) {
            String titulo = "getInt_id_abierto_funcionario_grupo_comision";
            String sql = "select coalesce(fgc.idfuncionario_grupo_comision,0) as idfuncionario_grupo_comision  \n"
                    + "from funcionario_grupo_comision fgc \n"
                    + "where fgc.fk_idfuncionario=" + fk_idfuncionario
                    + " and fgc.es_abierto=true;";

            try {
                ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
                boolean si_grupo = true;
                if (rs.next()) {
                    idfuncionario_grupo_comision = rs.getInt("idfuncionario_grupo_comision");
                    si_grupo = false;
                }
                if (si_grupo) {
                    JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN GRUPO DE COMISION PARA ESTE FUNCIONARIO");
                    idfuncionario_grupo_comision = 0;
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, sql + "\n", titulo);
            }
        }
        return idfuncionario_grupo_comision;
    }
    public void update_funcionario_grupo_comision_CERRAR(Connection conn, funcionario_grupo_comision fungc) {
        String titulo = "update_funcionario_grupo_comision_CERRAR";
        String sql_update_cerrar = "UPDATE funcionario_grupo_comision SET fecha_fin=?,estado=?,es_abierto=? "
                + "WHERE idfuncionario_grupo_comision=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_cerrar);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, fungc.getC6estado());
            pst.setBoolean(3, fungc.getC7es_abierto());
            pst.setInt(4, fungc.getC1idfuncionario_grupo_comision());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_cerrar + "\n" + fungc.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_cerrar + "\n" + fungc.toString(), titulo);
        }
    }
}
