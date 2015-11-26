/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.prestamos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author YHOAN CUCAITA
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIntIdusuario", query = "SELECT u FROM Usuario u WHERE u.intIdusuario = :intIdusuario"),
    @NamedQuery(name = "Usuario.findByUsuNombre", query = "SELECT u FROM Usuario u WHERE u.usuNombre = :usuNombre"),
    @NamedQuery(name = "Usuario.findByUserLikeNombre", query = "SELECT u FROM Usuario u WHERE u.usuNombre like :usuNombre"),
    @NamedQuery(name = "Usuario.findByUsuCedula", query = "SELECT u FROM Usuario u WHERE u.usuCedula = :usuCedula"),
    @NamedQuery(name = "Usuario.findByUsuMovil", query = "SELECT u FROM Usuario u WHERE u.usuMovil = :usuMovil"),
    @NamedQuery(name = "Usuario.findByUsuDireccion", query = "SELECT u FROM Usuario u WHERE u.usuDireccion = :usuDireccion"),
    @NamedQuery(name = "Usuario.findByUsuCorreo", query = "SELECT u FROM Usuario u WHERE u.usuCorreo = :usuCorreo")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "INT_IDUSUARIO")
    @GeneratedValue(generator = "USUARIO_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", initialValue = 1, allocationSize = 1)
    private Long intIdusuario;
    @Size(max = 100)
    @Column(name = "USU_NOMBRE")
    private String usuNombre;
    @Size(max = 30)
    @Column(name = "USU_CEDULA")
    private String usuCedula;
    @Size(max = 20)
    @Column(name = "USU_MOVIL")
    private String usuMovil;
    @Size(max = 100)
    @Column(name = "USU_DIRECCION")
    private String usuDireccion;
    @Size(max = 40)
    @Column(name = "USU_CORREO")
    private String usuCorreo;
    
    @Size(max = 20)
    @Column(name = "USU_TELEFONO")
    private String usuTelefono;
    
    @Size(max = 30)
    @Column(name = "USU_CIUDAD")
    private String usuCiudad;
    
//    @JoinTable(name = "USUARIO_PRESTAMO", joinColumns = {
//        @JoinColumn(name = "INT_IDUSUARIO", referencedColumnName = "INT_IDUSUARIO")}, inverseJoinColumns = {
//        @JoinColumn(name = "INT_IDUSUARIO", referencedColumnName = "INT_IDPRESTAMO")})
//    @ManyToMany
//    private List<Prestamo> prestamoList;

    public Usuario() {
    }

    public Usuario(Long intIdusuario) {
        this.intIdusuario = intIdusuario;
    }

    public Long getIntIdusuario() {
        return intIdusuario;
    }

    public void setIntIdusuario(Long intIdusuario) {
        this.intIdusuario = intIdusuario;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuCedula() {
        return usuCedula;
    }

    public void setUsuCedula(String usuCedula) {
        this.usuCedula = usuCedula;
    }

    public String getUsuMovil() {
        return usuMovil;
    }

    public void setUsuMovil(String usuMovil) {
        this.usuMovil = usuMovil;
    }

    public String getUsuDireccion() {
        return usuDireccion;
    }

    public void setUsuDireccion(String usuDireccion) {
        this.usuDireccion = usuDireccion;
    }

    public String getUsuCorreo() {
        return usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

//    @XmlTransient
//    public List<Prestamo> getPrestamoList() {
//        return prestamoList;
//    }
//
//    public void setPrestamoList(List<Prestamo> prestamoList) {
//        this.prestamoList = prestamoList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intIdusuario != null ? intIdusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.intIdusuario == null && other.intIdusuario != null) || (this.intIdusuario != null && !this.intIdusuario.equals(other.intIdusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.prestamos.entities.Usuario[ intIdusuario=" + intIdusuario + " ]";
    }

    public String getUsuCiudad() {
        return usuCiudad;
    }

    public void setUsuCiudad(String usuCiudad) {
        this.usuCiudad = usuCiudad;
    }

    public String getUsuTelefono() {
        return usuTelefono;
    }

    public void setUsuTelefono(String usuTelefono) {
        this.usuTelefono = usuTelefono;
    }

}
