/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUSCAR;

/**
 *
 * @author Digno
 */
public class ClaVarBuscar {
    private static int tipo_tabla;
    private static String nombre_tabla;
    private static String pre_busqueda;
    public static int getTipo_tabla() {
        return tipo_tabla;
    }

    public static void setTipo_tabla(int tipo_tabla) {
        ClaVarBuscar.tipo_tabla = tipo_tabla;
    }

    public static String getNombre_tabla() {
        return nombre_tabla;
    }

    public static void setNombre_tabla(String nombre_tabla) {
        ClaVarBuscar.nombre_tabla = nombre_tabla;
    }

    public static String getPre_busqueda() {
        return pre_busqueda;
    }

    public static void setPre_busqueda(String pre_busqueda) {
        ClaVarBuscar.pre_busqueda = pre_busqueda;
    }
    public void limpiar_variables_buscar(){
        setNombre_tabla("SIN-NOMBRE");
        setTipo_tabla(0);
        setPre_busqueda(null);
    }
}
