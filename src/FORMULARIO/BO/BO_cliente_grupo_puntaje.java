	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_cliente_grupo_puntaje;
	import FORMULARIO.ENTIDAD.cliente_grupo_puntaje;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_cliente_grupo_puntaje {
private DAO_cliente_grupo_puntaje cligp_dao = new DAO_cliente_grupo_puntaje();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_cliente_grupo_puntaje(cliente_grupo_puntaje cligp, JTable tbltabla) {
		String titulo = "insertar_cliente_grupo_puntaje";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			cligp_dao.insertar_cliente_grupo_puntaje(conn, cligp);
			cligp_dao.actualizar_tabla_cliente_grupo_puntaje(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, cligp.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, cligp.toString(), titulo);
			}
		}
	}
	public void update_cliente_grupo_puntaje(cliente_grupo_puntaje cligp, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CLIENTE_GRUPO_PUNTAJE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_cliente_grupo_puntaje";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				cligp_dao.update_cliente_grupo_puntaje(conn, cligp);
				cligp_dao.actualizar_tabla_cliente_grupo_puntaje(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, cligp.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, cligp.toString(), titulo);
				}
			}
		}
	}
}
