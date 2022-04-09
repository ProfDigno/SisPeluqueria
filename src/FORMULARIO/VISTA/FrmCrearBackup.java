/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.LOCAL.VariablesBD;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Pc
 */
public class FrmCrearBackup extends javax.swing.JInternalFrame {

    private static String informebackup;
    private boolean estadoTiempo;
    private Timer tiempo;
    private int contartiempo;
     private int max_contartiempo=20;
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec = new EvenFecha();
    EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    private static VariablesBD var = new VariablesBD();
    private static String fecha;
    private static String nombre_backup;
    boolean hab_realizar_backup = true;
    private static boolean sin_error_backup = true;

    public static void realizaBackup(VariablesBD var) throws IOException, InterruptedException {
        final List<String> comandos = new ArrayList<String>();
        comandos.add(var.getPsdirec_dump());
        comandos.add("-h");
        comandos.add(var.getPsLocalhost());     //ou  comandos.add("192.168.0.1"); 
        comandos.add("-p");
        comandos.add(var.getPsPort());
        comandos.add("-U");
        comandos.add(var.getPsUsuario());
        comandos.add("-F");
        comandos.add("c");
        comandos.add("-b");
        comandos.add("-v");
        comandos.add("-f");
        comandos.add(var.getPsdirec_backup() + nombre_backup);   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
        comandos.add(var.getPsNomBD());
        ProcessBuilder pb = new ProcessBuilder(comandos);
        pb.environment().put("PGPASSWORD", var.getPsContrasena());      //Somente coloque sua senha    
        informebackup = informebackup + "ARCHIVO:"+fecha+"\n";
        try {
            final Process process = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            informebackup = informebackup + "###INFORME DE BACKUP###\n";
            while (line != null) {
                System.err.println(line);
                informebackup = informebackup + line + "\r\n";
                line = r.readLine();
            }
            r.close();
            process.waitFor();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
            informebackup = informebackup + comandos + e + "\r\n";
            sin_error_backup = false;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            informebackup = informebackup + comandos + ie + "\r\n";
            sin_error_backup = false;
        }
    }

    private void cargar_textArea(JTextArea txtarea, String text) {
        txtarea.setText(txtarea.getText() + text + "\r\n");
        txtarea.repaint();
        DefaultCaret caret = (DefaultCaret) txtarea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    void abrir() {
        this.setTitle("BACKUP");
        VariablesBD var = new VariablesBD();
        evetbl.centrar_formulario_internalframa(this);
        fecha = evefec.getString_formato_fecha();
        nombre_backup=var.getPsnombre_backup();
        nombre_backup = nombre_backup + fecha;
        nombre_backup = nombre_backup + ".backup";
        iniciarTiempo();
    }

    void iniciarTiempo() {
        estadoTiempo = true;
        progreso_tiempo.setMaximum(max_contartiempo);
        tiempo = new Timer();
        //le asignamos una tarea al timer
        tiempo.schedule(new FrmCrearBackup.clasetimer(), 0, 1000 * 1);
        System.out.println("Timer Iniciado backup");
    }

    void pararTiempos() {
        if (estadoTiempo) {
            tiempo.cancel();
            System.out.println("Timer parado backup");
        }
    }
    public boolean getBoolean_existe_archivo_dump(VariablesBD var) {
        File file2 = new File(var.getPsdirec_dump());
        File file3 = new File(var.getPsdirec_backup());
        if (file2.exists()) {
            System.out.println(file2 + " Exists :"+var.getPsdirec_dump());
        } else {
            System.out.println(file2 + " Does not exists :"+var.getPsdirec_dump());
            return false;
        }
        if (file3.exists()) {
            System.out.println(file2 + " Exists:"+var.getPsdirec_backup());
        } else {
            System.out.println(file2 + " Does not exists:"+var.getPsdirec_backup());
            return false;
        }
        return true;
    }
    class clasetimer extends TimerTask {

        public void run() {
            contartiempo++;
            if (hab_realizar_backup) {
                if (contartiempo == 2) {
                    cargar_textArea(txtenviobackup, "####--INICIO BACKUP ESPERE...--####");
                }
                if (contartiempo == 4) {
                    try {
                        if (getBoolean_existe_archivo_dump(var)) {
                            realizaBackup(var);
                        }else{
                            cargar_textArea(txtenviobackup, "####---NO SE ENCONTRO LA RUTA DUMP O DE BACKUP---####");
                            cargar_textArea(txtenviobackup, var.getPsdirec_dump());
                            cargar_textArea(txtenviobackup,var.getPsdirec_backup());
                            informebackup="ERROR AL CREAR BACKUP";
                            contartiempo=21;
                        }
                        cargar_textArea(txtenviobackup, informebackup);
                    } catch (IOException e) {
                        e.printStackTrace();
                        sin_error_backup = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        sin_error_backup = false;
                    }

                }
                if (contartiempo == 9) {
                    cargar_textArea(txtenviobackup, "####--INSERTADO FECHA BACKUP--####");
                }
                if (contartiempo == 10) {
                    cargar_textArea(txtenviobackup, "LOCALHOST:" + var.getPsLocalhost());
                }
                if (contartiempo == 11) {
                    cargar_textArea(txtenviobackup, "ARCHIVO:" + var.getPsnombre_backup());
                }
                if (contartiempo == 12) {
                    cargar_textArea(txtenviobackup, "####---backup Terminado---####");
                    if (sin_error_backup) {
//                        bBO.update_backup_creado_hoy();
                    }
                }
                if (contartiempo == max_contartiempo) {
                    cerrar_formulario();
                    pararTiempos();
                }
                if (contartiempo >= 25) {
                    progreso_tiempo.setValue(contartiempo);
                    cerrar_formulario();
                    pararTiempos();
                }
            } else {
                if (contartiempo == 5) {
                    cargar_textArea(txtenviobackup, "####---NO HAY DATOS DE DIRECCION PARA BACKUP---####");
                }
                if (contartiempo == 10) {
                    cerrar_formulario();
                    pararTiempos();
                }
            }
            progreso_tiempo.setValue(contartiempo);
        }
    }

    void cerrar_formulario() {
        this.dispose();
    }

    public FrmCrearBackup() {
        initComponents();
        abrir();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtenviobackup = new javax.swing.JTextArea();
        progreso_tiempo = new javax.swing.JProgressBar();

        setClosable(true);
        setTitle("CREAR BACKUP");

        txtenviobackup.setColumns(20);
        txtenviobackup.setRows(5);
        jScrollPane1.setViewportView(txtenviobackup);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addComponent(progreso_tiempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progreso_tiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JProgressBar progreso_tiempo;
    private javax.swing.JTextArea txtenviobackup;
    // End of variables declaration//GEN-END:variables
}
