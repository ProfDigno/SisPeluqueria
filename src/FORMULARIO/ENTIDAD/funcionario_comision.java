	package FORMULARIO.ENTIDAD;
public class funcionario_comision {

//---------------DECLARAR VARIABLES---------------
private int C1idfuncionario_comision;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_pagado;
private double C5monto_comision;
private double C6monto_pagado;
private String C7estado;
private String C8descripcion;
private boolean C9es_pagado;
private int C10fk_idfuncionario_grupo_comision;
private int C11fk_iditem_venta;
private int C12fk_idfuncionario;
private int C13fk_idventa;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public funcionario_comision() {
		setTb_funcionario_comision("funcionario_comision");
		setId_idfuncionario_comision("idfuncionario_comision");
	}
	public static String getTb_funcionario_comision(){
		return nom_tabla;
	}
	public static void setTb_funcionario_comision(String nom_tabla){
		funcionario_comision.nom_tabla = nom_tabla;
	}
	public static String getId_idfuncionario_comision(){
		return nom_idtabla;
	}
	public static void setId_idfuncionario_comision(String nom_idtabla){
		funcionario_comision.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idfuncionario_comision(){
		return C1idfuncionario_comision;
	}
	public void setC1idfuncionario_comision(int C1idfuncionario_comision){
		this.C1idfuncionario_comision = C1idfuncionario_comision;
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
	public String getC4fecha_pagado(){
		return C4fecha_pagado;
	}
	public void setC4fecha_pagado(String C4fecha_pagado){
		this.C4fecha_pagado = C4fecha_pagado;
	}
	public double getC5monto_comision(){
		return C5monto_comision;
	}
	public void setC5monto_comision(double C5monto_comision){
		this.C5monto_comision = C5monto_comision;
	}
	public double getC6monto_pagado(){
		return C6monto_pagado;
	}
	public void setC6monto_pagado(double C6monto_pagado){
		this.C6monto_pagado = C6monto_pagado;
	}
	public String getC7estado(){
		return C7estado;
	}
	public void setC7estado(String C7estado){
		this.C7estado = C7estado;
	}
	public String getC8descripcion(){
		return C8descripcion;
	}
	public void setC8descripcion(String C8descripcion){
		this.C8descripcion = C8descripcion;
	}
	public boolean getC9es_pagado(){
		return C9es_pagado;
	}
	public void setC9es_pagado(boolean C9es_pagado){
		this.C9es_pagado = C9es_pagado;
	}
	public int getC10fk_idfuncionario_grupo_comision(){
		return C10fk_idfuncionario_grupo_comision;
	}
	public void setC10fk_idfuncionario_grupo_comision(int C10fk_idfuncionario_grupo_comision){
		this.C10fk_idfuncionario_grupo_comision = C10fk_idfuncionario_grupo_comision;
	}
	public int getC11fk_iditem_venta(){
		return C11fk_iditem_venta;
	}
	public void setC11fk_iditem_venta(int C11fk_iditem_venta){
		this.C11fk_iditem_venta = C11fk_iditem_venta;
	}
	public int getC12fk_idfuncionario(){
		return C12fk_idfuncionario;
	}
	public void setC12fk_idfuncionario(int C12fk_idfuncionario){
		this.C12fk_idfuncionario = C12fk_idfuncionario;
	}
	public int getC13fk_idventa(){
		return C13fk_idventa;
	}
	public void setC13fk_idventa(int C13fk_idventa){
		this.C13fk_idventa = C13fk_idventa;
	}
	public String toString() {
		return "funcionario_comision(" + ",idfuncionario_comision=" + C1idfuncionario_comision + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_pagado=" + C4fecha_pagado + " ,monto_comision=" + C5monto_comision + " ,monto_pagado=" + C6monto_pagado + " ,estado=" + C7estado + " ,descripcion=" + C8descripcion + " ,es_pagado=" + C9es_pagado + " ,fk_idfuncionario_grupo_comision=" + C10fk_idfuncionario_grupo_comision + " ,fk_iditem_venta=" + C11fk_iditem_venta + " ,fk_idfuncionario=" + C12fk_idfuncionario + " ,fk_idventa=" + C13fk_idventa + " )";
	}
}
