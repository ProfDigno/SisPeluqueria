	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.compra;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_compra {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "COMPRA GUARDADO CORRECTAMENTE";
	private String mensaje_update = "COMPRA MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO compra(idcompra,fecha_creado,creado_por,numero_nota,fecha_compra,monto_compra,monto_letra,estado,observacion,fk_idproveedor,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE compra SET fecha_creado=?,creado_por=?,numero_nota=?,fecha_compra=?,monto_compra=?,monto_letra=?,estado=?,observacion=?,fk_idproveedor=?,fk_idusuario=? WHERE idcompra=?;";
	private String sql_select = "SELECT idcompra,fecha_creado,creado_por,numero_nota,fecha_compra,monto_compra,monto_letra,estado,observacion,fk_idproveedor,fk_idusuario FROM compra order by 1 desc;";
	private String sql_cargar = "SELECT idcompra,fecha_creado,creado_por,numero_nota,fecha_compra,monto_compra,monto_letra,estado,observacion,fk_idproveedor,fk_idusuario FROM compra WHERE idcompra=";
	public void insertar_compra(Connection conn, compra com){
		com.setC1idcompra(eveconn.getInt_ultimoID_mas_uno(conn, com.getTb_compra(), com.getId_idcompra()));
		String titulo = "insertar_compra";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,com.getC1idcompra());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,com.getC3creado_por());
			pst.setInt(4,com.getC4numero_nota());
			pst.setDate(5,evefec.getDateSQL_sistema());
			pst.setDouble(6,com.getC6monto_compra());
			pst.setString(7,com.getC7monto_letra());
			pst.setString(8,com.getC8estado());
			pst.setString(9,com.getC9observacion());
			pst.setInt(10,com.getC10fk_idproveedor());
			pst.setInt(11,com.getC11fk_idusuario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + com.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + com.toString(), titulo);
		}
	}
	public void update_compra(Connection conn, compra com){
		String titulo = "update_compra";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,com.getC3creado_por());
			pst.setInt(3,com.getC4numero_nota());
			pst.setDate(4,evefec.getDateSQL_sistema());
			pst.setDouble(5,com.getC6monto_compra());
			pst.setString(6,com.getC7monto_letra());
			pst.setString(7,com.getC8estado());
			pst.setString(8,com.getC9observacion());
			pst.setInt(9,com.getC10fk_idproveedor());
			pst.setInt(10,com.getC11fk_idusuario());
			pst.setInt(11,com.getC1idcompra());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + com.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + com.toString(), titulo);
		}
	}
	public void cargar_compra(Connection conn, compra com,int idcompra){
		String titulo = "Cargar_compra";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idcompra, titulo);
			if(rs.next()){
				com.setC1idcompra(rs.getInt(1));
				com.setC2fecha_creado(rs.getString(2));
				com.setC3creado_por(rs.getString(3));
				com.setC4numero_nota(rs.getInt(4));
				com.setC5fecha_compra(rs.getString(5));
				com.setC6monto_compra(rs.getDouble(6));
				com.setC7monto_letra(rs.getString(7));
				com.setC8estado(rs.getString(8));
				com.setC9observacion(rs.getString(9));
				com.setC10fk_idproveedor(rs.getInt(10));
				com.setC11fk_idusuario(rs.getInt(11));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + com.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + com.toString(), titulo);
		}
	}
	public void actualizar_tabla_compra(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_compra(tbltabla);
	}
	public void ancho_tabla_compra(JTable tbltabla){
		int Ancho[]={9,9,9,9,9,9,9,9,9,9,9};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
