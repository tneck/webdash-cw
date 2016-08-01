package com.dr_alan_turing.webdash_cw.service;

import com.dr_alan_turing.webdash_cw.domain.Dashboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Dashboard.
 */
public interface DashboardService {

    /**
     * Save a dashboard.
     * 
     * @param dashboard the entity to save
     * @return the persisted entity
     */
    Dashboard save(Dashboard dashboard);

    /**
     *  Get all the dashboards.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Dashboard> findAll(Pageable pageable);

    /**
     *  Get the "id" dashboard.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Dashboard findOne(Long id);

    /**
     *  Delete the "id" dashboard.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
