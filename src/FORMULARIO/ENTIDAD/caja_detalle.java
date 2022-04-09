	package FORMULARIO.ENTIDAD;
public class caja_detalle {

//---------------DECLARAR VARIABLES---------------
private int C1idcaja_detalle;
private String C2fecha_creado;
private String C3creado_por;
private String C4descripcion;
private String C5tabla_origen;
private String C6estado;
private double C7monto_cierre;
private String C8cierre;
private double C9in_monto_apertura;
private double C10in_monto_venta;
private double C11eg_monto_recibo_funcionario;
private double C12eg_monto_gasto;
private double C13eg_monto_compra;
private int C14fk_idventa;
private int C15fk_idfuncionario_recibo;
private int C16fk_idgasto;
private int C17fk_idcompra;
private int C18fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public caja_detalle() {
		setTb_caja_detalle("caja_detalle");
		setId_idcaja_detalle("idcaja_detalle");
	}
	public static String getTb_caja_detalle(){
		return nom_tabla;
	}
	public static void setTb_caja_detalle(String nom_tabla){
		caja_detalle.nom_tabla = nom_tabla;
	}
	public static String getId_idcaja_detalle(){
		return nom_idtabla;
	}
	public static void setId_idcaja_detalle(String nom_idtabla){
		caja_detalle.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcaja_detalle(){
		return C1idcaja_detalle;
	}
	public void setC1idcaja_detalle(int C1idcaja_detalle){
		this.C1idcaja_detalle = C1idcaja_detalle;
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
	public String getC5tabla_origen(){
		return C5tabla_origen;
	}
	public void setC5tabla_origen(String C5tabla_origen){
		this.C5tabla_origen = C5tabla_origen;
	}
	public String getC6estado(){
		return C6estado;
	}
	public void setC6estado(String C6estado){
		this.C6estado = C6estado;
	}
	public double getC7monto_cierre(){
		return C7monto_cierre;
	}
	public void setC7monto_cierre(double C7monto_cierre){
		this.C7monto_cierre = C7monto_cierre;
	}
	public String getC8cierre(){
		return C8cierre;
	}
	public void setC8cierre(String C8cierre){
		this.C8cierre = C8cierre;
	}
	public double getC9in_monto_apertura(){
		return C9in_monto_apertura;
	}
	public void setC9in_monto_apertura(double C9in_monto_apertura){
		this.C9in_monto_apertura = C9in_monto_apertura;
	}
	public double getC10in_monto_venta(){
		return C10in_monto_venta;
	}
	public void setC10in_monto_venta(double C10in_monto_venta){
		this.C10in_monto_venta = C10in_monto_venta;
	}
	public double getC11eg_monto_recibo_funcionario(){
		return C11eg_monto_recibo_funcionario;
	}
	public void setC11eg_monto_recibo_funcionario(double C11eg_monto_recibo_funcionario){
		this.C11eg_monto_recibo_funcionario = C11eg_monto_recibo_funcionario;
	}
	public double getC12eg_monto_gasto(){
		return C12eg_monto_gasto;
	}
	public void setC12eg_monto_gasto(double C12eg_monto_gasto){
		this.C12eg_monto_gasto = C12eg_monto_gasto;
	}
	public double getC13eg_monto_compra(){
		return C13eg_monto_compra;
	}
	public void setC13eg_monto_compra(double C13eg_monto_compra){
		this.C13eg_monto_compra = C13eg_monto_compra;
	}
	public int getC14fk_idventa(){
		return C14fk_idventa;
	}
	public void setC14fk_idventa(int C14fk_idventa){
		this.C14fk_idventa = C14fk_idventa;
	}
	public int getC15fk_idfuncionario_recibo(){
		return C15fk_idfuncionario_recibo;
	}
	public void setC15fk_idfuncionario_recibo(int C15fk_idfuncionario_recibo){
		this.C15fk_idfuncionario_recibo = C15fk_idfuncionario_recibo;
	}
	public int getC16fk_idgasto(){
		return C16fk_idgasto;
	}
	public void setC16fk_idgasto(int C16fk_idgasto){
		this.C16fk_idgasto = C16fk_idgasto;
	}
	public int getC17fk_idcompra(){
		return C17fk_idcompra;
	}
	public void setC17fk_idcompra(int C17fk_idcompra){
		this.C17fk_idcompra = C17fk_idcompra;
	}
	public int getC18fk_idusuario(){
		return C18fk_idusuario;
	}
	public void setC18fk_idusuario(int C18fk_idusuario){
		this.C18fk_idusuario = C18fk_idusuario;
	}
	public String toString() {
		return "caja_detalle(" + ",idcaja_detalle=" + C1idcaja_detalle + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,descripcion=" + C4descripcion + " ,tabla_origen=" + C5tabla_origen + " ,estado=" + C6estado + " ,monto_cierre=" + C7monto_cierre + " ,cierre=" + C8cierre + " ,in_monto_apertura=" + C9in_monto_apertura + " ,in_monto_venta=" + C10in_monto_venta + " ,eg_monto_recibo_funcionario=" + C11eg_monto_recibo_funcionario + " ,eg_monto_gasto=" + C12eg_monto_gasto + " ,eg_monto_compra=" + C13eg_monto_compra + " ,fk_idventa=" + C14fk_idventa + " ,fk_idfuncionario_recibo=" + C15fk_idfuncionario_recibo + " ,fk_idgasto=" + C16fk_idgasto + " ,fk_idcompra=" + C17fk_idcompra + " ,fk_idusuario=" + C18fk_idusuario + " )";
	}
}
