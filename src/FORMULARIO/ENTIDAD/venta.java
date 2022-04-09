	package FORMULARIO.ENTIDAD;
public class venta {

//---------------DECLARAR VARIABLES---------------
private int C1idventa;
private String C2fecha_creado;
private String C3creado_por;
private String C4estado;
private String C5forma_pago;
private double C6monto_total;
private double C7monto_comision;
private double C8monto_descuento;
private double C9monto_pagado;
private double C10puntaje_cliente;
private boolean C11genera_puntaje;
private boolean C12uso_puntaje;
private int C13fk_idcliente;
private int C14fk_idconfiguracion_puntaje;
private int C15fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public venta() {
		setTb_venta("venta");
		setId_idventa("idventa");
	}
	public static String getTb_venta(){
		return nom_tabla;
	}
	public static void setTb_venta(String nom_tabla){
		venta.nom_tabla = nom_tabla;
	}
	public static String getId_idventa(){
		return nom_idtabla;
	}
	public static void setId_idventa(String nom_idtabla){
		venta.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idventa(){
		return C1idventa;
	}
	public void setC1idventa(int C1idventa){
		this.C1idventa = C1idventa;
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
	public String getC4estado(){
		return C4estado;
	}
	public void setC4estado(String C4estado){
		this.C4estado = C4estado;
	}
	public String getC5forma_pago(){
		return C5forma_pago;
	}
	public void setC5forma_pago(String C5forma_pago){
		this.C5forma_pago = C5forma_pago;
	}
	public double getC6monto_total(){
		return C6monto_total;
	}
	public void setC6monto_total(double C6monto_total){
		this.C6monto_total = C6monto_total;
	}
	public double getC7monto_comision(){
		return C7monto_comision;
	}
	public void setC7monto_comision(double C7monto_comision){
		this.C7monto_comision = C7monto_comision;
	}
	public double getC8monto_descuento(){
		return C8monto_descuento;
	}
	public void setC8monto_descuento(double C8monto_descuento){
		this.C8monto_descuento = C8monto_descuento;
	}
	public double getC9monto_pagado(){
		return C9monto_pagado;
	}
	public void setC9monto_pagado(double C9monto_pagado){
		this.C9monto_pagado = C9monto_pagado;
	}
	public double getC10puntaje_cliente(){
		return C10puntaje_cliente;
	}
	public void setC10puntaje_cliente(double C10puntaje_cliente){
		this.C10puntaje_cliente = C10puntaje_cliente;
	}
	public boolean getC11genera_puntaje(){
		return C11genera_puntaje;
	}
	public void setC11genera_puntaje(boolean C11genera_puntaje){
		this.C11genera_puntaje = C11genera_puntaje;
	}
	public boolean getC12uso_puntaje(){
		return C12uso_puntaje;
	}
	public void setC12uso_puntaje(boolean C12uso_puntaje){
		this.C12uso_puntaje = C12uso_puntaje;
	}
	public int getC13fk_idcliente(){
		return C13fk_idcliente;
	}
	public void setC13fk_idcliente(int C13fk_idcliente){
		this.C13fk_idcliente = C13fk_idcliente;
	}
	public int getC14fk_idconfiguracion_puntaje(){
		return C14fk_idconfiguracion_puntaje;
	}
	public void setC14fk_idconfiguracion_puntaje(int C14fk_idconfiguracion_puntaje){
		this.C14fk_idconfiguracion_puntaje = C14fk_idconfiguracion_puntaje;
	}
	public int getC15fk_idusuario(){
		return C15fk_idusuario;
	}
	public void setC15fk_idusuario(int C15fk_idusuario){
		this.C15fk_idusuario = C15fk_idusuario;
	}
	public String toString() {
		return "venta(" + ",idventa=" + C1idventa + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,estado=" + C4estado + " ,forma_pago=" + C5forma_pago + " ,monto_total=" + C6monto_total + " ,monto_comision=" + C7monto_comision + " ,monto_descuento=" + C8monto_descuento + " ,monto_pagado=" + C9monto_pagado + " ,puntaje_cliente=" + C10puntaje_cliente + " ,genera_puntaje=" + C11genera_puntaje + " ,uso_puntaje=" + C12uso_puntaje + " ,fk_idcliente=" + C13fk_idcliente + " ,fk_idconfiguracion_puntaje=" + C14fk_idconfiguracion_puntaje + " ,fk_idusuario=" + C15fk_idusuario + " )";
	}
}
