package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_compra;
import FORMULARIO.DAO.DAO_compra_item;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.compra;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_compra {

    private DAO_compra DAOcom = new DAO_compra();
    private DAO_compra_item DAOci=new DAO_compra_item();
    private DAO_caja_detalle DAOcd=new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public boolean  getboo_insertar_compra(compra com,JTable tblitem_producto,caja_detalle ENTcd) {
        boolean guardado=false;
        String titulo = "getboo_insertar_compra";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOcom.insertar_compra(conn, com);
            DAOci.insertar_item_compra_de_tabla(conn, tblitem_producto, com);
            DAOcd.insertar_caja_detalle(conn, ENTcd);
            conn.commit();
            guardado=true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, com.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
            }
            guardado=false;
        }
        return guardado;
    }

    public boolean getBoo_anular_update_compra(compra com,caja_detalle ENTcd) {
        boolean update=false;
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR COMPRA", "ANULAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_compra";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOcom.anular_update_compra(conn, com);
                DAOcd.anular_compra_update_caja_detalle(conn, ENTcd);
                DAOci.anular_item_compra(conn, com.getC1idcompra());
                conn.commit();
                update=true;
            } catch (SQLException e) {
                evmen.mensaje_error(e, com.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
                }
                update=false;
            }
        }
        return update;
    }
}
