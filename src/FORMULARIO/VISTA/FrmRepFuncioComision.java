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
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.producto_categoria;
import FORMULARIO.ENTIDAD.producto_unidad;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class FrmRepFuncioComision extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenJTextField evejte = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private producto ENTpro = new producto();
    private DAO_producto DAOpro = new DAO_producto();
    private BO_producto BOpro = new BO_producto();
    private EvenJTextField evejtf = new EvenJTextField();
    private producto_categoria ENTpc = new producto_categoria();
    private DAO_producto_categoria DAOpc = new DAO_producto_categoria();
    private producto_unidad ENTpu = new producto_unidad();
    private DAO_producto_unidad DAOpu = new DAO_producto_unidad();
    private DAO_funcionario DAOfun = new DAO_funcionario();
    private json_producto jsonp = new json_producto();
    private Global_datos gda = new Global_datos();
    private EvenFecha evefec = new EvenFecha();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    Connection conn = ConnPostgres.getConnPosgres();
    private String nombre_formulario = "FUNCIONARIO COMISION";

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        evefec.cargar_combobox_intervalo_fecha(cmbfiltro_fecha);
        actualizar_funcionario();
        boton_imprimir(false);
    }

    private void boton_imprimir(boolean imprimir) {
        String filtro = "";
        String filtro_stock = "";
        if (tblfuncionario.getSelectedRow() >= 0) {
            int idfuncionario = eveJtab.getInt_select_id(tblfuncionario);
            String categeria = " and f.idfuncionario=" + idfuncionario;
            filtro = filtro + categeria;
        }
        filtro = filtro + evefec.getIntervalo_fecha_combobox(cmbfiltro_fecha, "v.fecha_creado");
        suma_total_comision(filtro);
        if(imprimir){
            DAOfun.imprimir_rep_funcionario_comision(conn, filtro);
        }
    }

    private void actualizar_funcionario() {
        String sql = "select f.idfuncionario as idf, \n"
                + "(f.nombre||' '||f.apellido) as funcionario\n"
                + "from funcionario f \n"
                + "where f.activo=true\n"
                + "order by 2 asc;";
        eveconn.Select_cargar_jtable(conn, sql, tblfuncionario);
        ancho_tabla_producto_categoria(tblfuncionario);
    }

    private void suma_total_comision( String filtro) {
        String titulo = "suma_total_inventario";
        double tt_servicio = 0;
         double comision = 0;
          double ganancia = 0;
        String sql = "select \n"
                + "sum(iv.precio_venta*iv.cantidad) as tt_servicio,\n"
                + "sum(iv.monto_comision) as comision,\n"
                + "sum((iv.precio_venta*iv.cantidad)-iv.monto_comision) as ganancia\n"
                + "from venta v,cliente c,item_venta iv,funcionario f  \n"
                + "where v.fk_idcliente=c.idcliente \n"
                + "and v.idventa=iv.fk_idventa\n"
                + "and iv.fk_idfuncionario=f.idfuncionario \n"
                + "and (v.estado='"+gda.getEstado_emitido()+"' or v.estado='"+gda.getEstado_comision()+"') \n"
                + "and iv.es_servicio=true " + filtro;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                tt_servicio = rs.getDouble(1);
                comision = rs.getDouble(2);
                ganancia = rs.getDouble(3);
                jFsum_servicio.setValue(tt_servicio);
                jFsum_comision.setValue(comision);
                jFsum_ganancia.setValue(ganancia);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    public void ancho_tabla_producto_categoria(JTable tbltabla) {
        int Ancho[] = {10, 90};
        eveJtab.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public FrmRepFuncioComision() {
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
        tblfuncionario = new javax.swing.JTable();
        btncategoria_todos = new javax.swing.JButton();
        btnimprimir = new javax.swing.JButton();
        jFsum_servicio = new javax.swing.JFormattedTextField();
        cmbfiltro_fecha = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jFsum_ganancia = new javax.swing.JFormattedTextField();
        jFsum_comision = new javax.swing.JFormattedTextField();

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btncategoria_todos)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncategoria_todos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnimprimir.setText("IMPRIMIR ");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        jFsum_servicio.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL SERVICIO"));
        jFsum_servicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFsum_servicio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsum_servicio.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        cmbfiltro_fecha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfiltro_fecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbfiltro_fechaItemStateChanged(evt);
            }
        });

        jLabel18.setText("Fecha Filtro:");

        jFsum_ganancia.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL GANANCIA"));
        jFsum_ganancia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFsum_ganancia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsum_ganancia.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        jFsum_comision.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL COMISION"));
        jFsum_comision.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFsum_comision.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsum_comision.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnimprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFsum_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbfiltro_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jFsum_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFsum_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFsum_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jFsum_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jFsum_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
        ancho_tabla_producto_categoria(tblfuncionario);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btncategoria_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncategoria_todosActionPerformed
        // TODO add your handling code here:
        actualizar_funcionario();
        boton_imprimir(false);
    }//GEN-LAST:event_btncategoria_todosActionPerformed

    private void tblfuncionarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblfuncionarioMouseReleased
        // TODO add your handling code here:
        boton_imprimir(false);
    }//GEN-LAST:event_tblfuncionarioMouseReleased

    private void cmbfiltro_fechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbfiltro_fechaItemStateChanged
        // TODO add your handling code here:
         boton_imprimir(false);
    }//GEN-LAST:event_cmbfiltro_fechaItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncategoria_todos;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JComboBox<String> cmbfiltro_fecha;
    private javax.swing.ButtonGroup gru_cv;
    private javax.swing.JFormattedTextField jFsum_comision;
    private javax.swing.JFormattedTextField jFsum_ganancia;
    private javax.swing.JFormattedTextField jFsum_servicio;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblfuncionario;
    // End of variables declaration//GEN-END:variables
}
