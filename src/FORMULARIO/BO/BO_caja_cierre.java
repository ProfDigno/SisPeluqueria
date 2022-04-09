package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.ENTIDAD.caja_cierre;
import FORMULARIO.ENTIDAD.caja_detalle;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_caja_cierre {

    private DAO_caja_cierre cjcie_dao = new DAO_caja_cierre();
    private DAO_caja_detalle DAOcd=new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_caja_cierre(caja_cierre cjcie,caja_detalle ENTcd) {
        String titulo = "insertar_caja_cierre";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            cjcie_dao.insertar_caja_cierre(conn, cjcie);
            DAOcd.insertar_caja_detalle(conn, ENTcd);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, cjcie.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, cjcie.toString(), titulo);
            }
        }
    }

    public void update_caja_cierre(caja_cierre cjcie, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CAJA_CIERRE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_caja_cierre";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                cjcie_dao.update_caja_cierre(conn, cjcie);
                cjcie_dao.actualizar_tabla_caja_cierre(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, cjcie.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, cjcie.toString(), titulo);
                }
            }
        }
    }
}
