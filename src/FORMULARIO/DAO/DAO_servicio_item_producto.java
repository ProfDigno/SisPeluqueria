	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.servicio_item_producto;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_servicio_item_producto {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "SERVICIO_ITEM_PRODUCTO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "SERVICIO_ITEM_PRODUCTO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO servicio_item_producto(idservicio_item_producto,fecha_creado,creado_por,orden,activo,cantidad,precio,insumo,fk_idservicio,fk_idproducto) VALUES (?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE servicio_item_producto SET fecha_creado=?,creado_por=?,orden=?,activo=?,cantidad=?,precio=?,insumo=?,fk_idservicio=?,fk_idproducto=? WHERE idservicio_item_producto=?;";
	private String sql_select = "SELECT idservicio_item_producto,fecha_creado,creado_por,orden,activo,cantidad,precio,insumo,fk_idservicio,fk_idproducto FROM servicio_item_producto order by 1 desc;";
	private String sql_cargar = "SELECT idservicio_item_producto,fecha_creado,creado_por,orden,activo,cantidad,precio,insumo,fk_idservicio,fk_idproducto FROM servicio_item_producto WHERE idservicio_item_producto=";
	public void insertar_servicio_item_producto(Connection conn, servicio_item_producto sipro){
		sipro.setC1idservicio_item_producto(eveconn.getInt_ultimoID_mas_uno(conn, sipro.getTb_servicio_item_producto(), sipro.getId_idservicio_item_producto()));
		String titulo = "insertar_servicio_item_producto";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,sipro.getC1idservicio_item_producto());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,sipro.getC3creado_por());
			pst.setInt(4,sipro.getC4orden());
			pst.setBoolean(5,sipro.getC5activo());
			pst.setDouble(6,sipro.getC6cantidad());
			pst.setDouble(7,sipro.getC7precio());
			pst.setBoolean(8,sipro.getC8insumo());
			pst.setInt(9,sipro.getC9fk_idservicio());
			pst.setInt(10,sipro.getC10fk_idproducto());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + sipro.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + sipro.toString(), titulo);
		}
	}
	public void update_servicio_item_producto(Connection conn, servicio_item_producto sipro){
		String titulo = "update_servicio_item_producto";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,sipro.getC3creado_por());
			pst.setInt(3,sipro.getC4orden());
			pst.setBoolean(4,sipro.getC5activo());
			pst.setDouble(5,sipro.getC6cantidad());
			pst.setDouble(6,sipro.getC7precio());
			pst.setBoolean(7,sipro.getC8insumo());
			pst.setInt(8,sipro.getC9fk_idservicio());
			pst.setInt(9,sipro.getC10fk_idproducto());
			pst.setInt(10,sipro.getC1idservicio_item_producto());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + sipro.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + sipro.toString(), titulo);
		}
	}
	public void cargar_servicio_item_producto(Connection conn, servicio_item_producto sipro,int idservicio_item_producto){
		String titulo = "Cargar_servicio_item_producto";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idservicio_item_producto, titulo);
			if(rs.next()){
				sipro.setC1idservicio_item_producto(rs.getInt(1));
				sipro.setC2fecha_creado(rs.getString(2));
				sipro.setC3creado_por(rs.getString(3));
				sipro.setC4orden(rs.getInt(4));
				sipro.setC5activo(rs.getBoolean(5));
				sipro.setC6cantidad(rs.getDouble(6));
				sipro.setC7precio(rs.getDouble(7));
				sipro.setC8insumo(rs.getBoolean(8));
				sipro.setC9fk_idservicio(rs.getInt(9));
				sipro.setC10fk_idproducto(rs.getInt(10));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + sipro.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + sipro.toString(), titulo);
		}
	}
	public void actualizar_tabla_servicio_item_producto(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_servicio_item_producto(tbltabla);
	}
	public void ancho_tabla_servicio_item_producto(JTable tbltabla){
		int Ancho[]={10,10,10,10,10,10,10,10,10,10};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
