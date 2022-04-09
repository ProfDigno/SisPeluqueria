/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import BUSCAR.ClaVarBuscar;
import BUSCAR.JDiaBuscarDosColumnas;
import Evento.Color.cla_color_palete;
import Evento.Fecha.EvenFecha;
import Evento.JLabel.EvenJLabel;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class FrmGasto extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable eveJtab = new EvenJtable();
    EvenJLabel evelbl = new EvenJLabel();
    private gasto ENTg = new gasto();
    private BO_gasto BOg = new BO_gasto();
    private DAO_gasto DAOg = new DAO_gasto();
    private gasto_tipo ENTgt = new gasto_tipo();
    private DAO_gasto_tipo DAOgt = new DAO_gasto_tipo();
    private caja_detalle ENTcaja = new caja_detalle();
    private DAO_caja_detalle DAOcaja = new DAO_caja_detalle();
    private EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_palete clacolor = new cla_color_palete();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private EvenFecha evefec = new EvenFecha();
    private DAO_usuario dao_usu = new DAO_usuario();
    private usuario ENTusu = new usuario();
    private EvenConexion eveconn = new EvenConexion();
    private String creado_por ="";// ENTusu.getGlobal_idusuario() + "-" + ENTusu.getGlobal_nombre();
    private static int fk_idgasto_tipo;

    public static int getFk_idgasto_tipo() {
        return fk_idgasto_tipo;
    }

    public static void setFk_idgasto_tipo(int fk_idgasto_tipo) {
        FrmGasto.fk_idgasto_tipo = fk_idgasto_tipo;
    }
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ANULADO = "ANULADO";
    private double monto_gasto;
    private int idgasto;
    private int idgasto_select;

    private void abrir_formulario() {
        this.setTitle("GASTO");
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        color_formulario();
        evefec.cargar_combobox_intervalo_fecha(cmbfecha_gasto);
    }

    private void color_formulario() {
        panel_tabla.setBackground(clacolor.getColor_tabla());
        panel_insertar.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtfecha, "DEBE CARGAR UNA FECHA")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtadescripcion, "DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_gasto, "DEBE CARGAR UN MONTO")) {
            return false;
        }
        if (getFk_idgasto_tipo() == 0) {
            JOptionPane.showMessageDialog(null, "NO SE CARGO EL TIPO DE GASTO");
            txtbuscar_gasto_tipo.setText(null);
            txtbuscar_gasto_tipo.grabFocus();
            return false;
        }
        return true;
    }

    private void cargar_dato_caja_detalle() {
        DAOcaja.vaciar_caja_detalle(ENTcaja);
        ENTcaja.setC3creado_por(creado_por);
        ENTcaja.setC4descripcion("GASTO: " + txtadescripcion.getText());
        ENTcaja.setC6estado(estado_EMITIDO);
        ENTcaja.setC12eg_monto_gasto(monto_gasto);
        ENTcaja.setC16fk_idgasto(idgasto);
        ENTcaja.setC18fk_idusuario(ENTusu.getGlobal_idusuario());
    }

    private void cargar_dato() {
        ENTg.setC3creado_por(creado_por);
        ENTg.setC2fecha_creado(txtfecha.getText());
        ENTg.setC4descripcion(txtadescripcion.getText());
        monto_gasto = Double.parseDouble(txtmonto_gasto.getText());
        ENTg.setC6monto_gasto(monto_gasto);
        ENTg.setC5estado(estado_EMITIDO);
        ENTg.setC7fk_idgasto_tipo(getFk_idgasto_tipo());
        ENTg.setC8fk_idusuario(ENTusu.getGlobal_idusuario());
    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            cargar_dato_caja_detalle();
            BOg.insertar_gasto(ENTg,tbltabla);
            reestableser();
        }
    }

    private void boton_anular() {
        if (tbltabla.getSelectedRow() >= 0) {
            ENTg.setC1idgasto(Integer.parseInt(txtid.getText()));
            ENTg.setC5estado(estado_ANULADO);
            ENTcaja.setC6estado(estado_ANULADO);
//            ENTcaja.setNom_campo_todos("fk_idgasto");
//            ENTcaja.setFk_idtodos(idgasto_select);
//            BOg.anular_gasto(ENTg, ENTcaja);
            reestableser();
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONAR UN GASTO PARA ANULAR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionar_tabla() {
        idgasto_select = eveJtab.getInt_select_id(tbltabla);
        DAOg.cargar_gasto(conn, ENTg, idgasto_select);
        txtid.setText(String.valueOf(ENTg.getC1idgasto()));
        txtadescripcion.setText(ENTg.getC4descripcion());
        txtmonto_gasto.setText(String.valueOf((int) ENTg.getC6monto_gasto()));
        DAOgt.cargar_gasto_tipo(conn, ENTgt, ENTg.getC7fk_idgasto_tipo());
        txtbuscar_gasto_tipo.setText(ENTgt.getC5nombre());
        setFk_idgasto_tipo(ENTgt.getC1idgasto_tipo());
        btnguardar.setEnabled(false);
        btnanular.setEnabled(true);
    }

    private void reestableser() {
        idgasto = (eveconn.getInt_ultimoID_mas_uno(conn, ENTg.getTb_gasto(), ENTg.getId_idgasto()));
        actualizar_tabla_gasto();
        txtid.setText(null);
        txtfecha.setText(evefec.getString_formato_fecha());
        txtbuscar_gasto_tipo.setText(null);
        txtadescripcion.setText("NINGUNA");
        txtmonto_gasto.setText(null);
        btnguardar.setEnabled(true);
        btnanular.setEnabled(false);
        txtadescripcion.grabFocus();
    }

    void actualizar_tabla_gasto() {
        String filtro = "";
        String estado = "";
        if (jCanulado.isSelected()) {
            estado = " and g.estado='ANULADO' ";
        } else {
            estado = " and g.estado='EMITIDO' ";
        }
        String fecha = evefec.getIntervalo_fecha_combobox(cmbfecha_gasto, " g.fecha ");
        filtro = fecha + estado;
        DAOg.actualizar_tabla_gasto(conn, tbltabla);
        double suma_gasto = eveJtab.getDouble_sumar_tabla(tbltabla, 5);
        jFsuma_gasto.setValue(suma_gasto);
    }

    private void boton_nuevo() {
        reestableser();
    }

    private void abrir_buscar(int tipo, String nombre, JTextField txtbuscar) {
        vbus.setTipo_tabla(tipo);
        vbus.setNombre_tabla(nombre);
        vbus.setPre_busqueda(txtbuscar.getText());
        JDiaBuscarDosColumnas frm = new JDiaBuscarDosColumnas(null, true);
        frm.setVisible(true);
    }

    public FrmGasto() {
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

        panel_insertar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btnanular = new javax.swing.JButton();
        txtfecha = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtadescripcion = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtmonto_gasto = new javax.swing.JTextField();
        lblgasto_tipo = new javax.swing.JLabel();
        txtbuscar_gasto_tipo = new javax.swing.JTextField();
        btnbuscar_gasto_tipo = new javax.swing.JButton();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla = new javax.swing.JTable();
        jFsuma_gasto = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        cmbfecha_gasto = new javax.swing.JComboBox<>();
        jCanulado = new javax.swing.JCheckBox();

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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Fecha:");

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

        btnanular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btnanular.setText("ANULAR");
        btnanular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnanular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });

        txtfecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtfecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtadescripcion.setColumns(20);
        txtadescripcion.setRows(5);
        txtadescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));
        jScrollPane2.setViewportView(txtadescripcion);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("MONTO:");

        txtmonto_gasto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtmonto_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_gasto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_gastoKeyTyped(evt);
            }
        });

        lblgasto_tipo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblgasto_tipo.setText("Tipo:");
        lblgasto_tipo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblgasto_tipoMouseMoved(evt);
            }
        });
        lblgasto_tipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblgasto_tipoMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblgasto_tipoMouseExited(evt);
            }
        });

        txtbuscar_gasto_tipo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtbuscar_gasto_tipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_gasto_tipoKeyPressed(evt);
            }
        });

        btnbuscar_gasto_tipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_gasto_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_gasto_tipoActionPerformed(evt);
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
                        .addGap(93, 93, 93)
                        .addComponent(btnanular))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtmonto_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(lblgasto_tipo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(txtbuscar_gasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnbuscar_gasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblgasto_tipo)
                    .addComponent(txtbuscar_gasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_gasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtmonto_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btnanular))
                .addContainerGap())
        );

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

        jFsuma_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA GASTO:"));
        jFsuma_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsuma_gasto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO"));

        jLabel18.setText("Fecha:");

        cmbfecha_gasto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbfecha_gastoItemStateChanged(evt);
            }
        });

        jCanulado.setText("ANULADO");
        jCanulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCanuladoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCanulado)
                    .addComponent(cmbfecha_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbfecha_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCanulado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFsuma_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFsuma_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOg.ancho_tabla_gasto(tbltabla);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbltablaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltablaMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tbltablaMouseReleased

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnbuscar_gasto_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_gasto_tipoActionPerformed
        // TODO add your handling code here:
        abrir_buscar(9, "GASTO", txtbuscar_gasto_tipo);
    }//GEN-LAST:event_btnbuscar_gasto_tipoActionPerformed

    private void txtbuscar_gasto_tipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_gasto_tipoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            abrir_buscar(9, "GASTO", txtbuscar_gasto_tipo);
        }
    }//GEN-LAST:event_txtbuscar_gasto_tipoKeyPressed

    private void txtmonto_gastoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_gastoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_gastoKeyTyped

    private void cmbfecha_gastoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbfecha_gastoItemStateChanged
        // TODO add your handling code here:
        actualizar_tabla_gasto();
    }//GEN-LAST:event_cmbfecha_gastoItemStateChanged

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        // TODO add your handling code here:
        boton_anular();
    }//GEN-LAST:event_btnanularActionPerformed

    private void jCanuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCanuladoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_gasto();
    }//GEN-LAST:event_jCanuladoActionPerformed

    private void lblgasto_tipoMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblgasto_tipoMouseMoved
        // TODO add your handling code here:
        evelbl.evento_MouseMoved(lblgasto_tipo);
    }//GEN-LAST:event_lblgasto_tipoMouseMoved

    private void lblgasto_tipoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblgasto_tipoMouseExited
        // TODO add your handling code here:
        evelbl.evento_MouseExited(lblgasto_tipo);
    }//GEN-LAST:event_lblgasto_tipoMouseExited

    private void lblgasto_tipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblgasto_tipoMouseClicked
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto_tipo());
    }//GEN-LAST:event_lblgasto_tipoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btnbuscar_gasto_tipo;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JComboBox<String> cmbfecha_gasto;
    private javax.swing.JCheckBox jCanulado;
    private javax.swing.JFormattedTextField jFsuma_gasto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblgasto_tipo;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla;
    public static javax.swing.JTextArea txtadescripcion;
    public static javax.swing.JTextField txtbuscar_gasto_tipo;
    private javax.swing.JTextField txtfecha;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtmonto_gasto;
    // End of variables declaration//GEN-END:variables
}
