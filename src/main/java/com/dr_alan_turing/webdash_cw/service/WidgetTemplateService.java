package com.dr_alan_turing.webdash_cw.service;

import com.dr_alan_turing.webdash_cw.domain.WidgetTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing WidgetTemplate.
 */
public interface WidgetTemplateService {

    /**
     * Save a widgetTemplate.
     *
     * @param widgetTemplate the widgetTemplate to save
     * @return the persisted widgetTemplate
     */
    WidgetTemplate save(WidgetTemplate widgetTemplate);

    /**
     *  Get all the widgetTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of widgetTemplates
     */
    Page<WidgetTemplate> findAll(Pageable pageable);

    /**
     *  Get all the widgetTemplates of the user with the given id.
     *
     *  @param creatorId the id of the creatorId for which to get the widgetTemplates
     *  @param pageable the pagination information
     *  @return the list of widgetTemplates
     */
    Page<WidgetTemplate> findAllByCreatorId(Long creatorId, Pageable pageable);

    /**
     *  Get the "id" widgetTemplate.
     *
     *  @param id the id of the widgetTemplate
     *  @return the widgetTemplate
     */
    WidgetTemplate findOne(Long id);

    /**
     *  Delete the "id" widgetTemplate.
     *
     *  @param id the id of the widgetTemplate
     */
    void delete(Long id);

}
