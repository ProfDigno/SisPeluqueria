	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_combo_item_servicio;
	import FORMULARIO.ENTIDAD.combo_item_servicio;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_combo_item_servicio {
private DAO_combo_item_servicio cbiser_dao = new DAO_combo_item_servicio();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_combo_item_servicio(combo_item_servicio cbiser, JTable tbltabla) {
		String titulo = "insertar_combo_item_servicio";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			cbiser_dao.insertar_combo_item_servicio(conn, cbiser);
			cbiser_dao.actualizar_tabla_combo_item_servicio(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, cbiser.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, cbiser.toString(), titulo);
			}
		}
	}
	public void update_combo_item_servicio(combo_item_servicio cbiser, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR COMBO_ITEM_SERVICIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_combo_item_servicio";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				cbiser_dao.update_combo_item_servicio(conn, cbiser);
				cbiser_dao.actualizar_tabla_combo_item_servicio(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, cbiser.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, cbiser.toString(), titulo);
				}
			}
		}
	}
}
