/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.prestamos.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author YHOAN CUCAITA
 */
@Entity
@Table(name = "CUOTA", schema = "PRESTAMOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuota.findAll", query = "SELECT c FROM Cuota c"),
    @NamedQuery(name = "Cuota.findByIntIdcuota", query = "SELECT c FROM Cuota c WHERE c.intIdcuota = :intIdcuota"),
    @NamedQuery(name = "Cuota.findByIntIdPrestamo", query = "SELECT c FROM Cuota c WHERE c.intIdprestamo = :intIdprestamo"),
    @NamedQuery(name = "Cuota.findByValor", query = "SELECT c FROM Cuota c WHERE c.valor = :valor")})
public class Cuota implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "INT_IDCUOTA")
    @GeneratedValue(generator = "cuota_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cuota_seq", sequenceName = "CUOTA_SEQ", initialValue = 1, allocationSize = 1)
    private BigDecimal intIdcuota;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private BigInteger valor;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIAL")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicial;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_VENCIMIENTO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaVencimiento;
    
    @JoinColumn(name = "INT_IDPRESTAMO", referencedColumnName = "INT_IDPRESTAMO")
    @ManyToOne(optional = false)
    private Prestamo intIdprestamo;

    public Cuota() {
    }

    public Cuota(BigDecimal intIdcuota) {
        this.intIdcuota = intIdcuota;
    }

    public Cuota(BigDecimal intIdcuota, BigInteger valor) {
        this.intIdcuota = intIdcuota;
        this.valor = valor;
    }

    public BigDecimal getIntIdcuota() {
        return intIdcuota;
    }

    public void setIntIdcuota(BigDecimal intIdcuota) {
        this.intIdcuota = intIdcuota;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public Prestamo getIntIdprestamo() {
        return intIdprestamo;
    }

    public void setIntIdprestamo(Prestamo intIdprestamo) {
        this.intIdprestamo = intIdprestamo;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intIdcuota != null ? intIdcuota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuota)) {
            return false;
        }
        Cuota other = (Cuota) object;
        if ((this.intIdcuota == null && other.intIdcuota != null) || (this.intIdcuota != null && !this.intIdcuota.equals(other.intIdcuota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.prestamos.entities.Cuota[ intIdcuota=" + intIdcuota + " ]";
    }

}
