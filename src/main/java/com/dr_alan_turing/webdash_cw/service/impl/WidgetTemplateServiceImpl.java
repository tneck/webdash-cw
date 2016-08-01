package com.dr_alan_turing.webdash_cw.service.impl;

import com.dr_alan_turing.webdash_cw.service.WidgetTemplateService;
import com.dr_alan_turing.webdash_cw.domain.WidgetTemplate;
import com.dr_alan_turing.webdash_cw.repository.WidgetTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing WidgetTemplate.
 */
@Service
@Transactional
public class WidgetTemplateServiceImpl implements WidgetTemplateService{

    private final Logger log = LoggerFactory.getLogger(WidgetTemplateServiceImpl.class);
    
    @Inject
    private WidgetTemplateRepository widgetTemplateRepository;
    
    /**
     * Save a widgetTemplate.
     * 
     * @param widgetTemplate the entity to save
     * @return the persisted entity
     */
    public WidgetTemplate save(WidgetTemplate widgetTemplate) {
        log.debug("Request to save WidgetTemplate : {}", widgetTemplate);
        WidgetTemplate result = widgetTemplateRepository.save(widgetTemplate);
        return result;
    }

    /**
     *  Get all the widgetTemplates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<WidgetTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all WidgetTemplates");
        Page<WidgetTemplate> result = widgetTemplateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one widgetTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WidgetTemplate findOne(Long id) {
        log.debug("Request to get WidgetTemplate : {}", id);
        WidgetTemplate widgetTemplate = widgetTemplateRepository.findOne(id);
        return widgetTemplate;
    }

    /**
     *  Delete the  widgetTemplate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WidgetTemplate : {}", id);
        widgetTemplateRepository.delete(id);
    }
}
