/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.JLabel;

import Evento.Color.cla_color_palete;
import javax.swing.JLabel;

/**
 *
 * @author Digno
 */
public class EvenJLabel {
    cla_color_palete clacolor= new cla_color_palete();
    public void evento_MouseMoved(JLabel lblnombre){
        lblnombre.setBackground(clacolor.getCol_btn_move_ok());
        lblnombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    }
    public void evento_MouseExited(JLabel lblnombre){
        lblnombre.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    }
}
