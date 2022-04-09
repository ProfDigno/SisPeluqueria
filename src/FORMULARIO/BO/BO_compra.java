	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_compra;
	import FORMULARIO.ENTIDAD.compra;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_compra {
private DAO_compra com_dao = new DAO_compra();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_compra(compra com, JTable tbltabla) {
		String titulo = "insertar_compra";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			com_dao.insertar_compra(conn, com);
			com_dao.actualizar_tabla_compra(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, com.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
			}
		}
	}
	public void update_compra(compra com, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR COMPRA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_compra";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				com_dao.update_compra(conn, com);
				com_dao.actualizar_tabla_compra(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, com.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
				}
			}
		}
	}
}
