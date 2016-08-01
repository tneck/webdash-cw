package com.dr_alan_turing.webdash_cw.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dr_alan_turing.webdash_cw.domain.Dashboard;
import com.dr_alan_turing.webdash_cw.service.DashboardService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dashboard.
 */
@RestController
@RequestMapping("/api")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);
        
    @Inject
    private DashboardService dashboardService;
    
    /**
     * POST  /dashboards : Create a new dashboard.
     *
     * @param dashboard the dashboard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dashboard, or with status 400 (Bad Request) if the dashboard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dashboards",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dashboard> createDashboard(@RequestBody Dashboard dashboard) throws URISyntaxException {
        log.debug("REST request to save Dashboard : {}", dashboard);
        if (dashboard.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dashboard", "idexists", "A new dashboard cannot already have an ID")).body(null);
        }
        Dashboard result = dashboardService.save(dashboard);
        return ResponseEntity.created(new URI("/api/dashboards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dashboard", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dashboards : Updates an existing dashboard.
     *
     * @param dashboard the dashboard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dashboard,
     * or with status 400 (Bad Request) if the dashboard is not valid,
     * or with status 500 (Internal Server Error) if the dashboard couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dashboards",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dashboard> updateDashboard(@RequestBody Dashboard dashboard) throws URISyntaxException {
        log.debug("REST request to update Dashboard : {}", dashboard);
        if (dashboard.getId() == null) {
            return createDashboard(dashboard);
        }
        Dashboard result = dashboardService.save(dashboard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dashboard", dashboard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dashboards : get all the dashboards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dashboards in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/dashboards",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Dashboard>> getAllDashboards(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Dashboards");
        Page<Dashboard> page = dashboardService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dashboards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dashboards/:id : get the "id" dashboard.
     *
     * @param id the id of the dashboard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dashboard, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/dashboards/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dashboard> getDashboard(@PathVariable Long id) {
        log.debug("REST request to get Dashboard : {}", id);
        Dashboard dashboard = dashboardService.findOne(id);
        return Optional.ofNullable(dashboard)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dashboards/:id : delete the "id" dashboard.
     *
     * @param id the id of the dashboard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/dashboards/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDashboard(@PathVariable Long id) {
        log.debug("REST request to delete Dashboard : {}", id);
        dashboardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dashboard", id.toString())).build();
    }

}
