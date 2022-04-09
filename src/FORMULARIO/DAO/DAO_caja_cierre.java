package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import CONFIGURACION.Global_datos;
import FORMULARIO.ENTIDAD.caja_cierre;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class DAO_caja_cierre {

    private EvenConexion eveconn = new EvenConexion();
    private EvenJtable evejt = new EvenJtable();
    private EvenJasperReport rep = new EvenJasperReport();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenFecha evefec = new EvenFecha();
    private Global_datos gda = new Global_datos();
    private String mensaje_insert = "CAJA_CIERRE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CAJA_CIERRE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO caja_cierre(idcaja_cierre,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE caja_cierre SET fecha_creado=?,creado_por=?,fecha_fin=?,estado=? WHERE idcaja_cierre=?;";
    private String sql_select = "select cc.idcaja_cierre as idcc,\n"
            + "to_char(cc.fecha_inicio,'yyyy-MM-dd HH24:MI') as inicio,\n"
            + "to_char(cc.fecha_fin,'yyyy-MM-dd HH24:MI') as fin,\n"
            + "cc.estado,\n"
            + "TRIM(to_char(sum(cd.in_monto_apertura),'999G999G999')) as apertura,\n"
            + "TRIM(to_char(sum(cd.in_monto_venta),'999G999G999')) as venta,\n"
            + "TRIM(to_char(sum(cd.eg_monto_recibo_funcionario),'999G999G999')) as funcionario,\n"
            + "TRIM(to_char(sum(cd.eg_monto_gasto),'999G999G999')) as gasto,\n"
            + "TRIM(to_char(sum(cd.eg_monto_compra),'999G999G999')) as compra,\n"
            + "TRIM(to_char(sum(cd.monto_cierre),'999G999G999')) as cierre,\n"
            + "TRIM(to_char((sum(cd.monto_cierre)-((sum(cd.in_monto_apertura+cd.in_monto_venta))-\n"
            + "(sum(cd.eg_monto_recibo_funcionario+cd.eg_monto_gasto+cd.eg_monto_compra)))),'999G999G999')) as diferencia\n"
            + "from caja_cierre cc,caja_cierre_item cci,caja_detalle cd  \n"
            + "where cc.idcaja_cierre=cci.fk_idcaja_cierre \n"
            + "and cci.fk_idcaja_detalle=cd.idcaja_detalle \n"
            + "group by 1,2,3,4\n"
            + "order by cc.idcaja_cierre desc;";
    private String sql_cargar = "SELECT idcaja_cierre,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado FROM caja_cierre WHERE idcaja_cierre=";

    public void insertar_caja_cierre(Connection conn, caja_cierre cjcie) {
        cjcie.setC1idcaja_cierre(eveconn.getInt_ultimoID_mas_uno(conn, cjcie.getTb_caja_cierre(), cjcie.getId_idcaja_cierre()));
        String titulo = "insertar_caja_cierre";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, cjcie.getC1idcaja_cierre());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, cjcie.getC3creado_por());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setTimestamp(5, evefec.getTimestamp_sistema());
            pst.setString(6, cjcie.getC6estado());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + cjcie.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + cjcie.toString(), titulo);
        }
    }

    public void update_caja_cierre(Connection conn, caja_cierre cjcie) {
        String titulo = "update_caja_cierre";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, cjcie.getC3creado_por());
//            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setString(4, cjcie.getC6estado());
            pst.setInt(5, cjcie.getC1idcaja_cierre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cjcie.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cjcie.toString(), titulo);
        }
    }

    public void cargar_caja_cierre(Connection conn, caja_cierre cjcie, int idcaja_cierre) {
        String titulo = "Cargar_caja_cierre";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcaja_cierre, titulo);
            if (rs.next()) {
                cjcie.setC1idcaja_cierre(rs.getInt(1));
                cjcie.setC2fecha_creado(rs.getString(2));
                cjcie.setC3creado_por(rs.getString(3));
                cjcie.setC4fecha_inicio(rs.getString(4));
                cjcie.setC5fecha_fin(rs.getString(5));
                cjcie.setC6estado(rs.getString(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + cjcie.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cjcie.toString(), titulo);
        }
    }

    public void actualizar_tabla_caja_cierre(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_caja_cierre(tbltabla);
    }

    public void ancho_tabla_caja_cierre(JTable tbltabla) {
        int Ancho[] = {3, 13, 13, 8, 9, 9,9,9,9,9,9};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public boolean getboo_existe_abierto_cargar_caja_cierre(Connection conn) {
        boolean existe = false;
        String titulo = "getboo_existe_abierto_cargar_caja_cierre";
        String sql = "select count(*) as cant "
                + "from caja_cierre cc "
                + "where cc.estado='" + gda.getEstado_abierto() + "';";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                int cantidad = rs.getInt(1);
                if (cantidad == 0) {
                    existe = true;
                }
                if (cantidad == 1) {
                    existe = false;
                }
                if (cantidad > 1) {
                    existe = false;
                    JOptionPane.showMessageDialog(null, "EXISTE MAS DE UNA CAJA ABIERTO");
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return existe;
    }
}
