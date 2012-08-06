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
@Table(name = "test_case", catalog = "betabenchmark", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"target_name", "high_res", "anti_aliasing", "graphics_settings_anisotropic", "graphics_settings_double_sampling", "graphics_settings_post_screen_effects", "water_effects_id", "particle_system_quality_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestCase.findAll", query = "SELECT t FROM TestCase t"),
    @NamedQuery(name = "TestCase.findById", query = "SELECT t FROM TestCase t WHERE t.id = :id"),
    @NamedQuery(name = "TestCase.findByTargetName", query = "SELECT t FROM TestCase t WHERE t.targetName = :targetName"),
    @NamedQuery(name = "TestCase.findByHighRes", query = "SELECT t FROM TestCase t WHERE t.highRes = :highRes"),
    @NamedQuery(name = "TestCase.findByAntiAliasing", query = "SELECT t FROM TestCase t WHERE t.antiAliasing = :antiAliasing"),
    @NamedQuery(name = "TestCase.findByGraphicsSettingsAnisotropic", query = "SELECT t FROM TestCase t WHERE t.graphicsSettingsAnisotropic = :graphicsSettingsAnisotropic"),
    @NamedQuery(name = "TestCase.findByGraphicsSettingsDoubleSampling", query = "SELECT t FROM TestCase t WHERE t.graphicsSettingsDoubleSampling = :graphicsSettingsDoubleSampling"),
    @NamedQuery(name = "TestCase.findByGraphicsSettingsPostScreenEffects", query = "SELECT t FROM TestCase t WHERE t.graphicsSettingsPostScreenEffects = :graphicsSettingsPostScreenEffects")})
public class TestCase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "target_name", nullable = false, length = 255)
    private String targetName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "high_res", nullable = false)
    private boolean highRes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anti_aliasing", nullable = false)
    private boolean antiAliasing;
    @Basic(optional = false)
    @NotNull
    @Column(name = "graphics_settings_anisotropic", nullable = false)
    private boolean graphicsSettingsAnisotropic;
    @Basic(optional = false)
    @NotNull
    @Column(name = "graphics_settings_double_sampling", nullable = false)
    private boolean graphicsSettingsDoubleSampling;
    @Basic(optional = false)
    @NotNull
    @Column(name = "graphics_settings_post_screen_effects", nullable = false)
    private boolean graphicsSettingsPostScreenEffects;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testCase", fetch = FetchType.LAZY)
    private Collection<TestCaseResult> results;
    @JoinColumn(name = "particle_system_quality_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParticleSystemQuality particleSystemQuality;
    @JoinColumn(name = "water_effects_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private WaterEffects waterEffects;

    public TestCase() {
    }

    public TestCase(Integer id) {
        this.id = id;
    }

    public TestCase(Integer id, String targetName, boolean highRes, boolean antiAliasing, boolean graphicsSettingsAnisotropic, boolean graphicsSettingsDoubleSampling, boolean graphicsSettingsPostScreenEffects) {
        this.id = id;
        this.targetName = targetName;
        this.highRes = highRes;
        this.antiAliasing = antiAliasing;
        this.graphicsSettingsAnisotropic = graphicsSettingsAnisotropic;
        this.graphicsSettingsDoubleSampling = graphicsSettingsDoubleSampling;
        this.graphicsSettingsPostScreenEffects = graphicsSettingsPostScreenEffects;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public boolean getHighRes() {
        return highRes;
    }

    public void setHighRes(boolean highRes) {
        this.highRes = highRes;
    }

    public boolean getAntiAliasing() {
        return antiAliasing;
    }

    public void setAntiAliasing(boolean antiAliasing) {
        this.antiAliasing = antiAliasing;
    }

    public boolean getGraphicsSettingsAnisotropic() {
        return graphicsSettingsAnisotropic;
    }

    public void setGraphicsSettingsAnisotropic(boolean graphicsSettingsAnisotropic) {
        this.graphicsSettingsAnisotropic = graphicsSettingsAnisotropic;
    }

    public boolean getGraphicsSettingsDoubleSampling() {
        return graphicsSettingsDoubleSampling;
    }

    public void setGraphicsSettingsDoubleSampling(boolean graphicsSettingsDoubleSampling) {
        this.graphicsSettingsDoubleSampling = graphicsSettingsDoubleSampling;
    }

    public boolean getGraphicsSettingsPostScreenEffects() {
        return graphicsSettingsPostScreenEffects;
    }

    public void setGraphicsSettingsPostScreenEffects(boolean graphicsSettingsPostScreenEffects) {
        this.graphicsSettingsPostScreenEffects = graphicsSettingsPostScreenEffects;
    }

    @XmlTransient
    public Collection<TestCaseResult> getResults() {
        return results;
    }

    public void setResults(Collection<TestCaseResult> results) {
        this.results = results;
    }

    public ParticleSystemQuality getParticleSystemQuality() {
        return particleSystemQuality;
    }

    public void setParticleSystemQuality(ParticleSystemQuality particleSystemQuality) {
        this.particleSystemQuality = particleSystemQuality;
    }

    public WaterEffects getWaterEffects() {
        return waterEffects;
    }

    public void setWaterEffects(WaterEffects waterEffects) {
        this.waterEffects = waterEffects;
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
        if (!(object instanceof TestCase)) {
            return false;
        }
        TestCase other = (TestCase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.google.code.omegaengine.betabenchmark.controller.exceptions.TestCase[ id=" + id + " ]";
    }
}
