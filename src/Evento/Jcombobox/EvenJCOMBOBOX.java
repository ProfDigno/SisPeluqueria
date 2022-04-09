/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Jcombobox;

import BASEDATO.EvenConexion;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class EvenJCOMBOBOX {
    Connection conn = null;
ResultSet rs = null;
PreparedStatement pst = null;
EvenConexion eveconn = new EvenConexion();
EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
//FunMensaje fm=new FunMensaje();
//SQLprepar sqlpre =new SQLprepar();
int limite=2000;
int rowCampo=0;
int cantidad_vectores=13;
//String idcampo0[]=new String[limite];
String idcampo1[]=new String[limite];
String idcampo2[]=new String[limite];
String idcampo3[]=new String[limite];
String idcampo4[]=new String[limite];
String idcampo5[]=new String[limite];
String idcampo6[]=new String[limite];
String idcampo7[]=new String[limite];
String idcampo8[]=new String[limite];
String idcampo9[]=new String[limite];
String idcampo10[]=new String[limite];
String idcampo11[]=new String[limite];
String idcampo12[]=new String[limite];
String idcampo13[]=new String[limite];
/**
 * es para cargar un jcombobox con la base de dadotos en donde debemos poner el primero en nombre del campo 
 * a mostrar y segundo el campo con el id que vamos usar en la relacion
 * la primerera fila mostrara ----------------
 * @param conn la conexion avitual
 * @param sql es la consulta sql con la sintaxsis de (select * from nombreTabla order by campo desc)
 * @param combo es la variable del JComboBox que queremos cargar
 * @param nowCombo es en nombre del campo que se va cargar en el jcombobox para mostrar
 * @param nowVector es el nombre del id que queremos obtener al seleccionar el jcombobox
 * @param cant es la cantidad de combobox que puede haber en un formulario al mismo tiempo 
 * actualmente son 5 maximo es muy importante que coinsida con selectCombobox
 * esta variable va a selectCombobox(JComboBox cmbnom) 
 */
  public void cargarCombobox(Connection conn,String sql,JComboBox combo,String nowCombo,String nowVector,int cant){  
      DefaultComboBoxModel model = (DefaultComboBoxModel) combo.getModel();
      model.removeAllElements();//eliminamos todo antes de cargar
      if(cant<=cantidad_vectores){
        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            combo.addItem("0---------------"); 
            while (rs.next()){
                combo.addItem(rs.getObject(nowCombo)); 
                rowCampo++;
                idcampo1[0]="0";
                idcampo2[0]="0";
                idcampo3[0]="0";
                idcampo4[0]="0";
                idcampo5[0]="0";
                idcampo6[0]="0";
                idcampo7[0]="0";
                idcampo8[0]="0";
                idcampo9[0]="0";
                idcampo10[0]="0";
                idcampo11[0]="0";
                idcampo12[0]="0";
                idcampo13[0]="0";
                if(cant==1){
                    idcampo1[rowCampo]=(rs.getString(nowVector));
                }if(cant==2){
                    idcampo2[rowCampo]=(rs.getString(nowVector));
                }if(cant==3){
                    idcampo3[rowCampo]=(rs.getString(nowVector));
                }if(cant==4){
                    idcampo4[rowCampo]=(rs.getString(nowVector));
                }if(cant==5){
                    idcampo5[rowCampo]=(rs.getString(nowVector));
                }if(cant==6){
                    idcampo6[rowCampo]=(rs.getString(nowVector));
                }if(cant==7){
                    idcampo7[rowCampo]=(rs.getString(nowVector));
                }if(cant==8){
                    idcampo8[rowCampo]=(rs.getString(nowVector));
                }if(cant==9){
                    idcampo9[rowCampo]=(rs.getString(nowVector));
                }if(cant==10){
                    idcampo10[rowCampo]=(rs.getString(nowVector));
                }if(cant==11){
                    idcampo11[rowCampo]=(rs.getString(nowVector));
                }if(cant==12){
                    idcampo12[rowCampo]=(rs.getString(nowVector));
                }if(cant==13){
                    idcampo13[rowCampo]=(rs.getString(nowVector));
                }
            }
            
            if(rowCampo>limite){
                JOptionPane.showMessageDialog(null,"Exceso de cantidad de registro en combobox su limite es:"+limite+"  ");
            }
            rowCampo=0;
            System.out.println("---Cargade de Jcombobox sin error su sql es ---:"+sql);
        }catch(Exception e){
            System.out.println(e);
//            fm.mensaje_error(e, sql, "cargarCombobox");
        }
      }else{
          JOptionPane.showMessageDialog(null,"no se puede tener mas de "+cantidad_vectores+" combobox dentro del mismo formulario\n ver para aumentar ");
      }
    }  
  /**
   * esta funcion debuelde el id cargado anterior mente en cargarCombobox
   * @param cmbnom es el combobox que deseamos obtener su id
   * @param cant es muy importante que coinsida con la cant del cargarCombobox
   * si es del mismo jcombobox 
   * @return String con el numero de id
   */
    public String selectCombobox(JComboBox cmbnom, int cant) {
        String id="";
        int now = cmbnom.getSelectedIndex();
       // id = idcampo[now];
        if (cmbnom.getSelectedIndex() > 0) {
            if (cant == 1) {
                id = idcampo1[now];
            }
            if (cant == 2) {
                id = idcampo2[now];
            }
            if (cant == 3) {
                id = idcampo3[now];
            }
            if (cant == 4) {
                id = idcampo4[now];
            }
            if (cant == 5) {
                id = idcampo5[now];
            }
            if (cant == 6) {
                id = idcampo6[now];
            }
            if (cant == 7) {
                id = idcampo7[now];
            }
            if (cant == 8) {
                id = idcampo8[now];
            }
            if (cant == 9) {
                id = idcampo9[now];
            }
            if (cant == 10) {
                id = idcampo10[now];
            }
            if (cant == 11) {
                id = idcampo11[now];
            }
            if (cant == 12) {
                id = idcampo12[now];
            }
            if (cant == 13) {
                id = idcampo13[now];
            }
            System.out.println("numero de id de " + cmbnom.getSelectedItem().toString() + " id: " + id);
            return id;
        } else {
            System.out.println("Error combobox basio id: " + id);
            return id;

        }
    }
    public void getSeleccionar_combo(Connection conn,JComboBox cmbcombo,String tabla,String campoget,String idtabla,String setidtabla){
        String titulo = "getSeleccionar_combo";
        String sql = "select "+campoget+" from "+tabla+" where "+idtabla+"="+setidtabla+" ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql,titulo);
            if (rs.next()) {
                String campo=(rs.getString(1));
                cmbcombo.setSelectedItem(campo);
            }
        }catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }
}
