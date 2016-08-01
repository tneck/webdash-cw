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
     * @param widgetTemplate the entity to save
     * @return the persisted entity
     */
    WidgetTemplate save(WidgetTemplate widgetTemplate);

    /**
     *  Get all the widgetTemplates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WidgetTemplate> findAll(Pageable pageable);

    /**
     *  Get the "id" widgetTemplate.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    WidgetTemplate findOne(Long id);

    /**
     *  Delete the "id" widgetTemplate.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
