	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_servicio_categoria;
	import FORMULARIO.ENTIDAD.servicio_categoria;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_servicio_categoria {
private DAO_servicio_categoria secat_dao = new DAO_servicio_categoria();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_servicio_categoria(servicio_categoria secat, JTable tbltabla) {
		String titulo = "insertar_servicio_categoria";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			secat_dao.insertar_servicio_categoria(conn, secat);
			secat_dao.actualizar_tabla_servicio_categoria(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, secat.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, secat.toString(), titulo);
			}
		}
	}
	public void update_servicio_categoria(servicio_categoria secat, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR SERVICIO_CATEGORIA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_servicio_categoria";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				secat_dao.update_servicio_categoria(conn, secat);
				secat_dao.actualizar_tabla_servicio_categoria(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, secat.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, secat.toString(), titulo);
				}
			}
		}
	}
}
