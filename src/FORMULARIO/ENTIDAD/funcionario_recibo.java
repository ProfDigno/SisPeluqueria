	package FORMULARIO.ENTIDAD;
public class funcionario_recibo {

//---------------DECLARAR VARIABLES---------------
private int C1idfuncionario_recibo;
private String C2fecha_creado;
private String C3creado_por;
private String C4descripcion;
private double C5monto_recibo;
private String C6monto_letra;
private String C7estado;
private boolean C8pago_comision;
private boolean C9pago_salario;
private int C10fk_idfuncionario;
private int C11fk_idfuncionario_grupo_comision;
private int C12fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public funcionario_recibo() {
		setTb_funcionario_recibo("funcionario_recibo");
		setId_idfuncionario_recibo("idfuncionario_recibo");
	}
	public static String getTb_funcionario_recibo(){
		return nom_tabla;
	}
	public static void setTb_funcionario_recibo(String nom_tabla){
		funcionario_recibo.nom_tabla = nom_tabla;
	}
	public static String getId_idfuncionario_recibo(){
		return nom_idtabla;
	}
	public static void setId_idfuncionario_recibo(String nom_idtabla){
		funcionario_recibo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idfuncionario_recibo(){
		return C1idfuncionario_recibo;
	}
	public void setC1idfuncionario_recibo(int C1idfuncionario_recibo){
		this.C1idfuncionario_recibo = C1idfuncionario_recibo;
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
	public double getC5monto_recibo(){
		return C5monto_recibo;
	}
	public void setC5monto_recibo(double C5monto_recibo){
		this.C5monto_recibo = C5monto_recibo;
	}
	public String getC6monto_letra(){
		return C6monto_letra;
	}
	public void setC6monto_letra(String C6monto_letra){
		this.C6monto_letra = C6monto_letra;
	}
	public String getC7estado(){
		return C7estado;
	}
	public void setC7estado(String C7estado){
		this.C7estado = C7estado;
	}
	public boolean getC8pago_comision(){
		return C8pago_comision;
	}
	public void setC8pago_comision(boolean C8pago_comision){
		this.C8pago_comision = C8pago_comision;
	}
	public boolean getC9pago_salario(){
		return C9pago_salario;
	}
	public void setC9pago_salario(boolean C9pago_salario){
		this.C9pago_salario = C9pago_salario;
	}
	public int getC10fk_idfuncionario(){
		return C10fk_idfuncionario;
	}
	public void setC10fk_idfuncionario(int C10fk_idfuncionario){
		this.C10fk_idfuncionario = C10fk_idfuncionario;
	}
	public int getC11fk_idfuncionario_grupo_comision(){
		return C11fk_idfuncionario_grupo_comision;
	}
	public void setC11fk_idfuncionario_grupo_comision(int C11fk_idfuncionario_grupo_comision){
		this.C11fk_idfuncionario_grupo_comision = C11fk_idfuncionario_grupo_comision;
	}
	public int getC12fk_idusuario(){
		return C12fk_idusuario;
	}
	public void setC12fk_idusuario(int C12fk_idusuario){
		this.C12fk_idusuario = C12fk_idusuario;
	}
	public String toString() {
		return "funcionario_recibo(" + ",idfuncionario_recibo=" + C1idfuncionario_recibo + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,descripcion=" + C4descripcion + " ,monto_recibo=" + C5monto_recibo + " ,monto_letra=" + C6monto_letra + " ,estado=" + C7estado + " ,pago_comision=" + C8pago_comision + " ,pago_salario=" + C9pago_salario + " ,fk_idfuncionario=" + C10fk_idfuncionario + " ,fk_idfuncionario_grupo_comision=" + C11fk_idfuncionario_grupo_comision + " ,fk_idusuario=" + C12fk_idusuario + " )";
	}
}
