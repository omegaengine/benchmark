package com.google.code.omegaengine.betabenchmark.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "submission", catalog = "betabenchmark", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Submission.findAll", query = "SELECT s FROM Submission s"),
    @NamedQuery(name = "Submission.findById", query = "SELECT s FROM Submission s WHERE s.id = :id"),
    @NamedQuery(name = "Submission.findBySubmissionTime", query = "SELECT s FROM Submission s WHERE s.submissionTime = :submissionTime"),
    @NamedQuery(name = "Submission.findByUserName", query = "SELECT s FROM Submission s WHERE s.userName = :userName"),
    @NamedQuery(name = "Submission.findByGameVersion", query = "SELECT s FROM Submission s WHERE s.gameVersion = :gameVersion"),
    @NamedQuery(name = "Submission.findByEngineVersion", query = "SELECT s FROM Submission s WHERE s.engineVersion = :engineVersion"),
    @NamedQuery(name = "Submission.findByRam", query = "SELECT s FROM Submission s WHERE s.ram = :ram")})
public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "submission_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "user_name", nullable = false, length = 128)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "game_version", nullable = false, length = 5)
    private String gameVersion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "engine_version", nullable = false, length = 5)
    private String engineVersion;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int ram;
    @Lob
    @Size(max = 65535)
    @Column(name = "game_log", length = 65535)
    private String gameLog;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submission", fetch = FetchType.LAZY)
    private Collection<TestCaseResult> testCaseResultCollection;
    @JoinColumn(name = "gpu_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gpu gpu;
    @JoinColumn(name = "cpu_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cpu cpu;
    @JoinColumn(name = "os_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Os os;

    public Submission() {
    }

    public Submission(Integer id) {
        this.id = id;
    }

    public Submission(Integer id, Date submissionTime, String userName, String gameVersion, String engineVersion, int ram) {
        this.id = id;
        this.submissionTime = submissionTime;
        this.userName = userName;
        this.gameVersion = gameVersion;
        this.engineVersion = engineVersion;
        this.ram = ram;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Date submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getGameLog() {
        return gameLog;
    }

    public void setGameLog(String gameLog) {
        this.gameLog = gameLog;
    }

    @XmlTransient
    public Collection<TestCaseResult> getTestCaseResultCollection() {
        return testCaseResultCollection;
    }

    public void setTestCaseResultCollection(Collection<TestCaseResult> testCaseResultCollection) {
        this.testCaseResultCollection = testCaseResultCollection;
    }

    public Gpu getGpu() {
        return gpu;
    }

    public void setGpu(Gpu gpu) {
        this.gpu = gpu;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Os getOs() {
        return os;
    }

    public void setOs(Os os) {
        this.os = os;
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
        if (!(object instanceof Submission)) {
            return false;
        }
        Submission other = (Submission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.google.code.omegaengine.betabenchmark.controller.exceptions.Submission[ id=" + id + " ]";
    }
}
