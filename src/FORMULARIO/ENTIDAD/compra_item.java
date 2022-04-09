	package FORMULARIO.ENTIDAD;
public class compra_item {

//---------------DECLARAR VARIABLES---------------
private int C1idcompra_item;
private String C2fecha_creado;
private String C3creado_por;
private String C4descripcion;
private double C5precio_compra;
private double C6cantidad;
private int C7fk_idcompra;
private int C8fk_idproducto;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public compra_item() {
		setTb_compra_item("compra_item");
		setId_idcompra_item("idcompra_item");
	}
	public static String getTb_compra_item(){
		return nom_tabla;
	}
	public static void setTb_compra_item(String nom_tabla){
		compra_item.nom_tabla = nom_tabla;
	}
	public static String getId_idcompra_item(){
		return nom_idtabla;
	}
	public static void setId_idcompra_item(String nom_idtabla){
		compra_item.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcompra_item(){
		return C1idcompra_item;
	}
	public void setC1idcompra_item(int C1idcompra_item){
		this.C1idcompra_item = C1idcompra_item;
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
	public double getC5precio_compra(){
		return C5precio_compra;
	}
	public void setC5precio_compra(double C5precio_compra){
		this.C5precio_compra = C5precio_compra;
	}
	public double getC6cantidad(){
		return C6cantidad;
	}
	public void setC6cantidad(double C6cantidad){
		this.C6cantidad = C6cantidad;
	}
	public int getC7fk_idcompra(){
		return C7fk_idcompra;
	}
	public void setC7fk_idcompra(int C7fk_idcompra){
		this.C7fk_idcompra = C7fk_idcompra;
	}
	public int getC8fk_idproducto(){
		return C8fk_idproducto;
	}
	public void setC8fk_idproducto(int C8fk_idproducto){
		this.C8fk_idproducto = C8fk_idproducto;
	}
	public String toString() {
		return "compra_item(" + ",idcompra_item=" + C1idcompra_item + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,descripcion=" + C4descripcion + " ,precio_compra=" + C5precio_compra + " ,cantidad=" + C6cantidad + " ,fk_idcompra=" + C7fk_idcompra + " ,fk_idproducto=" + C8fk_idproducto + " )";
	}
}
