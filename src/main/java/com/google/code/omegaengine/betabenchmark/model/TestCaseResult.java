package com.google.code.omegaengine.betabenchmark.model;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "test_case_result", catalog = "betabenchmark", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestCaseResult.findAll", query = "SELECT t FROM TestCaseResult t"),
    @NamedQuery(name = "TestCaseResult.findById", query = "SELECT t FROM TestCaseResult t WHERE t.id = :id"),
    @NamedQuery(name = "TestCaseResult.findByAverageFps", query = "SELECT t FROM TestCaseResult t WHERE t.averageFps = :averageFps"),
    @NamedQuery(name = "TestCaseResult.findByAverageFrameMs", query = "SELECT t FROM TestCaseResult t WHERE t.averageFrameMs = :averageFrameMs")})
public class TestCaseResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "average_fps", nullable = false)
    private float averageFps;
    @Basic(optional = false)
    @NotNull
    @Column(name = "average_frame_ms", nullable = false)
    private float averageFrameMs;
    @Lob
    @Size(max = 65535)
    @Column(name = "frame_log", length = 65535)
    private String frameLog;
    @Lob
    private byte[] screenshot;
    @JoinColumn(name = "test_case_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TestCase testCase;
    @JoinColumn(name = "submission_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Submission submission;

    public TestCaseResult() {
    }

    public TestCaseResult(Integer id) {
        this.id = id;
    }

    public TestCaseResult(Integer id, float averageFps, float averageFrameMs) {
        this.id = id;
        this.averageFps = averageFps;
        this.averageFrameMs = averageFrameMs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getAverageFps() {
        return averageFps;
    }

    public void setAverageFps(float averageFps) {
        this.averageFps = averageFps;
    }

    public float getAverageFrameMs() {
        return averageFrameMs;
    }

    public void setAverageFrameMs(float averageFrameMs) {
        this.averageFrameMs = averageFrameMs;
    }

    public String getFrameLog() {
        return frameLog;
    }

    public void setFrameLog(String frameLog) {
        this.frameLog = frameLog;
    }

    public byte[] getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(byte[] screenshot) {
        this.screenshot = screenshot;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
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
        if (!(object instanceof TestCaseResult)) {
            return false;
        }
        TestCaseResult other = (TestCaseResult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.google.code.omegaengine.betabenchmark.controller.exceptions.TestCaseResult[ id=" + id + " ]";
    }
}
