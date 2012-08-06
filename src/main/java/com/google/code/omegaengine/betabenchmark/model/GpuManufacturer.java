package com.google.code.omegaengine.betabenchmark.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "gpu_manufacturer", catalog = "betabenchmark", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GpuManufacturer.findAll", query = "SELECT g FROM GpuManufacturer g"),
    @NamedQuery(name = "GpuManufacturer.findById", query = "SELECT g FROM GpuManufacturer g WHERE g.id = :id"),
    @NamedQuery(name = "GpuManufacturer.findByName", query = "SELECT g FROM GpuManufacturer g WHERE g.name = :name")})
public class GpuManufacturer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manufacturer", fetch = FetchType.LAZY)
    private Collection<Gpu> gpuCollection;

    public GpuManufacturer() {
    }

    public GpuManufacturer(Integer id) {
        this.id = id;
    }

    public GpuManufacturer(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @XmlTransient
    public Collection<Gpu> getGpuCollection() {
        return gpuCollection;
    }

    public void setGpuCollection(Collection<Gpu> gpuCollection) {
        this.gpuCollection = gpuCollection;
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
        if (!(object instanceof GpuManufacturer)) {
            return false;
        }
        GpuManufacturer other = (GpuManufacturer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.google.code.omegaengine.betabenchmark.controller.exceptions.GpuManufacturer[ id=" + id + " ]";
    }
}
