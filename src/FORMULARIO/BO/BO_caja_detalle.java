	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_caja_detalle;
	import FORMULARIO.ENTIDAD.caja_detalle;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_caja_detalle {
private DAO_caja_detalle cjdet_dao = new DAO_caja_detalle();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_caja_detalle(caja_detalle cjdet, JTable tbltabla) {
		String titulo = "insertar_caja_detalle";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			cjdet_dao.insertar_caja_detalle(conn, cjdet);
			cjdet_dao.actualizar_tabla_caja_detalle(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, cjdet.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, cjdet.toString(), titulo);
			}
		}
	}
	public void update_caja_detalle(caja_detalle cjdet, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CAJA_DETALLE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_caja_detalle";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				cjdet_dao.update_caja_detalle(conn, cjdet);
				cjdet_dao.actualizar_tabla_caja_detalle(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, cjdet.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, cjdet.toString(), titulo);
				}
			}
		}
	}
}
