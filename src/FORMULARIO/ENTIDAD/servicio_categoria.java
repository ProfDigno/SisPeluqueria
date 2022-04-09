	package FORMULARIO.ENTIDAD;
public class servicio_categoria {

//---------------DECLARAR VARIABLES---------------
private int C1idservicio_categoria;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private String C6nombre;
private String C7ruta_icono;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public servicio_categoria() {
		setTb_servicio_categoria("servicio_categoria");
		setId_idservicio_categoria("idservicio_categoria");
	}
	public static String getTb_servicio_categoria(){
		return nom_tabla;
	}
	public static void setTb_servicio_categoria(String nom_tabla){
		servicio_categoria.nom_tabla = nom_tabla;
	}
	public static String getId_idservicio_categoria(){
		return nom_idtabla;
	}
	public static void setId_idservicio_categoria(String nom_idtabla){
		servicio_categoria.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idservicio_categoria(){
		return C1idservicio_categoria;
	}
	public void setC1idservicio_categoria(int C1idservicio_categoria){
		this.C1idservicio_categoria = C1idservicio_categoria;
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
	public String getC7ruta_icono(){
		return C7ruta_icono;
	}
	public void setC7ruta_icono(String C7ruta_icono){
		this.C7ruta_icono = C7ruta_icono;
	}
	public String toString() {
		return "servicio_categoria(" + ",idservicio_categoria=" + C1idservicio_categoria + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,nombre=" + C6nombre + " ,ruta_icono=" + C7ruta_icono + " )";
	}
}
