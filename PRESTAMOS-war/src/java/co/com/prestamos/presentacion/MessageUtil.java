/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.prestamos.presentacion;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 * Metodo encargado de retornar el mensaje almacenado en los bundle
 *
 * @author Almaviva
 */
public class MessageUtil {

    /**
     *
     * @param llave
     * @return
     */
    public static String getValueLogin(String llave) {
        String resultado;
        FacesContext fc = FacesContext.getCurrentInstance();
        ResourceBundle rb = fc.getApplication().getResourceBundle(fc, "login");

        try {
            resultado = rb.getString(llave);
        } catch (MissingResourceException mre) {
            resultado = "Llave no encontrada";
        }

        return resultado;
    }
}
