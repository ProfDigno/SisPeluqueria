package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_funcionario;
import FORMULARIO.DAO.DAO_funcionario_grupo_comision;
import FORMULARIO.ENTIDAD.funcionario;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_funcionario {

    private DAO_funcionario fun_dao = new DAO_funcionario();
    private DAO_funcionario_grupo_comision fungc_dao = new DAO_funcionario_grupo_comision();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_funcionario(funcionario fun, JTable tbltabla) {
        String titulo = "insertar_funcionario";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            fun_dao.insertar_funcionario(conn, fun);
            fungc_dao.crear_si_no_existe_funcionario_grupo_comision(conn,fun.getC1idfuncionario());
            fun_dao.actualizar_tabla_funcionario(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, fun.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, fun.toString(), titulo);
            }
        }
    }

    public void update_funcionario(funcionario fun, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR FUNCIONARIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_funcionario";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                fun_dao.update_funcionario(conn, fun);
                fungc_dao.crear_si_no_existe_funcionario_grupo_comision(conn,fun.getC1idfuncionario());
                fun_dao.actualizar_tabla_funcionario(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, fun.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, fun.toString(), titulo);
                }
            }
        }
    }
}
