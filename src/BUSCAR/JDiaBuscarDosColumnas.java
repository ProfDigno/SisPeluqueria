
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
import FORMULARIO.VISTA.FrmGasto;
import FORMULARIO.VISTA.FrmProducto;
import FORMULARIO.VISTA.FrmServicio;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class JDiaBuscarDosColumnas extends javax.swing.JDialog {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable eveJtab = new EvenJtable();
    EvenConexion eveconn = new EvenConexion();
    private EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_palete clacolor = new cla_color_palete();
    private ClaVarBuscar vbus = new ClaVarBuscar();

    private void abrir_formulario() {
        this.setTitle("BUSCAR " + vbus.getNombre_tabla());
        txtbuscar_nombre.setText(vbus.getPre_busqueda());
        actualizar_buscar();
    }

    private void actualizar_buscar() {
        String sql = "";
        String buscar = txtbuscar_nombre.getText();
        int Ancho[] = {20, 80};
        int cant_columna=0;
        if (vbus.getTipo_tabla() == 1) {
            cant_columna=2;
            sql = "select idproducto_categoria as idpc,nombre  \n"
                    + "from producto_categoria "
                    + "where activo=true\n"
                    + "and nombre ilike'%" + buscar + "%' "
                    + "order by 2 desc;";
        }
         if (vbus.getTipo_tabla() == 2) {
            cant_columna=2;
            sql = "select idproducto_unidad as idpu,nombre  \n"
                    + "from producto_unidad "
                    + "where activo=true\n"
                    + "and nombre ilike'%" + buscar + "%' "
                    + "order by 2 desc;";
        }
         if (vbus.getTipo_tabla() == 3) {
            cant_columna=2;
            sql = "select idservicio_categoria as idsc,nombre  \n"
                    + "from servicio_categoria "
                    + "where activo=true\n"
                    + "and nombre ilike'%" + buscar + "%' "
                    + "order by 2 desc;";
        }
         if (vbus.getTipo_tabla() == 4) {
            cant_columna=2;
            sql = "select idfuncionario as idf,nombre  \n"
                    + "from funcionario "
                    + "where activo=true\n"
                    + "and nombre ilike'%" + buscar + "%' "
                    + "order by 2 desc;";
        }
         if (vbus.getTipo_tabla() == 5) {
            cant_columna=2;
            sql = "select idgasto_tipo as idt,nombre  \n"
                    + "from gasto_tipo "
                    + "where activo=true\n"
                    + "and nombre ilike'%" + buscar + "%' "
                    + "order by 2 desc;";
        }
        if (cant_columna == 2) {
            eveconn.Select_cargar_jtable(conn, sql, tblbuscar);
            eveJtab.setAnchoColumnaJtable(tblbuscar, Ancho);
        }
    }

    void seleccionar_buscar() {
        if (tblbuscar.getSelectedRow() >= 0) {
            int id = eveJtab.getInt_select_id(tblbuscar);
            String nombre = eveJtab.getString_select(tblbuscar, 1);
            if (vbus.getTipo_tabla() == 1) {
                FrmProducto.txt20categoria.setText(nombre);
                FrmProducto.setFk_idproducto_categoria(id);
            }
            if (vbus.getTipo_tabla() == 2) {
                FrmProducto.txt21unidad.setText(nombre);
                FrmProducto.setFk_idproducto_unidad(id);
            }
            if (vbus.getTipo_tabla() == 3) {
                FrmServicio.txtbuscar_servicio_categoria.setText(nombre);
                FrmServicio.setFk_idservicio_categoria(id);
            }
            if (vbus.getTipo_tabla() == 4) {
                FrmServicio.txtbuscar_funcionario.setText(nombre);
                FrmServicio.setFk_idfuncionario(id);
            }
            if (vbus.getTipo_tabla() == 5) {
                FrmGasto.txtbuscar_gasto_tipo.setText(nombre);
                FrmGasto.setFk_idgasto_tipo(id);
            }
        }
    }

    void enfocar_campo() {
        if (vbus.getTipo_tabla() == 1) {
            FrmProducto.txt21unidad.grabFocus();
        }
       if (vbus.getTipo_tabla() == 2) {
            FrmProducto.txt8precio_venta.grabFocus();
        }
       if (vbus.getTipo_tabla() == 3) {
            FrmServicio.txtbuscar_funcionario.grabFocus();
        }
       if (vbus.getTipo_tabla() == 4) {
            FrmServicio.txtprecio_venta.grabFocus();
        }
       if (vbus.getTipo_tabla() == 5) {
            FrmGasto.txtmonto_gasto.grabFocus();
        }
    }

    public JDiaBuscarDosColumnas(java.awt.Frame parent, boolean modal) {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("BUSCAR:");

        txtbuscar_nombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtbuscar_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyReleased(evt);
            }
        });

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnselect_salir))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        actualizar_buscar();
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
            java.util.logging.Logger.getLogger(JDiaBuscarDosColumnas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarDosColumnas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarDosColumnas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarDosColumnas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDiaBuscarDosColumnas dialog = new JDiaBuscarDosColumnas(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblbuscar;
    private javax.swing.JTextField txtbuscar_nombre;
    // End of variables declaration//GEN-END:variables
}
