/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.prestamos.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "PRESTAMO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prestamo.findAll", query = "SELECT p FROM Prestamo p"),
    @NamedQuery(name = "Prestamo.findByIntIdprestamo", query = "SELECT p FROM Prestamo p WHERE p.intIdprestamo = :intIdprestamo"),
    @NamedQuery(name = "Prestamo.encontrarPorConsecutivo", query = "SELECT p FROM Prestamo p WHERE p.consecutivo = :consecutivo"),
    @NamedQuery(name = "Prestamo.findByMonto", query = "SELECT p FROM Prestamo p WHERE p.monto = :monto")})
public class Prestamo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "INT_IDPRESTAMO")
    @GeneratedValue(generator = "prestamos_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "prestamos_seq", sequenceName = "PRESTAMOS_SEQ", initialValue = 1, allocationSize = 1)
    private BigDecimal intIdprestamo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSECUTIVO")
    private BigInteger consecutivo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private BigInteger monto;
    
//    @ManyToMany(mappedBy = "prestamoList")
//    private List<Usuario> usuarioList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intIdprestamo")
    private List<Cuota> cuotasList;
//NUEVOS CAMPOS DESPUES DE REFACTOR DE TABLA
    @Size(max = 30)
    @Column(name = "USU_CEDULA")
    private String usuCedula;
    @Size(max = 20)
    @Column(name = "USU_MOVIL")
    private String usuMovil;
    @Size(max = 40)
    @Column(name = "USU_DIRECCION")
    private String usuDireccion;
    @Size(max = 40)
    @Column(name = "USU_CORREO")
    private String usuCorreo;

    public Prestamo() {
    }

    public Prestamo(BigDecimal intIdprestamo) {
        this.intIdprestamo = intIdprestamo;
    }

    public Prestamo(BigDecimal intIdprestamo, BigInteger monto) {
        this.intIdprestamo = intIdprestamo;
        this.monto = monto;
    }

    public BigDecimal getIntIdprestamo() {
        return intIdprestamo;
    }

    public void setIntIdprestamo(BigDecimal intIdprestamo) {
        this.intIdprestamo = intIdprestamo;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

//    @XmlTransient
//    public List<Usuario> getUsuarioList() {
//        return usuarioList;
//    }
//
//    public void setUsuarioList(List<Usuario> usuarioList) {
//        this.usuarioList = usuarioList;
//    }

    @XmlTransient
    public List<Cuota> getCuotasList() {
        return cuotasList;
    }

    public void setCuotasList(List<Cuota> cuotasList) {
        this.cuotasList = cuotasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intIdprestamo != null ? intIdprestamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prestamo)) {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.intIdprestamo == null && other.intIdprestamo != null) || (this.intIdprestamo != null && !this.intIdprestamo.equals(other.intIdprestamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.prestamos.entities.Prestamo[ intIdprestamo=" + intIdprestamo + " ]";
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

    public BigInteger getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(BigInteger consecutivo) {
        this.consecutivo = consecutivo;
    }

}
