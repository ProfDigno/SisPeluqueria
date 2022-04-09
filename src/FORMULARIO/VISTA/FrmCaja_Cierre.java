/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.EvenDatosPc;
import CONFIGURACION.Global_datos;
import Evento.Color.cla_color_palete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.BO.BO_caja_cierre;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_caja_cierre_item;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.ENTIDAD.caja_cierre;
import FORMULARIO.ENTIDAD.caja_cierre_item;
import FORMULARIO.ENTIDAD.caja_detalle;
//import FORMULARIO.DAO.dao_caja_cierre;
//import FORMULARIO.DAO.dao_caja_detalle;
//import FORMULARIO.DAO.dao_item_caja_cierre;
//import FORMULARIO.ENTIDAD.entidad_caja_cierre;
//import FORMULARIO.ENTIDAD.entidad_caja_detalle;
//import FORMULARIO.ENTIDAD.entidad_item_caja_cierre;
//import FORMULARIO.ENTIDAD.entidad_usuario;
//import FORMULARIO.DAO.DAO_caja_cierre;
//import FORMULARIO.DAO.DAO_caja_detalle;
//import FORMULARIO.DAO.DAO_item_caja_cierre;
//import FORMULARIO.ENTIDAD.caja_cierre;
//import FORMULARIO.ENTIDAD.caja_detalle;
//import FORMULARIO.ENTIDAD.item_caja_cierre;
//import FORMULARIO.ENTIDAD.usuario;
import IMPRESORA_POS.PosImprimir_Venta;
import IMPRESORA_POS.PosImprimir_caja_cierre;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class FrmCaja_Cierre extends javax.swing.JInternalFrame {

    PosImprimir_Venta posv = new PosImprimir_Venta();
    private Connection connLocal = null;
    private ConnPostgres cpt = new ConnPostgres();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenFecha evefec = new EvenFecha();
    private EvenDatosPc evepc = new EvenDatosPc();
    private EvenUtil eveut = new EvenUtil();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenConexion eveconn = new EvenConexion();
    private caja_cierre ENTcc = new caja_cierre();
//    entidad_usuario usu = new entidad_usuario();
    private caja_detalle ENTcd = new caja_detalle();
    private caja_cierre_item ENTcci = new caja_cierre_item();
    private EvenJFRAME evetbl = new EvenJFRAME();
    private BO_caja_cierre BOcc = new BO_caja_cierre();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    private DAO_caja_detalle DAOcd = new DAO_caja_detalle();
    private DAO_caja_cierre_item DAOcci = new DAO_caja_cierre_item();
    private Global_datos gda=new Global_datos();
    private String nombre_formulario = "CERRAR CAJA";
    private PosImprimir_caja_cierre poscc=new PosImprimir_caja_cierre();
    ArrayList<Integer> array_caja_detalle_abierto = new ArrayList<Integer>();
    private cla_color_palete clacolor= new cla_color_palete();
    double caja_detalle_SALDO = 0;
    double caja_detalle_DIFERENCIA=0;
    double caja_detalle_CIERRE;
    int fk_idusuario=1;
    void abrir_formulario() {
        this.setTitle(nombre_formulario);
        connLocal = cpt.getConnPosgres();
        evetbl.centrar_formulario(this);
        caja_detalle_cantidad_total(gda.getTbl_caja(), "in_monto_apertura", txtcant_caja, jFabrir_caja);
        caja_detalle_cantidad_total(gda.getTbl_venta(), "in_monto_venta", txtcant_venta, jFtotal_venta);
        caja_detalle_cantidad_total(gda.getTbl_gasto(), "eg_monto_gasto", txtcant_gasto, jFtotal_gasto);
        caja_detalle_cantidad_total(gda.getTbl_compra(), "eg_monto_compra", txtcant_compra, jFtotal_compra);
        caja_detalle_cantidad_total(gda.getTbl_funcionario(), "eg_monto_recibo_funcionario", txtcant_vale, jFtotal_vale);
        caja_detalle_SALDO();
        color_formulario();
        txtmonto_caja_cierre.grabFocus();
    }
    void color_formulario(){
        panel_ingreso.setBackground(clacolor.getColor_insertar_primario());
        panel_egreso.setBackground(clacolor.getColor_insertar_secundario());
        panel_resultado.setBackground(clacolor.getColor_referencia());
    }
    void verificar_caja_abierto() {
//        int idcaja_cierre = (eveconn.getInt_ultimoID_max(connLocal, ENTcc.getTb_caja_cierre(), ENTcc.getId_idcaja_cierre()));
//        cjcie.setC1idcaja_cierre(idcaja_cierre);
//        DAOcc.cargar_caja_cierre(connLocal, ENTcc, idcaja_cierre);
        if (DAOcc.getboo_existe_abierto_cargar_caja_cierre(connLocal)) {
            JOptionPane.showMessageDialog(null, "NO HAY CAJA ABIERTA SE DEBE ABRIR UNO NUEVO");
            this.dispose();
        }
    }

    void cargar_datos_caja_cierre() {
//        ENTcc.setC4estado("Abierto");
//        ENTcc.setC5fk_idusuario(fk_idusuario);
        ENTcc.setC3creado_por(gda.getCreado_por());
        ENTcc.setC6estado(gda.getEstado_abierto());
    }

    void cargar_datos_caja_detalle(double saldo_cierre) {
//        ENTcd.setC3descripcion("(VENTA) CAJA CERRAR:");
//        ENTcd.setC4total_venta_efectivo(0);
//        ENTcd.setC5total_venta_tarjeta(0);
//        ENTcd.setC6total_compra(0);
//        ENTcd.setC7total_gasto(0);
//        ENTcd.setC8total_salario(0);
//        ENTcd.setC9origen_tabla("CAJA_CERRAR");
//        ENTcd.setC10fk_id_tabla(0);
//        ENTcd.setC11estado("A");
//        ENTcd.setC12monto_abrir(0);
//        ENTcd.setC13monto_cerrar(saldo_cierre);
//        ENTcd.setC14fk_idusuario(fk_idusuario);
//        DAOcd.insertar_caja_detalle(connLocal, ENTcd);
        DAOcd.vaciar_caja_detalle(ENTcd);
        ENTcd.setC4descripcion(gda.getTbl_caja()+"=>"+nombre_formulario);
        ENTcd.setC5tabla_origen(gda.getTbl_caja());
        ENTcd.setC8cierre(gda.getCaja_abierto());
        ENTcd.setC6estado(gda.getEstado_emitido());
        ENTcd.setC7monto_cierre(saldo_cierre);
        DAOcd.insertar_caja_detalle(connLocal, ENTcd);
    }

    void boton_caja_cierre() {
        if (txtmonto_caja_cierre.getText().trim().length() > 0) {
            insertar_item_caja_cierre(caja_detalle_CIERRE);
//            int idcaja_cierre = (eveconn.getInt_ultimoID_max(connLocal, cjcie.getTb_caja_cierre(), cjcie.getId_idcaja_cierre()));
//            poscc.boton_imprimir_pos_caja_cierre(connLocal, idcaja_cierre);
            JOptionPane.showMessageDialog(null, "EL SISTEMA SE VA CERRAR","CERRAR",JOptionPane.WARNING_MESSAGE);
            System.exit(0);
//            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "CARGAR UN MONTO TOTAL DE LA CAJA");
            txtmonto_caja_cierre.grabFocus();
        }
    }

    void caja_detalle_cantidad_total(String origen_tabla, String campo_total, JTextField txtcantidad, JFormattedTextField jftotal) {
        String titulo = "caja_detalle_cantidad_total";
        String sql = "select count(*) as cantidad,sum(" + campo_total + ") as total\n"
                + " from caja_detalle c \n"
                + "where c.cierre='"+gda.getCaja_abierto()+"' \n"
                + "and c.tabla_origen='" + origen_tabla + "'";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            if (rs.next()) {
                String cantidad = rs.getString("cantidad");
                txtcantidad.setText(cantidad);
                int total = rs.getInt("total");
                jftotal.setValue(total);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void caja_detalle_SALDO() {
        String titulo = "caja_detalle_cantidad_total";
        String sql = "select ((sum(in_monto_venta+in_monto_apertura))-"
                + "(sum(eg_monto_gasto+eg_monto_compra+eg_monto_recibo_funcionario))) as saldo \n"
                + "from caja_detalle where cierre='"+gda.getCaja_abierto()+"' ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            if (rs.next()) {
                double saldo = rs.getDouble("saldo");
                jFcaja_detalle_sistema.setValue(saldo);
                caja_detalle_SALDO = saldo;
                if (caja_detalle_SALDO < 0) {
                    jFcaja_detalle_sistema.setBackground(Color.red);
                } else {
                    jFcaja_detalle_sistema.setBackground(Color.green);
                }
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void calcular_diferencia() {
        caja_detalle_CIERRE = evejtf.getDouble_format_nro_entero(txtmonto_caja_cierre);
        caja_detalle_DIFERENCIA = caja_detalle_CIERRE - caja_detalle_SALDO;
        jFcaja_detalle_DIFERENCIA.setValue(caja_detalle_DIFERENCIA);
        if (caja_detalle_DIFERENCIA < 0) {
            jFcaja_detalle_DIFERENCIA.setBackground(Color.red);
        } else {
            jFcaja_detalle_DIFERENCIA.setBackground(Color.green);
        }
    }

    void cargar_arrayList_caja_abierto() {
        array_caja_detalle_abierto.clear();
        String titulo = "cargar_vector_caja_abierto";
        String sql = "select idcaja_detalle "
                + "from caja_detalle "
                + "where cierre='"+gda.getCaja_abierto()+"' "
                + "order by idcaja_detalle asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                array_caja_detalle_abierto.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
//        for(int fila=0;fila<array_caja_detalle_abierto.size();fila++){
//            System.out.println("id="+array_caja_detalle_abierto.get(fila));
//        }
        System.out.println("total array=" + array_caja_detalle_abierto.size());

    }

    void insertar_item_caja_cierre(double saldo_cierre) {
        String titulo = "insertar_item_caja_cierre";
        try {
            if (connLocal.getAutoCommit()) {
                connLocal.setAutoCommit(false);
            }
            int idcaja_cierre = (eveconn.getInt_ultimoID_max(connLocal, ENTcc.getTb_caja_cierre(), ENTcc.getId_idcaja_cierre()));
            cargar_datos_caja_detalle(saldo_cierre);
            cargar_arrayList_caja_abierto();
//            ENTcci.setC1idcaja_cierre_item(idcaja_cierre);
            ENTcci.setC3creado_por(gda.getCreado_por());
            for (int fila = 0; fila < array_caja_detalle_abierto.size(); fila++) {
                ENTcci.setC4fk_idcaja_cierre(idcaja_cierre);
                ENTcci.setC5fk_idcaja_detalle(array_caja_detalle_abierto.get(fila));
                DAOcci.insertar_caja_cierre_item(connLocal, ENTcci);
            }
            ENTcc.setC1idcaja_cierre(idcaja_cierre);
            ENTcc.setC3creado_por(gda.getCreado_por());
            ENTcc.setC6estado(gda.getEstado_cerrado());
            DAOcc.update_caja_cierre(connLocal, ENTcc);
            DAOcd.update_caja_detalle_CERRARTODO(connLocal);
            connLocal.commit();
        } catch (SQLException e) {
            evemen.mensaje_error(e, ENTcc.toString(), titulo);
            try {
                connLocal.rollback();
            } catch (SQLException e1) {
                evemen.Imprimir_serial_sql_error(e1, ENTcc.toString(), titulo);
            }
        }
    }

    public FrmCaja_Cierre() {
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

        panel_ingreso = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtcant_venta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jFabrir_caja = new javax.swing.JFormattedTextField();
        jFtotal_venta = new javax.swing.JFormattedTextField();
        txtcant_caja = new javax.swing.JTextField();
        panel_egreso = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtcant_gasto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtcant_compra = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtcant_vale = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jFtotal_gasto = new javax.swing.JFormattedTextField();
        jFtotal_compra = new javax.swing.JFormattedTextField();
        jFtotal_vale = new javax.swing.JFormattedTextField();
        panel_resultado = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jFcaja_detalle_sistema = new javax.swing.JFormattedTextField();
        txtmonto_caja_cierre = new javax.swing.JTextField();
        jFcaja_detalle_DIFERENCIA = new javax.swing.JFormattedTextField();
        btncaja_cierre = new javax.swing.JButton();

        setClosable(true);

        panel_ingreso.setBackground(new java.awt.Color(102, 153, 255));
        panel_ingreso.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("ABRIR CAJA:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("CANT. VENTA:");

        txtcant_venta.setEditable(false);
        txtcant_venta.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_venta.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_venta.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("TOTAL VENTA:");

        jFabrir_caja.setEditable(false);
        jFabrir_caja.setBackground(new java.awt.Color(204, 204, 204));
        jFabrir_caja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFabrir_caja.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        jFtotal_venta.setEditable(false);
        jFtotal_venta.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        txtcant_caja.setEditable(false);
        txtcant_caja.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_caja.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_caja.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout panel_ingresoLayout = new javax.swing.GroupLayout(panel_ingreso);
        panel_ingreso.setLayout(panel_ingresoLayout);
        panel_ingresoLayout.setHorizontalGroup(
            panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ingresoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ingresoLayout.createSequentialGroup()
                        .addComponent(txtcant_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(txtcant_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(jFabrir_caja))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_ingresoLayout.setVerticalGroup(
            panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ingresoLayout.createSequentialGroup()
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jFabrir_caja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_caja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtcant_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jFtotal_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        panel_egreso.setBackground(new java.awt.Color(153, 204, 255));
        panel_egreso.setBorder(javax.swing.BorderFactory.createTitledBorder("EGRESO"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("CANT. GASTO:");

        txtcant_gasto.setEditable(false);
        txtcant_gasto.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_gasto.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_gasto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("TOTAL GASTO:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("CANT. COMPRA:");

        txtcant_compra.setEditable(false);
        txtcant_compra.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_compra.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_compra.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("TOTAL COMPRA:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("CANT. VALE:");

        txtcant_vale.setEditable(false);
        txtcant_vale.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_vale.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_vale.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("TOTAL VALE:");

        jFtotal_gasto.setEditable(false);
        jFtotal_gasto.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_gasto.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        jFtotal_compra.setEditable(false);
        jFtotal_compra.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_compra.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        jFtotal_vale.setEditable(false);
        jFtotal_vale.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_vale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_vale.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        javax.swing.GroupLayout panel_egresoLayout = new javax.swing.GroupLayout(panel_egreso);
        panel_egreso.setLayout(panel_egresoLayout);
        panel_egresoLayout.setHorizontalGroup(
            panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_egresoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcant_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_compra, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(jFtotal_gasto)
                    .addComponent(jFtotal_vale))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_egresoLayout.setVerticalGroup(
            panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_egresoLayout.createSequentialGroup()
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtcant_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jFtotal_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtcant_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jFtotal_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtcant_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jFtotal_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        panel_resultado.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultado"));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("SISTEMA:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("CIERRE:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("DIFERENCIA:");

        jFcaja_detalle_sistema.setBackground(new java.awt.Color(204, 204, 204));
        jFcaja_detalle_sistema.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcaja_detalle_sistema.setText("000");
        jFcaja_detalle_sistema.setFont(new java.awt.Font("Stencil", 0, 30)); // NOI18N

        txtmonto_caja_cierre.setFont(new java.awt.Font("Stencil", 0, 30)); // NOI18N
        txtmonto_caja_cierre.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_caja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmonto_caja_cierreActionPerformed(evt);
            }
        });
        txtmonto_caja_cierre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmonto_caja_cierreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_caja_cierreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_caja_cierreKeyTyped(evt);
            }
        });

        jFcaja_detalle_DIFERENCIA.setBackground(new java.awt.Color(204, 204, 204));
        jFcaja_detalle_DIFERENCIA.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcaja_detalle_DIFERENCIA.setFont(new java.awt.Font("Stencil", 0, 30)); // NOI18N

        btncaja_cierre.setText("CAJA CIERRE");
        btncaja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaja_cierreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_resultadoLayout = new javax.swing.GroupLayout(panel_resultado);
        panel_resultado.setLayout(panel_resultadoLayout);
        panel_resultadoLayout.setHorizontalGroup(
            panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_resultadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_resultadoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFcaja_detalle_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_resultadoLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(jFcaja_detalle_DIFERENCIA, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_resultadoLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtmonto_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_resultadoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btncaja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_resultadoLayout.setVerticalGroup(
            panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_resultadoLayout.createSequentialGroup()
                .addGroup(panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jFcaja_detalle_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtmonto_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jFcaja_detalle_DIFERENCIA, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncaja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_resultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_egreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_egreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_resultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtmonto_caja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmonto_caja_cierreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmonto_caja_cierreActionPerformed

    private void txtmonto_caja_cierreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_caja_cierreKeyReleased
        // TODO add your handling code here:
        calcular_diferencia();
    }//GEN-LAST:event_txtmonto_caja_cierreKeyReleased

    private void btncaja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaja_cierreActionPerformed
        // TODO add your handling code here:
        boton_caja_cierre();
    }//GEN-LAST:event_btncaja_cierreActionPerformed

    private void txtmonto_caja_cierreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_caja_cierreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_caja_cierre();
        }
    }//GEN-LAST:event_txtmonto_caja_cierreKeyPressed

    private void txtmonto_caja_cierreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_caja_cierreKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_caja_cierreKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncaja_cierre;
    private javax.swing.JFormattedTextField jFabrir_caja;
    private javax.swing.JFormattedTextField jFcaja_detalle_DIFERENCIA;
    private javax.swing.JFormattedTextField jFcaja_detalle_sistema;
    private javax.swing.JFormattedTextField jFtotal_compra;
    private javax.swing.JFormattedTextField jFtotal_gasto;
    private javax.swing.JFormattedTextField jFtotal_vale;
    private javax.swing.JFormattedTextField jFtotal_venta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panel_egreso;
    private javax.swing.JPanel panel_ingreso;
    private javax.swing.JPanel panel_resultado;
    private javax.swing.JTextField txtcant_caja;
    private javax.swing.JTextField txtcant_compra;
    private javax.swing.JTextField txtcant_gasto;
    private javax.swing.JTextField txtcant_vale;
    private javax.swing.JTextField txtcant_venta;
    private javax.swing.JTextField txtmonto_caja_cierre;
    // End of variables declaration//GEN-END:variables
}
