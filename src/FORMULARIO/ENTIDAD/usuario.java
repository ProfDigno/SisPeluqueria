	package FORMULARIO.ENTIDAD;
public class usuario {

//---------------DECLARAR VARIABLES---------------
private int C1idusuario;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private String C5usuario;
private String C6password;
private String C7nivel;
private static String nom_tabla;
private static String nom_idtabla;
private static int global_idusuario;
private static String global_nombre;
private static String global_nivel;
//---------------TABLA-ID---------------
	public usuario() {
		setTb_usuario("usuario");
		setId_idusuario("idusuario");
	}
	public static String getTb_usuario(){
		return nom_tabla;
	}
	public static void setTb_usuario(String nom_tabla){
		usuario.nom_tabla = nom_tabla;
	}
	public static String getId_idusuario(){
		return nom_idtabla;
	}
	public static void setId_idusuario(String nom_idtabla){
		usuario.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idusuario(){
		return C1idusuario;
	}
	public void setC1idusuario(int C1idusuario){
		this.C1idusuario = C1idusuario;
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
	public String getC5usuario(){
		return C5usuario;
	}
	public void setC5usuario(String C5usuario){
		this.C5usuario = C5usuario;
	}
	public String getC6password(){
		return C6password;
	}
	public void setC6password(String C6password){
		this.C6password = C6password;
	}
	public String getC7nivel(){
		return C7nivel;
	}
	public void setC7nivel(String C7nivel){
		this.C7nivel = C7nivel;
	}

    public static int getGlobal_idusuario() {
        return global_idusuario;
    }

    public static void setGlobal_idusuario(int global_idusuario) {
        usuario.global_idusuario = global_idusuario;
    }

    public static String getGlobal_nombre() {
        return global_nombre;
    }

    public static void setGlobal_nombre(String global_nombre) {
        usuario.global_nombre = global_nombre;
    }

    public static String getGlobal_nivel() {
        return global_nivel;
    }

    public static void setGlobal_nivel(String global_nivel) {
        usuario.global_nivel = global_nivel;
    }
        
	public String toString() {
		return "usuario(" + ",idusuario=" + C1idusuario + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,usuario=" + C5usuario + " ,password=" + C6password + " ,nivel=" + C7nivel + " )";
	}
}
