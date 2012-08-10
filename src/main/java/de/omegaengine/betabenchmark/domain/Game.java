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
@Table(name = "game", catalog = "betabenchmark", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "version", "engine_version"})})
@XmlRootElement
public class Game implements Serializable {

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
    @Size(min = 1, max = 64)
    @Column(nullable = false, length = 5)
    private String version;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "engine_version", nullable = false, length = 5)
    private String engineVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game", fetch = FetchType.LAZY)
    private Collection<Submission> submissions;

    public Game() {
    }

    public Game(Integer id) {
        this.id = id;
    }

    public Game(Integer id, String name, String version, String engineVersion) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.engineVersion = engineVersion;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
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
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.google.code.omegaengine.betabenchmark.model.Game[ id=" + id + " ]";
    }
}
