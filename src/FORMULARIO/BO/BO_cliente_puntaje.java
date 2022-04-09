	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_cliente_puntaje;
	import FORMULARIO.ENTIDAD.cliente_puntaje;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_cliente_puntaje {
private DAO_cliente_puntaje clipu_dao = new DAO_cliente_puntaje();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_cliente_puntaje(cliente_puntaje clipu, JTable tbltabla) {
		String titulo = "insertar_cliente_puntaje";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			clipu_dao.insertar_cliente_puntaje(conn, clipu);
			clipu_dao.actualizar_tabla_cliente_puntaje(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, clipu.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, clipu.toString(), titulo);
			}
		}
	}
	public void update_cliente_puntaje(cliente_puntaje clipu, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CLIENTE_PUNTAJE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_cliente_puntaje";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				clipu_dao.update_cliente_puntaje(conn, clipu);
				clipu_dao.actualizar_tabla_cliente_puntaje(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, clipu.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, clipu.toString(), titulo);
				}
			}
		}
	}
}
