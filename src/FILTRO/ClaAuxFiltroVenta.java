/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FILTRO;

import javax.swing.JCheckBox;

/**
 *
 * @author Digno
 */
public class ClaAuxFiltroVenta {
    public String filtro_estado(JCheckBox jCestado_emitido,JCheckBox jCestado_anulado) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCestado_emitido.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='PAGADO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_anulado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='ANULADO' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public String forma_pago(JCheckBox jCpago_efectivo,JCheckBox jCpago_tarjeta,JCheckBox jCpago_combinado) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCpago_efectivo.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='EFECTIVO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCpago_tarjeta.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='TARJETA' ";
            sumaestado = sumaestado + estado;
        }
        if (jCpago_combinado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='COMBINADO' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
        public String filtro_tipo_nota(JCheckBox jCtipo_ticket,JCheckBox jCtipo_factura) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCtipo_ticket.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.tipo_nota='TICKET' ";
            sumaestado = sumaestado + estado;
        }
        if (jCtipo_factura.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.tipo_nota='FACTURA' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public String filtro_tipocliente(JCheckBox jCtipo_cliente,JCheckBox jCtipo_funcionario) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCtipo_cliente.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " c.tipo='cliente' ";
            sumaestado = sumaestado + estado;
        }
        if (jCtipo_funcionario.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " c.tipo='funcionario' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public String filtro_grupo_producto(JCheckBox jCgrupo_0,JCheckBox jCgrupo_1,JCheckBox jCdelivery) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCgrupo_0.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " iv.grupo=0 and iv.tipo!='D' ";
            sumaestado = sumaestado + estado;
        }
        if (jCgrupo_1.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " iv.grupo=1 and iv.tipo!='D' ";
            sumaestado = sumaestado + estado;
        }
        if (jCdelivery.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + "  iv.tipo='D' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public String filtro_tipo_comprobante(JCheckBox jCcon_comprobante,JCheckBox jCsin_comprobante,JCheckBox jCboleta_despachante,JCheckBox jCmercaderia,JCheckBox jCtipo_factura) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
//        String eswhere = "";
        if (jCcon_comprobante.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " where(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " con_comprobante=true ";
            sumaestado = sumaestado + estado;
        }
        if (jCsin_comprobante.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " where(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " sin_comprobante=true ";
            sumaestado = sumaestado + estado;
        }
        if (jCboleta_despachante.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " where(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " boleta_despachante=true ";
            sumaestado = sumaestado + estado;
        }
        if (jCmercaderia.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " where(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " mercaderia=true ";
            sumaestado = sumaestado + estado;
        }
        if (jCtipo_factura.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " where(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " tipo_factura=true ";
            sumaestado = sumaestado + estado;
        }
        
        return sumaestado + fin;
    }
    
    public String filtro_liquidacion(JCheckBox jCliq_emitido,JCheckBox jCliq_pagado,JCheckBox jCliq_proforma,JCheckBox jCliq_anulado) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
//        String eswhere = "";
        if (jCliq_emitido.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " lf.estado='EMITIDO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCliq_pagado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " lf.estado='PAGADO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCliq_proforma.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " lf.estado='PROFORMA' ";
            sumaestado = sumaestado + estado;
        }
        if (jCliq_anulado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " lf.estado='ANULADO' ";
            sumaestado = sumaestado + estado;
        }        
        return sumaestado + fin;
    }
}
