/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.prestamos.entities;

import java.util.List;

/**
 *
 * @author 
 */
public class ReportePrestamoDTO {
    
    private Usuario usuario;
    private Prestamo prestamo;
    private List<Cuota> listCuotas;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Cuota> getListCuotas() {
        return listCuotas;
    }

    public void setListCuotas(List<Cuota> listCuotas) {
        this.listCuotas = listCuotas;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }
    
}
