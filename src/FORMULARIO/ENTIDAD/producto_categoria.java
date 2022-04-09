	package FORMULARIO.ENTIDAD;
public class producto_categoria {

//---------------DECLARAR VARIABLES---------------
private int C1idproducto_categoria;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private String C6nombre;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto_categoria() {
		setTb_producto_categoria("producto_categoria");
		setId_idproducto_categoria("idproducto_categoria");
	}
	public static String getTb_producto_categoria(){
		return nom_tabla;
	}
	public static void setTb_producto_categoria(String nom_tabla){
		producto_categoria.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto_categoria(){
		return nom_idtabla;
	}
	public static void setId_idproducto_categoria(String nom_idtabla){
		producto_categoria.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto_categoria(){
		return C1idproducto_categoria;
	}
	public void setC1idproducto_categoria(int C1idproducto_categoria){
		this.C1idproducto_categoria = C1idproducto_categoria;
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
	public String toString() {
		return "producto_categoria(" + ",idproducto_categoria=" + C1idproducto_categoria + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,nombre=" + C6nombre + " )";
	}
}
