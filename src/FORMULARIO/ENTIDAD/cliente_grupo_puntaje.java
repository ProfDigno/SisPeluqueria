	package FORMULARIO.ENTIDAD;
public class cliente_grupo_puntaje {

//---------------DECLARAR VARIABLES---------------
private int C1idcliente_grupo_puntaje;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_inicio;
private String C5fecha_fin;
private String C6estado;
private boolean C7es_abierto;
private int C8fk_idcliente;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public cliente_grupo_puntaje() {
		setTb_cliente_grupo_puntaje("cliente_grupo_puntaje");
		setId_idcliente_grupo_puntaje("idcliente_grupo_puntaje");
	}
	public static String getTb_cliente_grupo_puntaje(){
		return nom_tabla;
	}
	public static void setTb_cliente_grupo_puntaje(String nom_tabla){
		cliente_grupo_puntaje.nom_tabla = nom_tabla;
	}
	public static String getId_idcliente_grupo_puntaje(){
		return nom_idtabla;
	}
	public static void setId_idcliente_grupo_puntaje(String nom_idtabla){
		cliente_grupo_puntaje.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcliente_grupo_puntaje(){
		return C1idcliente_grupo_puntaje;
	}
	public void setC1idcliente_grupo_puntaje(int C1idcliente_grupo_puntaje){
		this.C1idcliente_grupo_puntaje = C1idcliente_grupo_puntaje;
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
	public String getC4fecha_inicio(){
		return C4fecha_inicio;
	}
	public void setC4fecha_inicio(String C4fecha_inicio){
		this.C4fecha_inicio = C4fecha_inicio;
	}
	public String getC5fecha_fin(){
		return C5fecha_fin;
	}
	public void setC5fecha_fin(String C5fecha_fin){
		this.C5fecha_fin = C5fecha_fin;
	}
	public String getC6estado(){
		return C6estado;
	}
	public void setC6estado(String C6estado){
		this.C6estado = C6estado;
	}
	public boolean getC7es_abierto(){
		return C7es_abierto;
	}
	public void setC7es_abierto(boolean C7es_abierto){
		this.C7es_abierto = C7es_abierto;
	}
	public int getC8fk_idcliente(){
		return C8fk_idcliente;
	}
	public void setC8fk_idcliente(int C8fk_idcliente){
		this.C8fk_idcliente = C8fk_idcliente;
	}
	public String toString() {
		return "cliente_grupo_puntaje(" + ",idcliente_grupo_puntaje=" + C1idcliente_grupo_puntaje + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_inicio=" + C4fecha_inicio + " ,fecha_fin=" + C5fecha_fin + " ,estado=" + C6estado + " ,es_abierto=" + C7es_abierto + " ,fk_idcliente=" + C8fk_idcliente + " )";
	}
}
