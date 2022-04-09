	package FORMULARIO.ENTIDAD;
public class producto {

//---------------DECLARAR VARIABLES---------------
private int C1idproducto;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private String C6cod_barra;
private String C7nombre;
private double C8precio_venta;
private double C9precio_compra;
private double C10stock_actual;
private double C11stock_minimo;
private double C12stock_maximo;
private boolean C13control_stock;
private String C14ult_venta;
private String C15ult_compra;
private boolean C16insumo;
private double C17cantidad_uso;
private double C18precio_por_uso;
private double C19porcentaje_comision;
private int C20fk_idproducto_categoria;
private int C21fk_idproducto_unidad;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto() {
		setTb_producto("producto");
		setId_idproducto("idproducto");
	}
	public static String getTb_producto(){
		return nom_tabla;
	}
	public static void setTb_producto(String nom_tabla){
		producto.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto(){
		return nom_idtabla;
	}
	public static void setId_idproducto(String nom_idtabla){
		producto.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto(){
		return C1idproducto;
	}
	public void setC1idproducto(int C1idproducto){
		this.C1idproducto = C1idproducto;
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
	public String getC6cod_barra(){
		return C6cod_barra;
	}
	public void setC6cod_barra(String C6cod_barra){
		this.C6cod_barra = C6cod_barra;
	}
	public String getC7nombre(){
		return C7nombre;
	}
	public void setC7nombre(String C7nombre){
		this.C7nombre = C7nombre;
	}
	public double getC8precio_venta(){
		return C8precio_venta;
	}
	public void setC8precio_venta(double C8precio_venta){
		this.C8precio_venta = C8precio_venta;
	}
	public double getC9precio_compra(){
		return C9precio_compra;
	}
	public void setC9precio_compra(double C9precio_compra){
		this.C9precio_compra = C9precio_compra;
	}
	public double getC10stock_actual(){
		return C10stock_actual;
	}
	public void setC10stock_actual(double C10stock_actual){
		this.C10stock_actual = C10stock_actual;
	}
	public double getC11stock_minimo(){
		return C11stock_minimo;
	}
	public void setC11stock_minimo(double C11stock_minimo){
		this.C11stock_minimo = C11stock_minimo;
	}
	public double getC12stock_maximo(){
		return C12stock_maximo;
	}
	public void setC12stock_maximo(double C12stock_maximo){
		this.C12stock_maximo = C12stock_maximo;
	}
	public boolean getC13control_stock(){
		return C13control_stock;
	}
	public void setC13control_stock(boolean C13control_stock){
		this.C13control_stock = C13control_stock;
	}
	public String getC14ult_venta(){
		return C14ult_venta;
	}
	public void setC14ult_venta(String C14ult_venta){
		this.C14ult_venta = C14ult_venta;
	}
	public String getC15ult_compra(){
		return C15ult_compra;
	}
	public void setC15ult_compra(String C15ult_compra){
		this.C15ult_compra = C15ult_compra;
	}
	public boolean getC16insumo(){
		return C16insumo;
	}
	public void setC16insumo(boolean C16insumo){
		this.C16insumo = C16insumo;
	}
	public double getC17cantidad_uso(){
		return C17cantidad_uso;
	}
	public void setC17cantidad_uso(double C17cantidad_uso){
		this.C17cantidad_uso = C17cantidad_uso;
	}
	public double getC18precio_por_uso(){
		return C18precio_por_uso;
	}
	public void setC18precio_por_uso(double C18precio_por_uso){
		this.C18precio_por_uso = C18precio_por_uso;
	}
	public double getC19porcentaje_comision(){
		return C19porcentaje_comision;
	}
	public void setC19porcentaje_comision(double C19porcentaje_comision){
		this.C19porcentaje_comision = C19porcentaje_comision;
	}
	public int getC20fk_idproducto_categoria(){
		return C20fk_idproducto_categoria;
	}
	public void setC20fk_idproducto_categoria(int C20fk_idproducto_categoria){
		this.C20fk_idproducto_categoria = C20fk_idproducto_categoria;
	}
	public int getC21fk_idproducto_unidad(){
		return C21fk_idproducto_unidad;
	}
	public void setC21fk_idproducto_unidad(int C21fk_idproducto_unidad){
		this.C21fk_idproducto_unidad = C21fk_idproducto_unidad;
	}
	public String toString() {
		return "producto(" + ",idproducto=" + C1idproducto + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,cod_barra=" + C6cod_barra + " ,nombre=" + C7nombre + " ,precio_venta=" + C8precio_venta + " ,precio_compra=" + C9precio_compra + " ,stock_actual=" + C10stock_actual + " ,stock_minimo=" + C11stock_minimo + " ,stock_maximo=" + C12stock_maximo + " ,control_stock=" + C13control_stock + " ,ult_venta=" + C14ult_venta + " ,ult_compra=" + C15ult_compra + " ,insumo=" + C16insumo + " ,cantidad_uso=" + C17cantidad_uso + " ,precio_por_uso=" + C18precio_por_uso + " ,porcentaje_comision=" + C19porcentaje_comision + " ,fk_idproducto_categoria=" + C20fk_idproducto_categoria + " ,fk_idproducto_unidad=" + C21fk_idproducto_unidad + " )";
	}
}
