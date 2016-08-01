package com.dr_alan_turing.webdash_cw.service;

import com.dr_alan_turing.webdash_cw.domain.WidgetTemplateCategory;

import java.util.List;

/**
 * Service Interface for managing WidgetTemplateCategory.
 */
public interface WidgetTemplateCategoryService {

    /**
     * Save a widgetTemplateCategory.
     * 
     * @param widgetTemplateCategory the entity to save
     * @return the persisted entity
     */
    WidgetTemplateCategory save(WidgetTemplateCategory widgetTemplateCategory);

    /**
     *  Get all the widgetTemplateCategories.
     *  
     *  @return the list of entities
     */
    List<WidgetTemplateCategory> findAll();

    /**
     *  Get the "id" widgetTemplateCategory.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    WidgetTemplateCategory findOne(Long id);

    /**
     *  Delete the "id" widgetTemplateCategory.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
