	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_servicio_item_producto;
	import FORMULARIO.ENTIDAD.servicio_item_producto;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_servicio_item_producto {
private DAO_servicio_item_producto sipro_dao = new DAO_servicio_item_producto();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_servicio_item_producto(servicio_item_producto sipro, JTable tbltabla) {
		String titulo = "insertar_servicio_item_producto";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			sipro_dao.insertar_servicio_item_producto(conn, sipro);
			sipro_dao.actualizar_tabla_servicio_item_producto(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, sipro.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, sipro.toString(), titulo);
			}
		}
	}
	public void update_servicio_item_producto(servicio_item_producto sipro, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR SERVICIO_ITEM_PRODUCTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_servicio_item_producto";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				sipro_dao.update_servicio_item_producto(conn, sipro);
				sipro_dao.actualizar_tabla_servicio_item_producto(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, sipro.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, sipro.toString(), titulo);
				}
			}
		}
	}
}
