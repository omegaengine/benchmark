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
@Table(name = "os", catalog = "betabenchmark", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"platform", "is64bit", "version", "service_pack"})})
@XmlRootElement
public class Os implements Serializable {

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
    private String platform;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean is64bit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String version;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "service_pack", nullable = false, length = 255)
    private String servicePack;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "os", fetch = FetchType.LAZY)
    private Collection<Submission> submissions;

    public Os() {
    }

    public Os(Integer id) {
        this.id = id;
    }

    public Os(Integer id, String platform, boolean is64bit, String version, String servicePack) {
        this.id = id;
        this.platform = platform;
        this.is64bit = is64bit;
        this.version = version;
        this.servicePack = servicePack;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean getIs64bit() {
        return is64bit;
    }

    public void setIs64bit(boolean is64bit) {
        this.is64bit = is64bit;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServicePack() {
        return servicePack;
    }

    public void setServicePack(String servicePack) {
        this.servicePack = servicePack;
    }

    @XmlTransient
    public Collection<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Collection<Submission> submissions) {
        this.submissions = submissions;
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
        if (!(object instanceof Os)) {
            return false;
        }
        Os other = (Os) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.google.code.omegaengine.betabenchmark.model.Os[ id=" + id + " ]";
    }
}
