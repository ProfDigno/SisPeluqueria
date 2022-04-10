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
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import IMPRESORA_POS.PosImprimir_Funcio_Recibo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmFuncionario extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable eveJtab = new EvenJtable();
    private EvenConexion eveconn = new EvenConexion();
    private funcionario ENTfun = new funcionario();
    private funcionario_comision ENTfc = new funcionario_comision();
    private funcionario_grupo_comision ENTfgc = new funcionario_grupo_comision();
    private funcionario_recibo ENTfr = new funcionario_recibo();
    private DAO_funcionario DAOfun = new DAO_funcionario();
    private BO_funcionario BOfun = new BO_funcionario();
//    private DAO_funcionario_grupo_comision DAOfg = new DAO_funcionario_grupo_comision();
    private DAO_funcionario_comision DAOfc = new DAO_funcionario_comision();
    private DAO_funcionario_grupo_comision DAOfgc = new DAO_funcionario_grupo_comision();
    private DAO_funcionario_recibo DAOfr = new DAO_funcionario_recibo();
    private caja_detalle ENTcd = new caja_detalle();
    private DAO_caja_detalle DAOcd = new DAO_caja_detalle();
    private venta ENTven = new venta();
    private DAO_venta DAOven = new DAO_venta();
    private BO_venta BOven = new BO_venta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    private Global_datos gda = new Global_datos();
    private PosImprimir_Funcio_Recibo posfr=new PosImprimir_Funcio_Recibo();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_palete clacolor = new cla_color_palete();
    private String nombre_formulario = "FUNCIONARIO";
    String creado_por = "DIGNO";
    private double total_comision;
    private double monto_recibo;
    private int fk_idfuncionario;
    private int idfuncionario_grupo_comision;

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        DAOfun.actualizar_tabla_funcionario(conn, tblfuncionario);
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
        if (evejtf.getBoo_JTextField_vacio(txtapellido, "DEBE CARGAR UN APELLIDO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcedula, "DEBE CARGAR UNA CEDULA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdireccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttelefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttotal_salario, "DEBE CARGAR UN TOTAL SALARIO")) {
            return false;
        }
        return true;
    }

    private void cargar_dato() {
        ENTfun.setC3creado_por(creado_por);
        ENTfun.setC4orden(Integer.parseInt(txtorden.getText()));
        ENTfun.setC5activo(jCactivar.isSelected());
        ENTfun.setC6nombre(txtnombre.getText());
        ENTfun.setC7apellido(txtapellido.getText());
        ENTfun.setC8cedula(txtcedula.getText());
        ENTfun.setC9direccion(txtdireccion.getText());
        ENTfun.setC10telefono(txttelefono.getText());
        ENTfun.setC11tiene_comision(jCtiene_comision.isSelected());
        ENTfun.setC12total_comision(getTotal_comision());
        ENTfun.setC13tiene_salario(jCtiene_salario.isSelected());
        ENTfun.setC14total_salario(Double.parseDouble(txttotal_salario.getText()));
    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOfun.insertar_funcionario(ENTfun, tblfuncionario);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTfun.setC1idfuncionario(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOfun.update_funcionario(ENTfun, tblfuncionario);
        }
    }

    private void seleccionar_tabla_funcionario() {
        int fk_idfuncionario = eveJtab.getInt_select_id(tblfuncionario);
        DAOfun.cargar_funcionario(conn, ENTfun, fk_idfuncionario);
        txtid.setText(String.valueOf(ENTfun.getC1idfuncionario()));
        txtorden.setText(String.valueOf(ENTfun.getC4orden()));
        jCactivar.setSelected(ENTfun.getC5activo());
        txtnombre.setText(ENTfun.getC6nombre());
        txtapellido.setText(ENTfun.getC7apellido());
        txtcedula.setText(ENTfun.getC8cedula());
        txtdireccion.setText(ENTfun.getC9direccion());
        txttelefono.setText(ENTfun.getC10telefono());
        jCtiene_comision.setSelected(ENTfun.getC11tiene_comision());
        jFtotal_comision.setValue(ENTfun.getC12total_comision());
        txttotal_comision.setText(String.valueOf((int) ENTfun.getC12total_comision()));
        txtmonto_pagar.setText("0");
        jCtiene_salario.setSelected(ENTfun.getC13tiene_salario());
        txttotal_salario.setText(String.valueOf(ENTfun.getC14total_salario()));
        DAOfgc.actualizar_tabla_funcionario_grupo_comision(conn, tblgrupo_comision, fk_idfuncionario);
        DAOfr.actualizar_tabla_funcionario_recibo(conn, tblrecibo, fk_idfuncionario);
        int idfuncionario_grupo_comision = DAOfgc.getInt_id_abierto_funcionario_grupo_comision(conn, fk_idfuncionario);
        DAOfc.actualizar_tabla_funcionario_comision(conn, tblcomision, idfuncionario_grupo_comision);
        calcular_saldo_comision();
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void calcular_saldo_comision() {
        int len_tt_comi = txttotal_comision.getText().trim().length();
        int len_pagar = txtmonto_pagar.getText().trim().length();
        if (len_pagar > 0 && len_tt_comi > 0) {
            int total_comision = Integer.parseInt(txttotal_comision.getText());
            int pagado = Integer.parseInt(txtmonto_pagar.getText());
            int saldo = total_comision - pagado;
            txtsaldo_comision.setText(String.valueOf(saldo));
            String monto_letra = nroletra.Convertir(txtmonto_pagar.getText(), true);
            txtmonto_letra.setText(monto_letra);
        }
    }

    private void seleccionar_tabla_grupo_comision() {
        int fk_idfuncionario_grupo_comision = eveJtab.getInt_select_id(tblgrupo_comision);
        DAOfc.actualizar_tabla_funcionario_comision(conn, tblcomision, fk_idfuncionario_grupo_comision);
    }

    private void reestableser() {
        txtid.setText(null);
        txtnombre.setText(null);
        txtorden.setText(eveconn.getInt_ultimo_ORDEN_mas_uno(conn, "funcionario", "orden"));
        txtapellido.setText(null);
        jCactivar.setSelected(true);
        txtcedula.setText(null);
        txtdireccion.setText(null);
        txttelefono.setText(null);
        txtdescripcion_recibo.setText("RECIBO POR COMISION");
        jCtiene_comision.setSelected(false);
        jFtotal_comision.setValue(0);
        jCtiene_salario.setSelected(false);
        txttotal_salario.setText(null);
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

    private boolean validar_pago() {
        if (tblfuncionario.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "SELECCIONAR UN FUNCIONARIO PARA PAGAR");
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_pagar, "CARGAR UN MONTO PARA PAGAR")) {
            return false;
        }
        int saldo = Integer.parseInt(txtsaldo_comision.getText());
        if (saldo < 0) {
            JOptionPane.showMessageDialog(null, "EL MONTO NO DEBE SER MAYOR A LA COMISION TOTAL:\nCOMISION:" + txttotal_comision.getText());
            txtmonto_pagar.setText("0");
            calcular_saldo_comision();
            txtmonto_pagar.grabFocus();
            return false;
        }
        return true;
    }

    private void boton_actualizar_total_comision() {
        if (tblfuncionario.getSelectedRow() >= 0) {
            int fk_idfuncionario = eveJtab.getInt_select_id(tblfuncionario);
            DAOfc.update_total_comision_funcionario(conn, fk_idfuncionario);
            seleccionar_tabla_funcionario();
            DAOfun.actualizar_tabla_funcionario(conn, tblfuncionario);
        }
    }

    private void cargar_dato_caja_detalle() {
        DAOcd.vaciar_caja_detalle(ENTcd);
        ENTcd.setC4descripcion(gda.getTbl_funcionario() + "=>" + txtnombre.getText());
        ENTcd.setC5tabla_origen(gda.getTbl_funcionario());
        ENTcd.setC8cierre(gda.getCaja_abierto());
        ENTcd.setC6estado(gda.getEstado_emitido());
        ENTcd.setC11eg_monto_recibo_funcionario(monto_recibo);
        ENTcd.setC15fk_idfuncionario_recibo(ENTfr.getC1idfuncionario_recibo());
        DAOcd.insertar_caja_detalle(conn, ENTcd);
    }

    private void cargar_dato_funcionario_recibo() {
        ENTfr.setC3creado_por(gda.getCreado_por());
        ENTfr.setC4descripcion("PAGO DE COMISION");
        ENTfr.setC5monto_recibo(monto_recibo);
        ENTfr.setC6monto_letra(txtmonto_letra.getText());
        ENTfr.setC7estado(gda.getEstado_emitido());
        ENTfr.setC8pago_comision(true);
        ENTfr.setC9pago_salario(false);
        ENTfr.setC10fk_idfuncionario(fk_idfuncionario);
        ENTfr.setC11fk_idfuncionario_grupo_comision(idfuncionario_grupo_comision);
        ENTfr.setC12fk_idusuario(gda.getFk_idusuario());
        DAOfr.insertar_funcionario_recibo(conn, ENTfr);
    }

    private void cargar_dato_funcionario_grupo_comision() {
        ENTfgc.setC6estado(gda.getEstado_cerrado());
        ENTfgc.setC7es_abierto(false);
        ENTfgc.setC1idfuncionario_grupo_comision(idfuncionario_grupo_comision);
        DAOfgc.update_funcionario_grupo_comision_CERRAR(conn, ENTfgc);
        DAOfgc.crear_si_no_existe_funcionario_grupo_comision(conn, fk_idfuncionario);
    }

    private void boton_pagar_comision() {
        if (validar_pago()) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE REALIZAR ESTE PAGO", "PAGO FUNCIONARIO", "PAGAR", "CANCELAR")) {
                fk_idfuncionario = eveJtab.getInt_select_id(tblfuncionario);
                idfuncionario_grupo_comision = DAOfgc.getInt_id_abierto_funcionario_grupo_comision(conn, fk_idfuncionario);
                int monto_pago = Integer.parseInt(txtmonto_pagar.getText());
                monto_recibo = Double.parseDouble(txtmonto_pagar.getText());
                int saldo_final = Integer.parseInt(txtsaldo_comision.getText());
                String sql = "select fc.idfuncionario_comision,fc.fk_idventa,fc.monto_pagado,(fc.monto_comision-fc.monto_pagado) as saldo\n"
                        + "from funcionario_comision fc \n"
                        + "where fc.fk_idfuncionario_grupo_comision=" + idfuncionario_grupo_comision
                        + " and fc.es_pagado=false\n"
                        + "order by fc.idfuncionario_comision  asc;";
                String titulo = "boton_pagar_comision";
                try {
                    if (conn.getAutoCommit()) {
                        conn.setAutoCommit(false);
                    }
                    ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
                    while (rs.next()) {
                        int idfuncionario_comision = rs.getInt("idfuncionario_comision");
                        int fk_idventa = rs.getInt("fk_idventa");
                        int saldo = rs.getInt("saldo");
                        int monto_pagado = rs.getInt("monto_pagado");
                        if (monto_pago > 0) {
                            if (saldo <= monto_pago) {
                                monto_pago = monto_pago - saldo;
                                if (monto_pagado > 0) {
                                    saldo = saldo + monto_pagado;
                                }
                                System.out.println("idfuncionario_comision:" + idfuncionario_comision + ", saldo:" + saldo);
                                ENTfc.setC1idfuncionario_comision(idfuncionario_comision);
                                ENTfc.setC6monto_pagado(saldo);
                                ENTfc.setC7estado(gda.getEstado_pagado());
                                ENTfc.setC9es_pagado(true);
                                DAOfc.update_funcionario_comision_PAGAR(conn, ENTfc);
                                ENTven.setC4estado(gda.getEstado_comision());
                                ENTven.setC1idventa(fk_idventa);
                                DAOven.estado_update_venta(conn, ENTven);
                            } else {
                                System.out.println("final=>idfuncionario_comision:" + idfuncionario_comision + ", saldo:" + monto_pago);
                                ENTfc.setC1idfuncionario_comision(idfuncionario_comision);
                                ENTfc.setC6monto_pagado(monto_pago);
                                ENTfc.setC7estado(gda.getEstado_abierto());
                                ENTfc.setC9es_pagado(false);
                                DAOfc.update_funcionario_comision_PAGAR(conn, ENTfc);
                                monto_pago = 0;
                            }
                        }
                    }
                    cargar_dato_funcionario_recibo();
                    if (saldo_final == 0) {
                        cargar_dato_funcionario_grupo_comision();
                    }
                    DAOfc.update_total_comision_funcionario(conn, fk_idfuncionario);
                    cargar_dato_caja_detalle();
                    seleccionar_tabla_funcionario();
                    DAOfun.actualizar_tabla_funcionario(conn, tblfuncionario);
                    conn.commit();
                    posfr.boton_imprimir_pos_funcionario_recibo(conn, ENTfr.getC1idfuncionario_recibo());
                } catch (Exception e) {
                    evemen.mensaje_error(e, ENTfc.toString(), titulo);
                    try {
                        conn.rollback();
                    } catch (SQLException e1) {
                        evemen.Imprimir_serial_sql_error(e1, ENTfc.toString(), titulo);
                    }
                }
            }
        }
    }
    private void boton_imprimir_funcionario_recibo() {
        if(tblrecibo.getSelectedRow()>=0){
            int idfuncionario_recibo = eveJtab.getInt_select_id(tblrecibo);
            posfr.boton_imprimir_pos_funcionario_recibo(conn, idfuncionario_recibo);
        }
    }
    public FrmFuncionario() {
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
        txtapellido = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtcedula = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtdireccion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txttelefono = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jCtiene_comision = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jFtotal_comision = new javax.swing.JFormattedTextField();
        jCtiene_salario = new javax.swing.JCheckBox();
        txttotal_salario = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblfuncionario = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        txttotal_comision = new javax.swing.JTextField();
        txtmonto_pagar = new javax.swing.JTextField();
        txtsaldo_comision = new javax.swing.JTextField();
        btnpagar_comision = new javax.swing.JButton();
        txtmonto_letra = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtdescripcion_recibo = new javax.swing.JTextField();
        btnactualizar_comision = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblgrupo_comision = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblcomision = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblrecibo = new javax.swing.JTable();
        btnimprimir_recibo = new javax.swing.JButton();

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
        jLabel5.setText("APELLIDO:");

        txtapellido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtapellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtapellidoKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("CEDULA:");

        txtcedula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcedulaKeyPressed(evt);
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("PAGOS"));

        jCtiene_comision.setText("TIENE COMISION");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("TOTAL COMISION:");

        jFtotal_comision.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_comision.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jCtiene_salario.setText("TIENE SALARIO");

        txttotal_salario.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txttotal_salario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txttotal_salario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttotal_salarioKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("TOTAL SALARIO:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCtiene_salario)
                    .addComponent(jCtiene_comision)
                    .addComponent(jLabel9)
                    .addComponent(jFtotal_comision, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabel10)
                    .addComponent(txttotal_salario))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jCtiene_comision)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jCtiene_salario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttotal_salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addComponent(btnnuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnguardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btndeletar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtorden, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCactivar)
                            .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtapellido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                .addComponent(txtnombre, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtcedula, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtdireccion)
                                .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(txtapellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtcedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jCactivar))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
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

        jTabbedPane1.addTab("DATO FUNCIONARIO", jPanel1);

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblfuncionario.setModel(new javax.swing.table.DefaultTableModel(
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
        tblfuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblfuncionarioMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblfuncionario);

        jLabel3.setText("BUSCAR:");

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("PAGO DE COMISION"));

        txttotal_comision.setEditable(false);
        txttotal_comision.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txttotal_comision.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txttotal_comision.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL COMISION"));

        txtmonto_pagar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtmonto_pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_pagar.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO PAGADO"));
        txtmonto_pagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtmonto_pagarMouseReleased(evt);
            }
        });
        txtmonto_pagar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_pagarKeyReleased(evt);
            }
        });

        txtsaldo_comision.setEditable(false);
        txtsaldo_comision.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtsaldo_comision.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtsaldo_comision.setBorder(javax.swing.BorderFactory.createTitledBorder("SALDO PENDIENTE"));

        btnpagar_comision.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnpagar_comision.setText("PAGAR");
        btnpagar_comision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpagar_comisionActionPerformed(evt);
            }
        });

        txtmonto_letra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel11.setText("DESCRIPCION:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtdescripcion_recibo))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(txttotal_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtmonto_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtsaldo_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnpagar_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtdescripcion_recibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txttotal_comision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtmonto_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtsaldo_comision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnpagar_comision, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btnactualizar_comision.setText("ACTUALIZAR COMISION");
        btnactualizar_comision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_comisionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnactualizar_comision)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnactualizar_comision))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jTabbedPane1.addTab("FILTRO FUNCIONARIO", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("GRUPO COMISION"));

        tblgrupo_comision.setModel(new javax.swing.table.DefaultTableModel(
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
        tblgrupo_comision.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblgrupo_comisionMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblgrupo_comision);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("COMISION"));

        tblcomision.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblcomision);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("COMISION", jPanel4);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("RECIBO FUNCIONARIO"));

        tblrecibo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblrecibo);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnimprimir_recibo.setText("IMPRIMIR RECIBO");
        btnimprimir_recibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_reciboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_recibo, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_recibo)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("RECIBOS", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOfun.ancho_tabla_funcionario(tblfuncionario);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblfuncionarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblfuncionarioMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_funcionario();
    }//GEN-LAST:event_tblfuncionarioMouseReleased

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
        evejtf.saltar_campo_enter(evt, txtnombre, txtapellido);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
//        DAOsc.buscar_tabla_funcionario(conn, tbltabla, txtbuscar);
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtordenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtordenKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtorden, txtnombre);
    }//GEN-LAST:event_txtordenKeyPressed

    private void txtapellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapellidoKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtapellido, txtcedula);
    }//GEN-LAST:event_txtapellidoKeyPressed

    private void txtcedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcedulaKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtcedula, txtdireccion);
    }//GEN-LAST:event_txtcedulaKeyPressed

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtdireccion, txttelefono);
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void txttelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txttelefono, txttotal_salario);
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txttotal_salarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttotal_salarioKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txttotal_salario, txtorden);
    }//GEN-LAST:event_txttotal_salarioKeyPressed

    private void tblgrupo_comisionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupo_comisionMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_grupo_comision();
    }//GEN-LAST:event_tblgrupo_comisionMouseReleased

    private void txtmonto_pagarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmonto_pagarMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmonto_pagarMouseReleased

    private void txtmonto_pagarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_pagarKeyReleased
        // TODO add your handling code here:
        calcular_saldo_comision();
    }//GEN-LAST:event_txtmonto_pagarKeyReleased

    private void btnpagar_comisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpagar_comisionActionPerformed
        // TODO add your handling code here:
        boton_pagar_comision();
    }//GEN-LAST:event_btnpagar_comisionActionPerformed

    private void btnactualizar_comisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_comisionActionPerformed
        // TODO add your handling code here:
        boton_actualizar_total_comision();
    }//GEN-LAST:event_btnactualizar_comisionActionPerformed

    private void btnimprimir_reciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_reciboActionPerformed
        // TODO add your handling code here:
        boton_imprimir_funcionario_recibo();
    }//GEN-LAST:event_btnimprimir_reciboActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_comision;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnimprimir_recibo;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnpagar_comision;
    private javax.swing.JCheckBox jCactivar;
    private javax.swing.JCheckBox jCtiene_comision;
    private javax.swing.JCheckBox jCtiene_salario;
    private javax.swing.JFormattedTextField jFtotal_comision;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tblcomision;
    private javax.swing.JTable tblfuncionario;
    private javax.swing.JTable tblgrupo_comision;
    private javax.swing.JTable tblrecibo;
    private javax.swing.JTextField txtapellido;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcedula;
    private javax.swing.JTextField txtdescripcion_recibo;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtmonto_letra;
    private javax.swing.JTextField txtmonto_pagar;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtorden;
    private javax.swing.JTextField txtsaldo_comision;
    private javax.swing.JTextField txttelefono;
    private javax.swing.JTextField txttotal_comision;
    private javax.swing.JTextField txttotal_salario;
    // End of variables declaration//GEN-END:variables
}
