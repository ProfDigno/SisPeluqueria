	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_producto;
	import FORMULARIO.ENTIDAD.producto;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_producto {
private DAO_producto prod_dao = new DAO_producto();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_producto(producto prod, JTable tbltabla) {
		String titulo = "insertar_producto";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			prod_dao.insertar_producto(conn, prod);
			prod_dao.actualizar_tabla_producto(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, prod.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, prod.toString(), titulo);
			}
		}
	}
	public void update_producto(producto prod, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR PRODUCTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_producto";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				prod_dao.update_producto(conn, prod);
				prod_dao.actualizar_tabla_producto(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, prod.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, prod.toString(), titulo);
				}
			}
		}
	}
}
