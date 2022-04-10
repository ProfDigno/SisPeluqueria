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
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import IMPRESORA_POS.PosImprimir_Venta;
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
public class FrmVenta extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenConexion eveconn = new EvenConexion();
    private EvenFecha evefec = new EvenFecha();
    private venta ENTven = new venta();
    private DAO_venta DAOven = new DAO_venta();
    private BO_venta BOven = new BO_venta();
    private cliente ENTcli = new cliente();
    private DAO_cliente DAOcli = new DAO_cliente();
    private configuracion_puntaje ENTcp = new configuracion_puntaje();
    private DAO_configuracion_puntaje DAOcp = new DAO_configuracion_puntaje();
    private servicio_categoria ENTsc = new servicio_categoria();
    private DAO_servicio_categoria DAOsc = new DAO_servicio_categoria();
    private producto_categoria ENTpc = new producto_categoria();
    private DAO_producto_categoria DAOpc = new DAO_producto_categoria();
    private servicio ENTser = new servicio();
    private DAO_servicio DAOser = new DAO_servicio();
    private producto ENTpro = new producto();
    private DAO_producto DAOpro = new DAO_producto();
    private funcionario_comision ENTfc = new funcionario_comision();
    private DAO_funcionario_comision DAOfc = new DAO_funcionario_comision();
    private caja_detalle ENTcd = new caja_detalle();
    private DAO_caja_detalle DAOcd = new DAO_caja_detalle();
    private EvenJTextField evejtf = new EvenJTextField();
    private DefaultTableModel model_itemf = new DefaultTableModel();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private Global_datos gda = new Global_datos();
    private PosImprimir_Venta posven = new PosImprimir_Venta();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_palete clacolor = new cla_color_palete();
    private String nombre_formulario = "VENTA";
//    String creado_por = "DIGNO";
    private static int fk_idcliente;
    private static int fk_idconfiguracion_puntaje;
    private static boolean genera_puntaje;
    private static double cliente_total_puntaje;
    private double monto_descuento;
    private double monto_pagado;
    private double monto_total;
    private double monto_comision;
    private String estado_emitido;
    private double puntaje_cliente;
    private boolean uso_puntaje = false;
    private int idventa;

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        crear_item_venta();
        DAOven.actualizar_tabla_venta(conn, tblventa, "");
        evefec.cargar_combobox_intervalo_fecha(cmbfiltro_fecha);
        color_formulario();
        reestableser_venta();
    }

    private void color_formulario() {
        panel_tabla.setBackground(clacolor.getColor_tabla());
        panel_insertar.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar_venta() {
        if (getFk_idcliente() == 0) {
            JOptionPane.showMessageDialog(null, "SE DEBE CARGAR UN CLIENTE");
            txtcli_nombre.grabFocus();
            return false;
        }
        if (tblitem_venta.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "SE DEBE CARGAR AL MENOS UN ITEM PARA GUARDAR");
            return false;
        }
        return true;
    }

    private void cargar_dato_venta() {
        ENTven.setC3creado_por(gda.getCreado_por());
        ENTven.setC4estado(gda.getEstado_emitido());
        ENTven.setC5forma_pago(gda.getPago_contado());
        ENTven.setC6monto_total(monto_total);
        ENTven.setC7monto_comision(monto_comision);
        ENTven.setC8monto_descuento(monto_descuento);
        ENTven.setC9monto_pagado(monto_pagado);
        ENTven.setC10puntaje_cliente(puntaje_cliente);
        ENTven.setC11genera_puntaje(isGenera_puntaje());
        ENTven.setC12uso_puntaje(uso_puntaje);
        ENTven.setC13fk_idcliente(getFk_idcliente());
        ENTven.setC14fk_idconfiguracion_puntaje(getFk_idconfiguracion_puntaje());
        ENTven.setC15fk_idusuario(gda.getFk_idusuario());
        System.out.println("VENTA:" + ENTven.toString());
    }

    private void cargar_dato_caja_detalle() {
        DAOcd.vaciar_caja_detalle(ENTcd);
        ENTcd.setC4descripcion(gda.getTbl_venta() + "=>" + txtcli_nombre.getText());
        ENTcd.setC5tabla_origen(gda.getTbl_venta());
        ENTcd.setC8cierre(gda.getCaja_abierto());
        ENTcd.setC6estado(gda.getEstado_emitido());
        ENTcd.setC10in_monto_venta(monto_pagado);
        ENTcd.setC14fk_idventa(idventa);
    }

    private void boton_guardar_venta() {
        if (validar_guardar_venta()) {
            cargar_dato_venta();
            cargar_dato_caja_detalle();
            if (BOven.getboo_insertar_venta(ENTven, tblitem_venta, ENTcd)) {
                posven.boton_imprimir_pos_VENTA(conn, idventa);
                reestableser_venta();
            }
        }
    }

    private void boton_anular_venta() {
        if (tblventa.getSelectedRow() > 0) {
            int idventa_select = eveJtab.getInt_select_id(tblventa);
            ENTven.setC4estado(gda.getEstado_anulado());
            ENTven.setC1idventa(idventa_select);
            ENTcd.setC6estado(gda.getEstado_anulado());
            ENTcd.setC8cierre(gda.getCaja_anulado());
            ENTcd.setC14fk_idventa(idventa_select);
            ENTfc.setC7estado(gda.getEstado_anulado());
            ENTfc.setC13fk_idventa(idventa_select);
            if (BOven.anular_update_venta(ENTven, ENTcd, ENTfc)) {
                reestableser_venta();
            }
        }
    }

    private void boton_imprimir_ticket() {
        if (tblventa.getSelectedRow() > 0) {
            int idventa_select = eveJtab.getInt_select_id(tblventa);
            posven.boton_imprimir_pos_VENTA(conn, idventa_select);
        }
    }

    private void seleccionar_tabla_venta() {
        int id = eveJtab.getInt_select_id(tblventa);
        String estado=eveJtab.getString_select(tblventa, 9);
        if(estado.equals(gda.getEstado_emitido())){
            btnanular.setEnabled(true);
        }
        if(estado.equals(gda.getEstado_comision())){
            btnanular.setEnabled(false);
        }
        if(estado.equals(gda.getEstado_anulado())){
            btnanular.setEnabled(false);
        }
    }

    private void reestableser_venta() {
        idventa = (eveconn.getInt_ultimoID_mas_uno(conn, ENTven.getTb_venta(), ENTven.getId_idventa()));
        txtid.setText(String.valueOf(idventa));
        btnguardar.setEnabled(true);
        eveJtab.limpiar_tabla_datos(model_itemf);
        DAOsc.actualizar_tabla_servicio_categoria_venta(conn, tblservicio_categoria);
        DAOpc.actualizar_tabla_producto_categoria_venta(conn, tblproducto_categoria);
        DAOser.actualizar_tabla_servicio_venta(conn, tblservicio, "");
        DAOpro.actualizar_tabla_producto_venta(conn, tblproducto, "");
        DAOven.actualizar_tabla_venta(conn, tblventa, "");
        txtmonto_descuento.setText(null);
        setFk_idcliente(0);
        setGenera_puntaje(false);
        setCliente_total_puntaje(0);
        sumar_item_venta();
        limpiar_cliente();
        monto_descuento = 0;
        txtmonto_descuento.setText(String.valueOf((int) monto_descuento));
    }

    private void limpiar_cliente() {
        txtcli_nombre.setText(null);
        txtcli_ruc.setText(null);
        txtcli_direccion.setText(null);
        txtcli_telefono.setText(null);
        txtcli_total_puntaje.setText(null);
        lbltiene_puntaje.setText(null);
    }

    private void seleccionar_servicio_categoria() {
        if (tblservicio_categoria.getSelectedRow() >= 0) {
            int fk_idservicio_categoria = eveJtab.getInt_select_id(tblservicio_categoria);
            String filtro = " and s.fk_idservicio_categoria=" + fk_idservicio_categoria;
            DAOser.actualizar_tabla_servicio_venta(conn, tblservicio, filtro);
        }
    }

    private void seleccionar_servicio() {
        if (tblservicio.getSelectedRow() >= 0) {
            int fk_idservicio = eveJtab.getInt_select_id(tblservicio);
            DAOser.cargar_servicio(conn, ENTser, fk_idservicio);
            txtprecio_venta_servicio.setText(String.valueOf((int) ENTser.getC9precio_venta()));
        }
    }

    private void seleccionar_producto() {
        if (tblproducto.getSelectedRow() >= 0) {
            int fk_idproducto = eveJtab.getInt_select_id(tblproducto);
            DAOpro.cargar_producto(conn, ENTpro, fk_idproducto);
            txtprecio_venta_produto.setText(String.valueOf((int) ENTpro.getC8precio_venta()));
        }
    }

    private void buscar_servicio() {
        if (txtbuscar_servicio.getText().trim().length() > 0) {
            String buscar = txtbuscar_servicio.getText();
            String filtro = " and s.nombre ilike'%" + buscar + "%' ";
            DAOser.actualizar_tabla_servicio_venta(conn, tblservicio, filtro);
        }
    }

    private void seleccionar_producto_categoria() {
        if (tblproducto_categoria.getSelectedRow() >= 0) {
            int fk_idproducto_categoria = eveJtab.getInt_select_id(tblproducto_categoria);
            String filtro = " and s.fk_idproducto_categoria=" + fk_idproducto_categoria;
            DAOpro.actualizar_tabla_producto_venta(conn, tblproducto, filtro);
        }
    }

    private void buscar_producto() {
        if (txtbuscar_producto.getText().trim().length() > 0) {
            String buscar = txtbuscar_producto.getText();
            String filtro = " and s.nombre ilike'%" + buscar + "%' ";
            DAOpro.actualizar_tabla_producto_venta(conn, tblproducto, filtro);
        }
    }

    private void crear_item_venta() {
        String dato[] = {"ord", "tipo", "%", "descripcion", "precio", "cant", "total", "es_producto", "es_servicio", "es_sum_punto",
            "fk_idventa", "fk_idservicio", "fk_idproducto", "fk_idfuncionario", "monto_comision", "precio_compra", "cliente"};
        eveJtab.crear_tabla_datos(tblitem_venta, model_itemf, dato);
    }

    private void ancho_item_venta() {
        int Ancho[] = {5, 16, 5, 43, 12, 7, 12, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        eveJtab.setAnchoColumnaJtable(tblitem_venta, Ancho);
        eveJtab.ocultar_columna(tblitem_venta, 7);
        eveJtab.ocultar_columna(tblitem_venta, 8);
        eveJtab.ocultar_columna(tblitem_venta, 9);
        eveJtab.ocultar_columna(tblitem_venta, 10);
        eveJtab.ocultar_columna(tblitem_venta, 11);
        eveJtab.ocultar_columna(tblitem_venta, 12);
        eveJtab.ocultar_columna(tblitem_venta, 13);
        eveJtab.ocultar_columna(tblitem_venta, 14);
        eveJtab.ocultar_columna(tblitem_venta, 15);
        eveJtab.ocultar_columna(tblitem_venta, 16);
    }

    private boolean validar_item_venta(int tipoitem) {
        if (tipoitem == 1) {
            if (tblservicio.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONAR UN SERVICIO");
                return false;
            }
            if (txtprecio_venta_servicio.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "CARGAR UN PRECIO");
                return false;
            }
        }
        if (tipoitem == 2) {
            if (tblproducto.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONAR UN PRODUCTO");
                return false;
            }
            if (txtprecio_venta_produto.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "CARGAR UN PRECIO");
                return false;
            }
        }
        return true;
    }

    private void cargar_item_venta(int tipoitem, int cantidad) {
        if (validar_item_venta(tipoitem)) {
            String ord = String.valueOf(tblitem_venta.getRowCount() + 1);
            String tipo = "";
            String descripcion = "";
            String precio = "";
            String cant = "";
            String total = "";
            String es_producto = "";
            String es_servicio = "";
            String es_sum_punto = "";
            String porcentaje_comision = "";
            String fk_idventa = "";
            String fk_idservicio = "";
            String fk_idproducto = "";
            String fk_idfuncionario = "";
            String monto_comision = "";
            String precio_compra = "";
            String cliente = txtcli_nombre.getText();
            if (tipoitem == 1) {
                tipo = "SERVICIO";
                descripcion = eveJtab.getString_select(tblservicio, 1);
                precio = txtprecio_venta_servicio.getText();
                cant = String.valueOf(cantidad);
                int Iprecio = Integer.parseInt(precio);
                int Itotal = cantidad * Iprecio;
                total = String.valueOf(Itotal);
                es_producto = "F";
                es_servicio = "T";
                es_sum_punto = "T";
                fk_idservicio = eveJtab.getString_select(tblservicio, 0);
                fk_idproducto = "0";
                fk_idfuncionario = eveJtab.getString_select(tblservicio, 5);
                porcentaje_comision = eveJtab.getString_select(tblservicio, 2);
                double Iporcentaje_comision = Double.parseDouble(porcentaje_comision);
                int Imonto_comision = (Itotal * (int) Iporcentaje_comision) / 100;
                monto_comision = String.valueOf(Imonto_comision);
                precio_compra = eveJtab.getString_select(tblservicio, 6);
            }
            if (tipoitem == 2) {
                tipo = "PRODUCTO";
                descripcion = eveJtab.getString_select(tblproducto, 1);
                precio = txtprecio_venta_produto.getText();
                cant = String.valueOf(cantidad);
                int Iprecio = Integer.parseInt(precio);
                int Itotal = cantidad * Iprecio;
                total = String.valueOf(Itotal);
                es_producto = "T";
                es_servicio = "F";
                es_sum_punto = "F";
                fk_idservicio = "0";
                fk_idproducto = eveJtab.getString_select(tblproducto, 0);
                fk_idfuncionario = "0";
                porcentaje_comision = "0";
                monto_comision = "0";
                precio_compra = eveJtab.getString_select(tblproducto, 3);
            }
            fk_idventa = String.valueOf(idventa);
            String dato[] = {ord, tipo, porcentaje_comision, descripcion, precio, cant, total, es_producto, es_servicio, es_sum_punto,
                fk_idventa, fk_idservicio, fk_idproducto, fk_idfuncionario, monto_comision, precio_compra, cliente};
            eveJtab.cargar_tabla_datos(tblitem_venta, model_itemf, dato);
            sumar_item_venta();

        }
    }

    private void sumar_item_venta() {
        monto_total = eveJtab.getDouble_sumar_tabla(tblitem_venta, 6);
        monto_comision = eveJtab.getDouble_sumar_tabla(tblitem_venta, 14);
        if (isGenera_puntaje()) {
            if (getFk_idconfiguracion_puntaje() > 0) {
                DAOcp.cargar_configuracion_puntaje(conn, ENTcp, getFk_idconfiguracion_puntaje());
                double minimo_monto_generar = ENTcp.getC9minimo_monto_generar();
                double punto_maximo = ENTcp.getC10punto_maximo();
                if (monto_total >= minimo_monto_generar && getCliente_total_puntaje() <= punto_maximo) {
                    double porcentaje_del_total = ENTcp.getC6porcentaje_del_total();
                    double monto_del_porcentaje = (monto_total * porcentaje_del_total) / 100;
                    jFmonto_puntaje_cliente.setValue(monto_del_porcentaje);
                    double monto_por_punto = ENTcp.getC8monto_por_punto();
                    puntaje_cliente = (monto_del_porcentaje / monto_por_punto);
                } else {
                    puntaje_cliente = 0;
                    jFmonto_puntaje_cliente.setValue(0);
                }
                System.out.println("getCliente_total_puntaje:" + getCliente_total_puntaje());
                System.out.println("punto_maximo:" + punto_maximo);
                if (getCliente_total_puntaje() >= punto_maximo) {
                    lbltiene_puntaje.setText("LLEGO AL MAXIMO");
                    lbltiene_puntaje.setForeground(Color.BLUE);
                }
            }
        } else {
            puntaje_cliente = 0;
            jFmonto_puntaje_cliente.setValue(0);
        }
        if (txtmonto_descuento.getText().trim().length() > 0) {
            monto_descuento = Double.parseDouble(txtmonto_descuento.getText());
        } else {
            monto_descuento = 0;
            txtmonto_descuento.setText(String.valueOf((int) monto_descuento));
        }
        monto_pagado = monto_total - monto_descuento;
        jFnuevo_puntaje_cliente.setValue(puntaje_cliente);
        jFmonto_total.setValue(monto_total);
        jFmonto_comision.setValue(monto_comision);
        jFmonto_pagado.setValue(monto_pagado);
    }

    private void calcular_descuento() {
        if (txtmonto_descuento.getText().trim().length() > 0) {
            monto_descuento = Double.parseDouble(txtmonto_descuento.getText());
        } else {
            monto_descuento = 0;
            txtmonto_descuento.setText(String.valueOf((int) monto_descuento));
        }
        monto_pagado = monto_total - monto_descuento;
        jFmonto_pagado.setValue(monto_pagado);
    }

    void boton_eliminar_item() {
        if (!eveJtab.getBoolean_validar_select(tblitem_venta)) {
            if (eveJtab.getBoolean_Eliminar_Fila(tblitem_venta, model_itemf)) {
                sumar_item_venta();
            }

        }
    }

    private void cargar_cantidad_servicio(int cant) {
        if (getFk_idcliente() > 0) {
            cargar_item_venta(1, cant);
        } else {
            abrir_buscar(1, "CLIENTE", txtcli_nombre);
        }
    }

    private void cargar_cantidad_producto(int cant) {
        if (getFk_idcliente() > 0) {
            cargar_item_venta(2, cant);
        } else {
            abrir_buscar(1, "CLIENTE", txtcli_nombre);
        }
    }

    private void boton_nuevo() {
        reestableser_venta();
    }

    private void abrir_buscar(int tipo, String nombre, JTextField txtbuscar) {
        vbus.setTipo_tabla(tipo);
        vbus.setNombre_tabla(nombre);
        vbus.setPre_busqueda(txtbuscar.getText());
        JDiaBuscarPersona frm = new JDiaBuscarPersona(null, true);
        frm.setVisible(true);
    }

    public static int getFk_idcliente() {
        return fk_idcliente;
    }

    public static void setFk_idcliente(int fk_idcliente) {
        FrmVenta.fk_idcliente = fk_idcliente;
    }

    public static int getFk_idconfiguracion_puntaje() {
        return fk_idconfiguracion_puntaje;
    }

    public static void setFk_idconfiguracion_puntaje(int fk_idconfiguracion_puntaje) {
        FrmVenta.fk_idconfiguracion_puntaje = fk_idconfiguracion_puntaje;
    }

    public static boolean isGenera_puntaje() {
        return genera_puntaje;
    }

    public static void setGenera_puntaje(boolean genera_puntaje) {
        FrmVenta.genera_puntaje = genera_puntaje;
    }

    public static double getCliente_total_puntaje() {
        return cliente_total_puntaje;
    }

    public static void setCliente_total_puntaje(double cliente_total_puntaje) {
        FrmVenta.cliente_total_puntaje = cliente_total_puntaje;
    }

    private void filtro_venta() {
        String filtro = "";
        filtro = filtro + filtro_estado(jCestado_emitido, jCestado_anulado,jCestado_comision);
        filtro = filtro + evefec.getIntervalo_fecha_combobox(cmbfiltro_fecha, "v.fecha_creado");
        DAOven.actualizar_tabla_venta(conn, tblventa, filtro);
        suma_monto_venta(filtro);
    }

    private void suma_monto_venta(String filtro) {
        String sql = "select \n"
                + "sum(v.monto_comision) as comision,\n"
                + "sum(v.monto_pagado) as pagado\n"
                + "from venta v,cliente c \n"
                + "where v.fk_idcliente=c.idcliente "+filtro;
        String titulo = "suma_monto_venta";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                double monto_comision=(rs.getDouble(1));
                double monto_pagado=(rs.getDouble(2));
                jFtotal_comision.setValue(monto_comision);
                jFtotal_pagado.setValue(monto_pagado);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
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

    private void generar_puntaje() {
        System.out.println("generar_puntaje:");
        setGenera_puntaje(jCgenerar_puntaje.isSelected());
        sumar_item_venta();
    }

    public FrmVenta() {
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
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtcli_nombre = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtcli_ruc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtcli_direccion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtcli_telefono = new javax.swing.JTextField();
        btnbuscar_cliente = new javax.swing.JButton();
        btnnuevo_cliente = new javax.swing.JButton();
        lbltiene_puntaje = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtcli_total_puntaje = new javax.swing.JTextField();
        jCgenerar_puntaje = new javax.swing.JCheckBox();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblservicio_categoria = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblservicio = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtprecio_venta_servicio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtbuscar_servicio = new javax.swing.JTextField();
        btncantidad_5 = new javax.swing.JButton();
        btncantidad_4 = new javax.swing.JButton();
        btncantidad_3 = new javax.swing.JButton();
        btncantidad_2 = new javax.swing.JButton();
        btncantidad_1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblproducto_categoria = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtprecio_venta_produto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtbuscar_producto = new javax.swing.JTextField();
        btncant_pro_1 = new javax.swing.JButton();
        btncant_pro_2 = new javax.swing.JButton();
        btncant_pro_3 = new javax.swing.JButton();
        btncant_pro_4 = new javax.swing.JButton();
        btncant_pro_5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblcombo_servicio = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblitem_combo_servicio = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblitem_venta = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jFmonto_total = new javax.swing.JFormattedTextField();
        jFmonto_comision = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtmonto_descuento = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jFnuevo_puntaje_cliente = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jFmonto_puntaje_cliente = new javax.swing.JFormattedTextField();
        btneliminar_item = new javax.swing.JButton();
        btnactualizar_suma = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jFmonto_pagado = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblventa = new javax.swing.JTable();
        btnimprimir_ticket = new javax.swing.JButton();
        btnanular = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        jCestado_comision = new javax.swing.JCheckBox();
        cmbfiltro_fecha = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jFtotal_comision = new javax.swing.JFormattedTextField();
        jFtotal_pagado = new javax.swing.JFormattedTextField();

        setClosable(true);
        setIconifiable(true);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
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

        btnnuevo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("DATO CLIENTE"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("CLIENTE:");

        txtcli_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_nombreKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("RUC:");

        txtcli_ruc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_rucKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("DIREC:");

        txtcli_direccion.setEditable(false);
        txtcli_direccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_direccionKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("TEL:");

        txtcli_telefono.setEditable(false);
        txtcli_telefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_telefonoKeyPressed(evt);
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

        lbltiene_puntaje.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbltiene_puntaje.setText("punto");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("PUNTAJE:");

        txtcli_total_puntaje.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtcli_total_puntaje.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jCgenerar_puntaje.setText("GENERAR");
        jCgenerar_puntaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCgenerar_puntajeActionPerformed(evt);
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
                    .addComponent(txtcli_nombre)
                    .addComponent(txtcli_direccion, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11))
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcli_ruc, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(txtcli_telefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcli_total_puntaje, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCgenerar_puntaje))
                    .addComponent(lbltiene_puntaje, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtcli_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltiene_puntaje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(txtcli_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtcli_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtcli_total_puntaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCgenerar_puntaje))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("CATEGORIA"));

        tblservicio_categoria.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblservicio_categoria.setModel(new javax.swing.table.DefaultTableModel(
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
        tblservicio_categoria.setRowHeight(25);
        tblservicio_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblservicio_categoriaMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblservicio_categoria);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("SERVICIO"));

        tblservicio.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tblservicio.setModel(new javax.swing.table.DefaultTableModel(
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
        tblservicio.setRowHeight(20);
        tblservicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblservicioMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tblservicio);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("PRECIO:");

        txtprecio_venta_servicio.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtprecio_venta_servicio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprecio_venta_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprecio_venta_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("SERVICIO:");

        txtbuscar_servicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscar_servicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_servicioKeyReleased(evt);
            }
        });

        btncantidad_5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_5.png"))); // NOI18N
        btncantidad_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_5ActionPerformed(evt);
            }
        });

        btncantidad_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_4.png"))); // NOI18N
        btncantidad_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_4ActionPerformed(evt);
            }
        });

        btncantidad_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_3.png"))); // NOI18N
        btncantidad_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_3ActionPerformed(evt);
            }
        });

        btncantidad_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_2.png"))); // NOI18N
        btncantidad_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_2ActionPerformed(evt);
            }
        });

        btncantidad_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_1.png"))); // NOI18N
        btncantidad_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 107, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btncantidad_5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btncantidad_4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btncantidad_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btncantidad_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btncantidad_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbuscar_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btncantidad_1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 114, Short.MAX_VALUE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("SERVICIO", jPanel4);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("CATEGORIA"));

        tblproducto_categoria.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblproducto_categoria.setModel(new javax.swing.table.DefaultTableModel(
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
        tblproducto_categoria.setRowHeight(25);
        tblproducto_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproducto_categoriaMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblproducto_categoria);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTO"));

        tblproducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
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
        tblproducto.setRowHeight(20);
        tblproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproductoMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblproducto);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("PRECIO:");

        txtprecio_venta_produto.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtprecio_venta_produto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprecio_venta_produto, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprecio_venta_produto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("PRODUCTO:");

        txtbuscar_producto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        btncant_pro_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_1.png"))); // NOI18N
        btncant_pro_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncant_pro_1ActionPerformed(evt);
            }
        });

        btncant_pro_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_2.png"))); // NOI18N
        btncant_pro_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncant_pro_2ActionPerformed(evt);
            }
        });

        btncant_pro_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_3.png"))); // NOI18N
        btncant_pro_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncant_pro_3ActionPerformed(evt);
            }
        });

        btncant_pro_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_4.png"))); // NOI18N
        btncant_pro_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncant_pro_4ActionPerformed(evt);
            }
        });

        btncant_pro_5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/nro_5.png"))); // NOI18N
        btncant_pro_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncant_pro_5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 100, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btncant_pro_5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btncant_pro_4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btncant_pro_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btncant_pro_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btncant_pro_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btncant_pro_1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncant_pro_2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncant_pro_3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncant_pro_4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncant_pro_5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jTabbedPane2.addTab("PRODUCTO", jPanel5);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("COMBO"));

        tblcombo_servicio.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblcombo_servicio);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("COMBO:");

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("COMBO-SERVICIO"));

        tblitem_combo_servicio.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblitem_combo_servicio);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(278, Short.MAX_VALUE))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("COMBO", jPanel6);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM VENTA"));

        tblitem_venta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(tblitem_venta);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO"));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("DESCUENTO:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("TOTAL:");

        jFmonto_total.setEditable(false);
        jFmonto_total.setBackground(new java.awt.Color(204, 204, 204));
        jFmonto_total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFmonto_comision.setEditable(false);
        jFmonto_comision.setBackground(new java.awt.Color(204, 204, 204));
        jFmonto_comision.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_comision.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_comision.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("COMISION:");

        txtmonto_descuento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtmonto_descuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_descuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_descuentoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_descuentoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel10)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFmonto_total)
                    .addComponent(jFmonto_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmonto_comision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmonto_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("PUNTAJE:"));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("GENERAR:");

        jFnuevo_puntaje_cliente.setEditable(false);
        jFnuevo_puntaje_cliente.setBackground(new java.awt.Color(204, 204, 204));
        jFnuevo_puntaje_cliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFnuevo_puntaje_cliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("MONTO:");

        jFmonto_puntaje_cliente.setEditable(false);
        jFmonto_puntaje_cliente.setBackground(new java.awt.Color(204, 204, 204));
        jFmonto_puntaje_cliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFmonto_puntaje_cliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFnuevo_puntaje_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFmonto_puntaje_cliente)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFnuevo_puntaje_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmonto_puntaje_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)))
        );

        btneliminar_item.setText("ELIMINAR ITEM");
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        btnactualizar_suma.setText("ACT");
        btnactualizar_suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_sumaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(btneliminar_item, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnactualizar_suma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btneliminar_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnactualizar_suma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("PAGAR:");

        jFmonto_pagado.setEditable(false);
        jFmonto_pagado.setBackground(new java.awt.Color(204, 204, 204));
        jFmonto_pagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0Gs"))));
        jFmonto_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_pagado.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFmonto_pagado))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertarLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnnuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnguardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jFmonto_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel17))
                        .addGap(27, 27, 27))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane2))))
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

        jTabbedPane1.addTab("DATO VENTA", jPanel1);

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblventa.setModel(new javax.swing.table.DefaultTableModel(
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
        tblventa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblventaMouseReleased(evt);
            }
        });
        tblventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblventaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblventa);

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        btnimprimir_ticket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_ticket.setText("IMPRIMIR TICKET");
        btnimprimir_ticket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ticketActionPerformed(evt);
            }
        });

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
                .addComponent(jCestado_emitido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCestado_anulado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCestado_comision)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_anulado)
                    .addComponent(jCestado_comision))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        cmbfiltro_fecha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfiltro_fecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbfiltro_fechaItemStateChanged(evt);
            }
        });

        jLabel18.setText("Fecha Filtro:");

        jFtotal_comision.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL COMISION"));
        jFtotal_comision.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_comision.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_comision.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jFtotal_pagado.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL PAGADO"));
        jFtotal_pagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_pagado.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_ticket)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnanular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(68, 68, 68)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnimprimir_ticket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jFtotal_comision, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_pagado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(542, 542, 542)
                .addComponent(btnanular, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        jTabbedPane1.addTab("FILTRO VENTA", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_venta();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_venta();
        DAOven.ancho_tabla_venta(tblventa);
        DAOser.ancho_tabla_servicio_venta(tblservicio);
        DAOpro.ancho_tabla_producto_venta(tblproducto);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblventaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblventaMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_venta();
    }//GEN-LAST:event_tblventaMouseReleased

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtcli_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_nombreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            abrir_buscar(1, "CLIENTE", txtcli_nombre);
        }
    }//GEN-LAST:event_txtcli_nombreKeyPressed

    private void txtcli_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_rucKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            abrir_buscar(2, "CLIENTE", txtcli_ruc);
        }
    }//GEN-LAST:event_txtcli_rucKeyPressed

    private void txtcli_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_direccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcli_direccionKeyPressed

    private void txtcli_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_telefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcli_telefonoKeyPressed

    private void btnbuscar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_clienteActionPerformed
        // TODO add your handling code here:
        abrir_buscar(1, "CLIENTE", txtcli_nombre);
    }//GEN-LAST:event_btnbuscar_clienteActionPerformed

    private void btnnuevo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_clienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnnuevo_clienteActionPerformed

    private void tblservicio_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblservicio_categoriaMouseReleased
        // TODO add your handling code here:
        seleccionar_servicio_categoria();
    }//GEN-LAST:event_tblservicio_categoriaMouseReleased

    private void txtbuscar_servicioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_servicioKeyReleased
        // TODO add your handling code here:
        buscar_servicio();
    }//GEN-LAST:event_txtbuscar_servicioKeyReleased

    private void tblproducto_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproducto_categoriaMouseReleased
        // TODO add your handling code here:
        seleccionar_producto_categoria();
    }//GEN-LAST:event_tblproducto_categoriaMouseReleased

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
        buscar_producto();
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void btncantidad_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_5ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_servicio(5);
    }//GEN-LAST:event_btncantidad_5ActionPerformed

    private void btncantidad_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_4ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_servicio(4);
    }//GEN-LAST:event_btncantidad_4ActionPerformed

    private void btncantidad_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_3ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_servicio(3);
    }//GEN-LAST:event_btncantidad_3ActionPerformed

    private void btncantidad_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_2ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_servicio(2);
    }//GEN-LAST:event_btncantidad_2ActionPerformed

    private void btncantidad_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_1ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_servicio(1);
    }//GEN-LAST:event_btncantidad_1ActionPerformed

    private void btncant_pro_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncant_pro_1ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(1);
    }//GEN-LAST:event_btncant_pro_1ActionPerformed

    private void btncant_pro_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncant_pro_2ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(2);
    }//GEN-LAST:event_btncant_pro_2ActionPerformed

    private void btncant_pro_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncant_pro_3ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(3);
    }//GEN-LAST:event_btncant_pro_3ActionPerformed

    private void btncant_pro_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncant_pro_4ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(4);
    }//GEN-LAST:event_btncant_pro_4ActionPerformed

    private void btncant_pro_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncant_pro_5ActionPerformed
        // TODO add your handling code here:
        cargar_cantidad_producto(5);
    }//GEN-LAST:event_btncant_pro_5ActionPerformed

    private void tblservicioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblservicioMouseReleased
        // TODO add your handling code here:
        seleccionar_servicio();
    }//GEN-LAST:event_tblservicioMouseReleased

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void txtmonto_descuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_descuentoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_descuentoKeyTyped

    private void txtmonto_descuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_descuentoKeyReleased
        // TODO add your handling code here:
        calcular_descuento();
    }//GEN-LAST:event_txtmonto_descuentoKeyReleased

    private void jCgenerar_puntajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCgenerar_puntajeActionPerformed
        // TODO add your handling code here:
        generar_puntaje();
    }//GEN-LAST:event_jCgenerar_puntajeActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
        generar_puntaje();
    }//GEN-LAST:event_formFocusGained

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        // TODO add your handling code here:
        generar_puntaje();
    }//GEN-LAST:event_formFocusLost

    private void btnactualizar_sumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_sumaActionPerformed
        // TODO add your handling code here:
        generar_puntaje();
    }//GEN-LAST:event_btnactualizar_sumaActionPerformed

    private void btnimprimir_ticketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ticketActionPerformed
        // TODO add your handling code here:
        boton_imprimir_ticket();
    }//GEN-LAST:event_btnimprimir_ticketActionPerformed

    private void tblproductoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductoMouseReleased
        // TODO add your handling code here:
        seleccionar_producto();
    }//GEN-LAST:event_tblproductoMouseReleased

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        // TODO add your handling code here:
        boton_anular_venta();
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

    private void jCestado_comisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_comisionActionPerformed
        // TODO add your handling code here:
        filtro_venta();
    }//GEN-LAST:event_jCestado_comisionActionPerformed

    private void tblventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblventaKeyReleased
        // TODO add your handling code here:
        seleccionar_tabla_venta();
    }//GEN-LAST:event_tblventaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_suma;
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btnbuscar_cliente;
    private javax.swing.JButton btncant_pro_1;
    private javax.swing.JButton btncant_pro_2;
    private javax.swing.JButton btncant_pro_3;
    private javax.swing.JButton btncant_pro_4;
    private javax.swing.JButton btncant_pro_5;
    private javax.swing.JButton btncantidad_1;
    private javax.swing.JButton btncantidad_2;
    private javax.swing.JButton btncantidad_3;
    private javax.swing.JButton btncantidad_4;
    private javax.swing.JButton btncantidad_5;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnimprimir_ticket;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_cliente;
    private javax.swing.JComboBox<String> cmbfiltro_fecha;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_comision;
    private javax.swing.JCheckBox jCestado_emitido;
    public static javax.swing.JCheckBox jCgenerar_puntaje;
    private javax.swing.JFormattedTextField jFmonto_comision;
    private javax.swing.JFormattedTextField jFmonto_pagado;
    private javax.swing.JFormattedTextField jFmonto_puntaje_cliente;
    private javax.swing.JFormattedTextField jFmonto_total;
    private javax.swing.JFormattedTextField jFnuevo_puntaje_cliente;
    private javax.swing.JFormattedTextField jFtotal_comision;
    private javax.swing.JFormattedTextField jFtotal_pagado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
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
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField3;
    public static javax.swing.JLabel lbltiene_puntaje;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tblcombo_servicio;
    private javax.swing.JTable tblitem_combo_servicio;
    private javax.swing.JTable tblitem_venta;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTable tblproducto_categoria;
    private javax.swing.JTable tblservicio;
    public static javax.swing.JTable tblservicio_categoria;
    private javax.swing.JTable tblventa;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtbuscar_servicio;
    public static javax.swing.JTextField txtcli_direccion;
    public static javax.swing.JTextField txtcli_nombre;
    public static javax.swing.JTextField txtcli_ruc;
    public static javax.swing.JTextField txtcli_telefono;
    public static javax.swing.JTextField txtcli_total_puntaje;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtmonto_descuento;
    private javax.swing.JTextField txtprecio_venta_produto;
    private javax.swing.JTextField txtprecio_venta_servicio;
    // End of variables declaration//GEN-END:variables
}
