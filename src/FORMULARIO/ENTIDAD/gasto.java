	package FORMULARIO.ENTIDAD;
public class gasto {

//---------------DECLARAR VARIABLES---------------
private int C1idgasto;
private String C2fecha_creado;
private String C3creado_por;
private String C4descripcion;
private String C5estado;
private double C6monto_gasto;
private int C7fk_idgasto_tipo;
private int C8fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public gasto() {
		setTb_gasto("gasto");
		setId_idgasto("idgasto");
	}
	public static String getTb_gasto(){
		return nom_tabla;
	}
	public static void setTb_gasto(String nom_tabla){
		gasto.nom_tabla = nom_tabla;
	}
	public static String getId_idgasto(){
		return nom_idtabla;
	}
	public static void setId_idgasto(String nom_idtabla){
		gasto.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idgasto(){
		return C1idgasto;
	}
	public void setC1idgasto(int C1idgasto){
		this.C1idgasto = C1idgasto;
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
	public String getC4descripcion(){
		return C4descripcion;
	}
	public void setC4descripcion(String C4descripcion){
		this.C4descripcion = C4descripcion;
	}
	public String getC5estado(){
		return C5estado;
	}
	public void setC5estado(String C5estado){
		this.C5estado = C5estado;
	}
	public double getC6monto_gasto(){
		return C6monto_gasto;
	}
	public void setC6monto_gasto(double C6monto_gasto){
		this.C6monto_gasto = C6monto_gasto;
	}
	public int getC7fk_idgasto_tipo(){
		return C7fk_idgasto_tipo;
	}
	public void setC7fk_idgasto_tipo(int C7fk_idgasto_tipo){
		this.C7fk_idgasto_tipo = C7fk_idgasto_tipo;
	}
	public int getC8fk_idusuario(){
		return C8fk_idusuario;
	}
	public void setC8fk_idusuario(int C8fk_idusuario){
		this.C8fk_idusuario = C8fk_idusuario;
	}
	public String toString() {
		return "gasto(" + ",idgasto=" + C1idgasto + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,descripcion=" + C4descripcion + " ,estado=" + C5estado + " ,monto_gasto=" + C6monto_gasto + " ,fk_idgasto_tipo=" + C7fk_idgasto_tipo + " ,fk_idusuario=" + C8fk_idusuario + " )";
	}
}
