package com.dr_alan_turing.webdash_cw.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dr_alan_turing.webdash_cw.domain.WidgetTemplateCategory;
import com.dr_alan_turing.webdash_cw.service.WidgetTemplateCategoryService;
import com.dr_alan_turing.webdash_cw.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WidgetTemplateCategory.
 */
@RestController
@RequestMapping("/api")
public class WidgetTemplateCategoryResource {

    private final Logger log = LoggerFactory.getLogger(WidgetTemplateCategoryResource.class);
        
    @Inject
    private WidgetTemplateCategoryService widgetTemplateCategoryService;
    
    /**
     * POST  /widget-template-categories : Create a new widgetTemplateCategory.
     *
     * @param widgetTemplateCategory the widgetTemplateCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new widgetTemplateCategory, or with status 400 (Bad Request) if the widgetTemplateCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/widget-template-categories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WidgetTemplateCategory> createWidgetTemplateCategory(@Valid @RequestBody WidgetTemplateCategory widgetTemplateCategory) throws URISyntaxException {
        log.debug("REST request to save WidgetTemplateCategory : {}", widgetTemplateCategory);
        if (widgetTemplateCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("widgetTemplateCategory", "idexists", "A new widgetTemplateCategory cannot already have an ID")).body(null);
        }
        WidgetTemplateCategory result = widgetTemplateCategoryService.save(widgetTemplateCategory);
        return ResponseEntity.created(new URI("/api/widget-template-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("widgetTemplateCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /widget-template-categories : Updates an existing widgetTemplateCategory.
     *
     * @param widgetTemplateCategory the widgetTemplateCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated widgetTemplateCategory,
     * or with status 400 (Bad Request) if the widgetTemplateCategory is not valid,
     * or with status 500 (Internal Server Error) if the widgetTemplateCategory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/widget-template-categories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WidgetTemplateCategory> updateWidgetTemplateCategory(@Valid @RequestBody WidgetTemplateCategory widgetTemplateCategory) throws URISyntaxException {
        log.debug("REST request to update WidgetTemplateCategory : {}", widgetTemplateCategory);
        if (widgetTemplateCategory.getId() == null) {
            return createWidgetTemplateCategory(widgetTemplateCategory);
        }
        WidgetTemplateCategory result = widgetTemplateCategoryService.save(widgetTemplateCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("widgetTemplateCategory", widgetTemplateCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /widget-template-categories : get all the widgetTemplateCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of widgetTemplateCategories in body
     */
    @RequestMapping(value = "/widget-template-categories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<WidgetTemplateCategory> getAllWidgetTemplateCategories() {
        log.debug("REST request to get all WidgetTemplateCategories");
        return widgetTemplateCategoryService.findAll();
    }

    /**
     * GET  /widget-template-categories/:id : get the "id" widgetTemplateCategory.
     *
     * @param id the id of the widgetTemplateCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the widgetTemplateCategory, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/widget-template-categories/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WidgetTemplateCategory> getWidgetTemplateCategory(@PathVariable Long id) {
        log.debug("REST request to get WidgetTemplateCategory : {}", id);
        WidgetTemplateCategory widgetTemplateCategory = widgetTemplateCategoryService.findOne(id);
        return Optional.ofNullable(widgetTemplateCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /widget-template-categories/:id : delete the "id" widgetTemplateCategory.
     *
     * @param id the id of the widgetTemplateCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/widget-template-categories/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWidgetTemplateCategory(@PathVariable Long id) {
        log.debug("REST request to delete WidgetTemplateCategory : {}", id);
        widgetTemplateCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("widgetTemplateCategory", id.toString())).build();
    }

}
