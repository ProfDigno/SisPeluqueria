package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_funcionario_comision;
import FORMULARIO.ENTIDAD.funcionario_comision;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_funcionario_comision {

    private DAO_funcionario_comision funco_dao = new DAO_funcionario_comision();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_funcionario_comision(funcionario_comision funco, JTable tbltabla) {
        String titulo = "insertar_funcionario_comision";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            funco_dao.insertar_funcionario_comision(conn, funco);
//			funco_dao.actualizar_tabla_funcionario_comision(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, funco.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, funco.toString(), titulo);
            }
        }
    }

    public void update_funcionario_comision(funcionario_comision funco, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR FUNCIONARIO_COMISION", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_funcionario_comision";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                funco_dao.update_funcionario_comision(conn, funco);
//                funco_dao.actualizar_tabla_funcionario_comision(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, funco.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, funco.toString(), titulo);
                }
            }
        }
    }
}
