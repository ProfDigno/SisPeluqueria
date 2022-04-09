	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_combo_servicio;
	import FORMULARIO.ENTIDAD.combo_servicio;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_combo_servicio {
private DAO_combo_servicio cbser_dao = new DAO_combo_servicio();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_combo_servicio(combo_servicio cbser, JTable tbltabla) {
		String titulo = "insertar_combo_servicio";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			cbser_dao.insertar_combo_servicio(conn, cbser);
			cbser_dao.actualizar_tabla_combo_servicio(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, cbser.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, cbser.toString(), titulo);
			}
		}
	}
	public void update_combo_servicio(combo_servicio cbser, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR COMBO_SERVICIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_combo_servicio";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				cbser_dao.update_combo_servicio(conn, cbser);
				cbser_dao.actualizar_tabla_combo_servicio(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, cbser.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, cbser.toString(), titulo);
				}
			}
		}
	}
}
