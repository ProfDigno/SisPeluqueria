	package FORMULARIO.ENTIDAD;
public class servicio {

//---------------DECLARAR VARIABLES---------------
private int C1idservicio;
private String C2fecha_creado;
private String C3creado_por;
private int C4orden;
private boolean C5activo;
private String C6cod_barra;
private String C7nombre;
private String C8descripcion;
private double C9precio_venta;
private double C10precio_compra;
private double C11porcentaje_comision;
private int C12duracion_hora;
private int C13duracion_minuto;
private int C14fk_idservicio_categoria;
private int C15fk_idfuncionario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public servicio() {
		setTb_servicio("servicio");
		setId_idservicio("idservicio");
	}
	public static String getTb_servicio(){
		return nom_tabla;
	}
	public static void setTb_servicio(String nom_tabla){
		servicio.nom_tabla = nom_tabla;
	}
	public static String getId_idservicio(){
		return nom_idtabla;
	}
	public static void setId_idservicio(String nom_idtabla){
		servicio.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idservicio(){
		return C1idservicio;
	}
	public void setC1idservicio(int C1idservicio){
		this.C1idservicio = C1idservicio;
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
	public String getC8descripcion(){
		return C8descripcion;
	}
	public void setC8descripcion(String C8descripcion){
		this.C8descripcion = C8descripcion;
	}
	public double getC9precio_venta(){
		return C9precio_venta;
	}
	public void setC9precio_venta(double C9precio_venta){
		this.C9precio_venta = C9precio_venta;
	}
	public double getC10precio_compra(){
		return C10precio_compra;
	}
	public void setC10precio_compra(double C10precio_compra){
		this.C10precio_compra = C10precio_compra;
	}
	public double getC11porcentaje_comision(){
		return C11porcentaje_comision;
	}
	public void setC11porcentaje_comision(double C11porcentaje_comision){
		this.C11porcentaje_comision = C11porcentaje_comision;
	}
	public int getC12duracion_hora(){
		return C12duracion_hora;
	}
	public void setC12duracion_hora(int C12duracion_hora){
		this.C12duracion_hora = C12duracion_hora;
	}
	public int getC13duracion_minuto(){
		return C13duracion_minuto;
	}
	public void setC13duracion_minuto(int C13duracion_minuto){
		this.C13duracion_minuto = C13duracion_minuto;
	}
	public int getC14fk_idservicio_categoria(){
		return C14fk_idservicio_categoria;
	}
	public void setC14fk_idservicio_categoria(int C14fk_idservicio_categoria){
		this.C14fk_idservicio_categoria = C14fk_idservicio_categoria;
	}
	public int getC15fk_idfuncionario(){
		return C15fk_idfuncionario;
	}
	public void setC15fk_idfuncionario(int C15fk_idfuncionario){
		this.C15fk_idfuncionario = C15fk_idfuncionario;
	}
	public String toString() {
		return "servicio(" + ",idservicio=" + C1idservicio + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,orden=" + C4orden + " ,activo=" + C5activo + " ,cod_barra=" + C6cod_barra + " ,nombre=" + C7nombre + " ,descripcion=" + C8descripcion + " ,precio_venta=" + C9precio_venta + " ,precio_compra=" + C10precio_compra + " ,porcentaje_comision=" + C11porcentaje_comision + " ,duracion_hora=" + C12duracion_hora + " ,duracion_minuto=" + C13duracion_minuto + " ,fk_idservicio_categoria=" + C14fk_idservicio_categoria + " ,fk_idfuncionario=" + C15fk_idfuncionario + " )";
	}
}
