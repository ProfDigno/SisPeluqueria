	package FORMULARIO.ENTIDAD;
public class compra {

//---------------DECLARAR VARIABLES---------------
private int C1idcompra;
private String C2fecha_creado;
private String C3creado_por;
private int C4numero_nota;
private String C5fecha_compra;
private double C6monto_compra;
private String C7monto_letra;
private String C8estado;
private String C9observacion;
private int C10fk_idproveedor;
private int C11fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public compra() {
		setTb_compra("compra");
		setId_idcompra("idcompra");
	}
	public static String getTb_compra(){
		return nom_tabla;
	}
	public static void setTb_compra(String nom_tabla){
		compra.nom_tabla = nom_tabla;
	}
	public static String getId_idcompra(){
		return nom_idtabla;
	}
	public static void setId_idcompra(String nom_idtabla){
		compra.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcompra(){
		return C1idcompra;
	}
	public void setC1idcompra(int C1idcompra){
		this.C1idcompra = C1idcompra;
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
	public int getC4numero_nota(){
		return C4numero_nota;
	}
	public void setC4numero_nota(int C4numero_nota){
		this.C4numero_nota = C4numero_nota;
	}
	public String getC5fecha_compra(){
		return C5fecha_compra;
	}
	public void setC5fecha_compra(String C5fecha_compra){
		this.C5fecha_compra = C5fecha_compra;
	}
	public double getC6monto_compra(){
		return C6monto_compra;
	}
	public void setC6monto_compra(double C6monto_compra){
		this.C6monto_compra = C6monto_compra;
	}
	public String getC7monto_letra(){
		return C7monto_letra;
	}
	public void setC7monto_letra(String C7monto_letra){
		this.C7monto_letra = C7monto_letra;
	}
	public String getC8estado(){
		return C8estado;
	}
	public void setC8estado(String C8estado){
		this.C8estado = C8estado;
	}
	public String getC9observacion(){
		return C9observacion;
	}
	public void setC9observacion(String C9observacion){
		this.C9observacion = C9observacion;
	}
	public int getC10fk_idproveedor(){
		return C10fk_idproveedor;
	}
	public void setC10fk_idproveedor(int C10fk_idproveedor){
		this.C10fk_idproveedor = C10fk_idproveedor;
	}
	public int getC11fk_idusuario(){
		return C11fk_idusuario;
	}
	public void setC11fk_idusuario(int C11fk_idusuario){
		this.C11fk_idusuario = C11fk_idusuario;
	}
	public String toString() {
		return "compra(" + ",idcompra=" + C1idcompra + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,numero_nota=" + C4numero_nota + " ,fecha_compra=" + C5fecha_compra + " ,monto_compra=" + C6monto_compra + " ,monto_letra=" + C7monto_letra + " ,estado=" + C8estado + " ,observacion=" + C9observacion + " ,fk_idproveedor=" + C10fk_idproveedor + " ,fk_idusuario=" + C11fk_idusuario + " )";
	}
}
