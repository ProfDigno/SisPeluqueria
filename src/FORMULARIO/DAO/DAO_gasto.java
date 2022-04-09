	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.gasto;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_gasto {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "GASTO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "GASTO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO gasto(idgasto,fecha_creado,creado_por,descripcion,estado,monto_gasto,fk_idgasto_tipo,fk_idusuario) VALUES (?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE gasto SET fecha_creado=?,creado_por=?,descripcion=?,estado=?,monto_gasto=?,fk_idgasto_tipo=?,fk_idusuario=? WHERE idgasto=?;";
	private String sql_select = "SELECT idgasto,fecha_creado,creado_por,descripcion,estado,monto_gasto,fk_idgasto_tipo,fk_idusuario FROM gasto order by 1 desc;";
	private String sql_cargar = "SELECT idgasto,fecha_creado,creado_por,descripcion,estado,monto_gasto,fk_idgasto_tipo,fk_idusuario FROM gasto WHERE idgasto=";
	public void insertar_gasto(Connection conn, gasto gas){
		gas.setC1idgasto(eveconn.getInt_ultimoID_mas_uno(conn, gas.getTb_gasto(), gas.getId_idgasto()));
		String titulo = "insertar_gasto";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,gas.getC1idgasto());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,gas.getC3creado_por());
			pst.setString(4,gas.getC4descripcion());
			pst.setString(5,gas.getC5estado());
			pst.setDouble(6,gas.getC6monto_gasto());
			pst.setInt(7,gas.getC7fk_idgasto_tipo());
			pst.setInt(8,gas.getC8fk_idusuario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + gas.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + gas.toString(), titulo);
		}
	}
	public void update_gasto(Connection conn, gasto gas){
		String titulo = "update_gasto";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,gas.getC3creado_por());
			pst.setString(3,gas.getC4descripcion());
			pst.setString(4,gas.getC5estado());
			pst.setDouble(5,gas.getC6monto_gasto());
			pst.setInt(6,gas.getC7fk_idgasto_tipo());
			pst.setInt(7,gas.getC8fk_idusuario());
			pst.setInt(8,gas.getC1idgasto());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + gas.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + gas.toString(), titulo);
		}
	}
	public void cargar_gasto(Connection conn, gasto gas,int idgasto){
		String titulo = "Cargar_gasto";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idgasto, titulo);
			if(rs.next()){
				gas.setC1idgasto(rs.getInt(1));
				gas.setC2fecha_creado(rs.getString(2));
				gas.setC3creado_por(rs.getString(3));
				gas.setC4descripcion(rs.getString(4));
				gas.setC5estado(rs.getString(5));
				gas.setC6monto_gasto(rs.getDouble(6));
				gas.setC7fk_idgasto_tipo(rs.getInt(7));
				gas.setC8fk_idusuario(rs.getInt(8));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + gas.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + gas.toString(), titulo);
		}
	}
	public void actualizar_tabla_gasto(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_gasto(tbltabla);
	}
	public void ancho_tabla_gasto(JTable tbltabla){
		int Ancho[]={12,12,12,12,12,12,12,12};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
