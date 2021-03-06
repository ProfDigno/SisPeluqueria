/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.Global_datos;
import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmProveedor extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable eveJtab = new EvenJtable();
    private EvenConexion eveconn=new EvenConexion();
    private proveedor ENTfun = new proveedor();
    private DAO_proveedor DAOfun = new DAO_proveedor();
    private BO_proveedor BOfun = new BO_proveedor();
    private EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_palete clacolor = new cla_color_palete();
    private Global_datos gda = new Global_datos();
    private String nombre_formulario = "PROVEEDOR";
//    String creado_por = "DIGNO";
    private double total_comision;

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        DAOfun.actualizar_tabla_proveedor(conn, tbltabla);
        color_formulario();
    }

    private void color_formulario() {
        panel_tabla.setBackground(clacolor.getColor_tabla());
        panel_insertar.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtorden, "DEBE CARGAR UN ORDEN")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtrazon_social, "DEBE CARGAR UN RAZON SOCIAL")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdireccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttelefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtruc, "DEBE CARGAR UN RUC")) {
            return false;
        }
        return true;
    }

    private void cargar_dato() {
        ENTfun.setC3creado_por(gda.getCreado_por());
        ENTfun.setC4orden(Integer.parseInt(txtorden.getText()));
        ENTfun.setC5activo(jCactivar.isSelected());
        ENTfun.setC7nombre(txtnombre.getText());
        ENTfun.setC6razon_social(txtrazon_social.getText());
        ENTfun.setC8ruc(txtruc.getText());
        ENTfun.setC9direccion(txtdireccion.getText());
        ENTfun.setC10telefono(txttelefono.getText());
    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOfun.insertar_proveedor(ENTfun, tbltabla);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTfun.setC1idproveedor(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOfun.update_proveedor(ENTfun, tbltabla);
        }
    }

    private void seleccionar_tabla() {
        int id = eveJtab.getInt_select_id(tbltabla);
        DAOfun.cargar_proveedor(conn, ENTfun, id);
        txtid.setText(String.valueOf(ENTfun.getC1idproveedor()));
        txtorden.setText(String.valueOf(ENTfun.getC4orden()));
        jCactivar.setSelected(ENTfun.getC5activo());
        txtnombre.setText(ENTfun.getC7nombre());
        txtrazon_social.setText(ENTfun.getC6razon_social());
        txtruc.setText(ENTfun.getC8ruc());
        txtdireccion.setText(ENTfun.getC9direccion());
        txttelefono.setText(ENTfun.getC10telefono());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser() {
        txtid.setText(null);
        txtnombre.setText(null);
        txtorden.setText(eveconn.getInt_ultimo_ORDEN_mas_uno(conn,"proveedor","orden"));
        txtrazon_social.setText(null);
        jCactivar.setSelected(true);
        txtdireccion.setText(null);
        txttelefono.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void boton_nuevo() {
        reestableser();
    }

    private double getTotal_comision() {
        return total_comision;
    }

    
    public FrmProveedor() {
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panel_insertar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        jCactivar = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        txtorden = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtrazon_social = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtdireccion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txttelefono = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtruc = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
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

        panel_insertar.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("NOMBRE:");

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
        });

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jCactivar.setText("ACTIVAR");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("ORDEN:");

        txtorden.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtorden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtordenKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("RAZON SOCIAL:");

        txtrazon_social.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtrazon_social.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrazon_socialKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("DIRECCION:");

        txtdireccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdireccionKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("TELEFONO:");

        txttelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefonoKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("RUC:");

        txtruc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrucKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndeletar))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtorden, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCactivar)
                            .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtnombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                .addComponent(txtdireccion)
                                .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtrazon_social, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 322, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtrazon_social, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCactivar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addComponent(btndeletar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("DATO PROVEEDOR", jPanel1);

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tbltabla.setModel(new javax.swing.table.DefaultTableModel(
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
        tbltabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbltablaMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbltabla);

        jLabel3.setText("BUSCAR:");

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(515, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("FILTRO PROVEEDOR", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOfun.ancho_tabla_proveedor(tbltabla);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbltablaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltablaMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tbltablaMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
       evejtf.saltar_campo_enter(evt, txtnombre, txtrazon_social);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
//        DAOsc.buscar_tabla_funcionario(conn, tbltabla, txtbuscar);
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtordenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtordenKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtorden, txtnombre);
    }//GEN-LAST:event_txtordenKeyPressed

    private void txtrazon_socialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrazon_socialKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtrazon_social, txtruc);
    }//GEN-LAST:event_txtrazon_socialKeyPressed

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtdireccion, txttelefono);
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void txttelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txttelefono, txtorden);
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txtrucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrucKeyPressed
        // TODO add your handling code here:
         evejtf.saltar_campo_enter(evt, txtruc, txtdireccion);
    }//GEN-LAST:event_txtrucKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JCheckBox jCactivar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtorden;
    private javax.swing.JTextField txtrazon_social;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
