package com.dr_alan_turing.webdash_cw.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dr_alan_turing.webdash_cw.domain.User;
import com.dr_alan_turing.webdash_cw.domain.WidgetTemplate;
import com.dr_alan_turing.webdash_cw.security.AuthoritiesConstants;
import com.dr_alan_turing.webdash_cw.service.UserService;
import com.dr_alan_turing.webdash_cw.service.WidgetTemplateService;
import com.dr_alan_turing.webdash_cw.web.rest.errors.CustomParameterizedException;
import com.dr_alan_turing.webdash_cw.web.rest.util.HeaderUtil;
import com.dr_alan_turing.webdash_cw.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WidgetTemplate.
 */
@RestController
@RequestMapping("/api")
public class WidgetTemplateResource {

    private final Logger log = LoggerFactory.getLogger(WidgetTemplateResource.class);

    @Inject
    private WidgetTemplateService widgetTemplateService;

    @Inject
    private UserService userService;

    /**
     * POST  /widget-templates : Create a new widgetTemplate.
     *
     * @param widgetTemplate the widgetTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new widgetTemplate, or with status 400 (Bad Request) if the widgetTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/widget-templates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WidgetTemplate> createWidgetTemplate(@Valid @RequestBody WidgetTemplate widgetTemplate) throws URISyntaxException {
        log.debug("REST request to save WidgetTemplate : {}", widgetTemplate);
        if (widgetTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("widgetTemplate", "idexists", "A new widgetTemplate cannot already have an ID")).body(null);
        }
        // Set auto-determined properties
        User loggedInUser = userService.getUserWithAuthorities();
        if(!userService.isLoggedInUserAdmin()) { // If user is not admin, force auto-determined values, else leave input values
            widgetTemplate.setCreator(loggedInUser);
            widgetTemplate.setDateCreated(ZonedDateTime.now());
        }
        if(userService.isLoggedInUserAdmin()){
            throw new CustomParameterizedException("crash boom kaput");
        }
        widgetTemplate.setDateLastModified(ZonedDateTime.now());
        // Save entity
        WidgetTemplate result = widgetTemplateService.save(widgetTemplate);
        return ResponseEntity.created(new URI("/api/widget-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("widgetTemplate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /widget-templates : Update an existing widgetTemplate.
     *
     * @param widgetTemplate the widgetTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated widgetTemplate,
     * or with status 400 (Bad Request) if the widgetTemplate is not valid,
     * or with status 500 (Internal Server Error) if the widgetTemplate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/widget-templates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WidgetTemplate> updateWidgetTemplate(@Valid @RequestBody WidgetTemplate widgetTemplate) throws URISyntaxException {
        log.debug("REST request to update WidgetTemplate : {}", widgetTemplate);
        if (widgetTemplate.getId() == null) {
            return createWidgetTemplate(widgetTemplate);
        }
        // Set auto-determined properties
        WidgetTemplate old = widgetTemplateService.findOne(widgetTemplate.getId());
        User loggedInUser = userService.getUserWithAuthorities();
        if(!userService.isLoggedInUserAdmin()) { // If user is not admin, force auto-determined values, else leave input values
            widgetTemplate.setCreator(old.getCreator());
            widgetTemplate.setDateCreated(old.getDateCreated());
        }
        widgetTemplate.setDateLastModified(ZonedDateTime.now());
        // Save entity
        WidgetTemplate result = widgetTemplateService.save(widgetTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("widgetTemplate", widgetTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /widget-templates : Get all the widgetTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of widgetTemplates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/widget-templates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<WidgetTemplate>> getAllWidgetTemplates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WidgetTemplates");
        Page<WidgetTemplate> page = widgetTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/widget-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /widget-templates/:id : Get the "id" widgetTemplate.
     *
     * @param id the id of the widgetTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the widgetTemplate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/widget-templates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WidgetTemplate> getWidgetTemplate(@PathVariable Long id) {
        log.debug("REST request to get WidgetTemplate : {}", id);
        WidgetTemplate widgetTemplate = widgetTemplateService.findOne(id);
        return Optional.ofNullable(widgetTemplate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /widget-templates/:id : Delete the "id" widgetTemplate.
     *
     * @param id the id of the widgetTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/widget-templates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWidgetTemplate(@PathVariable Long id) {
        log.debug("REST request to delete WidgetTemplate : {}", id);
        widgetTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("widgetTemplate", id.toString())).build();
    }

}
