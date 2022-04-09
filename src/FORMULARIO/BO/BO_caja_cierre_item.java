	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_caja_cierre_item;
	import FORMULARIO.ENTIDAD.caja_cierre_item;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_caja_cierre_item {
private DAO_caja_cierre_item cjciei_dao = new DAO_caja_cierre_item();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_caja_cierre_item(caja_cierre_item cjciei, JTable tbltabla) {
		String titulo = "insertar_caja_cierre_item";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			cjciei_dao.insertar_caja_cierre_item(conn, cjciei);
			cjciei_dao.actualizar_tabla_caja_cierre_item(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, cjciei.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, cjciei.toString(), titulo);
			}
		}
	}
	public void update_caja_cierre_item(caja_cierre_item cjciei, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CAJA_CIERRE_ITEM", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_caja_cierre_item";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				cjciei_dao.update_caja_cierre_item(conn, cjciei);
				cjciei_dao.actualizar_tabla_caja_cierre_item(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, cjciei.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, cjciei.toString(), titulo);
				}
			}
		}
	}
}
