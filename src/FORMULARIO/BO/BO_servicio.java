	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_servicio;
	import FORMULARIO.ENTIDAD.servicio;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_servicio {
private DAO_servicio serv_dao = new DAO_servicio();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_servicio(servicio serv, JTable tbltabla) {
		String titulo = "insertar_servicio";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			serv_dao.insertar_servicio(conn, serv);
			serv_dao.actualizar_tabla_servicio(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, serv.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, serv.toString(), titulo);
			}
		}
	}
	public void update_servicio(servicio serv, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR SERVICIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_servicio";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				serv_dao.update_servicio(conn, serv);
				serv_dao.actualizar_tabla_servicio(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, serv.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, serv.toString(), titulo);
				}
			}
		}
	}
}
