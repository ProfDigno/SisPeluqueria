/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import Config_JSON.json_grafico;
import Evento.Fecha.EvenFecha;
import Evento.Grafico.EvenDataSetPelu;
import Evento.Grafico.FunFreeChard;
import Evento.Jframe.EvenJFRAME;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.Connection;
import javax.swing.JPanel;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Digno
 */
public class FrmGraficoTodo extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenDataSetPelu evedata = new EvenDataSetPelu();
    private FunFreeChard chard = new FunFreeChard();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private Connection conn = ConnPostgres.getConnPosgres();
    private EvenFecha evefec = new EvenFecha();
    private json_grafico jsongra = new json_grafico();
    private String nombre_formulario = "GRAFICOS TODO";

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        jsongra.cargar_jsom_grafico();
        evetbl.centrar_formulario_internalframa(this);
        evefec.cargar_combobox_intervalo_fecha(cmbfiltro_fecha);
    }

    private void grafico_producto_vendido(int tipo) {
        String top = jsongra.getTop_producto_vendido();
        String titulo = "titulo";
        String plano_horizontal = "horizontal";
        String plano_vertical = "vertical";
        String campo = "";
        String filtro = "";
        JPanel panel_grafico = null;
        String nom_fec=cmbfiltro_fecha.getSelectedItem().toString();
        if (tipo == 1) {
            titulo = nom_fec+",PRODUCTO CANTIDAD TOP:" + top;
            plano_horizontal = "PRODUCTOS";
            plano_vertical = "CANTIDAD";
            campo = "iv.cantidad";
            panel_grafico = panel_producto_vendido_cantidad;
        }
        if (tipo == 2) {
            titulo = nom_fec+",PRODUCTO MONTO TOP:" + top;
            plano_horizontal = "PRODUCTOS";
            plano_vertical = "MONTO";
            campo = "iv.cantidad*iv.precio_venta";
            panel_grafico = panel_producto_vendido_monto;
        }
        filtro = filtro + evefec.getIntervalo_fecha_combobox(cmbfiltro_fecha, "v.fecha_creado");
        DefaultCategoryDataset dataset = evedata.createDataset_producto_mas_vendido(conn, campo, filtro, top);
        chard.crear_graficoBar3D(panel_grafico, dataset, titulo, plano_horizontal, plano_vertical);
    }

    private void grafico_servicio_vendido(int tipo) {
        String top = jsongra.getTop_servicio_vendido();
        String titulo = "titulo";
        String plano_horizontal = "horizontal";
        String plano_vertical = "vertical";
        String campo = "";
        String filtro = "";
        JPanel panel_grafico = null;
        String nom_fec=cmbfiltro_fecha.getSelectedItem().toString();
        if (tipo == 1) {
            titulo = nom_fec+",SERVICIO CANTIDAD TOP:" + top;
            plano_horizontal = "SERVICIO";
            plano_vertical = "CANTIDAD";
            campo = "iv.cantidad";
            panel_grafico = panel_servicio_vendido_cantidad;
        }
        if (tipo == 2) {
            titulo = nom_fec+",SERVICIO MONTO TOP:" + top;
            plano_horizontal = "SERVICIO";
            plano_vertical = "MONTO";
            campo = "iv.cantidad*iv.precio_venta";
            panel_grafico = panel_servicio_vendido_monto;
        }
        filtro = filtro + evefec.getIntervalo_fecha_combobox(cmbfiltro_fecha, "v.fecha_creado");
        DefaultCategoryDataset dataset = evedata.createDataset_servicio_mas_vendido(conn, campo, filtro, top);
        chard.crear_graficoBar3D(panel_grafico, dataset, titulo, plano_horizontal, plano_vertical);
    }
    private void grafico_venta_semana() {
        String titulo = "titulo";
        String plano_horizontal = "horizontal";
        String plano_vertical = "vertical";
        String campo = "";
        JPanel panel_grafico = panel_total_venta;
        DefaultCategoryDataset dataset =null;
        String calendario="";
        if (jResta_semana.isSelected()) {
            titulo = "VENTA ESTA SEMANA";
            plano_horizontal = "DIAS";
            plano_vertical = "MONTO";
            campo = "";
            calendario="week";
            dataset = evedata.createDataset_venta_dia_semana(conn,calendario, campo);
        }
        if (jRsemana_pasada.isSelected()) {
            titulo = "VENTA SEMANA PASADA";
            plano_horizontal = "DIAS";
            plano_vertical = "MONTO";
            campo = " - interval '1 week' ";
            calendario="week";
            dataset = evedata.createDataset_venta_dia_semana(conn, calendario,campo);
        }
        if (jReste_mes.isSelected()) {
            titulo = "VENTA ESTE MES";
            plano_horizontal = "DIAS";
            plano_vertical = "MONTO";
            campo = "";
            calendario="month";
            dataset = evedata.createDataset_venta_dia_semana(conn,calendario, campo);
        }
        if (jRmes_anterior.isSelected()) {
            titulo = "VENTA MES ANTERIOR";
            plano_horizontal = "DIAS";
            plano_vertical = "MONTO";
            campo = " - interval '1 month' ";
            calendario="month";
            dataset = evedata.createDataset_venta_dia_semana(conn,calendario, campo);
        }
        chard.crear_graficoBar3D(panel_grafico, dataset, titulo, plano_horizontal, plano_vertical);
    }
    private void actualizar_filtro() {
        if (jTabbe_grafico.getSelectedIndex() == 0) {
            grafico_producto_vendido(1);
            grafico_producto_vendido(2);
        }
        if (jTabbe_grafico.getSelectedIndex() == 1) {
            grafico_servicio_vendido(1);
            grafico_servicio_vendido(2);
        }
        if(jTabbe_grafico.getSelectedIndex()==2) {
            grafico_venta_semana();
        }
    }

    public FrmGraficoTodo() {
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

        gru_ven_fec = new javax.swing.ButtonGroup();
        jTabbe_grafico = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        panel_producto_vendido_cantidad = new javax.swing.JPanel();
        panel_producto_vendido_monto = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        panel_servicio_vendido_cantidad = new javax.swing.JPanel();
        panel_servicio_vendido_monto = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        panel_total_venta = new javax.swing.JPanel();
        jResta_semana = new javax.swing.JRadioButton();
        jRsemana_pasada = new javax.swing.JRadioButton();
        jRmes_anterior = new javax.swing.JRadioButton();
        jReste_mes = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        cmbfiltro_fecha = new javax.swing.JComboBox<>();
        btnconfig = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        jTabbe_grafico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbe_graficoMousePressed(evt);
            }
        });

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_producto_vendido_cantidad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panel_producto_vendido_cantidadLayout = new javax.swing.GroupLayout(panel_producto_vendido_cantidad);
        panel_producto_vendido_cantidad.setLayout(panel_producto_vendido_cantidadLayout);
        panel_producto_vendido_cantidadLayout.setHorizontalGroup(
            panel_producto_vendido_cantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 554, Short.MAX_VALUE)
        );
        panel_producto_vendido_cantidadLayout.setVerticalGroup(
            panel_producto_vendido_cantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );

        jLayeredPane1.add(panel_producto_vendido_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 560, 480));

        panel_producto_vendido_monto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panel_producto_vendido_montoLayout = new javax.swing.GroupLayout(panel_producto_vendido_monto);
        panel_producto_vendido_monto.setLayout(panel_producto_vendido_montoLayout);
        panel_producto_vendido_montoLayout.setHorizontalGroup(
            panel_producto_vendido_montoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
        );
        panel_producto_vendido_montoLayout.setVerticalGroup(
            panel_producto_vendido_montoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );

        jLayeredPane1.add(panel_producto_vendido_monto, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 570, 480));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        );

        jTabbe_grafico.addTab("VENTA PRODUCTO", jPanel1);

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_servicio_vendido_cantidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_servicio_vendido_cantidadLayout = new javax.swing.GroupLayout(panel_servicio_vendido_cantidad);
        panel_servicio_vendido_cantidad.setLayout(panel_servicio_vendido_cantidadLayout);
        panel_servicio_vendido_cantidadLayout.setHorizontalGroup(
            panel_servicio_vendido_cantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 556, Short.MAX_VALUE)
        );
        panel_servicio_vendido_cantidadLayout.setVerticalGroup(
            panel_servicio_vendido_cantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        jLayeredPane2.add(panel_servicio_vendido_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 560, 480));

        panel_servicio_vendido_monto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_servicio_vendido_montoLayout = new javax.swing.GroupLayout(panel_servicio_vendido_monto);
        panel_servicio_vendido_monto.setLayout(panel_servicio_vendido_montoLayout);
        panel_servicio_vendido_montoLayout.setHorizontalGroup(
            panel_servicio_vendido_montoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        panel_servicio_vendido_montoLayout.setVerticalGroup(
            panel_servicio_vendido_montoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        jLayeredPane2.add(panel_servicio_vendido_monto, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 570, 480));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        );

        jTabbe_grafico.addTab("VENTA SERVICIO", jPanel2);

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLayeredPane3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_total_venta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panel_total_ventaLayout = new javax.swing.GroupLayout(panel_total_venta);
        panel_total_venta.setLayout(panel_total_ventaLayout);
        panel_total_ventaLayout.setHorizontalGroup(
            panel_total_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1016, Short.MAX_VALUE)
        );
        panel_total_ventaLayout.setVerticalGroup(
            panel_total_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        jLayeredPane3.add(panel_total_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 1020, 480));

        gru_ven_fec.add(jResta_semana);
        jResta_semana.setSelected(true);
        jResta_semana.setText("Esta Semana");
        jResta_semana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jResta_semanaActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jResta_semana, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        gru_ven_fec.add(jRsemana_pasada);
        jRsemana_pasada.setText("Semana Pasada");
        jRsemana_pasada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRsemana_pasadaActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jRsemana_pasada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        gru_ven_fec.add(jRmes_anterior);
        jRmes_anterior.setText("Mes Anterior");
        jRmes_anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRmes_anteriorActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jRmes_anterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        gru_ven_fec.add(jReste_mes);
        jReste_mes.setText("Este Mes");
        jReste_mes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jReste_mesActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jReste_mes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        );

        jTabbe_grafico.addTab("TOTAL VENTA", jPanel3);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Fecha:");

        cmbfiltro_fecha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfiltro_fecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbfiltro_fechaItemStateChanged(evt);
            }
        });
        cmbfiltro_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbfiltro_fechaActionPerformed(evt);
            }
        });

        btnconfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/config.png"))); // NOI18N
        btnconfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfigActionPerformed(evt);
            }
        });

        jButton1.setText("VER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbe_grafico)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel18)
                .addGap(5, 5, 5)
                .addComponent(cmbfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jTabbe_grafico, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        actualizar_filtro();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbfiltro_fechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbfiltro_fechaItemStateChanged
        // TODO add your handling code here:
//        boton_imprimir(false);
        
    }//GEN-LAST:event_cmbfiltro_fechaItemStateChanged

    private void btnconfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfigActionPerformed
        // TODO add your handling code here:
        jsongra.abrir_config_grafico();
    }//GEN-LAST:event_btnconfigActionPerformed

    private void jTabbe_graficoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbe_graficoMousePressed
        // TODO add your handling code here:
        actualizar_filtro();
    }//GEN-LAST:event_jTabbe_graficoMousePressed

    private void jResta_semanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jResta_semanaActionPerformed
        // TODO add your handling code here:
        actualizar_filtro();
    }//GEN-LAST:event_jResta_semanaActionPerformed

    private void jRsemana_pasadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRsemana_pasadaActionPerformed
        // TODO add your handling code here:
        actualizar_filtro();
    }//GEN-LAST:event_jRsemana_pasadaActionPerformed

    private void jReste_mesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jReste_mesActionPerformed
        // TODO add your handling code here:
        actualizar_filtro();
    }//GEN-LAST:event_jReste_mesActionPerformed

    private void jRmes_anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRmes_anteriorActionPerformed
        // TODO add your handling code here:
        actualizar_filtro();
    }//GEN-LAST:event_jRmes_anteriorActionPerformed

    private void cmbfiltro_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfiltro_fechaActionPerformed
        // TODO add your handling code here:
//        actualizar_filtro();
    }//GEN-LAST:event_cmbfiltro_fechaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnconfig;
    private javax.swing.JComboBox<String> cmbfiltro_fecha;
    private javax.swing.ButtonGroup gru_ven_fec;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jResta_semana;
    private javax.swing.JRadioButton jReste_mes;
    private javax.swing.JRadioButton jRmes_anterior;
    private javax.swing.JRadioButton jRsemana_pasada;
    private javax.swing.JTabbedPane jTabbe_grafico;
    private javax.swing.JPanel panel_producto_vendido_cantidad;
    private javax.swing.JPanel panel_producto_vendido_monto;
    private javax.swing.JPanel panel_servicio_vendido_cantidad;
    private javax.swing.JPanel panel_servicio_vendido_monto;
    private javax.swing.JPanel panel_total_venta;
    // End of variables declaration//GEN-END:variables
}
