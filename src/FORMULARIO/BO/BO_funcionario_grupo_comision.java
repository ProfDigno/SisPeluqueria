package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_funcionario_grupo_comision;
import FORMULARIO.ENTIDAD.funcionario_grupo_comision;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_funcionario_grupo_comision {

    private DAO_funcionario_grupo_comision fungc_dao = new DAO_funcionario_grupo_comision();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_funcionario_grupo_comision(funcionario_grupo_comision fungc, JTable tbltabla) {
        String titulo = "insertar_funcionario_grupo_comision";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            fungc_dao.insertar_funcionario_grupo_comision(conn, fungc);
//            fungc_dao.actualizar_tabla_funcionario_grupo_comision(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, fungc.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, fungc.toString(), titulo);
            }
        }
    }

    public void update_funcionario_grupo_comision(funcionario_grupo_comision fungc, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR FUNCIONARIO_GRUPO_COMISION", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_funcionario_grupo_comision";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                fungc_dao.update_funcionario_grupo_comision(conn, fungc);
//                fungc_dao.crear_si_no_existe_funcionario_grupo_comision(conn, fungc.getC8fk_idfuncionario());
//                fungc_dao.actualizar_tabla_funcionario_grupo_comision(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, fungc.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, fungc.toString(), titulo);
                }
            }
        }
    }
}
