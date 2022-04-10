/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONFIGURACION;

/**
 *
 * @author Digno
 */
public class Global_datos {

    public Global_datos() {
        setCreado_por("Digno");
        setFk_idusuario(1);
        setEstado_emitido("EMITIDO");
        setEstado_anulado("ANULADO");
        setEstado_pagado("PAGADO");
        setEstado_comision("COMISION");
        setPago_contado("CONTADO");
        setPago_credito("CREDITO");
        setEstado_abierto("ABIERTO");
        setEstado_cerrado("CERRADO");
        setTbl_venta("VENTA");
        setTbl_funcionario("FUNCIONARIO");
        setTbl_gasto("GASTO");
        setTbl_compra("COMPRA");
        setTbl_caja("CAJA");
        setCaja_abierto("A");
        setCaja_cierre("C");
        setCaja_anulado("N");
    }
    
    private static String estado_emitido;
    private static String estado_anulado;
    private static String estado_abierto;
    private static String estado_cerrado;
    private static String estado_pagado;
    private static String estado_comision;
    private static String pago_contado;
    private static String pago_credito;
    private static String creado_por;
    private static int fk_idusuario;
    private static String tbl_venta;
    private static String tbl_funcionario;
    private static String tbl_gasto;
    private static String tbl_compra;
    private static String tbl_caja;
    private static String caja_cierre;
    private static String caja_abierto;
    private static String caja_anulado;

    public static String getEstado_comision() {
        return estado_comision;
    }

    public static void setEstado_comision(String estado_comision) {
        Global_datos.estado_comision = estado_comision;
    }

    public static String getCaja_anulado() {
        return caja_anulado;
    }

    public static void setCaja_anulado(String caja_anulado) {
        Global_datos.caja_anulado = caja_anulado;
    }

    public static String getEstado_pagado() {
        return estado_pagado;
    }

    public static void setEstado_pagado(String estado_pagado) {
        Global_datos.estado_pagado = estado_pagado;
    }

    public static String getTbl_caja() {
        return tbl_caja;
    }

    public static void setTbl_caja(String tbl_caja) {
        Global_datos.tbl_caja = tbl_caja;
    }

    public static String getCaja_cierre() {
        return caja_cierre;
    }

    public static void setCaja_cierre(String caja_cierre) {
        Global_datos.caja_cierre = caja_cierre;
    }

    public static String getCaja_abierto() {
        return caja_abierto;
    }

    public static void setCaja_abierto(String caja_abierto) {
        Global_datos.caja_abierto = caja_abierto;
    }

    public static String getTbl_venta() {
        return tbl_venta;
    }

    public static void setTbl_venta(String tbl_venta) {
        Global_datos.tbl_venta = tbl_venta;
    }

    public static String getTbl_funcionario() {
        return tbl_funcionario;
    }

    public static void setTbl_funcionario(String tbl_funcionario) {
        Global_datos.tbl_funcionario = tbl_funcionario;
    }

    public static String getTbl_gasto() {
        return tbl_gasto;
    }

    public static void setTbl_gasto(String tbl_gasto) {
        Global_datos.tbl_gasto = tbl_gasto;
    }

    public static String getTbl_compra() {
        return tbl_compra;
    }

    public static void setTbl_compra(String tbl_compra) {
        Global_datos.tbl_compra = tbl_compra;
    }

    public static String getEstado_abierto() {
        return estado_abierto;
    }

    public static void setEstado_abierto(String estado_abierto) {
        Global_datos.estado_abierto = estado_abierto;
    }

    public static String getEstado_cerrado() {
        return estado_cerrado;
    }

    public static void setEstado_cerrado(String estado_cerrado) {
        Global_datos.estado_cerrado = estado_cerrado;
    }

    public static int getFk_idusuario() {
        return fk_idusuario;
    }

    public static void setFk_idusuario(int fk_idusuario) {
        Global_datos.fk_idusuario = fk_idusuario;
    }

    public static String getCreado_por() {
        return creado_por;
    }

    public static void setCreado_por(String creado_por) {
        Global_datos.creado_por = creado_por;
    }

    public static String getEstado_emitido() {
        return estado_emitido;
    }

    public static void setEstado_emitido(String estado_emitido) {
        Global_datos.estado_emitido = estado_emitido;
    }

    public static String getEstado_anulado() {
        return estado_anulado;
    }

    public static void setEstado_anulado(String estado_anulado) {
        Global_datos.estado_anulado = estado_anulado;
    }

    public static String getPago_contado() {
        return pago_contado;
    }

    public static void setPago_contado(String pago_contado) {
        Global_datos.pago_contado = pago_contado;
    }

    public static String getPago_credito() {
        return pago_credito;
    }

    public static void setPago_credito(String pago_credito) {
        Global_datos.pago_credito = pago_credito;
    }
    
    
}
