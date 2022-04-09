	package FORMULARIO.ENTIDAD;
public class empresa {

//---------------DECLARAR VARIABLES---------------
private int C1idempresa;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private String C5direccion;
private String C6telefono;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public empresa() {
		setTb_empresa("empresa");
		setId_idempresa("idempresa");
	}
	public static String getTb_empresa(){
		return nom_tabla;
	}
	public static void setTb_empresa(String nom_tabla){
		empresa.nom_tabla = nom_tabla;
	}
	public static String getId_idempresa(){
		return nom_idtabla;
	}
	public static void setId_idempresa(String nom_idtabla){
		empresa.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idempresa(){
		return C1idempresa;
	}
	public void setC1idempresa(int C1idempresa){
		this.C1idempresa = C1idempresa;
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
	public String getC4nombre(){
		return C4nombre;
	}
	public void setC4nombre(String C4nombre){
		this.C4nombre = C4nombre;
	}
	public String getC5direccion(){
		return C5direccion;
	}
	public void setC5direccion(String C5direccion){
		this.C5direccion = C5direccion;
	}
	public String getC6telefono(){
		return C6telefono;
	}
	public void setC6telefono(String C6telefono){
		this.C6telefono = C6telefono;
	}
	public String toString() {
		return "empresa(" + ",idempresa=" + C1idempresa + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,direccion=" + C5direccion + " ,telefono=" + C6telefono + " )";
	}
}
