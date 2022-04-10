package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.funcionario_recibo;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_funcionario_recibo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "FUNCIONARIO_RECIBO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "FUNCIONARIO_RECIBO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO funcionario_recibo(idfuncionario_recibo,fecha_creado,creado_por,descripcion,monto_recibo,monto_letra,estado,pago_comision,pago_salario,fk_idfuncionario,fk_idfuncionario_grupo_comision,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE funcionario_recibo SET fecha_creado=?,creado_por=?,descripcion=?,monto_recibo=?,monto_letra=?,estado=?,pago_comision=?,pago_salario=?,fk_idfuncionario=?,fk_idfuncionario_grupo_comision=?,fk_idusuario=? WHERE idfuncionario_recibo=?;";
    private String sql_select = "select fr.idfuncionario_recibo as idfr,to_char(fr.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
            + "fr.descripcion,(f.nombre||'-'||f.apellido) as funcionario,\n"
            + "fr.estado,trim(to_char(fr.monto_recibo,'999G999G999')) as monto \n"
            + "from funcionario_recibo fr,funcionario f  \n"
            + "where fr.fk_idfuncionario=f.idfuncionario  \n"
            + "and fr.fk_idfuncionario=";
    private String sql_cargar = "SELECT idfuncionario_recibo,fecha_creado,creado_por,descripcion,monto_recibo,monto_letra,estado,pago_comision,pago_salario,fk_idfuncionario,fk_idfuncionario_grupo_comision,fk_idusuario FROM funcionario_recibo WHERE idfuncionario_recibo=";

    public void insertar_funcionario_recibo(Connection conn, funcionario_recibo funrec) {
        funrec.setC1idfuncionario_recibo(eveconn.getInt_ultimoID_mas_uno(conn, funrec.getTb_funcionario_recibo(), funrec.getId_idfuncionario_recibo()));
        String titulo = "insertar_funcionario_recibo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, funrec.getC1idfuncionario_recibo());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, funrec.getC3creado_por());
            pst.setString(4, funrec.getC4descripcion());
            pst.setDouble(5, funrec.getC5monto_recibo());
            pst.setString(6, funrec.getC6monto_letra());
            pst.setString(7, funrec.getC7estado());
            pst.setBoolean(8, funrec.getC8pago_comision());
            pst.setBoolean(9, funrec.getC9pago_salario());
            pst.setInt(10, funrec.getC10fk_idfuncionario());
            pst.setInt(11, funrec.getC11fk_idfuncionario_grupo_comision());
            pst.setInt(12, funrec.getC12fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + funrec.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + funrec.toString(), titulo);
        }
    }

    public void update_funcionario_recibo(Connection conn, funcionario_recibo funrec) {
        String titulo = "update_funcionario_recibo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, funrec.getC3creado_por());
            pst.setString(3, funrec.getC4descripcion());
            pst.setDouble(4, funrec.getC5monto_recibo());
            pst.setString(5, funrec.getC6monto_letra());
            pst.setString(6, funrec.getC7estado());
            pst.setBoolean(7, funrec.getC8pago_comision());
            pst.setBoolean(8, funrec.getC9pago_salario());
            pst.setInt(9, funrec.getC10fk_idfuncionario());
            pst.setInt(10, funrec.getC11fk_idfuncionario_grupo_comision());
            pst.setInt(11, funrec.getC12fk_idusuario());
            pst.setInt(12, funrec.getC1idfuncionario_recibo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + funrec.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + funrec.toString(), titulo);
        }
    }

    public void cargar_funcionario_recibo(Connection conn, funcionario_recibo funrec, int idfuncionario_recibo) {
        String titulo = "Cargar_funcionario_recibo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idfuncionario_recibo, titulo);
            if (rs.next()) {
                funrec.setC1idfuncionario_recibo(rs.getInt(1));
                funrec.setC2fecha_creado(rs.getString(2));
                funrec.setC3creado_por(rs.getString(3));
                funrec.setC4descripcion(rs.getString(4));
                funrec.setC5monto_recibo(rs.getDouble(5));
                funrec.setC6monto_letra(rs.getString(6));
                funrec.setC7estado(rs.getString(7));
                funrec.setC8pago_comision(rs.getBoolean(8));
                funrec.setC9pago_salario(rs.getBoolean(9));
                funrec.setC10fk_idfuncionario(rs.getInt(10));
                funrec.setC11fk_idfuncionario_grupo_comision(rs.getInt(11));
                funrec.setC12fk_idusuario(rs.getInt(12));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + funrec.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + funrec.toString(), titulo);
        }
    }

    public void actualizar_tabla_funcionario_recibo(Connection conn, JTable tbltabla, int fk_idfuncionario) {
        String sql = sql_select + fk_idfuncionario + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_funcionario_recibo(tbltabla);
    }

    public void ancho_tabla_funcionario_recibo(JTable tbltabla) {
        int Ancho[] = {5, 15, 30,30, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

}
