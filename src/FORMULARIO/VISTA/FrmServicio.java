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
import Config_JSON.json_producto;
import Config_JSON.json_servicio;
import Evento.Color.cla_color_palete;
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
public class FrmServicio extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable eveJtab = new EvenJtable();
    EvenJTextField evejte = new EvenJTextField();
    private EvenConexion eveconn=new EvenConexion();
    private servicio ENTser = new servicio();
    private DAO_servicio DAOser = new DAO_servicio();
    private BO_servicio BOser = new BO_servicio();
    private EvenJTextField evejtf = new EvenJTextField();
    private servicio_categoria ENTsc = new servicio_categoria();
    private DAO_servicio_categoria DAOsc = new DAO_servicio_categoria();
    private funcionario ENTpu = new funcionario();
    private DAO_funcionario DAOpu = new DAO_funcionario();
//    private json_producto jsonp = new json_servicio();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_palete clacolor = new cla_color_palete();
    private String nombre_formulario = "SERVICIO";
    String creado_por = "DIGNO";
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private static int fk_idservicio_categoria;
    private static int fk_idfuncionario;
    private json_servicio jsons=new json_servicio();
    public static int getFk_idservicio_categoria() {
        return fk_idservicio_categoria;
    }

    public static void setFk_idservicio_categoria(int fk_idservicio_categoria) {
        FrmServicio.fk_idservicio_categoria = fk_idservicio_categoria;
    }

    public static int getFk_idfuncionario() {
        return fk_idfuncionario;
    }

    public static void setFk_idfuncionario(int fk_idfuncionario) {
        FrmServicio.fk_idfuncionario = fk_idfuncionario;
    }
    

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        jsons.cargar_jsom_servicio();
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        DAOser.actualizar_tabla_servicio(conn, tbltabla);
        color_formulario();
    }

    private void color_formulario() {
        panel_tabla.setBackground(clacolor.getColor_tabla());
        panel_insertar.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtcod_barra, "DEBE CARGAR UN COD BARRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtbuscar_servicio_categoria, "DEBE CARGAR UNA CATEGORIA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtbuscar_funcionario, "DEBE CARGAR UN FUNCIONARIO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtprecio_venta, "DEBE CARGAR UN PRECIO VENTA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtprecio_compra, "DEBE CARGAR UN PRECIO COMPRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtporcentaje_comision, "DEBE CARGAR UN PORCENTAJE COMISION")) {
            return false;
        }
        
        if (getboo_config_comision()) {
            return false;
        }
        if(getboo_limite_min_max(txtduracion_hora, 0, 8)){
            return false;
        }
        if(getboo_limite_min_max(txtduracion_minuto, 0, 59)){
            return false;
        }
        return true;
    }

    void cargar_dato_servicio() {
        ENTser.setC3creado_por(creado_por);
        ENTser.setC4orden(Integer.parseInt(txtorden.getText()));
        ENTser.setC5activo(jCactivar.isSelected());
        ENTser.setC6cod_barra(txtcod_barra.getText());
        ENTser.setC7nombre(txtnombre.getText());
        ENTser.setC8descripcion(txtadescripcion.getText());
        ENTser.setC9precio_venta(Double.parseDouble(txtprecio_venta.getText()));
        ENTser.setC10precio_compra(Double.parseDouble(txtprecio_compra.getText()));
        ENTser.setC11porcentaje_comision(Double.parseDouble(txtporcentaje_comision.getText()));
        ENTser.setC12duracion_hora(Integer.parseInt(txtduracion_hora.getText()));
        ENTser.setC13duracion_minuto(Integer.parseInt(txtduracion_minuto.getText()));
        ENTser.setC14fk_idservicio_categoria(getFk_idservicio_categoria());
        ENTser.setC15fk_idfuncionario(getFk_idfuncionario());
    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato_servicio();
            BOser.insertar_servicio(ENTser, tbltabla);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTser.setC1idservicio(Integer.parseInt(txtid.getText()));
            cargar_dato_servicio();
            BOser.update_servicio(ENTser, tbltabla);
        }
    }

    private void seleccionar_tabla() {
        int idservicio = eveJtab.getInt_select_id(tbltabla);
        DAOser.cargar_servicio(conn, ENTser, idservicio);
        txtid.setText(String.valueOf(ENTser.getC1idservicio()));
        txtorden.setText(String.valueOf(ENTser.getC4orden()));
        jCactivar.setSelected(ENTser.getC5activo());
        txtcod_barra.setText(ENTser.getC6cod_barra());
        txtnombre.setText(ENTser.getC7nombre());
        txtadescripcion.setText(ENTser.getC8descripcion());
        txtprecio_venta.setText(String.valueOf((int)ENTser.getC9precio_venta()));
        txtprecio_compra.setText(String.valueOf((int)ENTser.getC10precio_compra()));
        txtporcentaje_comision.setText(String.valueOf(ENTser.getC11porcentaje_comision()));
        txtduracion_hora.setText(String.valueOf(ENTser.getC12duracion_hora()));
        txtduracion_minuto.setText(String.valueOf(ENTser.getC13duracion_minuto()));
        DAOsc.cargar_servicio_categoria(conn, ENTsc, ENTser.getC14fk_idservicio_categoria());
        setFk_idservicio_categoria(ENTser.getC14fk_idservicio_categoria());
        txtbuscar_servicio_categoria.setText(ENTsc.getC6nombre());
        DAOpu.cargar_funcionario(conn, ENTpu, ENTser.getC15fk_idfuncionario());
        setFk_idfuncionario(ENTser.getC15fk_idfuncionario());
        txtbuscar_funcionario.setText(ENTpu.getC6nombre());
        set_precio_ganancia_utilidad();
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser() {
        txtid.setText(null);
        txtorden.setText(eveconn.getInt_ultimo_ORDEN_mas_uno(conn,"servicio","orden"));
        jCactivar.setSelected(true);
        txtcod_barra.setText(null);
        txtnombre.setText(null);
        txtadescripcion.setText(jsons.getDefauld_descripcion());
        txtprecio_venta.setText(null);
        txtprecio_compra.setText(null);
        txtporcentaje_comision.setText(jsons.getDefauld_porcentaje());
        txtduracion_hora.setText(jsons.getDefauld_hora());
        txtduracion_minuto.setText(jsons.getDefauld_minuto());
        txtbuscar_servicio_categoria.setText(null);
        txtbuscar_funcionario.setText(null);
        txtser_ganancia.setText(null);
        jFutilidad.setValue(0);
        setFk_idservicio_categoria(0);
        setFk_idfuncionario(0);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void abrir_buscar(int tipo, String nombre, JTextField txtbuscar) {
        vbus.setTipo_tabla(tipo);
        vbus.setNombre_tabla(nombre);
        vbus.setPre_busqueda(txtbuscar.getText());
        JDiaBuscarDosColumnas frm = new JDiaBuscarDosColumnas(null, true);
        frm.setVisible(true);
    }

    private void boton_nuevo() {
        reestableser();
    }

   

    private void set_precio_ganancia_utilidad() {
        String ganancia = "0";
        if ((txtprecio_venta.getText().trim().length() > 0) && (txtprecio_compra.getText().trim().length() > 0)) {
            String Sprecio_venta = txtprecio_venta.getText();
            String Sprecio_compra = txtprecio_compra.getText();
            double Iprecio_venta = Double.parseDouble(Sprecio_venta);
            double Iprecio_compra = Double.parseDouble(Sprecio_compra);
            double Iganacia = Iprecio_venta - Iprecio_compra;
            ganancia = String.valueOf(Iganacia);
            double Iutilidad = Iganacia / Iprecio_venta * 100;
            txtser_ganancia.setText(ganancia);
            jFutilidad.setValue(Iutilidad);
        }
    }

    private boolean getboo_config_comision() {
        boolean comision = false;
        if (txtporcentaje_comision.getText().trim().length() > 0) {
            String por_comi = txtporcentaje_comision.getText();
            double Ipor_comi = Double.parseDouble(por_comi);
            int por_max=jsons.getMax_por_comision();
            int por_min=jsons.getMin_por_comision();
            if (Ipor_comi > por_max) {
                JOptionPane.showMessageDialog(this, "EL LIMITE MAXIMO DE COMISION ES:\n" +por_max+" %");
                txtporcentaje_comision.setText(String.valueOf(por_max));
                txtporcentaje_comision.grabFocus();
                comision = true;
            }
            if (Ipor_comi < por_min) {
                JOptionPane.showMessageDialog(this, "EL LIMITE MINIMO DE COMISION ES:\n" + por_min+" %");
                txtporcentaje_comision.setText(String.valueOf(por_min));
                txtporcentaje_comision.grabFocus();
                comision = true;
            }
        }
        return comision;
    }
     private boolean getboo_limite_min_max(JTextField txttexto,int nro_min,int nro_max) {
         boolean limite = false;
        if (txttexto.getText().trim().length() > 0) {
            String Snumero = txttexto.getText();
            int Inumero = Integer.parseInt(Snumero);
            if (Inumero > nro_max) {
                JOptionPane.showMessageDialog(this, "EL LIMITE MAXIMO ES:\n" + nro_max);
                txttexto.setText(String.valueOf(nro_max));
                limite = true;
                txttexto.grabFocus();
            }
            if (Inumero < nro_min) {
                JOptionPane.showMessageDialog(this, "EL LIMITE MINIMO ES:\n" + nro_min);
                txttexto.setText(String.valueOf(nro_min));
                limite = true;
                txttexto.grabFocus();
            }
        }
        return limite;
    }
    public FrmServicio() {
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
        panel_insertar = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtprecio_venta = new javax.swing.JTextField();
        txtprecio_compra = new javax.swing.JTextField();
        txtser_ganancia = new javax.swing.JTextField();
        jFutilidad = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtcod_barra = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtorden = new javax.swing.JTextField();
        jCactivar = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        txtbuscar_servicio_categoria = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtbuscar_funcionario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtadescripcion = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtporcentaje_comision = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtduracion_hora = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtduracion_minuto = new javax.swing.JTextField();
        btnconfig = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtbuscar_servicio = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_categoria = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtbuscar_codbarra = new javax.swing.JTextField();
        btnactualizar_todo = new javax.swing.JButton();

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIOS"));

        txtprecio_venta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtprecio_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprecio_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO-VENTA"));
        txtprecio_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyTyped(evt);
            }
        });

        txtprecio_compra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtprecio_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprecio_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO-COMPRA"));
        txtprecio_compra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecio_compraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecio_compraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecio_compraKeyTyped(evt);
            }
        });

        txtser_ganancia.setEditable(false);
        txtser_ganancia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtser_ganancia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtser_ganancia.setBorder(javax.swing.BorderFactory.createTitledBorder("GANANCIA"));

        jFutilidad.setEditable(false);
        jFutilidad.setBorder(javax.swing.BorderFactory.createTitledBorder("% UTILIDAD"));
        jFutilidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        jFutilidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFutilidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtser_ganancia, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                    .addComponent(txtprecio_venta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtprecio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFutilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprecio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprecio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtser_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFutilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("BASICO"));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("COD.BARRA:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("NOMBRE:");

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
        });

        txtcod_barra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcod_barra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("ORDEN:");

        txtorden.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtorden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtordenKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtordenKeyTyped(evt);
            }
        });

        jCactivar.setText("ACTIVAR");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("CATEGORIA:");

        txtbuscar_servicio_categoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtbuscar_servicio_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_servicio_categoriaKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("FUNCIO:");

        txtbuscar_funcionario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtbuscar_funcionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_funcionarioKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtadescripcion.setColumns(20);
        txtadescripcion.setRows(5);
        txtadescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION:"));
        jScrollPane2.setViewportView(txtadescripcion);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtorden, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCactivar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 136, Short.MAX_VALUE))
                            .addComponent(txtnombre)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtbuscar_servicio_categoria)
                            .addComponent(txtbuscar_funcionario))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jButton2)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(txtorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCactivar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtbuscar_servicio_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtbuscar_funcionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("COMISION"));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("COMISION %:");

        txtporcentaje_comision.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtporcentaje_comision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtporcentaje_comisionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtporcentaje_comisionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtporcentaje_comisionKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("HORA:");

        txtduracion_hora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtduracion_hora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtduracion_horaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtduracion_horaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtduracion_horaKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("MINUTO:");

        txtduracion_minuto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtduracion_minuto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtduracion_minutoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtduracion_minutoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtduracion_minutoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(25, 25, 25)
                        .addComponent(txtporcentaje_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtduracion_hora, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtduracion_minuto, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtporcentaje_comision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtduracion_hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtduracion_minuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        btnconfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/config.png"))); // NOI18N
        btnconfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar))
                    .addComponent(btnconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnnuevo)
                        .addComponent(btnguardar)
                        .addComponent(btneditar))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        jTabbedPane1.addTab("DATO SERVICIO", panel_insertar);

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

        jLabel3.setText("SERVICIO:");

        txtbuscar_servicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_servicioKeyReleased(evt);
            }
        });

        jLabel10.setText("CATEGORIA:");

        txtbuscar_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_categoriaKeyReleased(evt);
            }
        });

        jLabel11.setText("CODBARRA:");

        txtbuscar_codbarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_codbarraKeyReleased(evt);
            }
        });

        btnactualizar_todo.setText("TODO");
        btnactualizar_todo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_todoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnactualizar_todo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtbuscar_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnactualizar_todo))
                    .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtbuscar_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("FILTRO SERVICIO", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOser.ancho_tabla_servicio(tbltabla);
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
        evejtf.saltar_campo_enter(evt, txtnombre, txtbuscar_servicio_categoria);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtbuscar_servicioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_servicioKeyReleased
        // TODO add your handling code here:
        DAOser.buscar_tabla_servicio(conn, tbltabla, txtbuscar_servicio, "s.nombre");
    }//GEN-LAST:event_txtbuscar_servicioKeyReleased

    private void txtordenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtordenKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txtorden, txtcod_barra);
    }//GEN-LAST:event_txtordenKeyPressed

    private void txtcod_barraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txtcod_barra, txtnombre);
    }//GEN-LAST:event_txtcod_barraKeyPressed

    private void txtbuscar_servicio_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_servicio_categoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            abrir_buscar(3, "CATEGORIA", txtbuscar_servicio_categoria);
        }
    }//GEN-LAST:event_txtbuscar_servicio_categoriaKeyPressed

    private void txtbuscar_funcionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_funcionarioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            abrir_buscar(4, "FUNCIONARIO", txtbuscar_funcionario);
        }
    }//GEN-LAST:event_txtbuscar_funcionarioKeyPressed

    private void txtporcentaje_comisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtporcentaje_comisionKeyPressed
        // TODO add your handling code here:
//        evejte.saltar_campo_enter(evt, txtporcentaje_comision, txt18precio_por_uso);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getboo_config_comision();
            evejte.saltar_campo_directo(txtporcentaje_comision, txtduracion_hora);
        }
    }//GEN-LAST:event_txtporcentaje_comisionKeyPressed

    private void txtordenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtordenKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txtordenKeyTyped

    private void txtprecio_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txtprecio_ventaKeyTyped

    private void txtprecio_compraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_compraKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txtprecio_compraKeyTyped

    private void txtporcentaje_comisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtporcentaje_comisionKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txtporcentaje_comisionKeyTyped

    private void txtprecio_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txtprecio_venta, txtprecio_compra);
    }//GEN-LAST:event_txtprecio_ventaKeyPressed

    private void txtprecio_compraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_compraKeyPressed
        // TODO add your handling code here:
         evejte.saltar_campo_enter(evt, txtprecio_compra, txtporcentaje_comision);
    }//GEN-LAST:event_txtprecio_compraKeyPressed

    private void txtprecio_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyReleased
        // TODO add your handling code here:
        set_precio_ganancia_utilidad();
    }//GEN-LAST:event_txtprecio_ventaKeyReleased

    private void txtprecio_compraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_compraKeyReleased
        // TODO add your handling code here:
        set_precio_ganancia_utilidad();
    }//GEN-LAST:event_txtprecio_compraKeyReleased

    private void txtporcentaje_comisionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtporcentaje_comisionKeyReleased
        // TODO add your handling code here:
//        config_comision();
    }//GEN-LAST:event_txtporcentaje_comisionKeyReleased

    private void btnconfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfigActionPerformed
        // TODO add your handling code here:
        jsons.abrir_config_servicio();
    }//GEN-LAST:event_btnconfigActionPerformed

    private void txtbuscar_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_categoriaKeyReleased
        // TODO add your handling code here:
        DAOser.buscar_tabla_servicio(conn, tbltabla, txtbuscar_categoria, "sc.nombre");
    }//GEN-LAST:event_txtbuscar_categoriaKeyReleased

    private void txtbuscar_codbarraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_codbarraKeyReleased
        // TODO add your handling code here:
        DAOser.buscar_tabla_servicio(conn, tbltabla, txtbuscar_codbarra, "s.cod_barra");
    }//GEN-LAST:event_txtbuscar_codbarraKeyReleased

    private void btnactualizar_todoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_todoActionPerformed
        // TODO add your handling code here:
        DAOser.actualizar_tabla_servicio(conn, tbltabla);
    }//GEN-LAST:event_btnactualizar_todoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        abrir_buscar(3, "CATEGORIA", txtbuscar_servicio_categoria);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        abrir_buscar(4, "FUNCIONARIO", txtbuscar_funcionario);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtduracion_horaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtduracion_horaKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txtduracion_hora, txtduracion_minuto);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getboo_limite_min_max(txtduracion_hora, 0, 8);
        }
    }//GEN-LAST:event_txtduracion_horaKeyPressed

    private void txtduracion_horaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtduracion_horaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtduracion_horaKeyReleased

    private void txtduracion_horaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtduracion_horaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtduracion_horaKeyTyped

    private void txtduracion_minutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtduracion_minutoKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txtduracion_minuto, txtorden);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getboo_limite_min_max(txtduracion_minuto, 0, 59);
        }
    }//GEN-LAST:event_txtduracion_minutoKeyPressed

    private void txtduracion_minutoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtduracion_minutoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtduracion_minutoKeyReleased

    private void txtduracion_minutoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtduracion_minutoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtduracion_minutoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_todo;
    private javax.swing.JButton btnconfig;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCactivar;
    private javax.swing.JFormattedTextField jFutilidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla;
    private javax.swing.JTextArea txtadescripcion;
    private javax.swing.JTextField txtbuscar_categoria;
    private javax.swing.JTextField txtbuscar_codbarra;
    public static javax.swing.JTextField txtbuscar_funcionario;
    private javax.swing.JTextField txtbuscar_servicio;
    public static javax.swing.JTextField txtbuscar_servicio_categoria;
    private javax.swing.JTextField txtcod_barra;
    private javax.swing.JTextField txtduracion_hora;
    private javax.swing.JTextField txtduracion_minuto;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtorden;
    private javax.swing.JTextField txtporcentaje_comision;
    private javax.swing.JTextField txtprecio_compra;
    public static javax.swing.JTextField txtprecio_venta;
    private javax.swing.JTextField txtser_ganancia;
    // End of variables declaration//GEN-END:variables
}
