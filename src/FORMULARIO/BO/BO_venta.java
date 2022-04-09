package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_item_venta;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.venta;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta {

    private DAO_venta ven_dao = new DAO_venta();
    private DAO_item_venta iven_dao = new DAO_item_venta();
    private DAO_caja_detalle DAOcd=new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public boolean getboo_insertar_venta(venta ven, JTable tbltabla,caja_detalle ENTcd) {
        boolean guardado=false;
        String titulo = "insertar_venta";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ven_dao.insertar_venta(conn, ven);
            iven_dao.insertar_item_venta_de_tabla(conn, tbltabla,ven);
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

    public void update_venta(venta ven, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR VENTA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_venta";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                ven_dao.update_venta(conn, ven);
//                ven_dao.actualizar_tabla_venta(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ven.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
                }
            }
        }
    }
}
