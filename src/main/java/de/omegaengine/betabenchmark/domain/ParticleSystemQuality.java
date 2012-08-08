package de.omegaengine.betabenchmark.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "particle_system_quality", catalog = "betabenchmark", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
public class ParticleSystemQuality implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "particleSystemQuality", fetch = FetchType.LAZY)
    private Collection<TestCase> testCases;

    public ParticleSystemQuality() {
    }

    public ParticleSystemQuality(Integer id) {
        this.id = id;
    }

    public ParticleSystemQuality(Integer id, String name) {
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
    public Collection<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(Collection<TestCase> testCases) {
        this.testCases = testCases;
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
        if (!(object instanceof ParticleSystemQuality)) {
            return false;
        }
        ParticleSystemQuality other = (ParticleSystemQuality) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.google.code.omegaengine.betabenchmark.model.ParticleSystemQuality[ id=" + id + " ]";
    }
}
