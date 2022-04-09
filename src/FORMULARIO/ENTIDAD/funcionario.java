	package FORMULARIO.ENTIDAD;
public class funcionario {

//---------------DECLARAR VARIABLES---------------
private int C1idfuncionario;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private String C6nombre;
private String C7apellido;
private String C8cedula;
private String C9direccion;
private String C10telefono;
private boolean C11tiene_comision;
private double C12total_comision;
private boolean C13tiene_salario;
private double C14total_salario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public funcionario() {
		setTb_funcionario("funcionario");
		setId_idfuncionario("idfuncionario");
	}
	public static String getTb_funcionario(){
		return nom_tabla;
	}
	public static void setTb_funcionario(String nom_tabla){
		funcionario.nom_tabla = nom_tabla;
	}
	public static String getId_idfuncionario(){
		return nom_idtabla;
	}
	public static void setId_idfuncionario(String nom_idtabla){
		funcionario.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idfuncionario(){
		return C1idfuncionario;
	}
	public void setC1idfuncionario(int C1idfuncionario){
		this.C1idfuncionario = C1idfuncionario;
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
	public String getC7apellido(){
		return C7apellido;
	}
	public void setC7apellido(String C7apellido){
		this.C7apellido = C7apellido;
	}
	public String getC8cedula(){
		return C8cedula;
	}
	public void setC8cedula(String C8cedula){
		this.C8cedula = C8cedula;
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
	public boolean getC11tiene_comision(){
		return C11tiene_comision;
	}
	public void setC11tiene_comision(boolean C11tiene_comision){
		this.C11tiene_comision = C11tiene_comision;
	}
	public double getC12total_comision(){
		return C12total_comision;
	}
	public void setC12total_comision(double C12total_comision){
		this.C12total_comision = C12total_comision;
	}
	public boolean getC13tiene_salario(){
		return C13tiene_salario;
	}
	public void setC13tiene_salario(boolean C13tiene_salario){
		this.C13tiene_salario = C13tiene_salario;
	}
	public double getC14total_salario(){
		return C14total_salario;
	}
	public void setC14total_salario(double C14total_salario){
		this.C14total_salario = C14total_salario;
	}
	public String toString() {
		return "funcionario(" + ",idfuncionario=" + C1idfuncionario + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,nombre=" + C6nombre + " ,apellido=" + C7apellido + " ,cedula=" + C8cedula + " ,direccion=" + C9direccion + " ,telefono=" + C10telefono + " ,tiene_comision=" + C11tiene_comision + " ,total_comision=" + C12total_comision + " ,tiene_salario=" + C13tiene_salario + " ,total_salario=" + C14total_salario + " )";
	}
}
