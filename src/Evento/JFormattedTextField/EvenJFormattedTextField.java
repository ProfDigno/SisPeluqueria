/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.JFormattedTextField;

import java.text.DecimalFormat;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Digno
 */
public class EvenJFormattedTextField {
    public void setCargarMonto_Formato(JFormattedTextField jFtexto_format,double monto,String codigoMoneda){
        String texto_formato="#,##0.00 "+codigoMoneda;
        NumberFormatter formatter = new NumberFormatter(new DecimalFormat(texto_formato)); //$NON-NLS-1$
	formatter.setValueClass(Float.class);
	DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
	jFtexto_format.setEditable(false);
	jFtexto_format.setFormatterFactory(factory);
        jFtexto_format.setValue(monto);
    }
}
