	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_configuracion_puntaje;
	import FORMULARIO.ENTIDAD.configuracion_puntaje;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_configuracion_puntaje {
private DAO_configuracion_puntaje confpu_dao = new DAO_configuracion_puntaje();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_configuracion_puntaje(configuracion_puntaje confpu, JTable tbltabla) {
		String titulo = "insertar_configuracion_puntaje";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			confpu_dao.insertar_configuracion_puntaje(conn, confpu);
			confpu_dao.actualizar_tabla_configuracion_puntaje(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, confpu.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, confpu.toString(), titulo);
			}
		}
	}
	public void update_configuracion_puntaje(configuracion_puntaje confpu, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CONFIGURACION_PUNTAJE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_configuracion_puntaje";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				confpu_dao.update_configuracion_puntaje(conn, confpu);
				confpu_dao.actualizar_tabla_configuracion_puntaje(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, confpu.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, confpu.toString(), titulo);
				}
			}
		}
	}
}
