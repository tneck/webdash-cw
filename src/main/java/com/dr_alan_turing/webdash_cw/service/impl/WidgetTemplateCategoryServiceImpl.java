package com.dr_alan_turing.webdash_cw.service.impl;

import com.dr_alan_turing.webdash_cw.service.WidgetTemplateCategoryService;
import com.dr_alan_turing.webdash_cw.domain.WidgetTemplateCategory;
import com.dr_alan_turing.webdash_cw.repository.WidgetTemplateCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing WidgetTemplateCategory.
 */
@Service
@Transactional
public class WidgetTemplateCategoryServiceImpl implements WidgetTemplateCategoryService{

    private final Logger log = LoggerFactory.getLogger(WidgetTemplateCategoryServiceImpl.class);
    
    @Inject
    private WidgetTemplateCategoryRepository widgetTemplateCategoryRepository;
    
    /**
     * Save a widgetTemplateCategory.
     * 
     * @param widgetTemplateCategory the entity to save
     * @return the persisted entity
     */
    public WidgetTemplateCategory save(WidgetTemplateCategory widgetTemplateCategory) {
        log.debug("Request to save WidgetTemplateCategory : {}", widgetTemplateCategory);
        WidgetTemplateCategory result = widgetTemplateCategoryRepository.save(widgetTemplateCategory);
        return result;
    }

    /**
     *  Get all the widgetTemplateCategories.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<WidgetTemplateCategory> findAll() {
        log.debug("Request to get all WidgetTemplateCategories");
        List<WidgetTemplateCategory> result = widgetTemplateCategoryRepository.findAll();
        return result;
    }

    /**
     *  Get one widgetTemplateCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WidgetTemplateCategory findOne(Long id) {
        log.debug("Request to get WidgetTemplateCategory : {}", id);
        WidgetTemplateCategory widgetTemplateCategory = widgetTemplateCategoryRepository.findOne(id);
        return widgetTemplateCategory;
    }

    /**
     *  Delete the  widgetTemplateCategory by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WidgetTemplateCategory : {}", id);
        widgetTemplateCategoryRepository.delete(id);
    }
}
