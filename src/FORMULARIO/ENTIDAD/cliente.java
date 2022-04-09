	package FORMULARIO.ENTIDAD;
public class cliente {

//---------------DECLARAR VARIABLES---------------
private int C1idcliente;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private String C6nombre;
private String C7apellido;
private String C8ruc;
private String C9cedula;
private String C10direccion;
private String C11telefono;
private boolean C12tiene_puntaje;
private double C13total_puntaje;
private int C14fk_idconfiguracion_puntaje;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public cliente() {
		setTb_cliente("cliente");
		setId_idcliente("idcliente");
	}
	public static String getTb_cliente(){
		return nom_tabla;
	}
	public static void setTb_cliente(String nom_tabla){
		cliente.nom_tabla = nom_tabla;
	}
	public static String getId_idcliente(){
		return nom_idtabla;
	}
	public static void setId_idcliente(String nom_idtabla){
		cliente.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcliente(){
		return C1idcliente;
	}
	public void setC1idcliente(int C1idcliente){
		this.C1idcliente = C1idcliente;
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
	public int getC4orden(){
		return C4orden;
	}
	public void setC4orden(int C4orden){
		this.C4orden = C4orden;
	}
	public boolean getC5activo(){
		return C5activo;
	}
	public void setC5activo(boolean C5activo){
		this.C5activo = C5activo;
	}
	public String getC6nombre(){
		return C6nombre;
	}
	public void setC6nombre(String C6nombre){
		this.C6nombre = C6nombre;
	}
	public String getC7apellido(){
		return C7apellido;
	}
	public void setC7apellido(String C7apellido){
		this.C7apellido = C7apellido;
	}
	public String getC8ruc(){
		return C8ruc;
	}
	public void setC8ruc(String C8ruc){
		this.C8ruc = C8ruc;
	}
	public String getC9cedula(){
		return C9cedula;
	}
	public void setC9cedula(String C9cedula){
		this.C9cedula = C9cedula;
	}
	public String getC10direccion(){
		return C10direccion;
	}
	public void setC10direccion(String C10direccion){
		this.C10direccion = C10direccion;
	}
	public String getC11telefono(){
		return C11telefono;
	}
	public void setC11telefono(String C11telefono){
		this.C11telefono = C11telefono;
	}
	public boolean getC12tiene_puntaje(){
		return C12tiene_puntaje;
	}
	public void setC12tiene_puntaje(boolean C12tiene_puntaje){
		this.C12tiene_puntaje = C12tiene_puntaje;
	}
	public double getC13total_puntaje(){
		return C13total_puntaje;
	}
	public void setC13total_puntaje(double C13total_puntaje){
		this.C13total_puntaje = C13total_puntaje;
	}
	public int getC14fk_idconfiguracion_puntaje(){
		return C14fk_idconfiguracion_puntaje;
	}
	public void setC14fk_idconfiguracion_puntaje(int C14fk_idconfiguracion_puntaje){
		this.C14fk_idconfiguracion_puntaje = C14fk_idconfiguracion_puntaje;
	}
	public String toString() {
		return "cliente(" + ",idcliente=" + C1idcliente + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,nombre=" + C6nombre + " ,apellido=" + C7apellido + " ,ruc=" + C8ruc + " ,cedula=" + C9cedula + " ,direccion=" + C10direccion + " ,telefono=" + C11telefono + " ,tiene_puntaje=" + C12tiene_puntaje + " ,total_puntaje=" + C13total_puntaje + " ,fk_idconfiguracion_puntaje=" + C14fk_idconfiguracion_puntaje + " )";
	}
}
