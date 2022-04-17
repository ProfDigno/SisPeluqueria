/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.Global_datos;
import Config_JSON.json_producto;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.BO.BO_producto;
import FORMULARIO.DAO.DAO_funcionario;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_producto_categoria;
import FORMULARIO.DAO.DAO_producto_unidad;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.producto_categoria;
import FORMULARIO.ENTIDAD.producto_unidad;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class FrmRepVentaFecCli extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenJTextField evejte = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private EvenJTextField evejtf = new EvenJTextField();
    private DAO_venta DAOven = new DAO_venta();
    private json_producto jsonp = new json_producto();
    private Global_datos gda = new Global_datos();
    private EvenFecha evefec = new EvenFecha();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    Connection conn = ConnPostgres.getConnPosgres();
    private String nombre_formulario = "VENTA FECHA CLIENTE";

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        evefec.cargar_combobox_intervalo_fecha(cmbfiltro_fecha);
        jCestado_emitido.setSelected(true);
        jCestado_comision.setSelected(true);
        actualizar_cliente();
        boton_imprimir(false);
    }

    private void boton_imprimir(boolean imprimir) {
        String filtro = "";
        String filtro_stock = "";
        if (tblcliente.getSelectedRow() >= 0) {
            int idcliente = eveJtab.getInt_select_id(tblcliente);
            String categeria = " and c.idcliente=" + idcliente;
            filtro = filtro + categeria;
        }
        filtro = filtro + filtro_estado(jCestado_emitido, jCestado_anulado,jCestado_comision);
        filtro = filtro + evefec.getIntervalo_fecha_combobox(cmbfiltro_fecha, "v.fecha_creado");
        suma_total_venta(filtro);
        if (imprimir) {
            DAOven.imprimir_rep_venta_fec_cli(conn, filtro);
        }
    }

    private void actualizar_cliente() {
        String sql = "select c.idcliente as idc,\n"
                + "(c.nombre||' '||c.apellido) as cliente,c.ruc \n"
                + "from cliente c\n"
                + "where c.activo=true \n"
                + "order by c.nombre asc;";
        eveconn.Select_cargar_jtable(conn, sql, tblcliente);
        ancho_tabla_producto_categoria(tblcliente);
    }

    private void suma_total_venta(String filtro) {
        String titulo = "suma_total_venta";
        double sumventa = 0;
        String sql = "select \n"
                + "sum(v.monto_pagado) as sumventa\n"
                + "from venta v,cliente c \n"
                + "where v.fk_idcliente=c.idcliente  " + filtro;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                sumventa = rs.getDouble(1);
                jFsum_venta.setValue(sumventa);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    public void ancho_tabla_producto_categoria(JTable tbltabla) {
        int Ancho[] = {10,70,20};
        eveJtab.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public String filtro_estado(JCheckBox jCestado_emitido, JCheckBox jCestado_anulado, JCheckBox jCestado_comision) {
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
            estado = condi + " v.estado='" + gda.getEstado_emitido() + "' ";
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
            estado = condi + " v.estado='" + gda.getEstado_anulado() + "' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_comision.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='" + gda.getEstado_comision() + "' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public FrmRepVentaFecCli() {
        initComponents();
        abrir_formulario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gru_cv = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblcliente = new javax.swing.JTable();
        btncategoria_todos = new javax.swing.JButton();
        btnimprimir = new javax.swing.JButton();
        jFsum_venta = new javax.swing.JFormattedTextField();
        cmbfiltro_fecha = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        jCestado_comision = new javax.swing.JCheckBox();

        setClosable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLAS"));

        tblcliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblcliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblclienteMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblcliente);

        btncategoria_todos.setText("TODOS");
        btncategoria_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncategoria_todosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btncategoria_todos))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncategoria_todos, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
        );

        btnimprimir.setText("IMPRIMIR ");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        jFsum_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL SERVICIO"));
        jFsum_venta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFsum_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsum_venta.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        cmbfiltro_fecha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfiltro_fecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbfiltro_fechaItemStateChanged(evt);
            }
        });

        jLabel18.setText("Fecha Filtro:");

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("ESTADO"));

        jCestado_emitido.setText("EMITIDO");
        jCestado_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_emitidoActionPerformed(evt);
            }
        });

        jCestado_anulado.setText("ANULADO");
        jCestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_anuladoActionPerformed(evt);
            }
        });

        jCestado_comision.setText("COMISION");
        jCestado_comision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_comisionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_comision)
                    .addComponent(jCestado_anulado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jCestado_emitido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_comision)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_anulado))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFsum_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(cmbfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(btnimprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jFsum_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir(true);
    }//GEN-LAST:event_btnimprimirActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_tabla_producto_categoria(tblcliente);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btncategoria_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncategoria_todosActionPerformed
        // TODO add your handling code here:
        actualizar_cliente();
        boton_imprimir(false);
    }//GEN-LAST:event_btncategoria_todosActionPerformed

    private void tblclienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblclienteMouseReleased
        // TODO add your handling code here:
        boton_imprimir(false);
    }//GEN-LAST:event_tblclienteMouseReleased

    private void cmbfiltro_fechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbfiltro_fechaItemStateChanged
        // TODO add your handling code here:
        boton_imprimir(false);
    }//GEN-LAST:event_cmbfiltro_fechaItemStateChanged

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        boton_imprimir(false);
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        boton_imprimir(false);
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void jCestado_comisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_comisionActionPerformed
        // TODO add your handling code here:
        boton_imprimir(false);
    }//GEN-LAST:event_jCestado_comisionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncategoria_todos;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JComboBox<String> cmbfiltro_fecha;
    private javax.swing.ButtonGroup gru_cv;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_comision;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JFormattedTextField jFsum_venta;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblcliente;
    // End of variables declaration//GEN-END:variables
}
