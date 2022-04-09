	package FORMULARIO.ENTIDAD;
public class cliente_puntaje {

//---------------DECLARAR VARIABLES---------------
private int C1idcliente_puntaje;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_usado;
private double C5puntaje_ingreso;
private double C6puntaje_egreso;
private String C7estado;
private String C8descripcion;
private boolean C9es_usado;
private int C10dias_vencer;
private int C11fk_idcliente_grupo_puntaje;
private int C12fk_idcliente;
private int C13fk_idventa;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public cliente_puntaje() {
		setTb_cliente_puntaje("cliente_puntaje");
		setId_idcliente_puntaje("idcliente_puntaje");
	}
	public static String getTb_cliente_puntaje(){
		return nom_tabla;
	}
	public static void setTb_cliente_puntaje(String nom_tabla){
		cliente_puntaje.nom_tabla = nom_tabla;
	}
	public static String getId_idcliente_puntaje(){
		return nom_idtabla;
	}
	public static void setId_idcliente_puntaje(String nom_idtabla){
		cliente_puntaje.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcliente_puntaje(){
		return C1idcliente_puntaje;
	}
	public void setC1idcliente_puntaje(int C1idcliente_puntaje){
		this.C1idcliente_puntaje = C1idcliente_puntaje;
	}
	public String getC2fecha_creado(){
		return C2fecha_creado;
	}
	public void setC2fecha_creado(String C2fecha_creado){
		this.C2fecha_creado = C2fecha_creado;
	}
	public String getC3creado_por(){
		return C3creado_por;
	}
	public void setC3creado_por(String C3creado_por){
		this.C3creado_por = C3creado_por;
	}
	public String getC4fecha_usado(){
		return C4fecha_usado;
	}
	public void setC4fecha_usado(String C4fecha_usado){
		this.C4fecha_usado = C4fecha_usado;
	}
	public double getC5puntaje_ingreso(){
		return C5puntaje_ingreso;
	}
	public void setC5puntaje_ingreso(double C5puntaje_ingreso){
		this.C5puntaje_ingreso = C5puntaje_ingreso;
	}
	public double getC6puntaje_egreso(){
		return C6puntaje_egreso;
	}
	public void setC6puntaje_egreso(double C6puntaje_egreso){
		this.C6puntaje_egreso = C6puntaje_egreso;
	}
	public String getC7estado(){
		return C7estado;
	}
	public void setC7estado(String C7estado){
		this.C7estado = C7estado;
	}
	public String getC8descripcion(){
		return C8descripcion;
	}
	public void setC8descripcion(String C8descripcion){
		this.C8descripcion = C8descripcion;
	}
	public boolean getC9es_usado(){
		return C9es_usado;
	}
	public void setC9es_usado(boolean C9es_usado){
		this.C9es_usado = C9es_usado;
	}
	public int getC10dias_vencer(){
		return C10dias_vencer;
	}
	public void setC10dias_vencer(int C10dias_vencer){
		this.C10dias_vencer = C10dias_vencer;
	}
	public int getC11fk_idcliente_grupo_puntaje(){
		return C11fk_idcliente_grupo_puntaje;
	}
	public void setC11fk_idcliente_grupo_puntaje(int C11fk_idcliente_grupo_puntaje){
		this.C11fk_idcliente_grupo_puntaje = C11fk_idcliente_grupo_puntaje;
	}
	public int getC12fk_idcliente(){
		return C12fk_idcliente;
	}
	public void setC12fk_idcliente(int C12fk_idcliente){
		this.C12fk_idcliente = C12fk_idcliente;
	}
	public int getC13fk_idventa(){
		return C13fk_idventa;
	}
	public void setC13fk_idventa(int C13fk_idventa){
		this.C13fk_idventa = C13fk_idventa;
	}
	public String toString() {
		return "cliente_puntaje(" + ",idcliente_puntaje=" + C1idcliente_puntaje + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_usado=" + C4fecha_usado + " ,puntaje_ingreso=" + C5puntaje_ingreso + " ,puntaje_egreso=" + C6puntaje_egreso + " ,estado=" + C7estado + " ,descripcion=" + C8descripcion + " ,es_usado=" + C9es_usado + " ,dias_vencer=" + C10dias_vencer + " ,fk_idcliente_grupo_puntaje=" + C11fk_idcliente_grupo_puntaje + " ,fk_idcliente=" + C12fk_idcliente + " ,fk_idventa=" + C13fk_idventa + " )";
	}
}
