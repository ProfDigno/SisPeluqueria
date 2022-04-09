	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_compra_item;
	import FORMULARIO.ENTIDAD.compra_item;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_compra_item {
private DAO_compra_item comi_dao = new DAO_compra_item();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_compra_item(compra_item comi, JTable tbltabla) {
		String titulo = "insertar_compra_item";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			comi_dao.insertar_compra_item(conn, comi);
			comi_dao.actualizar_tabla_compra_item(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, comi.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, comi.toString(), titulo);
			}
		}
	}
	public void update_compra_item(compra_item comi, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR COMPRA_ITEM", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_compra_item";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				comi_dao.update_compra_item(conn, comi);
				comi_dao.actualizar_tabla_compra_item(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, comi.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, comi.toString(), titulo);
				}
			}
		}
	}
}
