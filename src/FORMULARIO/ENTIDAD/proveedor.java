	package FORMULARIO.ENTIDAD;
public class proveedor {

//---------------DECLARAR VARIABLES---------------
private int C1idproveedor;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private String C6razon_social;
private String C7nombre;
private String C8ruc;
private String C9direccion;
private String C10telefono;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public proveedor() {
		setTb_proveedor("proveedor");
		setId_idproveedor("idproveedor");
	}
	public static String getTb_proveedor(){
		return nom_tabla;
	}
	public static void setTb_proveedor(String nom_tabla){
		proveedor.nom_tabla = nom_tabla;
	}
	public static String getId_idproveedor(){
		return nom_idtabla;
	}
	public static void setId_idproveedor(String nom_idtabla){
		proveedor.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproveedor(){
		return C1idproveedor;
	}
	public void setC1idproveedor(int C1idproveedor){
		this.C1idproveedor = C1idproveedor;
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
	public String getC6razon_social(){
		return C6razon_social;
	}
	public void setC6razon_social(String C6razon_social){
		this.C6razon_social = C6razon_social;
	}
	public String getC7nombre(){
		return C7nombre;
	}
	public void setC7nombre(String C7nombre){
		this.C7nombre = C7nombre;
	}
	public String getC8ruc(){
		return C8ruc;
	}
	public void setC8ruc(String C8ruc){
		this.C8ruc = C8ruc;
	}
	public String getC9direccion(){
		return C9direccion;
	}
	public void setC9direccion(String C9direccion){
		this.C9direccion = C9direccion;
	}
	public String getC10telefono(){
		return C10telefono;
	}
	public void setC10telefono(String C10telefono){
		this.C10telefono = C10telefono;
	}
	public String toString() {
		return "proveedor(" + ",idproveedor=" + C1idproveedor + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,razon_social=" + C6razon_social + " ,nombre=" + C7nombre + " ,ruc=" + C8ruc + " ,direccion=" + C9direccion + " ,telefono=" + C10telefono + " )";
	}
}
