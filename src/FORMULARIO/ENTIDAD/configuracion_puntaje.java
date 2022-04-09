	package FORMULARIO.ENTIDAD;
public class configuracion_puntaje {

//---------------DECLARAR VARIABLES---------------
private int C1idconfiguracion_puntaje;
private String C2fecha_creado;
private String C3creado_por;
private boolean C4activo;
private String C5nombre;
private double C6porcentaje_del_total;
private double C7unidad_por_punto;
private double C8monto_por_punto;
private double C9minimo_monto_generar;
private double C10punto_maximo;
private int C11dias_vencer;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public configuracion_puntaje() {
		setTb_configuracion_puntaje("configuracion_puntaje");
		setId_idconfiguracion_puntaje("idconfiguracion_puntaje");
	}
	public static String getTb_configuracion_puntaje(){
		return nom_tabla;
	}
	public static void setTb_configuracion_puntaje(String nom_tabla){
		configuracion_puntaje.nom_tabla = nom_tabla;
	}
	public static String getId_idconfiguracion_puntaje(){
		return nom_idtabla;
	}
	public static void setId_idconfiguracion_puntaje(String nom_idtabla){
		configuracion_puntaje.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idconfiguracion_puntaje(){
		return C1idconfiguracion_puntaje;
	}
	public void setC1idconfiguracion_puntaje(int C1idconfiguracion_puntaje){
		this.C1idconfiguracion_puntaje = C1idconfiguracion_puntaje;
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
	public boolean getC4activo(){
		return C4activo;
	}
	public void setC4activo(boolean C4activo){
		this.C4activo = C4activo;
	}
	public String getC5nombre(){
		return C5nombre;
	}
	public void setC5nombre(String C5nombre){
		this.C5nombre = C5nombre;
	}
	public double getC6porcentaje_del_total(){
		return C6porcentaje_del_total;
	}
	public void setC6porcentaje_del_total(double C6porcentaje_del_total){
		this.C6porcentaje_del_total = C6porcentaje_del_total;
	}
	public double getC7unidad_por_punto(){
		return C7unidad_por_punto;
	}
	public void setC7unidad_por_punto(double C7unidad_por_punto){
		this.C7unidad_por_punto = C7unidad_por_punto;
	}
	public double getC8monto_por_punto(){
		return C8monto_por_punto;
	}
	public void setC8monto_por_punto(double C8monto_por_punto){
		this.C8monto_por_punto = C8monto_por_punto;
	}
	public double getC9minimo_monto_generar(){
		return C9minimo_monto_generar;
	}
	public void setC9minimo_monto_generar(double C9minimo_monto_generar){
		this.C9minimo_monto_generar = C9minimo_monto_generar;
	}
	public double getC10punto_maximo(){
		return C10punto_maximo;
	}
	public void setC10punto_maximo(double C10punto_maximo){
		this.C10punto_maximo = C10punto_maximo;
	}
	public int getC11dias_vencer(){
		return C11dias_vencer;
	}
	public void setC11dias_vencer(int C11dias_vencer){
		this.C11dias_vencer = C11dias_vencer;
	}
	public String toString() {
		return "configuracion_puntaje(" + ",idconfiguracion_puntaje=" + C1idconfiguracion_puntaje + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,activo=" + C4activo + " ,nombre=" + C5nombre + " ,porcentaje_del_total=" + C6porcentaje_del_total + " ,unidad_por_punto=" + C7unidad_por_punto + " ,monto_por_punto=" + C8monto_por_punto + " ,minimo_monto_generar=" + C9minimo_monto_generar + " ,punto_maximo=" + C10punto_maximo + " ,dias_vencer=" + C11dias_vencer + " )";
	}
}
