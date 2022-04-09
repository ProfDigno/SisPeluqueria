	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.combo_servicio;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_combo_servicio {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "COMBO_SERVICIO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "COMBO_SERVICIO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO combo_servicio(idcombo_servicio,fecha_creado,creado_por,orden,activo,nombre,descripcion,precio_venta_normal,precio_venta_descuento,fecha_inicio,dias,lunes,martes,miercoles,jueves,viernes,sabado,domingo,lunes_por,martes_por,miercoles_por,jueves_por,viernes_por,sabado_por,domingo_por) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE combo_servicio SET fecha_creado=?,creado_por=?,orden=?,activo=?,nombre=?,descripcion=?,precio_venta_normal=?,precio_venta_descuento=?,fecha_inicio=?,dias=?,lunes=?,martes=?,miercoles=?,jueves=?,viernes=?,sabado=?,domingo=?,lunes_por=?,martes_por=?,miercoles_por=?,jueves_por=?,viernes_por=?,sabado_por=?,domingo_por=? WHERE idcombo_servicio=?;";
	private String sql_select = "SELECT idcombo_servicio,fecha_creado,creado_por,orden,activo,nombre,descripcion,precio_venta_normal,precio_venta_descuento,fecha_inicio,dias,lunes,martes,miercoles,jueves,viernes,sabado,domingo,lunes_por,martes_por,miercoles_por,jueves_por,viernes_por,sabado_por,domingo_por FROM combo_servicio order by 1 desc;";
	private String sql_cargar = "SELECT idcombo_servicio,fecha_creado,creado_por,orden,activo,nombre,descripcion,precio_venta_normal,precio_venta_descuento,fecha_inicio,dias,lunes,martes,miercoles,jueves,viernes,sabado,domingo,lunes_por,martes_por,miercoles_por,jueves_por,viernes_por,sabado_por,domingo_por FROM combo_servicio WHERE idcombo_servicio=";
	public void insertar_combo_servicio(Connection conn, combo_servicio cbser){
		cbser.setC1idcombo_servicio(eveconn.getInt_ultimoID_mas_uno(conn, cbser.getTb_combo_servicio(), cbser.getId_idcombo_servicio()));
		String titulo = "insertar_combo_servicio";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,cbser.getC1idcombo_servicio());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,cbser.getC3creado_por());
			pst.setInt(4,cbser.getC4orden());
			pst.setBoolean(5,cbser.getC5activo());
			pst.setString(6,cbser.getC6nombre());
			pst.setString(7,cbser.getC7descripcion());
			pst.setDouble(8,cbser.getC8precio_venta_normal());
			pst.setDouble(9,cbser.getC9precio_venta_descuento());
			pst.setDate(10,evefec.getDateSQL_sistema());
			pst.setInt(11,cbser.getC11dias());
			pst.setBoolean(12,cbser.getC12lunes());
			pst.setBoolean(13,cbser.getC13martes());
			pst.setBoolean(14,cbser.getC14miercoles());
			pst.setBoolean(15,cbser.getC15jueves());
			pst.setBoolean(16,cbser.getC16viernes());
			pst.setBoolean(17,cbser.getC17sabado());
			pst.setBoolean(18,cbser.getC18domingo());
			pst.setDouble(19,cbser.getC19lunes_por());
			pst.setDouble(20,cbser.getC20martes_por());
			pst.setDouble(21,cbser.getC21miercoles_por());
			pst.setDouble(22,cbser.getC22jueves_por());
			pst.setDouble(23,cbser.getC23viernes_por());
			pst.setDouble(24,cbser.getC24sabado_por());
			pst.setDouble(25,cbser.getC25domingo_por());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + cbser.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + cbser.toString(), titulo);
		}
	}
	public void update_combo_servicio(Connection conn, combo_servicio cbser){
		String titulo = "update_combo_servicio";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,cbser.getC3creado_por());
			pst.setInt(3,cbser.getC4orden());
			pst.setBoolean(4,cbser.getC5activo());
			pst.setString(5,cbser.getC6nombre());
			pst.setString(6,cbser.getC7descripcion());
			pst.setDouble(7,cbser.getC8precio_venta_normal());
			pst.setDouble(8,cbser.getC9precio_venta_descuento());
			pst.setDate(9,evefec.getDateSQL_sistema());
			pst.setInt(10,cbser.getC11dias());
			pst.setBoolean(11,cbser.getC12lunes());
			pst.setBoolean(12,cbser.getC13martes());
			pst.setBoolean(13,cbser.getC14miercoles());
			pst.setBoolean(14,cbser.getC15jueves());
			pst.setBoolean(15,cbser.getC16viernes());
			pst.setBoolean(16,cbser.getC17sabado());
			pst.setBoolean(17,cbser.getC18domingo());
			pst.setDouble(18,cbser.getC19lunes_por());
			pst.setDouble(19,cbser.getC20martes_por());
			pst.setDouble(20,cbser.getC21miercoles_por());
			pst.setDouble(21,cbser.getC22jueves_por());
			pst.setDouble(22,cbser.getC23viernes_por());
			pst.setDouble(23,cbser.getC24sabado_por());
			pst.setDouble(24,cbser.getC25domingo_por());
			pst.setInt(25,cbser.getC1idcombo_servicio());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + cbser.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + cbser.toString(), titulo);
		}
	}
	public void cargar_combo_servicio(Connection conn, combo_servicio cbser,int idcombo_servicio){
		String titulo = "Cargar_combo_servicio";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idcombo_servicio, titulo);
			if(rs.next()){
				cbser.setC1idcombo_servicio(rs.getInt(1));
				cbser.setC2fecha_creado(rs.getString(2));
				cbser.setC3creado_por(rs.getString(3));
				cbser.setC4orden(rs.getInt(4));
				cbser.setC5activo(rs.getBoolean(5));
				cbser.setC6nombre(rs.getString(6));
				cbser.setC7descripcion(rs.getString(7));
				cbser.setC8precio_venta_normal(rs.getDouble(8));
				cbser.setC9precio_venta_descuento(rs.getDouble(9));
				cbser.setC10fecha_inicio(rs.getString(10));
				cbser.setC11dias(rs.getInt(11));
				cbser.setC12lunes(rs.getBoolean(12));
				cbser.setC13martes(rs.getBoolean(13));
				cbser.setC14miercoles(rs.getBoolean(14));
				cbser.setC15jueves(rs.getBoolean(15));
				cbser.setC16viernes(rs.getBoolean(16));
				cbser.setC17sabado(rs.getBoolean(17));
				cbser.setC18domingo(rs.getBoolean(18));
				cbser.setC19lunes_por(rs.getDouble(19));
				cbser.setC20martes_por(rs.getDouble(20));
				cbser.setC21miercoles_por(rs.getDouble(21));
				cbser.setC22jueves_por(rs.getDouble(22));
				cbser.setC23viernes_por(rs.getDouble(23));
				cbser.setC24sabado_por(rs.getDouble(24));
				cbser.setC25domingo_por(rs.getDouble(25));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + cbser.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + cbser.toString(), titulo);
		}
	}
	public void actualizar_tabla_combo_servicio(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_combo_servicio(tbltabla);
	}
	public void ancho_tabla_combo_servicio(JTable tbltabla){
		int Ancho[]={4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
