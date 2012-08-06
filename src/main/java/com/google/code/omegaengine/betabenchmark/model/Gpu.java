package com.google.code.omegaengine.betabenchmark.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "gpu", catalog = "betabenchmark", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"manufacturer_id", "name", "ram", "max_aa"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gpu.findAll", query = "SELECT g FROM Gpu g"),
    @NamedQuery(name = "Gpu.findById", query = "SELECT g FROM Gpu g WHERE g.id = :id"),
    @NamedQuery(name = "Gpu.findByName", query = "SELECT g FROM Gpu g WHERE g.name = :name"),
    @NamedQuery(name = "Gpu.findByRam", query = "SELECT g FROM Gpu g WHERE g.ram = :ram"),
    @NamedQuery(name = "Gpu.findByMaxAa", query = "SELECT g FROM Gpu g WHERE g.maxAa = :maxAa")})
public class Gpu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int ram;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_aa", nullable = false)
    private int maxAa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gpu", fetch = FetchType.LAZY)
    private Collection<Submission> submissionCollection;
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GpuManufacturer manufacturer;

    public Gpu() {
    }

    public Gpu(Integer id) {
        this.id = id;
    }

    public Gpu(Integer id, String name, int ram, int maxAa) {
        this.id = id;
        this.name = name;
        this.ram = ram;
        this.maxAa = maxAa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getMaxAa() {
        return maxAa;
    }

    public void setMaxAa(int maxAa) {
        this.maxAa = maxAa;
    }

    @XmlTransient
    public Collection<Submission> getSubmissionCollection() {
        return submissionCollection;
    }

    public void setSubmissionCollection(Collection<Submission> submissionCollection) {
        this.submissionCollection = submissionCollection;
    }

    public GpuManufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(GpuManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gpu)) {
            return false;
        }
        Gpu other = (Gpu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.google.code.omegaengine.betabenchmark.controller.exceptions.Gpu[ id=" + id + " ]";
    }
}
