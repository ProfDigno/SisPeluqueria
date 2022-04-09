package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.venta;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_venta {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "VENTA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta(idventa,fecha_creado,creado_por,estado,forma_pago,monto_total,monto_comision,monto_descuento,monto_pagado,puntaje_cliente,genera_puntaje,uso_puntaje,fk_idcliente,fk_idconfiguracion_puntaje,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE venta SET fecha_creado=?,creado_por=?,estado=?,forma_pago=?,monto_total=?,monto_comision=?,monto_descuento=?,monto_pagado=?,puntaje_cliente=?,genera_puntaje=?,uso_puntaje=?,fk_idcliente=?,fk_idconfiguracion_puntaje=?,fk_idusuario=? WHERE idventa=?;";
    private String sql_select = "select v.idventa as idv,to_char(v.fecha_creado,'dd-MM-yyyy HH24:MI') as fecha,\n"
            + "(c.nombre||' '||c.apellido) as cliente,c.direccion,c.ruc,c.telefono,\n"
            + "trim(to_char(v.monto_comision,'999G999G999')) as comision,\n"
            + "trim(to_char(v.monto_descuento ,'999G999G999')) as descuento,\n"
            + "trim(to_char(v.monto_pagado ,'999G999G999')) as pagado,v.estado \n"
            + "from venta v,cliente c \n"
            + "where v.fk_idcliente=c.idcliente ";
    private String sql_cargar = "SELECT idventa,fecha_creado,creado_por,estado,forma_pago,"
            + "monto_total,monto_comision,monto_descuento,monto_pagado,"
            + "puntaje_cliente,genera_puntaje,uso_puntaje,"
            + "fk_idcliente,fk_idconfiguracion_puntaje,fk_idusuario "
            + "FROM venta WHERE idventa=";
    private String sql_select_orden=" order by 1 desc;";
    public void insertar_venta(Connection conn, venta ven) {
        ven.setC1idventa(eveconn.getInt_ultimoID_mas_uno(conn, ven.getTb_venta(), ven.getId_idventa()));
        String titulo = "insertar_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ven.getC1idventa());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ven.getC3creado_por());
            pst.setString(4, ven.getC4estado());
            pst.setString(5, ven.getC5forma_pago());
            pst.setDouble(6, ven.getC6monto_total());
            pst.setDouble(7, ven.getC7monto_comision());
            pst.setDouble(8, ven.getC8monto_descuento());
            pst.setDouble(9, ven.getC9monto_pagado());
            pst.setDouble(10, ven.getC10puntaje_cliente());
            pst.setBoolean(11, ven.getC11genera_puntaje());
            pst.setBoolean(12, ven.getC12uso_puntaje());
            pst.setInt(13, ven.getC13fk_idcliente());
            pst.setInt(14, ven.getC14fk_idconfiguracion_puntaje());
            pst.setInt(15, ven.getC15fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ven.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ven.toString(), titulo);
        }
    }

    public void update_venta(Connection conn, venta ven) {
        String titulo = "update_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ven.getC3creado_por());
            pst.setString(3, ven.getC4estado());
            pst.setString(4, ven.getC5forma_pago());
            pst.setDouble(5, ven.getC6monto_total());
            pst.setDouble(6, ven.getC7monto_comision());
            pst.setDouble(7, ven.getC8monto_descuento());
            pst.setDouble(8, ven.getC9monto_pagado());
            pst.setDouble(9, ven.getC10puntaje_cliente());
            pst.setBoolean(10, ven.getC11genera_puntaje());
            pst.setBoolean(11, ven.getC12uso_puntaje());
            pst.setInt(12, ven.getC13fk_idcliente());
            pst.setInt(13, ven.getC14fk_idconfiguracion_puntaje());
            pst.setInt(14, ven.getC15fk_idusuario());
            pst.setInt(15, ven.getC1idventa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ven.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ven.toString(), titulo);
        }
    }

    public void cargar_venta(Connection conn, venta ven, int idventa) {
        String titulo = "Cargar_venta";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idventa, titulo);
            if (rs.next()) {
                ven.setC1idventa(rs.getInt(1));
                ven.setC2fecha_creado(rs.getString(2));
                ven.setC3creado_por(rs.getString(3));
                ven.setC4estado(rs.getString(4));
                ven.setC5forma_pago(rs.getString(5));
                ven.setC6monto_total(rs.getDouble(6));
                ven.setC7monto_comision(rs.getDouble(7));
                ven.setC8monto_descuento(rs.getDouble(8));
                ven.setC9monto_pagado(rs.getDouble(9));
                ven.setC10puntaje_cliente(rs.getDouble(10));
                ven.setC11genera_puntaje(rs.getBoolean(11));
                ven.setC12uso_puntaje(rs.getBoolean(12));
                ven.setC13fk_idcliente(rs.getInt(13));
                ven.setC14fk_idconfiguracion_puntaje(rs.getInt(14));
                ven.setC15fk_idusuario(rs.getInt(15));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ven.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ven.toString(), titulo);
        }
    }

    public void actualizar_tabla_venta(Connection conn, JTable tbltabla,String filtro) {
        eveconn.Select_cargar_jtable(conn, sql_select+filtro+sql_select_orden, tbltabla);
        ancho_tabla_venta(tbltabla);
    }

    public void ancho_tabla_venta(JTable tbltabla) {
        int Ancho[] = {5,11,22,22,8,8,8,8,8,8};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
