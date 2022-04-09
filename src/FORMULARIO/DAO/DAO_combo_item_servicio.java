	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.combo_item_servicio;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_combo_item_servicio {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "COMBO_ITEM_SERVICIO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "COMBO_ITEM_SERVICIO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO combo_item_servicio(idcombo_item_servicio,fecha_creado,creado_por,orden,activo,cantidad,precio,fk_idservicio,fk_idcombo_servicio) VALUES (?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE combo_item_servicio SET fecha_creado=?,creado_por=?,orden=?,activo=?,cantidad=?,precio=?,fk_idservicio=?,fk_idcombo_servicio=? WHERE idcombo_item_servicio=?;";
	private String sql_select = "SELECT idcombo_item_servicio,fecha_creado,creado_por,orden,activo,cantidad,precio,fk_idservicio,fk_idcombo_servicio FROM combo_item_servicio order by 1 desc;";
	private String sql_cargar = "SELECT idcombo_item_servicio,fecha_creado,creado_por,orden,activo,cantidad,precio,fk_idservicio,fk_idcombo_servicio FROM combo_item_servicio WHERE idcombo_item_servicio=";
	public void insertar_combo_item_servicio(Connection conn, combo_item_servicio cbiser){
		cbiser.setC1idcombo_item_servicio(eveconn.getInt_ultimoID_mas_uno(conn, cbiser.getTb_combo_item_servicio(), cbiser.getId_idcombo_item_servicio()));
		String titulo = "insertar_combo_item_servicio";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,cbiser.getC1idcombo_item_servicio());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,cbiser.getC3creado_por());
			pst.setInt(4,cbiser.getC4orden());
			pst.setBoolean(5,cbiser.getC5activo());
			pst.setInt(6,cbiser.getC6cantidad());
			pst.setDouble(7,cbiser.getC7precio());
			pst.setInt(8,cbiser.getC8fk_idservicio());
			pst.setInt(9,cbiser.getC9fk_idcombo_servicio());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + cbiser.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + cbiser.toString(), titulo);
		}
	}
	public void update_combo_item_servicio(Connection conn, combo_item_servicio cbiser){
		String titulo = "update_combo_item_servicio";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,cbiser.getC3creado_por());
			pst.setInt(3,cbiser.getC4orden());
			pst.setBoolean(4,cbiser.getC5activo());
			pst.setInt(5,cbiser.getC6cantidad());
			pst.setDouble(6,cbiser.getC7precio());
			pst.setInt(7,cbiser.getC8fk_idservicio());
			pst.setInt(8,cbiser.getC9fk_idcombo_servicio());
			pst.setInt(9,cbiser.getC1idcombo_item_servicio());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + cbiser.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + cbiser.toString(), titulo);
		}
	}
	public void cargar_combo_item_servicio(Connection conn, combo_item_servicio cbiser,int idcombo_item_servicio){
		String titulo = "Cargar_combo_item_servicio";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idcombo_item_servicio, titulo);
			if(rs.next()){
				cbiser.setC1idcombo_item_servicio(rs.getInt(1));
				cbiser.setC2fecha_creado(rs.getString(2));
				cbiser.setC3creado_por(rs.getString(3));
				cbiser.setC4orden(rs.getInt(4));
				cbiser.setC5activo(rs.getBoolean(5));
				cbiser.setC6cantidad(rs.getInt(6));
				cbiser.setC7precio(rs.getDouble(7));
				cbiser.setC8fk_idservicio(rs.getInt(8));
				cbiser.setC9fk_idcombo_servicio(rs.getInt(9));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + cbiser.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + cbiser.toString(), titulo);
		}
	}
	public void actualizar_tabla_combo_item_servicio(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_combo_item_servicio(tbltabla);
	}
	public void ancho_tabla_combo_item_servicio(JTable tbltabla){
		int Ancho[]={11,11,11,11,11,11,11,11,11};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
