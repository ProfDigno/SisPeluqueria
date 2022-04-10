package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_funcionario_recibo;
import FORMULARIO.ENTIDAD.funcionario_recibo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_funcionario_recibo {

    private DAO_funcionario_recibo funrec_dao = new DAO_funcionario_recibo();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_funcionario_recibo(funcionario_recibo funrec, JTable tbltabla) {
        String titulo = "insertar_funcionario_recibo";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            funrec_dao.insertar_funcionario_recibo(conn, funrec);
//			funrec_dao.actualizar_tabla_funcionario_recibo(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, funrec.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, funrec.toString(), titulo);
            }
        }
    }

    public void update_funcionario_recibo(funcionario_recibo funrec, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR FUNCIONARIO_RECIBO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_funcionario_recibo";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                funrec_dao.update_funcionario_recibo(conn, funrec);
//				funrec_dao.actualizar_tabla_funcionario_recibo(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, funrec.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, funrec.toString(), titulo);
                }
            }
        }
    }
}
