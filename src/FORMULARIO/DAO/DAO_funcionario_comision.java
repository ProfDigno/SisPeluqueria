package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import CONFIGURACION.Global_datos;
import FORMULARIO.ENTIDAD.funcionario_comision;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_funcionario_comision {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private Global_datos gda = new Global_datos();
    private String mensaje_insert = "FUNCIONARIO_COMISION GUARDADO CORRECTAMENTE";
    private String mensaje_update = "FUNCIONARIO_COMISION MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO funcionario_comision(idfuncionario_comision,fecha_creado,creado_por,"
            + "fecha_pagado,monto_comision,monto_pagado,estado,descripcion,es_pagado,"
            + "fk_idfuncionario_grupo_comision,fk_iditem_venta,fk_idfuncionario,fk_idventa) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE funcionario_comision SET fecha_creado=?,creado_por=?,"
            + "fecha_pagado=?,monto_comision=?,monto_pagado=?,estado=?,descripcion=?,es_pagado=?,"
            + "fk_idfuncionario_grupo_comision=?,fk_iditem_venta=?,fk_idfuncionario=?,fk_idventa=? WHERE idfuncionario_comision=?;";
    private String sql_select = "SELECT idfuncionario_comision,fecha_creado,creado_por,"
            + "fecha_pagado,monto_comision,monto_pagado,estado,descripcion,es_pagado,"
            + "fk_idfuncionario_grupo_comision,fk_iditem_venta,fk_idfuncionario,fk_idventa FROM funcionario_comision order by 1 desc;";
    private String sql_cargar = "SELECT idfuncionario_comision,fecha_creado,creado_por,"
            + "fecha_pagado,monto_comision,monto_pagado,estado,descripcion,es_pagado,"
            + "fk_idfuncionario_grupo_comision,fk_iditem_venta,fk_idfuncionario,fk_idventa FROM funcionario_comision WHERE idfuncionario_comision=";
    private String sql_update_cerrar = "UPDATE funcionario_comision "
            + "SET fecha_pagado=?,monto_pagado=?,estado=?,es_pagado=?"
            + " WHERE idfuncionario_comision=?;";

    public void insertar_funcionario_comision(Connection conn, funcionario_comision funco) {
        funco.setC1idfuncionario_comision(eveconn.getInt_ultimoID_mas_uno(conn, funco.getTb_funcionario_comision(), funco.getId_idfuncionario_comision()));
        String titulo = "insertar_funcionario_comision";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, funco.getC1idfuncionario_comision());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, funco.getC3creado_por());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setDouble(5, funco.getC5monto_comision());
            pst.setDouble(6, funco.getC6monto_pagado());
            pst.setString(7, funco.getC7estado());
            pst.setString(8, funco.getC8descripcion());
            pst.setBoolean(9, funco.getC9es_pagado());
            pst.setInt(10, funco.getC10fk_idfuncionario_grupo_comision());
            pst.setInt(11, funco.getC11fk_iditem_venta());
            pst.setInt(12, funco.getC12fk_idfuncionario());
            pst.setInt(13, funco.getC13fk_idventa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + funco.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + funco.toString(), titulo);
        }
    }

    public void update_funcionario_comision(Connection conn, funcionario_comision funco) {
        String titulo = "update_funcionario_comision";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, funco.getC3creado_por());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setDouble(4, funco.getC5monto_comision());
            pst.setDouble(5, funco.getC6monto_pagado());
            pst.setString(6, funco.getC7estado());
            pst.setString(7, funco.getC8descripcion());
            pst.setBoolean(8, funco.getC9es_pagado());
            pst.setInt(9, funco.getC10fk_idfuncionario_grupo_comision());
            pst.setInt(10, funco.getC11fk_iditem_venta());
            pst.setInt(11, funco.getC12fk_idfuncionario());
            pst.setInt(12, funco.getC13fk_idventa());
            pst.setInt(13, funco.getC1idfuncionario_comision());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + funco.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + funco.toString(), titulo);
        }
    }

    public void cargar_funcionario_comision(Connection conn, funcionario_comision funco, int idfuncionario_comision) {
        String titulo = "Cargar_funcionario_comision";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idfuncionario_comision, titulo);
            if (rs.next()) {
                funco.setC1idfuncionario_comision(rs.getInt(1));
                funco.setC2fecha_creado(rs.getString(2));
                funco.setC3creado_por(rs.getString(3));
                funco.setC4fecha_pagado(rs.getString(4));
                funco.setC5monto_comision(rs.getDouble(5));
                funco.setC6monto_pagado(rs.getDouble(6));
                funco.setC7estado(rs.getString(7));
                funco.setC8descripcion(rs.getString(8));
                funco.setC9es_pagado(rs.getBoolean(9));
                funco.setC10fk_idfuncionario_grupo_comision(rs.getInt(10));
                funco.setC11fk_iditem_venta(rs.getInt(11));
                funco.setC12fk_idfuncionario(rs.getInt(12));
                funco.setC13fk_idventa(rs.getInt(13));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + funco.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + funco.toString(), titulo);
        }
    }

    public void actualizar_tabla_funcionario_comision(Connection conn, JTable tbltabla, int fk_idfuncionario_grupo_comision) {
        String sql = "select fc.idfuncionario_comision as idf,fc.fk_idventa as ven,\n"
                + "to_char(fc.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,fc.descripcion,\n"
                + "TRIM(to_char(fc.monto_comision,'999G999G999')) as comision,"
                + "TRIM(to_char(fc.monto_pagado,'999G999G999')) as pagado,"
                + "fc.estado,\n"
                + "case when fc.es_pagado=true then to_char(fc.fecha_pagado,'yyyy-MM-dd HH24:MI') else 'FALTA PAGAR' end as fecha   \n"
                + "from funcionario_comision fc \n"
                + "where fc.fk_idfuncionario_grupo_comision=" + fk_idfuncionario_grupo_comision + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_funcionario_comision(tbltabla);
    }

    public void ancho_tabla_funcionario_comision(JTable tbltabla) {
        int Ancho[] = {5, 5, 15, 33, 9, 9, 8, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void update_total_comision_funcionario(Connection conn, int fk_idfuncionario) {
        String sql = "update funcionario set total_comision=(select coalesce(sum(fc.monto_comision-fc.monto_pagado),0) as saldo\n"
                + "from funcionario_comision fc,funcionario_grupo_comision fgc  \n"
                + "where fgc.idfuncionario_grupo_comision=fc.fk_idfuncionario_grupo_comision  \n"
                + "and fgc.es_abierto=true \n"
                + "and fc.estado='" + gda.getEstado_abierto() + "' \n"
                + "and fgc.fk_idfuncionario=" + fk_idfuncionario + ") where idfuncionario=" + fk_idfuncionario + "; ";
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void update_total_comision_funcionario_de_venta(Connection conn, int fk_idveta) {
        String sql = "update funcionario  set total_comision=(select coalesce(sum(fc.monto_comision-fc.monto_pagado),0) as saldo\n"
                + "from funcionario_comision fc,funcionario_grupo_comision fgc  \n"
                + "where fgc.idfuncionario_grupo_comision=fc.fk_idfuncionario_grupo_comision  \n"
                + "and fgc.es_abierto=true \n"
                + "and fc.estado='" + gda.getEstado_abierto() + "' \n"
                + "and fgc.fk_idfuncionario=iv.fk_idfuncionario) \n"
                + "from item_venta iv\n"
                + "where iv.fk_idfuncionario=idfuncionario \n"
                + "and  idfuncionario=iv.fk_idfuncionario\n"
                + "and iv.fk_idventa="+fk_idveta;
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void update_funcionario_comision_PAGAR(Connection conn, funcionario_comision funco) {
        String titulo = "update_funcionario_comision_PAGAR";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_cerrar);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setDouble(2, funco.getC6monto_pagado());
            pst.setString(3, funco.getC7estado());
            pst.setBoolean(4, funco.getC9es_pagado());
            pst.setInt(5, funco.getC1idfuncionario_comision());
            pst.execute();
            pst.close();
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_cerrar + "\n" + funco.toString(), titulo);
        }
    }

    public void anular_update_funcionario_comision(Connection conn, funcionario_comision funco) {
        String titulo = "anular_update_funcionario_comision";
        String sql = "UPDATE funcionario_comision SET estado=? WHERE fk_idventa=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, funco.getC7estado());
            pst.setInt(2, funco.getC13fk_idventa());
            pst.execute();
            pst.close();
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n" + funco.toString(), titulo);
        }
    }

}
