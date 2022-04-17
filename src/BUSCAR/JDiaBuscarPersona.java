
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUSCAR;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.VISTA.FrmCompra;
import FORMULARIO.VISTA.FrmGasto;
import FORMULARIO.VISTA.FrmProducto;
import FORMULARIO.VISTA.FrmServicio;
import FORMULARIO.VISTA.FrmVenta;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class JDiaBuscarPersona extends javax.swing.JDialog {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable eveJtab = new EvenJtable();
    EvenConexion eveconn = new EvenConexion();
    private EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_palete clacolor = new cla_color_palete();
    ClaVarBuscar vbus = new ClaVarBuscar();
    private String punto_si = "TIENE PUNTAJE";
    private String punto_no = "NO TIENE PUNTO";

    private void abrir_formulario() {
        this.setTitle("BUSCAR " + vbus.getNombre_tabla());
        inicio_actualizar_buscar();
        
    }
    private void inicio_actualizar_buscar(){
        if (vbus.getTipo_tabla() == 1) {
            txtbuscar_nombre.setText(vbus.getPre_busqueda());
            actualizar_buscar(txtbuscar_nombre, "c.nombre");
            txtbuscar_nombre.grabFocus();
        }
        if (vbus.getTipo_tabla() == 2) {
            txtbuscar_ruc.setText(vbus.getPre_busqueda());
            actualizar_buscar(txtbuscar_ruc, "c.ruc");
            txtbuscar_ruc.grabFocus();
        }
        if (vbus.getTipo_tabla() == 3) {
            txtbuscar_nombre.setText(vbus.getPre_busqueda());
            actualizar_buscar(txtbuscar_nombre, "p.razon_social");
            txtbuscar_nombre.grabFocus();
        }
        if (vbus.getTipo_tabla() == 4) {
            txtbuscar_ruc.setText(vbus.getPre_busqueda());
            actualizar_buscar(txtbuscar_ruc, "p.ruc");
            txtbuscar_ruc.grabFocus();
        }
    }
    private void campo_actualizar_buscar(int tipo){
        if (vbus.getTipo_tabla() == 1 && tipo==1) {
            actualizar_buscar(txtbuscar_nombre, "c.nombre");
        }
        if (vbus.getTipo_tabla() == 2 && tipo==2) {
            actualizar_buscar(txtbuscar_ruc, "c.ruc");
        }
        if (vbus.getTipo_tabla() == 3 && tipo==1) {
            actualizar_buscar(txtbuscar_nombre, "p.razon_social");
        }
        if (vbus.getTipo_tabla() == 4 && tipo==2) {
            actualizar_buscar(txtbuscar_ruc, "p.ruc");
        }
    }
    private void actualizar_buscar(JTextField txttexto, String campo) {
        String sql = "";
        String buscar = txttexto.getText();
        int Ancho[] = {5, 70, 10, 15, 1, 1, 1, 1};
        int Ancho2[] = {5,40, 10, 15,30};
        int cant_columna = 0;
        if (vbus.getTipo_tabla() == 1 || vbus.getTipo_tabla() == 2) {
            cant_columna = 8;
            sql = "select c.idcliente as idc,(c.nombre||' '||c.apellido) as cliente,\n"
                    + "c.ruc,c.telefono,c.direccion,c.fk_idconfiguracion_puntaje as idcp,  \n"
                    + "case when c.tiene_puntaje=true then '" + punto_si + "' else '" + punto_no + "' end as tiene_puntaje,\n"
                    + "TRIM(to_char(c.total_puntaje,'99')) as total_puntaje "
                    + "from cliente c \n"
                    + "where c.activo=true\n"
                    + "and " + campo + " ilike'%" + buscar + "%' "
                    + "order by c.nombre desc;";
        }
        if (vbus.getTipo_tabla() == 3 || vbus.getTipo_tabla() == 4) {
            cant_columna = 4;
            sql = "select p.idproveedor as idp,\n"
                    + "(p.razon_social) as proveedor,\n"
                    + "p.ruc as ruc,\n"
                    + "p.telefono as telefono,p.direccion \n"
                    + "from proveedor p \n"
                    + "where p.activo=true "
                    + "and " + campo + " ilike'%" + buscar + "%' "
                    + "order by p.razon_social desc;";
        }
        if (cant_columna == 8) {
            eveconn.Select_cargar_jtable(conn, sql, tblbuscar);
            eveJtab.setAnchoColumnaJtable(tblbuscar, Ancho);
            eveJtab.ocultar_columna(tblbuscar, 4);
            eveJtab.ocultar_columna(tblbuscar, 5);
            eveJtab.ocultar_columna(tblbuscar, 6);
            eveJtab.ocultar_columna(tblbuscar, 7);
        }
        if (cant_columna == 4) {
            eveconn.Select_cargar_jtable(conn, sql, tblbuscar);
            eveJtab.setAnchoColumnaJtable(tblbuscar, Ancho2);
        }
    }

    void seleccionar_buscar() {
        if (tblbuscar.getSelectedRow() >= 0) {
            if (vbus.getTipo_tabla() == 1 || vbus.getTipo_tabla() == 2) {
                int idc = eveJtab.getInt_select_id(tblbuscar);
                String cliente = eveJtab.getString_select(tblbuscar, 1);
                String ruc = eveJtab.getString_select(tblbuscar, 2);
                String telefono = eveJtab.getString_select(tblbuscar, 3);
                String direccion = eveJtab.getString_select(tblbuscar, 4);
                int idconfiguracion_puntaje = eveJtab.getInt_select(tblbuscar, 5);
                String tiene_puntaje = eveJtab.getString_select(tblbuscar, 6);
                String total_puntaje = eveJtab.getString_select(tblbuscar, 7);
                FrmVenta.txtcli_nombre.setText(cliente);
                FrmVenta.txtcli_ruc.setText(ruc);
                FrmVenta.txtcli_telefono.setText(telefono);
                FrmVenta.txtcli_direccion.setText(direccion);
                FrmVenta.lbltiene_puntaje.setText(tiene_puntaje);
                FrmVenta.txtcli_total_puntaje.setText(total_puntaje);
                if (tiene_puntaje.equals(punto_si)) {
                    FrmVenta.lbltiene_puntaje.setForeground(Color.ORANGE);
                    FrmVenta.txtcli_total_puntaje.setBackground(Color.ORANGE);
                    FrmVenta.setGenera_puntaje(true);
                    FrmVenta.jCgenerar_puntaje.setVisible(true);
                    FrmVenta.jCgenerar_puntaje.setSelected(true);
                }
                if (tiene_puntaje.equals(punto_no)) {
                    FrmVenta.lbltiene_puntaje.setForeground(Color.RED);
                    FrmVenta.txtcli_total_puntaje.setBackground(Color.RED);
                    FrmVenta.setGenera_puntaje(false);
                    FrmVenta.jCgenerar_puntaje.setVisible(false);
                    FrmVenta.jCgenerar_puntaje.setSelected(false);
                }
                FrmVenta.setCliente_total_puntaje(Double.parseDouble(total_puntaje));
                FrmVenta.setFk_idcliente(idc);
                FrmVenta.setFk_idconfiguracion_puntaje(idconfiguracion_puntaje);
            }
            if (vbus.getTipo_tabla() == 3 || vbus.getTipo_tabla() == 4) {
                int idproveedor = eveJtab.getInt_select_id(tblbuscar);
                String proveedor = eveJtab.getString_select(tblbuscar, 1);
                String ruc = eveJtab.getString_select(tblbuscar, 2);
                String telefono = eveJtab.getString_select(tblbuscar, 3);
                String direccion = eveJtab.getString_select(tblbuscar, 4);
                FrmCompra.setFk_idproveedor(idproveedor);
                FrmCompra.txtprov_nombre.setText(proveedor);
                FrmCompra.txtprov_ruc.setText(ruc);
                FrmCompra.txtprov_direccion.setText(direccion);
                FrmCompra.txtprov_telefono.setText(telefono);
            }

        }
    }

    void enfocar_campo() {
        if (vbus.getTipo_tabla() == 1 || vbus.getTipo_tabla() == 2) {
            FrmVenta.tblservicio_categoria.changeSelection(0, 0, false, false);
            FrmVenta.tblservicio_categoria.grabFocus();
        }
        if (vbus.getTipo_tabla() == 3 || vbus.getTipo_tabla() == 4) {
//            FrmVenta.tblservicio_categoria.changeSelection(0, 0, false, false);
//            FrmVenta.tblservicio_categoria.grabFocus();
        }
    }

    public JDiaBuscarPersona(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtbuscar_nombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbuscar = new javax.swing.JTable();
        btnselect_salir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtbuscar_ruc = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("NOMBRE:");

        txtbuscar_nombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtbuscar_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyReleased(evt);
            }
        });

        tblbuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblbuscar.setModel(new javax.swing.table.DefaultTableModel(
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
        tblbuscar.setRowHeight(23);
        tblbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblbuscarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblbuscarMouseReleased(evt);
            }
        });
        tblbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblbuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblbuscarKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblbuscar);

        btnselect_salir.setText("SELECCIONAR Y SALIR");
        btnselect_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnselect_salirActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("RUC:");

        txtbuscar_ruc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtbuscar_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_rucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_rucKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnselect_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnselect_salir))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
        campo_actualizar_buscar(1);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void txtbuscar_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_flecha_abajo(evt, tblbuscar);
    }//GEN-LAST:event_txtbuscar_nombreKeyPressed

    private void tblbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblbuscarKeyReleased
        // TODO add your handling code here:
        seleccionar_buscar();
    }//GEN-LAST:event_tblbuscarKeyReleased

    private void tblbuscarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscarMouseReleased
        // TODO add your handling code here:
        seleccionar_buscar();
    }//GEN-LAST:event_tblbuscarMouseReleased

    private void btnselect_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnselect_salirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnselect_salirActionPerformed

    private void tblbuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblbuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_buscar();
            this.dispose();
            enfocar_campo();
        }
    }//GEN-LAST:event_tblbuscarKeyPressed

    private void tblbuscarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscarMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            seleccionar_buscar();
            this.dispose();
            enfocar_campo();
        }
    }//GEN-LAST:event_tblbuscarMousePressed

    private void txtbuscar_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_rucKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_flecha_abajo(evt, tblbuscar);
    }//GEN-LAST:event_txtbuscar_rucKeyPressed

    private void txtbuscar_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_rucKeyReleased
        // TODO add your handling code here:
        campo_actualizar_buscar(2);
    }//GEN-LAST:event_txtbuscar_rucKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDiaBuscarPersona dialog = new JDiaBuscarPersona(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnselect_salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblbuscar;
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_ruc;
    // End of variables declaration//GEN-END:variables
}
