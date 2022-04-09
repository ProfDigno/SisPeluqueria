/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BASEDATO;

import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Digno
 */
public class EvenConexion {

    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
//    DefaultListModel model_lista;

    public EvenConexion() {

    }

    public int getInt_ultimoID_mas_uno(Connection conn, String tabla, String id) {
        String titulo = "getInt_ultimoID";
        int getid = 1;
        String sql = "select max(" + id + "+1) as getid from " + tabla + " ";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                getid = rs.getInt("getid");
                if(getid==0){
                    getid=1;
                }
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return getid;
    }
 public String getInt_ultimo_ORDEN_mas_uno(Connection conn, String tabla, String id) {
        String titulo = "getInt_ultimoID";
        int getid = 1;
        String sql = "select max(" + id + "+1) as getid from " + tabla + " ";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                getid = rs.getInt("getid");
                if(getid==0){
                    getid=1;
                }
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return String.valueOf(getid);
    }
    public int getInt_ultimoID_max(Connection conn, String tabla, String id) {
        String titulo = "getInt_ultimoID";
        int getid = 0;
        String sql = "select max(" + id + ") as getid from " + tabla + " ";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                getid = rs.getInt("getid");
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return getid;
    }
    public int getInt_idactivo(Connection conn, String tabla, String id) {
        String titulo = "getInt_idactivo";
        int getid = 0;
        String sql = "select (" + id + ") as getid from " + tabla + " "
                + "where activo=true;";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                getid = rs.getInt("getid");
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return getid;
    }
    public ResultSet getResulsetSQL(Connection conn, String sql, String titulo) {
        ResultSet rs = null;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            evmen.Imprimir_serial_sql(sql, titulo);
        } catch (Exception e) {
            evmen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return rs;
    }

    public String getString_contar_conexiones(Connection conn) {
        //SELECT pg_terminate_backend(pid) FROM pg_stat_activity
        String titulo = "getString_contar_conexiones";
        String sql = "select count(*) as cantidad from pg_stat_activity;";
        String cantidad = "0";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                cantidad = rs.getString("cantidad");
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return cantidad;
    }

    public double getdouble_sql_suma(Connection conn, String sql) {
        String titulo = "getdouble_sql_suma";
        double suma = 0;
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                suma = rs.getDouble(1);
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return suma;
    }

    public void Select_cargar_jtable(Connection conn, String sql, JTable tabla) {
        String titulo = "Select_cargar_jtable";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            tabla.setModel(DbUtils.resultSetToTableModel(rs));
            System.out.println("Select_cargar_jtable SQL:\n" + sql);
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
    }
    /**
     * 
     * @param conn
     * @param txtbuscar
     * @param jLista
     * @param tabla
     * @param campoBusca
     * @param campoMostrar
     * @param filtro 
     */
    public void buscar_cargar_condicion_Jlista(Connection conn, JTextField txtbuscar, JList jLista, String tabla, String campoBusca, String campoMostrar, String filtro) {
        if (txtbuscar.getText().trim().length() > 0) {
            String titulo = "buscar_cargar_Jlista";
            String buscar = txtbuscar.getText();
            String sql = "select " + campoMostrar + " from " + tabla + " "
                    + "where " + campoBusca + " ilike'%" + buscar + "%' " + filtro
                    + " order by " + campoBusca + " desc limit 4";
            try {
                ResultSet rs = getResulsetSQL(conn, sql, titulo);
                int cant = 0;
                DefaultListModel model_lista = new DefaultListModel();
                jLista.setModel(model_lista);
                model_lista.removeAllElements();
                while (rs.next()) {
                    String lista = rs.getString(1);
                    System.out.println("Lista:" + lista);
                    model_lista.addElement(lista);
                    cant++;
                }
                if (cant >= 1) {
                    jLista.setVisible(true);
                } else {
                    jLista.setVisible(false);
                }
            } catch (Exception e) {
                evmen.mensaje_error(e, sql, titulo);
            }
        } else {
            jLista.setVisible(false);
        }
    }
    /**
     * 
     * @param conn
     * @param txtbuscar
     * @param jLista
     * @param tabla
     * @param campoBusca
     * @param campoMostrar
     * @param limite 
     */
    public void buscar_cargar_Jlista(Connection conn, JTextField txtbuscar, JList jLista, String tabla, String campoBusca, String campoMostrar, int limite) {
        if (txtbuscar.getText().trim().length() > 0) {
            String titulo = "buscar_cargar_Jlista";
            String buscar = txtbuscar.getText();
            String sql = "select " + campoMostrar + " from " + tabla + " "
                    + "where " + campoBusca + " ilike'%" + buscar + "%' "
                    + "order by 1 desc limit " + limite;
            try {
                ResultSet rs = getResulsetSQL(conn, sql, titulo);
                int cant = 0;
                DefaultListModel model_lista = new DefaultListModel();
                jLista.setModel(model_lista);
                model_lista.removeAllElements();
                while (rs.next()) {
                    String lista = rs.getString(1);
                    System.out.println("Lista:" + lista);
                    model_lista.addElement(lista);
                    cant++;
                }
                if (cant >= 1) {
                    jLista.setVisible(true);
                } else {
//                    jLista.setVisible(false);
                }
            } catch (Exception e) {
                evmen.mensaje_error(e, sql, titulo);
            }
        } else {
//            jLista.setVisible(false);
        }
    }

    public int getInt_seleccionar_JLista(Connection conn, JTextField txtbuscar, JList jLista,boolean visible, String tabla, String campoNombre, String campoid) {
        int fila_ruc = jLista.getSelectedIndex();
        int getcampo = 0;
        if (fila_ruc >= 0) {
            String titulo = "getInt_seleccionar_JLista";
            String seleccion = jLista.getSelectedValue().toString();
            String sql = "select * from " + tabla + " "
                    + "where " + campoNombre + "='" + seleccion + "' "
                    + "";
            try {
                ResultSet rs = getResulsetSQL(conn, sql, titulo);
                DefaultListModel model_lista = new DefaultListModel();
                jLista.setModel(model_lista);
                model_lista.removeAllElements();
                if (rs.next()) {
                    getcampo = rs.getInt(campoid);
                    txtbuscar.setText(rs.getString(campoNombre));
                    jLista.setVisible(visible);
                }
            } catch (Exception e) {
                evmen.mensaje_error(e, sql, titulo);
            }
        }
        return getcampo;
    }

    public int getInt_Solo_seleccionar_JLista(Connection conn, JList jLista, String tabla, String campoNombre, String campoid,boolean ocultar) {
        int fila_ruc = jLista.getSelectedIndex();
        int getcampo = 0;
        if (fila_ruc >= 0) {
            String titulo = "getInt_seleccionar_JLista";
            String seleccion = jLista.getSelectedValue().toString();
            String sql = "select * from " + tabla + " "
                    + "where " + campoNombre + "='" + seleccion + "' "
                    + "";
            try {
                ResultSet rs = getResulsetSQL(conn, sql, titulo);
                DefaultListModel model_lista = new DefaultListModel();
                jLista.setModel(model_lista);
                model_lista.removeAllElements();
                if (rs.next()) {
                    getcampo = rs.getInt(campoid);
                    jLista.setVisible(ocultar);
                }
            } catch (Exception e) {
                evmen.mensaje_error(e, sql, titulo);
            }
        }
        return getcampo;
    }

    public void SQL_execute_libre(Connection conn, String sql) {
        String titulo = "SQL_execute_libre";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            evmen.Imprimir_serial_sql(sql, titulo);
        } catch (Exception e) {
            evmen.mensaje_error_libre(e, sql, titulo);
        }
    }

    public String getMes_de_AnoSemana(Connection conn, String fecha, String tabla, int ano, int semana) {
        String mes = "sin registro";
        String titulo = "getMes_de_AnoSemana";
        String sql = "select \n"
                + "CASE  \n"
                + "WHEN date_part('month', " + fecha + ")=1 THEN 'ENERO' \n"
                + "WHEN date_part('month', " + fecha + ")=2 THEN 'FEBRERO' \n"
                + "WHEN date_part('month', " + fecha + ")=3 THEN 'MARZO' \n"
                + "WHEN date_part('month', " + fecha + ")=4 THEN 'ABRIL' \n"
                + "WHEN date_part('month', " + fecha + ")=5 THEN 'MAYO' \n"
                + "WHEN date_part('month', " + fecha + ")=6 THEN 'JUNIO' \n"
                + "WHEN date_part('month', " + fecha + ")=7 THEN 'JULIO'\n"
                + "WHEN date_part('month', " + fecha + ")=8 THEN 'AGOSTO'\n"
                + "WHEN date_part('month', " + fecha + ")=9 THEN 'SETIEMBRE'\n"
                + "WHEN date_part('month', " + fecha + ")=10 THEN 'OCTUBRE'\n"
                + "WHEN date_part('month', " + fecha + ")=11 THEN 'NOVIEMBRE'\n"
                + "WHEN date_part('month', " + fecha + ")=12 THEN 'DICIEMBRE' \n"
                + "ELSE 'sin mes' END as mes\n"
                + "from " + tabla + " \n"
                + "where  date_part('year'," + fecha + ")=" + ano + " \n"
                + "and date_part('week'," + fecha + ")=" + semana + " \n"
                + "group by 1 ";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                mes = rs.getString(1);
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return mes;
    }

    public int getInt_mes_actual(Connection conn) {
        int mes = 0;
        String titulo = "getInt_mes_actual";
        String sql = "SELECT date_part('month',TIMESTAMP 'now()') as mes;";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                mes = rs.getInt(1);
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return mes;
    }
    public int getInt_ano_actual(Connection conn) {
        int ano = 0;
        String titulo = "getInt_mes_actual";
        String sql = "SELECT date_part('year',TIMESTAMP 'now()') as ano;";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                ano = rs.getInt(1);
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return ano;
    }
    public int getInt_semana_actual(Connection conn) {
        int semana = 0;
        String titulo = "getInt_mes_actual";
        String sql = "SELECT date_part('week',TIMESTAMP 'now()') as sem;";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                semana = rs.getInt(1);
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return semana;
    }
    public String getString_mes_actual(Connection conn) {
        String mes = "sin registro";
        String titulo = "getString_mes_actual";
        String sql = "select \n"
                + "CASE  \n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=1 THEN 'ENERO' \n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=2 THEN 'FEBRERO' \n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=3 THEN 'MARZO' \n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=4 THEN 'ABRIL' \n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=5 THEN 'MAYO' \n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=6 THEN 'JUNIO' \n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=7 THEN 'JULIO'\n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=8 THEN 'AGOSTO'\n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=9 THEN 'SETIEMBRE'\n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=10 THEN 'OCTUBRE'\n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=11 THEN 'NOVIEMBRE'\n"
                + "WHEN date_part('month', TIMESTAMP 'now()')=12 THEN 'DICIEMBRE' \n"
                + "ELSE 'sin mes' END as mes; ";
        try {
            ResultSet rs = getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                mes = rs.getString(1);
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, sql, titulo);
        }
        return mes;
    }
}
