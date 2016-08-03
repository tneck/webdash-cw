package com.dr_alan_turing.webdash_cw.web.rest.dto;

import com.dr_alan_turing.webdash_cw.domain.WidgetTemplate;
import com.dr_alan_turing.webdash_cw.domain.enumeration.WidgetAccess;

import java.time.ZonedDateTime;

/**
 * Created by Dr-Alan-Turing on 04/08/2016.
 */
public class MyWidgetTemplateDTO {

    private Long id;

    private String name;

    private String description;

    private ZonedDateTime dateCreated;

    private ZonedDateTime dateLastModified;

    private String currentVersion;

    private WidgetAccess access;

    private String shareCode;

    private byte[] imagePreview;

    private String imagePreviewContentType;

    private String options;

    private String contentUrl;

    /**
     * Empty constructor
     */
    public MyWidgetTemplateDTO() {}

    /**
     * Full constructor
     *
     * @param id
     * @param name
     * @param description
     * @param dateCreated
     * @param dateLastModified
     * @param currentVersion
     * @param access
     * @param shareCode
     * @param imagePreview
     * @param imagePreviewContentType
     * @param options
     * @param contentUrl
     */
    public MyWidgetTemplateDTO(Long id, String name, String description, ZonedDateTime dateCreated, ZonedDateTime dateLastModified, String currentVersion, WidgetAccess access, String shareCode, byte[] imagePreview, String imagePreviewContentType, String options, String contentUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateLastModified = dateLastModified;
        this.currentVersion = currentVersion;
        this.access = access;
        this.shareCode = shareCode;
        this.imagePreview = imagePreview;
        this.imagePreviewContentType = imagePreviewContentType;
        this.options = options;
        this.contentUrl = contentUrl;
    }

    /**
     * Constructor that copies attributes from related domain entity
     * @param widgetTemplate
     */
    public MyWidgetTemplateDTO(WidgetTemplate widgetTemplate) {
        this(widgetTemplate.getId(), widgetTemplate.getName(), widgetTemplate.getDescription(), widgetTemplate.getDateCreated(), widgetTemplate.getDateLastModified(), widgetTemplate.getCurrentVersion(), widgetTemplate.getAccess(), widgetTemplate.getShareCode(), widgetTemplate.getImagePreview(), widgetTemplate.getImagePreviewContentType(), widgetTemplate.getOptions(), widgetTemplate.getContentUrl());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public ZonedDateTime getDateLastModified() {
        return dateLastModified;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public WidgetAccess getAccess() {
        return access;
    }

    public String getShareCode() {
        return shareCode;
    }

    public byte[] getImagePreview() {
        return imagePreview;
    }

    public String getImagePreviewContentType() {
        return imagePreviewContentType;
    }

    public String getOptions() {
        return options;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateLastModified(ZonedDateTime dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public void setAccess(WidgetAccess access) {
        this.access = access;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public void setImagePreviewContentType(String imagePreviewContentType) {
        this.imagePreviewContentType = imagePreviewContentType;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
