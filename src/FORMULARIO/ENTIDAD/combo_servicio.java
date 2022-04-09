	package FORMULARIO.ENTIDAD;
public class combo_servicio {

//---------------DECLARAR VARIABLES---------------
private int C1idcombo_servicio;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private String C6nombre;
private String C7descripcion;
private double C8precio_venta_normal;
private double C9precio_venta_descuento;
private String C10fecha_inicio;
private int C11dias;
private boolean C12lunes;
private boolean C13martes;
private boolean C14miercoles;
private boolean C15jueves;
private boolean C16viernes;
private boolean C17sabado;
private boolean C18domingo;
private double C19lunes_por;
private double C20martes_por;
private double C21miercoles_por;
private double C22jueves_por;
private double C23viernes_por;
private double C24sabado_por;
private double C25domingo_por;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public combo_servicio() {
		setTb_combo_servicio("combo_servicio");
		setId_idcombo_servicio("idcombo_servicio");
	}
	public static String getTb_combo_servicio(){
		return nom_tabla;
	}
	public static void setTb_combo_servicio(String nom_tabla){
		combo_servicio.nom_tabla = nom_tabla;
	}
	public static String getId_idcombo_servicio(){
		return nom_idtabla;
	}
	public static void setId_idcombo_servicio(String nom_idtabla){
		combo_servicio.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcombo_servicio(){
		return C1idcombo_servicio;
	}
	public void setC1idcombo_servicio(int C1idcombo_servicio){
		this.C1idcombo_servicio = C1idcombo_servicio;
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
	public String getC7descripcion(){
		return C7descripcion;
	}
	public void setC7descripcion(String C7descripcion){
		this.C7descripcion = C7descripcion;
	}
	public double getC8precio_venta_normal(){
		return C8precio_venta_normal;
	}
	public void setC8precio_venta_normal(double C8precio_venta_normal){
		this.C8precio_venta_normal = C8precio_venta_normal;
	}
	public double getC9precio_venta_descuento(){
		return C9precio_venta_descuento;
	}
	public void setC9precio_venta_descuento(double C9precio_venta_descuento){
		this.C9precio_venta_descuento = C9precio_venta_descuento;
	}
	public String getC10fecha_inicio(){
		return C10fecha_inicio;
	}
	public void setC10fecha_inicio(String C10fecha_inicio){
		this.C10fecha_inicio = C10fecha_inicio;
	}
	public int getC11dias(){
		return C11dias;
	}
	public void setC11dias(int C11dias){
		this.C11dias = C11dias;
	}
	public boolean getC12lunes(){
		return C12lunes;
	}
	public void setC12lunes(boolean C12lunes){
		this.C12lunes = C12lunes;
	}
	public boolean getC13martes(){
		return C13martes;
	}
	public void setC13martes(boolean C13martes){
		this.C13martes = C13martes;
	}
	public boolean getC14miercoles(){
		return C14miercoles;
	}
	public void setC14miercoles(boolean C14miercoles){
		this.C14miercoles = C14miercoles;
	}
	public boolean getC15jueves(){
		return C15jueves;
	}
	public void setC15jueves(boolean C15jueves){
		this.C15jueves = C15jueves;
	}
	public boolean getC16viernes(){
		return C16viernes;
	}
	public void setC16viernes(boolean C16viernes){
		this.C16viernes = C16viernes;
	}
	public boolean getC17sabado(){
		return C17sabado;
	}
	public void setC17sabado(boolean C17sabado){
		this.C17sabado = C17sabado;
	}
	public boolean getC18domingo(){
		return C18domingo;
	}
	public void setC18domingo(boolean C18domingo){
		this.C18domingo = C18domingo;
	}
	public double getC19lunes_por(){
		return C19lunes_por;
	}
	public void setC19lunes_por(double C19lunes_por){
		this.C19lunes_por = C19lunes_por;
	}
	public double getC20martes_por(){
		return C20martes_por;
	}
	public void setC20martes_por(double C20martes_por){
		this.C20martes_por = C20martes_por;
	}
	public double getC21miercoles_por(){
		return C21miercoles_por;
	}
	public void setC21miercoles_por(double C21miercoles_por){
		this.C21miercoles_por = C21miercoles_por;
	}
	public double getC22jueves_por(){
		return C22jueves_por;
	}
	public void setC22jueves_por(double C22jueves_por){
		this.C22jueves_por = C22jueves_por;
	}
	public double getC23viernes_por(){
		return C23viernes_por;
	}
	public void setC23viernes_por(double C23viernes_por){
		this.C23viernes_por = C23viernes_por;
	}
	public double getC24sabado_por(){
		return C24sabado_por;
	}
	public void setC24sabado_por(double C24sabado_por){
		this.C24sabado_por = C24sabado_por;
	}
	public double getC25domingo_por(){
		return C25domingo_por;
	}
	public void setC25domingo_por(double C25domingo_por){
		this.C25domingo_por = C25domingo_por;
	}
	public String toString() {
		return "combo_servicio(" + ",idcombo_servicio=" + C1idcombo_servicio + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,nombre=" + C6nombre + " ,descripcion=" + C7descripcion + " ,precio_venta_normal=" + C8precio_venta_normal + " ,precio_venta_descuento=" + C9precio_venta_descuento + " ,fecha_inicio=" + C10fecha_inicio + " ,dias=" + C11dias + " ,lunes=" + C12lunes + " ,martes=" + C13martes + " ,miercoles=" + C14miercoles + " ,jueves=" + C15jueves + " ,viernes=" + C16viernes + " ,sabado=" + C17sabado + " ,domingo=" + C18domingo + " ,lunes_por=" + C19lunes_por + " ,martes_por=" + C20martes_por + " ,miercoles_por=" + C21miercoles_por + " ,jueves_por=" + C22jueves_por + " ,viernes_por=" + C23viernes_por + " ,sabado_por=" + C24sabado_por + " ,domingo_por=" + C25domingo_por + " )";
	}
}
