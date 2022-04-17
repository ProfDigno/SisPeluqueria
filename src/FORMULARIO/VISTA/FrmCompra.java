/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import BUSCAR.ClaVarBuscar;
import BUSCAR.JDiaBuscarPersona;
import CONFIGURACION.Global_datos;
import Evento.Color.cla_color_palete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.BO_compra;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_compra;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.compra;
import FORMULARIO.ENTIDAD.producto;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmCompra extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenConexion eveconn = new EvenConexion();
    private EvenFecha evefec = new EvenFecha();
    ClaVarBuscar vbus = new ClaVarBuscar();
    private Global_datos gda = new Global_datos();
    private EvenJTextField evejtf = new EvenJTextField();
    private DefaultTableModel model_itemf = new DefaultTableModel();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private Connection conn = ConnPostgres.getConnPosgres();
    private cla_color_palete clacolor = new cla_color_palete();
    private producto ENTpro = new producto();
    private DAO_producto DAOpro = new DAO_producto();
    private compra ENTcom = new compra();
    private DAO_compra DAOcom = new DAO_compra();
    private BO_compra BOcom = new BO_compra();
    private caja_detalle ENTcd = new caja_detalle();
    private DAO_caja_detalle DAOcd = new DAO_caja_detalle();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    private String nombre_formulario = "COMPRA";
    private static int fk_idproveedor;
    private double monto_compra;
    private String monto_letra;
    private int idcompra;
    private int fk_idproducto;

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        evefec.cargar_combobox_intervalo_fecha(cmbfiltro_fecha);
        crear_item_compra();
        reestableser_compra();
    }

    private void abrir_buscar(int tipo, String nombre, JTextField txtbuscar) {
        vbus.setTipo_tabla(tipo);
        vbus.setNombre_tabla(nombre);
        vbus.setPre_busqueda(txtbuscar.getText());
        JDiaBuscarPersona frm = new JDiaBuscarPersona(null, true);
        frm.setVisible(true);
    }

    public static int getFk_idproveedor() {
        return fk_idproveedor;
    }

    public static void setFk_idproveedor(int fk_idproveedor) {
        FrmCompra.fk_idproveedor = fk_idproveedor;
    }

    private void buscar_producto() {
        if (txtbuscar_producto.getText().trim().length() > 0) {
            String buscar = txtbuscar_producto.getText();
            String filtro = " and s.nombre ilike'%" + buscar + "%' ";
            DAOpro.actualizar_tabla_producto_compra(conn, tblproducto, filtro);
        }
    }

    private void seleccionar_producto() {
        if (tblproducto.getSelectedRow() >= 0) {
            fk_idproducto = eveJtab.getInt_select_id(tblproducto);
            DAOpro.cargar_producto(conn, ENTpro, fk_idproducto);
            txtcodbarra.setText(ENTpro.getC6cod_barra());
            String nombre = eveJtab.getString_select(tblproducto, 2);
            txtbuscar_producto.setText(nombre);
            txtpro_preciocompra.setText(String.valueOf((int) ENTpro.getC9precio_compra()));
            txtpro_cantidad.setText("1");
            calcular_subtotal();
        }
    }

    private void calcular_subtotal() {
        int len_precio = txtpro_preciocompra.getText().trim().length();
        int len_cant = txtpro_cantidad.getText().trim().length();
        if (len_precio > 0 && len_cant > 0) {
            String Scantidad = txtpro_cantidad.getText();
            String Sprecio = txtpro_preciocompra.getText();
            int cantidad = Integer.parseInt(Scantidad);
            int precio = Integer.parseInt(Sprecio);
            int subtotal = cantidad * precio;
            String Ssubtotal = String.valueOf(subtotal);
            txtpro_subtotal.setText(Ssubtotal);
        }
    }

    private void crear_item_compra() {
        String dato[] = {"idp", "descripcion", "precio", "cant", "total"};
        eveJtab.crear_tabla_datos(tblitem_compra, model_itemf, dato);
    }

    private void ancho_item_compra() {
        int Ancho[] = {10, 60, 10, 10, 10};
        eveJtab.setAnchoColumnaJtable(tblitem_compra, Ancho);
    }

    private boolean validar_item_compra() {
        if (tblproducto.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "SELECCIONAR UN PRODUCTO");
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtpro_preciocompra, "CARGAR UN PRECIO COMPRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtpro_cantidad, "CARGAR UNA CANTIDAD")) {
            return false;
        }
        return true;
    }

    private void cargar_item_compra() {
        if (validar_item_compra()) {
            String Sfk_idproducto = String.valueOf(fk_idproducto);
            String descripcion = txtbuscar_producto.getText();
            String precio = txtpro_preciocompra.getText();
            String cant = txtpro_cantidad.getText();
            String total = txtpro_subtotal.getText();
            String dato[] = {Sfk_idproducto, descripcion, precio, cant, total};
            eveJtab.cargar_tabla_datos(tblitem_compra, model_itemf, dato);
            sumar_item_compra();
            limpiar_item_compra();
        }
    }

    private void limpiar_item_compra() {
        txtpro_preciocompra.setText(null);
        txtpro_cantidad.setText(null);
        txtpro_subtotal.setText(null);
        txtcodbarra.setText(null);
        txtbuscar_producto.setText(null);
        txtpro_cantidad.setBackground(Color.white);
        txtbuscar_producto.setBackground(Color.yellow);
        txtbuscar_producto.grabFocus();
    }

    private void sumar_item_compra() {
        monto_compra = eveJtab.getDouble_sumar_tabla(tblitem_compra, 4);
        jFmonto_compra.setValue(monto_compra);
        monto_letra = nroletra.Convertir(String.valueOf(monto_compra), true);
        txtmonto_letra.setText(monto_letra);
    }

    private void boton_eliminar_item() {
        if (!eveJtab.getBoolean_validar_select(tblitem_compra)) {
            if (eveJtab.getBoolean_Eliminar_Fila(tblitem_compra, model_itemf)) {
                sumar_item_compra();
            }
        }
    }

    private void limpiar_proveedor() {
        txtprov_nombre.setText(null);
        txtprov_ruc.setText(null);
        txtprov_direccion.setText(null);
        txtprov_telefono.setText(null);
    }

    private void reestableser_compra() {
        idcompra = (eveconn.getInt_ultimoID_mas_uno(conn, ENTcom.getTb_compra(), ENTcom.getId_idcompra()));
        txtidcompra.setText(String.valueOf(idcompra));
        txtfecha.setText(evefec.getString_formato_fecha());
        eveJtab.limpiar_tabla_datos(model_itemf);
        txtobservacion.setText("NINGUNA");
        txtnumero_nota.setText(null);
        DAOpro.actualizar_tabla_producto_compra(conn, tblproducto, "");
        DAOcom.actualizar_tabla_compra(conn, tblcompra, "");
        setFk_idproveedor(0);
        limpiar_proveedor();
        limpiar_item_compra();
        sumar_item_compra();
    }

    private void cargar_dato_compra() {
        ENTcom.setC3creado_por(gda.getCreado_por());
        ENTcom.setC4numero_nota(Integer.parseInt(txtnumero_nota.getText()));
        ENTcom.setC5fecha_compra(txtfecha.getText());
        ENTcom.setC6monto_compra(monto_compra);
        ENTcom.setC7monto_letra(monto_letra);
        ENTcom.setC8estado(gda.getEstado_emitido());
        ENTcom.setC9observacion(txtobservacion.getText());
        ENTcom.setC10fk_idproveedor(getFk_idproveedor());
        ENTcom.setC11fk_idusuario(gda.getFk_idusuario());
    }

    private void cargar_dato_caja_detalle() {
        DAOcd.vaciar_caja_detalle(ENTcd);
        ENTcd.setC4descripcion(gda.getTbl_compra() + "=>" + txtprov_nombre.getText());
        ENTcd.setC5tabla_origen(gda.getTbl_compra());
        ENTcd.setC8cierre(gda.getCaja_abierto());
        ENTcd.setC6estado(gda.getEstado_emitido());
        ENTcd.setC13eg_monto_compra(monto_compra);
        ENTcd.setC17fk_idcompra(idcompra);
    }

    private void boton_cancelar() {
        reestableser_compra();
    }

    private boolean validar_guardar_compra() {
        if (getFk_idproveedor() == 0) {
            JOptionPane.showMessageDialog(null, "SE DEBE CARGAR UN PROVEEDOR");
            txtprov_nombre.grabFocus();
            return false;
        }
        if (tblitem_compra.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "SE DEBE CARGAR AL MENOS UN ITEM PARA GUARDAR");
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtnumero_nota, "SE DEBE CARGAR EL NUMERO DE NOTA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtobservacion, "SE DEBE CARGAR UNA OBSERVACION")) {
            return false;
        }
        return true;
    }

    private void boton_confirmar() {
        if (validar_guardar_compra()) {
            cargar_dato_compra();
            cargar_dato_caja_detalle();
            if (BOcom.getboo_insertar_compra(ENTcom, tblitem_compra, ENTcd)) {
                reestableser_compra();
            }
        }
    }

    public String filtro_estado(JCheckBox jCestado_emitido, JCheckBox jCestado_anulado) {
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
            estado = condi + " c.estado='" + gda.getEstado_emitido() + "' ";
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
            estado = condi + " c.estado='" + gda.getEstado_anulado() + "' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }

    private void filtro_venta() {
        String filtro = "";
        filtro = filtro + filtro_estado(jCestado_emitido, jCestado_anulado);
        filtro = filtro + evefec.getIntervalo_fecha_combobox(cmbfiltro_fecha, "c.fecha_compra");
        DAOcom.actualizar_tabla_compra(conn, tblcompra, filtro);
        suma_monto_compra(filtro);
    }

    private void suma_monto_compra(String filtro) {
        String sql = "select sum(c.monto_compra) as monto\n"
                + "from compra c,proveedor p \n"
                + "where c.fk_idproveedor=p.idproveedor " + filtro;
        String titulo = "suma_monto_venta";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                double monto_pagado = (rs.getDouble(1));
                jFtotal_pagado.setValue(monto_pagado);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }
    private void boton_anular_compra() {
        if (tblcompra.getSelectedRow() >= 0) {
            int idcompra_select = eveJtab.getInt_select_id(tblcompra);
            ENTcom.setC8estado(gda.getEstado_anulado());
            ENTcom.setC1idcompra(idcompra_select);
            ENTcd.setC6estado(gda.getEstado_anulado());
            ENTcd.setC8cierre(gda.getCaja_anulado());
            ENTcd.setC17fk_idcompra(idcompra_select);
            if (BOcom.getBoo_anular_update_compra(ENTcom, ENTcd)) {
                filtro_venta();
            }
        }
    }
    public FrmCompra() {
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
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtprov_nombre = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtprov_ruc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtprov_direccion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtprov_telefono = new javax.swing.JTextField();
        btnbuscar_cliente = new javax.swing.JButton();
        btnnuevo_cliente = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        txtbuscar_producto = new javax.swing.JTextField();
        txtcodbarra = new javax.swing.JTextField();
        txtpro_preciocompra = new javax.swing.JTextField();
        txtpro_cantidad = new javax.swing.JTextField();
        txtpro_subtotal = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_compra = new javax.swing.JTable();
        jFmonto_compra = new javax.swing.JFormattedTextField();
        btneliminar_item = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtobservacion = new javax.swing.JTextField();
        txtmonto_letra = new javax.swing.JTextField();
        btncancelar = new javax.swing.JButton();
        btnconfirmar = new javax.swing.JButton();
        txtnumero_nota = new javax.swing.JTextField();
        txtfecha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtidcompra = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblcompra = new javax.swing.JTable();
        btnanular = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        cmbfiltro_fecha = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jFtotal_pagado = new javax.swing.JFormattedTextField();

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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("PROVEEDOR"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("PROVE:");

        txtprov_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtprov_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprov_nombreKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("RUC:");

        txtprov_ruc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtprov_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprov_rucKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("DIREC:");

        txtprov_direccion.setEditable(false);
        txtprov_direccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtprov_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprov_direccionKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("TEL:");

        txtprov_telefono.setEditable(false);
        txtprov_telefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtprov_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprov_telefonoKeyPressed(evt);
            }
        });

        btnbuscar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_clienteActionPerformed(evt);
            }
        });

        btnnuevo_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_clienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtprov_nombre)
                    .addComponent(txtprov_direccion, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11))
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtprov_ruc, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(txtprov_telefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(233, 233, 233))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtprov_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(txtprov_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(txtprov_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtprov_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTO"));

        tblproducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproductoMouseReleased(evt);
            }
        });
        tblproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblproductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblproductoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblproducto);

        txtbuscar_producto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbuscar_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTO:"));
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        txtcodbarra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcodbarra.setBorder(javax.swing.BorderFactory.createTitledBorder("CODBARRA:"));

        txtpro_preciocompra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtpro_preciocompra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtpro_preciocompra.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO-COMPRA:"));
        txtpro_preciocompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpro_preciocompraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpro_preciocompraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpro_preciocompraKeyTyped(evt);
            }
        });

        txtpro_cantidad.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtpro_cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtpro_cantidad.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDAD:"));
        txtpro_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpro_cantidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpro_cantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpro_cantidadKeyTyped(evt);
            }
        });

        txtpro_subtotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtpro_subtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtpro_subtotal.setBorder(javax.swing.BorderFactory.createTitledBorder("SUBTOTAL:"));
        txtpro_subtotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpro_subtotalKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtpro_preciocompra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpro_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpro_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtcodbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_producto)))
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcodbarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtpro_preciocompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtpro_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtpro_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM COMPRA"));

        tblitem_compra.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblitem_compra);

        jFmonto_compra.setEditable(false);
        jFmonto_compra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_compra.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        btneliminar_item.setText("ELIMINAR ITEM");
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("TOTAL:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btneliminar_item)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFmonto_compra, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btneliminar_item)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jLabel3.setText("OBSERVACION:");

        jLabel4.setText("MONTO LETRA:");

        txtmonto_letra.setEditable(false);

        btncancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btncancelar.setText("CANCELAR");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        btnconfirmar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnconfirmar.setText("CONFIRMAR");
        btnconfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmarActionPerformed(evt);
            }
        });

        txtnumero_nota.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtnumero_nota.setBorder(javax.swing.BorderFactory.createTitledBorder("NUMERO NOTA:"));

        txtfecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfecha.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA"));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("ID:");

        txtidcompra.setEditable(false);
        txtidcompra.setBackground(new java.awt.Color(153, 153, 153));
        txtidcompra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtidcompra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtnumero_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 60, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtobservacion)
                            .addComponent(txtmonto_letra, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnconfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtnumero_nota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidcompra, javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.CENTER))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnconfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("CREAR COMPRA", jPanel1);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("COMPRA"));

        tblcompra.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblcompra);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
        );

        btnanular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/anular.png"))); // NOI18N
        btnanular.setText("ANULAR");
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCestado_emitido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCestado_anulado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_anulado))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        cmbfiltro_fecha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfiltro_fecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbfiltro_fechaItemStateChanged(evt);
            }
        });

        jLabel18.setText("Fecha Filtro:");

        jFtotal_pagado.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL PAGADO"));
        jFtotal_pagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_pagado.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnanular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 462, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jFtotal_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmbfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnanular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("FILTRO COMPRA", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtprov_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprov_nombreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            abrir_buscar(3, "PROVEEDOR", txtprov_nombre);
        }
    }//GEN-LAST:event_txtprov_nombreKeyPressed

    private void txtprov_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprov_rucKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            abrir_buscar(4, "PROVEEDOR", txtprov_ruc);
        }
    }//GEN-LAST:event_txtprov_rucKeyPressed

    private void txtprov_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprov_direccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprov_direccionKeyPressed

    private void txtprov_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprov_telefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprov_telefonoKeyPressed

    private void btnbuscar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_clienteActionPerformed
        // TODO add your handling code here:
        abrir_buscar(3, "PROVEEDOR", txtprov_nombre);
    }//GEN-LAST:event_btnbuscar_clienteActionPerformed

    private void btnnuevo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_clienteActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProveedor());
    }//GEN-LAST:event_btnnuevo_clienteActionPerformed

    private void txtpro_preciocompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_preciocompraKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtpro_preciocompraKeyTyped

    private void txtpro_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_cantidadKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtpro_cantidadKeyTyped

    private void txtpro_subtotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_subtotalKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtpro_subtotalKeyTyped

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
        buscar_producto();
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void tblproductoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductoMouseReleased
        // TODO add your handling code here:
        seleccionar_producto();
    }//GEN-LAST:event_tblproductoMouseReleased

    private void txtpro_preciocompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_preciocompraKeyReleased
        // TODO add your handling code here:
        calcular_subtotal();
    }//GEN-LAST:event_txtpro_preciocompraKeyReleased

    private void txtpro_cantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_cantidadKeyReleased
        // TODO add your handling code here:
        calcular_subtotal();
    }//GEN-LAST:event_txtpro_cantidadKeyReleased

    private void txtbuscar_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_flecha_abajo(evt, tblproducto);
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txtbuscar_producto.setBackground(Color.white);
        }
    }//GEN-LAST:event_txtbuscar_productoKeyPressed

    private void txtpro_preciocompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_preciocompraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtpro_preciocompra.setBackground(Color.white);
            txtpro_cantidad.setBackground(Color.yellow);
            txtpro_cantidad.grabFocus();
        }
    }//GEN-LAST:event_txtpro_preciocompraKeyPressed

    private void tblproductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblproductoKeyReleased
        // TODO add your handling code here:
        seleccionar_producto();
    }//GEN-LAST:event_tblproductoKeyReleased

    private void tblproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblproductoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            txtbuscar_producto.setBackground(Color.white);
            txtpro_preciocompra.setBackground(Color.yellow);
            txtpro_preciocompra.grabFocus();
        }
    }//GEN-LAST:event_tblproductoKeyPressed

    private void txtpro_cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_cantidadKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_compra();
        }
    }//GEN-LAST:event_txtpro_cantidadKeyPressed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_compra();
        DAOpro.ancho_tabla_producto_compra(tblproducto);
        DAOcom.ancho_tabla_compra(tblcompra);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        // TODO add your handling code here:
        boton_cancelar();
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btnconfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmarActionPerformed
        // TODO add your handling code here:
        boton_confirmar();
    }//GEN-LAST:event_btnconfirmarActionPerformed

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        // TODO add your handling code here:
        boton_anular_compra();
    }//GEN-LAST:event_btnanularActionPerformed

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        filtro_venta();
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        filtro_venta();
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void cmbfiltro_fechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbfiltro_fechaItemStateChanged
        // TODO add your handling code here:
        filtro_venta();
    }//GEN-LAST:event_cmbfiltro_fechaItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btnbuscar_cliente;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btnconfirmar;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnnuevo_cliente;
    private javax.swing.JComboBox<String> cmbfiltro_fecha;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JFormattedTextField jFmonto_compra;
    private javax.swing.JFormattedTextField jFtotal_pagado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblcompra;
    private javax.swing.JTable tblitem_compra;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtcodbarra;
    private javax.swing.JTextField txtfecha;
    private javax.swing.JTextField txtidcompra;
    private javax.swing.JTextField txtmonto_letra;
    private javax.swing.JTextField txtnumero_nota;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtpro_cantidad;
    private javax.swing.JTextField txtpro_preciocompra;
    private javax.swing.JTextField txtpro_subtotal;
    public static javax.swing.JTextField txtprov_direccion;
    public static javax.swing.JTextField txtprov_nombre;
    public static javax.swing.JTextField txtprov_ruc;
    public static javax.swing.JTextField txtprov_telefono;
    // End of variables declaration//GEN-END:variables
}
