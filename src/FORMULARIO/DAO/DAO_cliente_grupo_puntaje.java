	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.cliente_grupo_puntaje;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_cliente_grupo_puntaje {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "CLIENTE_GRUPO_PUNTAJE GUARDADO CORRECTAMENTE";
	private String mensaje_update = "CLIENTE_GRUPO_PUNTAJE MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO cliente_grupo_puntaje(idcliente_grupo_puntaje,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado,es_abierto,fk_idcliente) VALUES (?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE cliente_grupo_puntaje SET fecha_creado=?,creado_por=?,fecha_inicio=?,fecha_fin=?,estado=?,es_abierto=?,fk_idcliente=? WHERE idcliente_grupo_puntaje=?;";
	private String sql_select = "SELECT idcliente_grupo_puntaje,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado,es_abierto,fk_idcliente FROM cliente_grupo_puntaje order by 1 desc;";
	private String sql_cargar = "SELECT idcliente_grupo_puntaje,fecha_creado,creado_por,fecha_inicio,fecha_fin,estado,es_abierto,fk_idcliente FROM cliente_grupo_puntaje WHERE idcliente_grupo_puntaje=";
	public void insertar_cliente_grupo_puntaje(Connection conn, cliente_grupo_puntaje cligp){
		cligp.setC1idcliente_grupo_puntaje(eveconn.getInt_ultimoID_mas_uno(conn, cligp.getTb_cliente_grupo_puntaje(), cligp.getId_idcliente_grupo_puntaje()));
		String titulo = "insertar_cliente_grupo_puntaje";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,cligp.getC1idcliente_grupo_puntaje());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,cligp.getC3creado_por());
			pst.setTimestamp(4,evefec.getTimestamp_sistema());
			pst.setTimestamp(5,evefec.getTimestamp_sistema());
			pst.setString(6,cligp.getC6estado());
			pst.setBoolean(7,cligp.getC7es_abierto());
			pst.setInt(8,cligp.getC8fk_idcliente());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + cligp.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + cligp.toString(), titulo);
		}
	}
	public void update_cliente_grupo_puntaje(Connection conn, cliente_grupo_puntaje cligp){
		String titulo = "update_cliente_grupo_puntaje";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,cligp.getC3creado_por());
			pst.setTimestamp(3,evefec.getTimestamp_sistema());
			pst.setTimestamp(4,evefec.getTimestamp_sistema());
			pst.setString(5,cligp.getC6estado());
			pst.setBoolean(6,cligp.getC7es_abierto());
			pst.setInt(7,cligp.getC8fk_idcliente());
			pst.setInt(8,cligp.getC1idcliente_grupo_puntaje());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + cligp.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + cligp.toString(), titulo);
		}
	}
	public void cargar_cliente_grupo_puntaje(Connection conn, cliente_grupo_puntaje cligp,int idcliente_grupo_puntaje){
		String titulo = "Cargar_cliente_grupo_puntaje";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+idcliente_grupo_puntaje, titulo);
			if(rs.next()){
				cligp.setC1idcliente_grupo_puntaje(rs.getInt(1));
				cligp.setC2fecha_creado(rs.getString(2));
				cligp.setC3creado_por(rs.getString(3));
				cligp.setC4fecha_inicio(rs.getString(4));
				cligp.setC5fecha_fin(rs.getString(5));
				cligp.setC6estado(rs.getString(6));
				cligp.setC7es_abierto(rs.getBoolean(7));
				cligp.setC8fk_idcliente(rs.getInt(8));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + cligp.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + cligp.toString(), titulo);
		}
	}
	public void actualizar_tabla_cliente_grupo_puntaje(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_cliente_grupo_puntaje(tbltabla);
	}
	public void ancho_tabla_cliente_grupo_puntaje(JTable tbltabla){
		int Ancho[]={12,12,12,12,12,12,12,12};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
