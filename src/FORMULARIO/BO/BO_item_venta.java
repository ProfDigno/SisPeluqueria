package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_item_venta;
import FORMULARIO.ENTIDAD.item_venta;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_item_venta {

    private DAO_item_venta iven_dao = new DAO_item_venta();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_item_venta(item_venta iven, JTable tbltabla) {
        String titulo = "insertar_item_venta";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            iven_dao.insertar_item_venta(conn, iven);
            iven_dao.actualizar_tabla_item_venta(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, iven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, iven.toString(), titulo);
            }
        }
    }

    public void update_item_venta(item_venta iven, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ITEM_VENTA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_item_venta";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                iven_dao.update_item_venta(conn, iven);
                iven_dao.actualizar_tabla_item_venta(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, iven.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, iven.toString(), titulo);
                }
            }
        }
    }
}
