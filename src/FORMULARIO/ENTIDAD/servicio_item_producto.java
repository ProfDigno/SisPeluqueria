	package FORMULARIO.ENTIDAD;
public class servicio_item_producto {

//---------------DECLARAR VARIABLES---------------
private int C1idservicio_item_producto;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private double C6cantidad;
private double C7precio;
private boolean C8insumo;
private int C9fk_idservicio;
private int C10fk_idproducto;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public servicio_item_producto() {
		setTb_servicio_item_producto("servicio_item_producto");
		setId_idservicio_item_producto("idservicio_item_producto");
	}
	public static String getTb_servicio_item_producto(){
		return nom_tabla;
	}
	public static void setTb_servicio_item_producto(String nom_tabla){
		servicio_item_producto.nom_tabla = nom_tabla;
	}
	public static String getId_idservicio_item_producto(){
		return nom_idtabla;
	}
	public static void setId_idservicio_item_producto(String nom_idtabla){
		servicio_item_producto.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idservicio_item_producto(){
		return C1idservicio_item_producto;
	}
	public void setC1idservicio_item_producto(int C1idservicio_item_producto){
		this.C1idservicio_item_producto = C1idservicio_item_producto;
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
	public double getC6cantidad(){
		return C6cantidad;
	}
	public void setC6cantidad(double C6cantidad){
		this.C6cantidad = C6cantidad;
	}
	public double getC7precio(){
		return C7precio;
	}
	public void setC7precio(double C7precio){
		this.C7precio = C7precio;
	}
	public boolean getC8insumo(){
		return C8insumo;
	}
	public void setC8insumo(boolean C8insumo){
		this.C8insumo = C8insumo;
	}
	public int getC9fk_idservicio(){
		return C9fk_idservicio;
	}
	public void setC9fk_idservicio(int C9fk_idservicio){
		this.C9fk_idservicio = C9fk_idservicio;
	}
	public int getC10fk_idproducto(){
		return C10fk_idproducto;
	}
	public void setC10fk_idproducto(int C10fk_idproducto){
		this.C10fk_idproducto = C10fk_idproducto;
	}
	public String toString() {
		return "servicio_item_producto(" + ",idservicio_item_producto=" + C1idservicio_item_producto + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,cantidad=" + C6cantidad + " ,precio=" + C7precio + " ,insumo=" + C8insumo + " ,fk_idservicio=" + C9fk_idservicio + " ,fk_idproducto=" + C10fk_idproducto + " )";
	}
}
