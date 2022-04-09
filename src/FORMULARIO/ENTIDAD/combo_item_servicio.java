	package FORMULARIO.ENTIDAD;
public class combo_item_servicio {

//---------------DECLARAR VARIABLES---------------
private int C1idcombo_item_servicio;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private int C6cantidad;
private double C7precio;
private int C8fk_idservicio;
private int C9fk_idcombo_servicio;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public combo_item_servicio() {
		setTb_combo_item_servicio("combo_item_servicio");
		setId_idcombo_item_servicio("idcombo_item_servicio");
	}
	public static String getTb_combo_item_servicio(){
		return nom_tabla;
	}
	public static void setTb_combo_item_servicio(String nom_tabla){
		combo_item_servicio.nom_tabla = nom_tabla;
	}
	public static String getId_idcombo_item_servicio(){
		return nom_idtabla;
	}
	public static void setId_idcombo_item_servicio(String nom_idtabla){
		combo_item_servicio.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcombo_item_servicio(){
		return C1idcombo_item_servicio;
	}
	public void setC1idcombo_item_servicio(int C1idcombo_item_servicio){
		this.C1idcombo_item_servicio = C1idcombo_item_servicio;
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
	public int getC6cantidad(){
		return C6cantidad;
	}
	public void setC6cantidad(int C6cantidad){
		this.C6cantidad = C6cantidad;
	}
	public double getC7precio(){
		return C7precio;
	}
	public void setC7precio(double C7precio){
		this.C7precio = C7precio;
	}
	public int getC8fk_idservicio(){
		return C8fk_idservicio;
	}
	public void setC8fk_idservicio(int C8fk_idservicio){
		this.C8fk_idservicio = C8fk_idservicio;
	}
	public int getC9fk_idcombo_servicio(){
		return C9fk_idcombo_servicio;
	}
	public void setC9fk_idcombo_servicio(int C9fk_idcombo_servicio){
		this.C9fk_idcombo_servicio = C9fk_idcombo_servicio;
	}
	public String toString() {
		return "combo_item_servicio(" + ",idcombo_item_servicio=" + C1idcombo_item_servicio + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,cantidad=" + C6cantidad + " ,precio=" + C7precio + " ,fk_idservicio=" + C8fk_idservicio + " ,fk_idcombo_servicio=" + C9fk_idcombo_servicio + " )";
	}
}
