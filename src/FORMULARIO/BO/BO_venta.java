package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_funcionario_comision;
import FORMULARIO.DAO.DAO_item_venta;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.funcionario_comision;
import FORMULARIO.ENTIDAD.venta;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta {

    private DAO_venta DAOven = new DAO_venta();
    private DAO_item_venta DAOiv = new DAO_item_venta();
    private DAO_caja_detalle DAOcd=new DAO_caja_detalle();
    private DAO_funcionario_comision DAOfc = new DAO_funcionario_comision();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public boolean getboo_insertar_venta(venta ven, JTable tbltabla,caja_detalle ENTcd) {
        boolean guardado=false;
        String titulo = "insertar_venta";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOven.insertar_venta(conn, ven);
            DAOiv.insertar_item_venta_de_tabla(conn, tbltabla,ven);
            DAOcd.insertar_caja_detalle(conn, ENTcd);
//            ven_dao.actualizar_tabla_venta(conn, tbltabla);
            conn.commit();
            guardado=true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
                
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
            guardado=false;
        }
        return guardado;
    }

    public boolean anular_update_venta(venta ENTven,caja_detalle ENTcd,funcionario_comision ENTfc) {
        boolean anulado=false;
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR VENTA", "ANULAR VENTA", "ANULAR", "CANCELAR")) {
            String titulo = "update_venta";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOven.estado_update_venta(conn, ENTven);
                DAOcd.anular_venta_update_caja_detalle(conn, ENTcd);
                DAOfc.anular_update_funcionario_comision(conn, ENTfc);
                DAOfc.update_total_comision_funcionario_de_venta(conn, ENTven.getC1idventa());
                anulado=true;
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ENTven.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ENTven.toString(), titulo);
                }
                anulado=false;
            }
        }
        return anulado;
    }
}
