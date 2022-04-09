	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_producto_unidad;
	import FORMULARIO.ENTIDAD.producto_unidad;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_producto_unidad {
private DAO_producto_unidad puni_dao = new DAO_producto_unidad();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_producto_unidad(producto_unidad puni, JTable tbltabla) {
		String titulo = "insertar_producto_unidad";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			puni_dao.insertar_producto_unidad(conn, puni);
			puni_dao.actualizar_tabla_producto_unidad(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, puni.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, puni.toString(), titulo);
			}
		}
	}
	public void update_producto_unidad(producto_unidad puni, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR PRODUCTO_UNIDAD", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_producto_unidad";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				puni_dao.update_producto_unidad(conn, puni);
				puni_dao.actualizar_tabla_producto_unidad(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, puni.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, puni.toString(), titulo);
				}
			}
		}
	}
}
