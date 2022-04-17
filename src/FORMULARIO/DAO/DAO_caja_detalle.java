package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import CONFIGURACION.Global_datos;
import FORMULARIO.ENTIDAD.caja_detalle;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_caja_detalle {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private Global_datos gda=new Global_datos();
    EvenFecha evefec = new EvenFecha();
//    String fecha_creado="now()";
//    String creado_por="sin-dato";
    private String mensaje_insert = "CAJA_DETALLE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CAJA_DETALLE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO caja_detalle(idcaja_detalle,fecha_creado,creado_por,descripcion,"
            + "tabla_origen,estado,monto_cierre,cierre,"
            + "in_monto_apertura,in_monto_venta,"
            + "eg_monto_recibo_funcionario,eg_monto_gasto,eg_monto_compra,"
            + "fk_idventa,fk_idfuncionario_recibo,fk_idgasto,fk_idcompra,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE caja_detalle SET fecha_creado=?,creado_por=?,descripcion=?,"
            + "tabla_origen=?,estado=?,monto_cierre=?,cierre=?,"
            + "in_monto_apertura=?,in_monto_venta=?,"
            + "eg_monto_recibo_funcionario=?,eg_monto_gasto=?,eg_monto_compra=?,"
            + "fk_idventa=?,fk_idfuncionario_recibo=?,fk_idgasto=?,fk_idcompra=?,fk_idusuario=? WHERE idcaja_detalle=?;";
    private String sql_select = "SELECT idcaja_detalle,fecha_creado,creado_por,descripcion,tabla_origen,estado,"
            + "monto_cierre,cierre,"
            + "in_monto_apertura,in_monto_venta,"
            + "eg_monto_recibo_funcionario,eg_monto_gasto,eg_monto_compra,"
            + "fk_idventa,fk_idfuncionario_recibo,fk_idgasto,fk_idcompra,fk_idusuario "
            + "FROM caja_detalle order by 1 desc;";
    private String sql_cargar = "SELECT idcaja_detalle,fecha_creado,creado_por,descripcion,"
            + "tabla_origen,estado,monto_cierre,cierre,"
            + "in_monto_apertura,in_monto_venta,"
            + "eg_monto_recibo_funcionario,eg_monto_gasto,eg_monto_compra,"
            + "fk_idventa,fk_idfuncionario_recibo,fk_idgasto,fk_idcompra,fk_idusuario FROM caja_detalle WHERE idcaja_detalle=";

    public void insertar_caja_detalle(Connection conn, caja_detalle cjdet) {
        cjdet.setC1idcaja_detalle(eveconn.getInt_ultimoID_mas_uno(conn, cjdet.getTb_caja_detalle(), cjdet.getId_idcaja_detalle()));
        String titulo = "insertar_caja_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, cjdet.getC1idcaja_detalle());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, cjdet.getC3creado_por());
            pst.setString(4, cjdet.getC4descripcion());
            pst.setString(5, cjdet.getC5tabla_origen());
            pst.setString(6, cjdet.getC6estado());
            pst.setDouble(7, cjdet.getC7monto_cierre());
            pst.setString(8, cjdet.getC8cierre());
            pst.setDouble(9, cjdet.getC9in_monto_apertura());
            pst.setDouble(10, cjdet.getC10in_monto_venta());
            pst.setDouble(11, cjdet.getC11eg_monto_recibo_funcionario());
            pst.setDouble(12, cjdet.getC12eg_monto_gasto());
            pst.setDouble(13, cjdet.getC13eg_monto_compra());
            pst.setInt(14, cjdet.getC14fk_idventa());
            pst.setInt(15, cjdet.getC15fk_idfuncionario_recibo());
            pst.setInt(16, cjdet.getC16fk_idgasto());
            pst.setInt(17, cjdet.getC17fk_idcompra());
            pst.setInt(18, cjdet.getC18fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + cjdet.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + cjdet.toString(), titulo);
        }
    }

    public void update_caja_detalle(Connection conn, caja_detalle cjdet) {
        String titulo = "update_caja_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, cjdet.getC3creado_por());
            pst.setString(3, cjdet.getC4descripcion());
            pst.setString(4, cjdet.getC5tabla_origen());
            pst.setString(5, cjdet.getC6estado());
            pst.setDouble(6, cjdet.getC7monto_cierre());
            pst.setString(7, cjdet.getC8cierre());
            pst.setDouble(8, cjdet.getC9in_monto_apertura());
            pst.setDouble(9, cjdet.getC10in_monto_venta());
            pst.setDouble(10, cjdet.getC11eg_monto_recibo_funcionario());
            pst.setDouble(11, cjdet.getC12eg_monto_gasto());
            pst.setDouble(12, cjdet.getC13eg_monto_compra());
            pst.setInt(13, cjdet.getC14fk_idventa());
            pst.setInt(14, cjdet.getC15fk_idfuncionario_recibo());
            pst.setInt(15, cjdet.getC16fk_idgasto());
            pst.setInt(16, cjdet.getC17fk_idcompra());
            pst.setInt(17, cjdet.getC18fk_idusuario());
            pst.setInt(18, cjdet.getC1idcaja_detalle());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cjdet.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cjdet.toString(), titulo);
        }
    }

    public void cargar_caja_detalle(Connection conn, caja_detalle cjdet, int idcaja_detalle) {
        String titulo = "Cargar_caja_detalle";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcaja_detalle, titulo);
            if (rs.next()) {
                cjdet.setC1idcaja_detalle(rs.getInt(1));
                cjdet.setC2fecha_creado(rs.getString(2));
                cjdet.setC3creado_por(rs.getString(3));
                cjdet.setC4descripcion(rs.getString(4));
                cjdet.setC5tabla_origen(rs.getString(5));
                cjdet.setC6estado(rs.getString(6));
                cjdet.setC7monto_cierre(rs.getDouble(7));
                cjdet.setC8cierre(rs.getString(8));
                cjdet.setC9in_monto_apertura(rs.getDouble(9));
                cjdet.setC10in_monto_venta(rs.getDouble(10));
                cjdet.setC11eg_monto_recibo_funcionario(rs.getDouble(11));
                cjdet.setC12eg_monto_gasto(rs.getDouble(12));
                cjdet.setC13eg_monto_compra(rs.getDouble(13));
                cjdet.setC14fk_idventa(rs.getInt(14));
                cjdet.setC15fk_idfuncionario_recibo(rs.getInt(15));
                cjdet.setC16fk_idgasto(rs.getInt(16));
                cjdet.setC17fk_idcompra(rs.getInt(17));
                cjdet.setC18fk_idusuario(rs.getInt(18));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + cjdet.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cjdet.toString(), titulo);
        }
    }

    public void actualizar_tabla_caja_detalle(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_caja_detalle(tbltabla);
    }

    public void ancho_tabla_caja_detalle(JTable tbltabla) {
        int Ancho[] = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void vaciar_caja_detalle(caja_detalle cjdet) {
        cjdet.setC1idcaja_detalle(0);
//        cjdet.setC2fecha_creado(gda.get);
        cjdet.setC3creado_por(gda.getCreado_por());
        cjdet.setC4descripcion("ninguna");
        cjdet.setC5tabla_origen("sin-tabla");
        cjdet.setC6estado("sin-estado");
        cjdet.setC7monto_cierre(0);
        cjdet.setC8cierre(gda.getCaja_abierto());
        cjdet.setC9in_monto_apertura(0);
        cjdet.setC10in_monto_venta(0);
        cjdet.setC11eg_monto_recibo_funcionario(0);
        cjdet.setC12eg_monto_gasto(0);
        cjdet.setC13eg_monto_compra(0);
        cjdet.setC14fk_idventa(0);
        cjdet.setC15fk_idfuncionario_recibo(0);
        cjdet.setC16fk_idgasto(0);
        cjdet.setC17fk_idcompra(0);
        cjdet.setC18fk_idusuario(gda.getFk_idusuario());
    }
    public void update_caja_detalle_CERRARTODO(Connection conn){
        String titulo = "update_caja_detalle_CERRARTODO";
        String sql_update_cerrartodo="update caja_detalle set cierre='"+gda.getCaja_cierre()+"' "
                + "where cierre='"+gda.getCaja_abierto()+"' ";
        eveconn.SQL_execute_libre(conn, sql_update_cerrartodo);
    }
    public void anular_venta_update_caja_detalle(Connection conn, caja_detalle cjdet) {
        String titulo = "anular_venta_update_caja_detalle";
        String sql_update = "UPDATE caja_detalle SET estado=?,cierre=? WHERE fk_idventa=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, cjdet.getC6estado());
            pst.setString(2, cjdet.getC8cierre());
            pst.setInt(3, cjdet.getC14fk_idventa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cjdet.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cjdet.toString(), titulo);
        }
    }
    public void anular_compra_update_caja_detalle(Connection conn, caja_detalle cjdet) {
        String titulo = "anular_compra_update_caja_detalle";
        String sql_update = "UPDATE caja_detalle SET estado=?,cierre=? WHERE fk_idcompra=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, cjdet.getC6estado());
            pst.setString(2, cjdet.getC8cierre());
            pst.setInt(3, cjdet.getC17fk_idcompra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cjdet.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cjdet.toString(), titulo);
        }
    }
}
