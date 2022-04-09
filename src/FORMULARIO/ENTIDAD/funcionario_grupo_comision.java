	package FORMULARIO.ENTIDAD;
public class funcionario_grupo_comision {

//---------------DECLARAR VARIABLES---------------
private int C1idfuncionario_grupo_comision;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_inicio;
private String C5fecha_fin;
private String C6estado;
private boolean C7es_abierto;
private int C8fk_idfuncionario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public funcionario_grupo_comision() {
		setTb_funcionario_grupo_comision("funcionario_grupo_comision");
		setId_idfuncionario_grupo_comision("idfuncionario_grupo_comision");
	}
	public static String getTb_funcionario_grupo_comision(){
		return nom_tabla;
	}
	public static void setTb_funcionario_grupo_comision(String nom_tabla){
		funcionario_grupo_comision.nom_tabla = nom_tabla;
	}
	public static String getId_idfuncionario_grupo_comision(){
		return nom_idtabla;
	}
	public static void setId_idfuncionario_grupo_comision(String nom_idtabla){
		funcionario_grupo_comision.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idfuncionario_grupo_comision(){
		return C1idfuncionario_grupo_comision;
	}
	public void setC1idfuncionario_grupo_comision(int C1idfuncionario_grupo_comision){
		this.C1idfuncionario_grupo_comision = C1idfuncionario_grupo_comision;
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
	public int getC8fk_idfuncionario(){
		return C8fk_idfuncionario;
	}
	public void setC8fk_idfuncionario(int C8fk_idfuncionario){
		this.C8fk_idfuncionario = C8fk_idfuncionario;
	}
	public String toString() {
		return "funcionario_grupo_comision(" + ",idfuncionario_grupo_comision=" + C1idfuncionario_grupo_comision + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_inicio=" + C4fecha_inicio + " ,fecha_fin=" + C5fecha_fin + " ,estado=" + C6estado + " ,es_abierto=" + C7es_abierto + " ,fk_idfuncionario=" + C8fk_idfuncionario + " )";
	}
}
