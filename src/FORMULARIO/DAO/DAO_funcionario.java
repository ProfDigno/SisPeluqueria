package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.funcionario;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_funcionario {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "FUNCIONARIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "FUNCIONARIO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO funcionario(idfuncionario,fecha_creado,creado_por,orden,activo,"
            + "nombre,apellido,cedula,direccion,telefono,"
            + "tiene_comision,total_comision,tiene_salario,total_salario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE funcionario SET fecha_creado=?,creado_por=?,orden=?,activo=?,"
            + "nombre=?,apellido=?,cedula=?,direccion=?,telefono=?,"
            + "tiene_comision=?,total_comision=?,tiene_salario=?,total_salario=? WHERE idfuncionario=?;";
    private String sql_select = "SELECT idfuncionario as id,"
            + "(nombre||'-'||apellido) as nombre,direccion,cedula,telefono,"
            + "to_char(total_comision,'999G999G999') as comision FROM funcionario "; //5,45,20,10,10,10
    private String sql_select_ord=" desc;";
    private String sql_cargar = "SELECT idfuncionario,fecha_creado,creado_por,orden,activo,"
            + "nombre,apellido,cedula,direccion,telefono,"
            + "tiene_comision,total_comision,tiene_salario,total_salario FROM funcionario WHERE idfuncionario=";

    public void insertar_funcionario(Connection conn, funcionario fun) {
        fun.setC1idfuncionario(eveconn.getInt_ultimoID_mas_uno(conn, fun.getTb_funcionario(), fun.getId_idfuncionario()));
        String titulo = "insertar_funcionario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, fun.getC1idfuncionario());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, fun.getC3creado_por());
            pst.setInt(4, fun.getC4orden());
            pst.setBoolean(5, fun.getC5activo());
            pst.setString(6, fun.getC6nombre());
            pst.setString(7, fun.getC7apellido());
            pst.setString(8, fun.getC8cedula());
            pst.setString(9, fun.getC9direccion());
            pst.setString(10, fun.getC10telefono());
            pst.setBoolean(11, fun.getC11tiene_comision());
            pst.setDouble(12, fun.getC12total_comision());
            pst.setBoolean(13, fun.getC13tiene_salario());
            pst.setDouble(14, fun.getC14total_salario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + fun.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + fun.toString(), titulo);
        }
    }

    public void update_funcionario(Connection conn, funcionario fun) {
        String titulo = "update_funcionario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, fun.getC3creado_por());
            pst.setInt(3, fun.getC4orden());
            pst.setBoolean(4, fun.getC5activo());
            pst.setString(5, fun.getC6nombre());
            pst.setString(6, fun.getC7apellido());
            pst.setString(7, fun.getC8cedula());
            pst.setString(8, fun.getC9direccion());
            pst.setString(9, fun.getC10telefono());
            pst.setBoolean(10, fun.getC11tiene_comision());
            pst.setDouble(11, fun.getC12total_comision());
            pst.setBoolean(12, fun.getC13tiene_salario());
            pst.setDouble(13, fun.getC14total_salario());
            pst.setInt(14, fun.getC1idfuncionario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + fun.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + fun.toString(), titulo);
        }
    }

    public void cargar_funcionario(Connection conn, funcionario fun, int idfuncionario) {
        String titulo = "Cargar_funcionario";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idfuncionario, titulo);
            if (rs.next()) {
                fun.setC1idfuncionario(rs.getInt(1));
                fun.setC2fecha_creado(rs.getString(2));
                fun.setC3creado_por(rs.getString(3));
                fun.setC4orden(rs.getInt(4));
                fun.setC5activo(rs.getBoolean(5));
                fun.setC6nombre(rs.getString(6));
                fun.setC7apellido(rs.getString(7));
                fun.setC8cedula(rs.getString(8));
                fun.setC9direccion(rs.getString(9));
                fun.setC10telefono(rs.getString(10));
                fun.setC11tiene_comision(rs.getBoolean(11));
                fun.setC12total_comision(rs.getDouble(12));
                fun.setC13tiene_salario(rs.getBoolean(13));
                fun.setC14total_salario(rs.getDouble(14));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + fun.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + fun.toString(), titulo);
        }
    }

    public void actualizar_tabla_funcionario(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select+"order by 1 "+sql_select_ord, tbltabla);
        ancho_tabla_funcionario(tbltabla);
    }

    public void ancho_tabla_funcionario(JTable tbltabla) {
        int Ancho[] = {5,35,30,10,10,10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void buscar_tabla_funcionario(Connection conn, JTable tbltabla, JTextField txtbuscar,String columna) {
        if (txtbuscar.getText().trim().length() > 1) {
            String buscar = txtbuscar.getText();
            String sql = sql_select
                    + " where "+columna+" ilike'%" + buscar + "%' "
                    + "order by "+columna+sql_select_ord;
            eveconn.Select_cargar_jtable(conn, sql, tbltabla);
            ancho_tabla_funcionario(tbltabla);
        }
    }
}
