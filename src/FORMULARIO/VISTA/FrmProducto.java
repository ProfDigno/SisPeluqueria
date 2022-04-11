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
public class FrmProducto extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenJTextField evejte = new EvenJTextField();
    private EvenConexion eveconn=new EvenConexion();
    private producto ENTpro = new producto();
    private DAO_producto DAOpro = new DAO_producto();
    private BO_producto BOpro = new BO_producto();
    private EvenJTextField evejtf = new EvenJTextField();
    private producto_categoria ENTpc = new producto_categoria();
    private DAO_producto_categoria DAOpc = new DAO_producto_categoria();
    private producto_unidad ENTpu = new producto_unidad();
    private DAO_producto_unidad DAOpu = new DAO_producto_unidad();
    private json_producto jsonp = new json_producto();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_palete clacolor = new cla_color_palete();
    private String nombre_formulario = "PRODUCTO";
    String creado_por = "DIGNO";
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private static int fk_idproducto_categoria;
    private static int fk_idproducto_unidad;

    public static int getFk_idproducto_categoria() {
        return fk_idproducto_categoria;
    }

    public static void setFk_idproducto_categoria(int fk_idproducto_categoria) {
        FrmProducto.fk_idproducto_categoria = fk_idproducto_categoria;
    }

    public static int getFk_idproducto_unidad() {
        return fk_idproducto_unidad;
    }

    public static void setFk_idproducto_unidad(int fk_idproducto_unidad) {
        FrmProducto.fk_idproducto_unidad = fk_idproducto_unidad;
    }

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        jsonp.cargar_jsom_producto();
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        DAOpro.actualizar_tabla_producto(conn, tbltabla);
        color_formulario();
    }

    private void color_formulario() {
        panel_tabla.setBackground(clacolor.getColor_tabla());
        panel_insertar.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txt6cod_barra, "DEBE CARGAR UN COD BARRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt7nombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt20categoria, "DEBE CARGAR UNA CATEGORIA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt21unidad, "DEBE CARGAR UN UNIDAD")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt8precio_venta, "DEBE CARGAR UN PRECIO VENTA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt9precio_compra, "DEBE CARGAR UN PRECIO COMPRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt10stock_actual, "DEBE CARGAR UN STOCK ACTUAL")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt11stock_minimo, "DEBE CARGAR UN STOCK MINIMO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt12stock_maximo, "DEBE CARGAR UN STOCK MAXIMO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt17cantidad_uso, "DEBE CARGAR UN CANTIDAD USO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt19porcentaje_comision, "DEBE CARGAR UN PORCENTAJE COMISION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt18precio_por_uso, "DEBE CARGAR UN PRECIO POR USO")) {
            return false;
        }
        if (getboo_config_comision()) {
            return false;
        }
        return true;
    }

    void cargar_dato_producto() {
        ENTpro.setC3creado_por(creado_por);
        ENTpro.setC4orden(Integer.parseInt(txt4orden.getText()));
        ENTpro.setC5activo(jC5activar.isSelected());
        ENTpro.setC6cod_barra(txt6cod_barra.getText());
        ENTpro.setC7nombre(txt7nombre.getText());
        ENTpro.setC8precio_venta(Double.parseDouble(txt8precio_venta.getText()));
        ENTpro.setC9precio_compra(Double.parseDouble(txt9precio_compra.getText()));
        ENTpro.setC10stock_actual(Double.parseDouble(txt10stock_actual.getText()));
        ENTpro.setC11stock_minimo(Double.parseDouble(txt11stock_minimo.getText()));
        ENTpro.setC12stock_maximo(Double.parseDouble(txt12stock_maximo.getText()));
        ENTpro.setC13control_stock(jC13control_stock.isSelected());
        ENTpro.setC14ult_venta("now()");
        ENTpro.setC15ult_compra("now()");
        ENTpro.setC16insumo(jC16insumo.isSelected());
        ENTpro.setC17cantidad_uso(Double.parseDouble(txt17cantidad_uso.getText()));
        ENTpro.setC18precio_por_uso(Double.parseDouble(txt18precio_por_uso.getText()));
        ENTpro.setC19porcentaje_comision(Double.parseDouble(txt19porcentaje_comision.getText()));
        ENTpro.setC20fk_idproducto_categoria(getFk_idproducto_categoria());
        ENTpro.setC21fk_idproducto_unidad(getFk_idproducto_unidad());
    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato_producto();
            BOpro.insertar_producto(ENTpro, tbltabla);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTpro.setC1idproducto(Integer.parseInt(txtid.getText()));
            cargar_dato_producto();
            BOpro.update_producto(ENTpro, tbltabla);
        }
    }

    private void seleccionar_tabla() {
        int idproducto = eveJtab.getInt_select_id(tbltabla);
        DAOpro.cargar_producto(conn, ENTpro, idproducto);
        txtid.setText(String.valueOf(ENTpro.getC1idproducto()));
        txt4orden.setText(String.valueOf(ENTpro.getC4orden()));
        jC5activar.setSelected(ENTpro.getC5activo());
        txt6cod_barra.setText(ENTpro.getC6cod_barra());
        txt7nombre.setText(ENTpro.getC7nombre());
        txt8precio_venta.setText(String.valueOf((int) ENTpro.getC8precio_venta()));
        txt9precio_compra.setText(String.valueOf((int) ENTpro.getC9precio_compra()));
        txt10stock_actual.setText(String.valueOf((int) ENTpro.getC10stock_actual()));
        txt11stock_minimo.setText(String.valueOf((int) ENTpro.getC11stock_minimo()));
        txt12stock_maximo.setText(String.valueOf((int) ENTpro.getC12stock_maximo()));
        jC13control_stock.setSelected(ENTpro.getC13control_stock());
        jC16insumo.setSelected(ENTpro.getC16insumo());
        txt17cantidad_uso.setText(String.valueOf((int) ENTpro.getC17cantidad_uso()));
        txt18precio_por_uso.setText(String.valueOf((int) ENTpro.getC18precio_por_uso()));
        txt19porcentaje_comision.setText((String.valueOf(ENTpro.getC19porcentaje_comision())));
        DAOpc.cargar_producto_categoria(conn, ENTpc, ENTpro.getC20fk_idproducto_categoria());
        txt20categoria.setText(ENTpc.getC6nombre());
        setFk_idproducto_categoria(ENTpro.getC20fk_idproducto_categoria());
        DAOpu.cargar_producto_unidad(conn, ENTpu, ENTpro.getC21fk_idproducto_unidad());
        txt21unidad.setText(ENTpu.getC5nombre());
        setFk_idproducto_unidad(ENTpro.getC21fk_idproducto_unidad());
        set_precio_ganancia_utilidad();
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser() {
        txtid.setText(null);
        txt4orden.setText(eveconn.getInt_ultimo_ORDEN_mas_uno(conn,"producto","orden"));
        jC5activar.setSelected(true);
        txt6cod_barra.setText(null);
        txt7nombre.setText(null);
        txt8precio_venta.setText(null);
        txt9precio_compra.setText(null);
        txt10stock_actual.setText(null);
        txt11stock_minimo.setText(jsonp.getDefauld_Stock_minimo());
        txt12stock_maximo.setText(jsonp.getDefauld_Stock_maximo());
        jC13control_stock.setSelected(true);
        jC16insumo.setSelected(false);
        txt17cantidad_uso.setText(null);
        txt18precio_por_uso.setText(null);
        txt19porcentaje_comision.setText(jsonp.getDefauld_Porcentaje_comision());
        txt20categoria.setText(null);
        txt21unidad.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txt7nombre.grabFocus();
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

    private void set_precio_por_uso() {
        String precio_uso = "0";
        if ((txt8precio_venta.getText().trim().length() > 0) && (txt17cantidad_uso.getText().trim().length() > 0)) {
            String Sprecio_venta = txt8precio_venta.getText();
            String Scant_uso = txt17cantidad_uso.getText();
            int Iprecio_venta = Integer.parseInt(Sprecio_venta);
            int Icant_uso = Integer.parseInt(Scant_uso);
            int Iprecio_por_uso = Iprecio_venta / Icant_uso;
            precio_uso = String.valueOf(Iprecio_por_uso);
            txt18precio_por_uso.setText(precio_uso);
        }
    }

    private void set_precio_ganancia_utilidad() {
        String ganancia = "0";
        if ((txt8precio_venta.getText().trim().length() > 0) && (txt9precio_compra.getText().trim().length() > 0)) {
            String Sprecio_venta = txt8precio_venta.getText();
            String Sprecio_compra = txt9precio_compra.getText();
            double Iprecio_venta = Double.parseDouble(Sprecio_venta);
            double Iprecio_compra = Double.parseDouble(Sprecio_compra);
            double Iganacia = Iprecio_venta - Iprecio_compra;
            ganancia = String.valueOf(Iganacia);
            double Iutilidad = Iganacia / Iprecio_venta * 100;
            txtpro_ganancia.setText(ganancia);
            jFutilidad.setValue(Iutilidad);
        }
    }

    private boolean getboo_config_comision() {
        boolean comision = false;
        if (txt19porcentaje_comision.getText().trim().length() > 0) {
            String por_comi = txt19porcentaje_comision.getText();
            double Ipor_comi = Double.parseDouble(por_comi);
            if (Ipor_comi > jsonp.getMax_por_comision()) {
                JOptionPane.showMessageDialog(this, "EL LIMITE MAXIMO DE COMISION ES:" + jsonp.getMax_por_comision());
                txt19porcentaje_comision.setText(String.valueOf(jsonp.getMax_por_comision()));
                txt19porcentaje_comision.grabFocus();
                comision = true;
            }
            if (Ipor_comi < jsonp.getMin_por_comision()) {
                JOptionPane.showMessageDialog(this, "EL LIMITE MINIMO DE COMISION ES:" + jsonp.getMin_por_comision());
                txt19porcentaje_comision.setText(String.valueOf(jsonp.getMin_por_comision()));
                txt19porcentaje_comision.grabFocus();
                comision = true;
            }
        }
        return comision;
    }
    public FrmProducto() {
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
        btndeletar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt8precio_venta = new javax.swing.JTextField();
        txt9precio_compra = new javax.swing.JTextField();
        txtpro_ganancia = new javax.swing.JTextField();
        jFutilidad = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        txt10stock_actual = new javax.swing.JTextField();
        txt11stock_minimo = new javax.swing.JTextField();
        txt12stock_maximo = new javax.swing.JTextField();
        jC13control_stock = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt7nombre = new javax.swing.JTextField();
        txt6cod_barra = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt4orden = new javax.swing.JTextField();
        jC5activar = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        txt20categoria = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt21unidad = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnnuevo_categoria = new javax.swing.JButton();
        btnnuevo_unidad = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt17cantidad_uso = new javax.swing.JTextField();
        jC16insumo = new javax.swing.JCheckBox();
        txt18precio_por_uso = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt19porcentaje_comision = new javax.swing.JTextField();
        btnconfig = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtbuscar_producto = new javax.swing.JTextField();
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

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIOS"));

        txt8precio_venta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt8precio_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt8precio_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO-VENTA"));
        txt8precio_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt8precio_ventaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt8precio_ventaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt8precio_ventaKeyTyped(evt);
            }
        });

        txt9precio_compra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt9precio_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt9precio_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO-COMPRA"));
        txt9precio_compra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt9precio_compraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt9precio_compraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt9precio_compraKeyTyped(evt);
            }
        });

        txtpro_ganancia.setEditable(false);
        txtpro_ganancia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtpro_ganancia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtpro_ganancia.setBorder(javax.swing.BorderFactory.createTitledBorder("GANANCIA"));

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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtpro_ganancia)
                    .addComponent(txt8precio_venta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt9precio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFutilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt8precio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt9precio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtpro_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFutilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("STOCK"));

        txt10stock_actual.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt10stock_actual.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt10stock_actual.setBorder(javax.swing.BorderFactory.createTitledBorder("ACTUAL"));
        txt10stock_actual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt10stock_actualKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt10stock_actualKeyTyped(evt);
            }
        });

        txt11stock_minimo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt11stock_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt11stock_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("MINIMO"));
        txt11stock_minimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt11stock_minimoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt11stock_minimoKeyTyped(evt);
            }
        });

        txt12stock_maximo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt12stock_maximo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt12stock_maximo.setBorder(javax.swing.BorderFactory.createTitledBorder("MAXIMO"));
        txt12stock_maximo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt12stock_maximoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt12stock_maximoKeyTyped(evt);
            }
        });

        jC13control_stock.setText("CONTROLAR STOCK");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt10stock_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt11stock_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt12stock_maximo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jC13control_stock))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt10stock_actual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt11stock_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt12stock_maximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jC13control_stock)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("BASICO"));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("COD.BARRA:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("NOMBRE:");

        txt7nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt7nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt7nombreKeyPressed(evt);
            }
        });

        txt6cod_barra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt6cod_barra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt6cod_barraKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("ORDEN:");

        txt4orden.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt4orden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt4ordenKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt4ordenKeyTyped(evt);
            }
        });

        jC5activar.setText("ACTIVAR");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("CATEGORIA:");

        txt20categoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt20categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt20categoriaKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("UNIDAD:");

        txt21unidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt21unidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt21unidadKeyPressed(evt);
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

        btnnuevo_categoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_categoriaActionPerformed(evt);
            }
        });

        btnnuevo_unidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_unidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt21unidad)
                            .addComponent(txt20categoria))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnnuevo_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnnuevo_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(txt7nombre)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txt6cod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt4orden, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jC5activar)
                .addGap(91, 91, 91))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(txt4orden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jC5activar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt6cod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt7nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txt20categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(txt21unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("POR USO"));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("CANTIDAD USO:");

        txt17cantidad_uso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt17cantidad_uso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt17cantidad_usoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt17cantidad_usoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt17cantidad_usoKeyTyped(evt);
            }
        });

        jC16insumo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jC16insumo.setText("INSUMO");

        txt18precio_por_uso.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt18precio_por_uso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt18precio_por_uso.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO-VENTA->POR-USO"));
        txt18precio_por_uso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt18precio_por_usoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt18precio_por_usoKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("COMISION %:");

        txt19porcentaje_comision.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt19porcentaje_comision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt19porcentaje_comisionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt19porcentaje_comisionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt19porcentaje_comisionKeyTyped(evt);
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
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt19porcentaje_comision, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(txt17cantidad_uso)))
                    .addComponent(jC16insumo)
                    .addComponent(txt18precio_por_uso, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jC16insumo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt17cantidad_uso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt19porcentaje_comision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt18precio_por_uso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                            .addComponent(btnconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addComponent(btndeletar))
                .addGap(52, 52, 52))
        );

        jTabbedPane1.addTab("DATO PRODUCTO", panel_insertar);

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

        jLabel3.setText("PRODUCTO:");

        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
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
                .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
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
                        .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        jTabbedPane1.addTab("FILTRO PRODUCTO", jPanel1);

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
        DAOpro.ancho_tabla_producto(tbltabla);
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

    private void txt7nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt7nombreKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txt7nombre, txt20categoria);
    }//GEN-LAST:event_txt7nombreKeyPressed

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
        DAOpro.buscar_tabla_producto(conn, tbltabla, txtbuscar_producto, "p.nombre");
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void txt4ordenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt4ordenKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt4orden, txt6cod_barra);
    }//GEN-LAST:event_txt4ordenKeyPressed

    private void txt6cod_barraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt6cod_barraKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt6cod_barra, txt7nombre);
    }//GEN-LAST:event_txt6cod_barraKeyPressed

    private void txt20categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt20categoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            abrir_buscar(1, "CATEGORIA", txt20categoria);
        }
    }//GEN-LAST:event_txt20categoriaKeyPressed

    private void txt21unidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt21unidadKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            abrir_buscar(2, "UNIDAD", txt21unidad);
        }
    }//GEN-LAST:event_txt21unidadKeyPressed

    private void txt17cantidad_usoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt17cantidad_usoKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt17cantidad_uso, txt19porcentaje_comision);
    }//GEN-LAST:event_txt17cantidad_usoKeyPressed

    private void txt19porcentaje_comisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt19porcentaje_comisionKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt19porcentaje_comision, txt18precio_por_uso);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            getboo_config_comision();
        }
    }//GEN-LAST:event_txt19porcentaje_comisionKeyPressed

    private void txt4ordenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt4ordenKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txt4ordenKeyTyped

    private void txt8precio_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt8precio_ventaKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txt8precio_ventaKeyTyped

    private void txt9precio_compraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt9precio_compraKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txt9precio_compraKeyTyped

    private void txt10stock_actualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt10stock_actualKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txt10stock_actualKeyTyped

    private void txt11stock_minimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt11stock_minimoKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txt11stock_minimoKeyTyped

    private void txt12stock_maximoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt12stock_maximoKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txt12stock_maximoKeyTyped

    private void txt17cantidad_usoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt17cantidad_usoKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txt17cantidad_usoKeyTyped

    private void txt19porcentaje_comisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt19porcentaje_comisionKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txt19porcentaje_comisionKeyTyped

    private void txt18precio_por_usoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt18precio_por_usoKeyTyped
        // TODO add your handling code here:
        evejte.soloNumero(evt);
    }//GEN-LAST:event_txt18precio_por_usoKeyTyped

    private void txt8precio_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt8precio_ventaKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt8precio_venta, txt9precio_compra);
    }//GEN-LAST:event_txt8precio_ventaKeyPressed

    private void txt9precio_compraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt9precio_compraKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt9precio_compra, txt10stock_actual);
    }//GEN-LAST:event_txt9precio_compraKeyPressed

    private void txt10stock_actualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt10stock_actualKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt10stock_actual, txt11stock_minimo);
    }//GEN-LAST:event_txt10stock_actualKeyPressed

    private void txt11stock_minimoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt11stock_minimoKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt11stock_minimo, txt12stock_maximo);
    }//GEN-LAST:event_txt11stock_minimoKeyPressed

    private void txt12stock_maximoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt12stock_maximoKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt12stock_maximo, txt17cantidad_uso);
    }//GEN-LAST:event_txt12stock_maximoKeyPressed

    private void txt18precio_por_usoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt18precio_por_usoKeyPressed
        // TODO add your handling code here:
        evejte.saltar_campo_enter(evt, txt18precio_por_uso, txt4orden);
    }//GEN-LAST:event_txt18precio_por_usoKeyPressed

    private void txt8precio_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt8precio_ventaKeyReleased
        // TODO add your handling code here:
        set_precio_por_uso();
        set_precio_ganancia_utilidad();
    }//GEN-LAST:event_txt8precio_ventaKeyReleased

    private void txt17cantidad_usoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt17cantidad_usoKeyReleased
        // TODO add your handling code here:
        set_precio_por_uso();
    }//GEN-LAST:event_txt17cantidad_usoKeyReleased

    private void txt9precio_compraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt9precio_compraKeyReleased
        // TODO add your handling code here:
        set_precio_ganancia_utilidad();
    }//GEN-LAST:event_txt9precio_compraKeyReleased

    private void txt19porcentaje_comisionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt19porcentaje_comisionKeyReleased
        // TODO add your handling code here:
//        config_comision();
    }//GEN-LAST:event_txt19porcentaje_comisionKeyReleased

    private void btnconfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfigActionPerformed
        // TODO add your handling code here:
        jsonp.abrir_config_producto();
    }//GEN-LAST:event_btnconfigActionPerformed

    private void txtbuscar_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_categoriaKeyReleased
        // TODO add your handling code here:
        DAOpro.buscar_tabla_producto(conn, tbltabla, txtbuscar_categoria, "pc.nombre");
    }//GEN-LAST:event_txtbuscar_categoriaKeyReleased

    private void txtbuscar_codbarraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_codbarraKeyReleased
        // TODO add your handling code here:
        DAOpro.buscar_tabla_producto(conn, tbltabla, txtbuscar_codbarra, "p.cod_barra");
    }//GEN-LAST:event_txtbuscar_codbarraKeyReleased

    private void btnactualizar_todoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_todoActionPerformed
        // TODO add your handling code here:
        DAOpro.actualizar_tabla_producto(conn, tbltabla);
    }//GEN-LAST:event_btnactualizar_todoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        abrir_buscar(1, "CATEGORIA", txt20categoria);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        abrir_buscar(2, "UNIDAD", txt21unidad);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnnuevo_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_categoriaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_categoria());
    }//GEN-LAST:event_btnnuevo_categoriaActionPerformed

    private void btnnuevo_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_unidadActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_unidad());
    }//GEN-LAST:event_btnnuevo_unidadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_todo;
    private javax.swing.JButton btnconfig;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_categoria;
    private javax.swing.JButton btnnuevo_unidad;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jC13control_stock;
    private javax.swing.JCheckBox jC16insumo;
    private javax.swing.JCheckBox jC5activar;
    private javax.swing.JFormattedTextField jFutilidad;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla;
    private javax.swing.JTextField txt10stock_actual;
    private javax.swing.JTextField txt11stock_minimo;
    private javax.swing.JTextField txt12stock_maximo;
    private javax.swing.JTextField txt17cantidad_uso;
    private javax.swing.JTextField txt18precio_por_uso;
    private javax.swing.JTextField txt19porcentaje_comision;
    public static javax.swing.JTextField txt20categoria;
    public static javax.swing.JTextField txt21unidad;
    private javax.swing.JTextField txt4orden;
    private javax.swing.JTextField txt6cod_barra;
    private javax.swing.JTextField txt7nombre;
    public static javax.swing.JTextField txt8precio_venta;
    private javax.swing.JTextField txt9precio_compra;
    private javax.swing.JTextField txtbuscar_categoria;
    private javax.swing.JTextField txtbuscar_codbarra;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtpro_ganancia;
    // End of variables declaration//GEN-END:variables
}
