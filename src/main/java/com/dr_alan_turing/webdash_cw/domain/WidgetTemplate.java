package com.dr_alan_turing.webdash_cw.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.dr_alan_turing.webdash_cw.domain.enumeration.WidgetAccess;

/**
 * A WidgetTemplate.
 */
@Entity
@Table(name = "widget_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WidgetTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private ZonedDateTime dateCreated;

    @Column(name = "date_last_modified")
    private ZonedDateTime dateLastModified;

    @NotNull
    @Column(name = "current_version", nullable = false)
    private String currentVersion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "access", nullable = false)
    private WidgetAccess access;

    @Column(name = "share_code")
    private String shareCode;

    @Lob
    @Column(name = "image_preview")
    private byte[] imagePreview;

    @Column(name = "image_preview_content_type")
    private String imagePreviewContentType;

    @Column(name = "options")
    private String options;

    @Column(name = "content_url")
    private String contentUrl;

    /**
     * content of widget template is either hosted on an external site (-> url) or on WebDash CW (-> data attributes below)
     * 
     */
    @ApiModelProperty(value = ""
        + "content of widget template is either hosted on an external site (-> url) or on WebDash CW (-> data attributes below)"
        + "")
    @Column(name = "data_libraries")
    private String dataLibraries;

    @Column(name = "data_html")
    private String dataHtml;

    @Column(name = "data_css")
    private String dataCss;

    @Column(name = "data_javascript")
    private String dataJavascript;

    @Column(name = "data_input_variables")
    private String dataInputVariables;

    @ManyToOne
    private User creator;

    @ManyToOne
    private WidgetTemplateCategory category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(ZonedDateTime dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public WidgetAccess getAccess() {
        return access;
    }

    public void setAccess(WidgetAccess access) {
        this.access = access;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public byte[] getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public String getImagePreviewContentType() {
        return imagePreviewContentType;
    }

    public void setImagePreviewContentType(String imagePreviewContentType) {
        this.imagePreviewContentType = imagePreviewContentType;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getDataLibraries() {
        return dataLibraries;
    }

    public void setDataLibraries(String dataLibraries) {
        this.dataLibraries = dataLibraries;
    }

    public String getDataHtml() {
        return dataHtml;
    }

    public void setDataHtml(String dataHtml) {
        this.dataHtml = dataHtml;
    }

    public String getDataCss() {
        return dataCss;
    }

    public void setDataCss(String dataCss) {
        this.dataCss = dataCss;
    }

    public String getDataJavascript() {
        return dataJavascript;
    }

    public void setDataJavascript(String dataJavascript) {
        this.dataJavascript = dataJavascript;
    }

    public String getDataInputVariables() {
        return dataInputVariables;
    }

    public void setDataInputVariables(String dataInputVariables) {
        this.dataInputVariables = dataInputVariables;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public WidgetTemplateCategory getCategory() {
        return category;
    }

    public void setCategory(WidgetTemplateCategory widgetTemplateCategory) {
        this.category = widgetTemplateCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WidgetTemplate widgetTemplate = (WidgetTemplate) o;
        if(widgetTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, widgetTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WidgetTemplate{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateLastModified='" + dateLastModified + "'" +
            ", currentVersion='" + currentVersion + "'" +
            ", access='" + access + "'" +
            ", shareCode='" + shareCode + "'" +
            ", imagePreview='" + imagePreview + "'" +
            ", imagePreviewContentType='" + imagePreviewContentType + "'" +
            ", options='" + options + "'" +
            ", contentUrl='" + contentUrl + "'" +
            ", dataLibraries='" + dataLibraries + "'" +
            ", dataHtml='" + dataHtml + "'" +
            ", dataCss='" + dataCss + "'" +
            ", dataJavascript='" + dataJavascript + "'" +
            ", dataInputVariables='" + dataInputVariables + "'" +
            '}';
    }
}
