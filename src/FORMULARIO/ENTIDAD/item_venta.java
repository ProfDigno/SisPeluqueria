	package FORMULARIO.ENTIDAD;
public class item_venta {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_venta;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5es_producto;
private boolean C6es_servicio;
private String C7descripcion;
private double C8precio_venta;
private double C9precio_compra;
private double C10cantidad;
private boolean C11es_sum_punto;
private double C12porcentaje_comision;
private int C13fk_idventa;
private int C14fk_idservicio;
private int C15fk_idproducto;
private int C16fk_idfuncionario;
private double C17monto_comision;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_venta() {
		setTb_item_venta("item_venta");
		setId_iditem_venta("iditem_venta");
	}
	public static String getTb_item_venta(){
		return nom_tabla;
	}
	public static void setTb_item_venta(String nom_tabla){
		item_venta.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_venta(){
		return nom_idtabla;
	}
	public static void setId_iditem_venta(String nom_idtabla){
		item_venta.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iditem_venta(){
		return C1iditem_venta;
	}
	public void setC1iditem_venta(int C1iditem_venta){
		this.C1iditem_venta = C1iditem_venta;
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
	public boolean getC5es_producto(){
		return C5es_producto;
	}
	public void setC5es_producto(boolean C5es_producto){
		this.C5es_producto = C5es_producto;
	}
	public boolean getC6es_servicio(){
		return C6es_servicio;
	}
	public void setC6es_servicio(boolean C6es_servicio){
		this.C6es_servicio = C6es_servicio;
	}
	public String getC7descripcion(){
		return C7descripcion;
	}
	public void setC7descripcion(String C7descripcion){
		this.C7descripcion = C7descripcion;
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
	public double getC10cantidad(){
		return C10cantidad;
	}
	public void setC10cantidad(double C10cantidad){
		this.C10cantidad = C10cantidad;
	}
	public boolean getC11es_sum_punto(){
		return C11es_sum_punto;
	}
	public void setC11es_sum_punto(boolean C11es_sum_punto){
		this.C11es_sum_punto = C11es_sum_punto;
	}
	public double getC12porcentaje_comision(){
		return C12porcentaje_comision;
	}
	public void setC12porcentaje_comision(double C12porcentaje_comision){
		this.C12porcentaje_comision = C12porcentaje_comision;
	}
	public int getC13fk_idventa(){
		return C13fk_idventa;
	}
	public void setC13fk_idventa(int C13fk_idventa){
		this.C13fk_idventa = C13fk_idventa;
	}
	public int getC14fk_idservicio(){
		return C14fk_idservicio;
	}
	public void setC14fk_idservicio(int C14fk_idservicio){
		this.C14fk_idservicio = C14fk_idservicio;
	}
	public int getC15fk_idproducto(){
		return C15fk_idproducto;
	}
	public void setC15fk_idproducto(int C15fk_idproducto){
		this.C15fk_idproducto = C15fk_idproducto;
	}
	public int getC16fk_idfuncionario(){
		return C16fk_idfuncionario;
	}
	public void setC16fk_idfuncionario(int C16fk_idfuncionario){
		this.C16fk_idfuncionario = C16fk_idfuncionario;
	}

    public double getC17monto_comision() {
        return C17monto_comision;
    }

    public void setC17monto_comision(double C17monto_comision) {
        this.C17monto_comision = C17monto_comision;
    }
        
	public String toString() {
		return "item_venta(" + ",iditem_venta=" + C1iditem_venta + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,es_producto=" + C5es_producto + " ,es_servicio=" + C6es_servicio + " ,descripcion=" + C7descripcion + " ,precio_venta=" + C8precio_venta + " ,precio_compra=" + C9precio_compra + " ,cantidad=" + C10cantidad + " ,es_sum_punto=" + C11es_sum_punto + " ,porcentaje_comision=" + C12porcentaje_comision + " ,fk_idventa=" + C13fk_idventa + " ,fk_idservicio=" + C14fk_idservicio + " ,fk_idproducto=" + C15fk_idproducto + " ,fk_idfuncionario=" + C16fk_idfuncionario + " )";
	}
}
