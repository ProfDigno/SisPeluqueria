package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_gasto;
import FORMULARIO.ENTIDAD.gasto;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_gasto {

    private DAO_gasto gas_dao = new DAO_gasto();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_gasto(gasto gas, JTable tbltabla) {
        String titulo = "insertar_gasto";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            gas_dao.insertar_gasto(conn, gas);
            gas_dao.actualizar_tabla_gasto(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, gas.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, gas.toString(), titulo);
            }
        }
    }

    public void update_gasto(gasto gas, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR GASTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_gasto";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                gas_dao.update_gasto(conn, gas);
                gas_dao.actualizar_tabla_gasto(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, gas.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, gas.toString(), titulo);
                }
            }
        }
    }
}
