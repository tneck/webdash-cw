package com.dr_alan_turing.webdash_cw.service;

import com.dr_alan_turing.webdash_cw.domain.Dashboard;
import com.dr_alan_turing.webdash_cw.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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
     * Create a dashboard for given user.
     *
     * @param user the user for which to create a dashboard
     * @return the created dashboard
     */
    Dashboard createForUser(User user);

    /**
     *  Get all the dashboards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Dashboard> findAll(Pageable pageable);

    /**
     *  Get one dashboard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Dashboard findOne(Long id);

    /**
     *  Get one dashboard by userId.
     *
     *  @param userId the id of the user
     *  @return the entity
     */
    Dashboard findOneByUserId(Long userId);

    /**
     *  Delete dashboard by id.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Delete dashboard by userId.
     *
     *  @param userId the id of the user
     */
    void deleteByUserId(Long userId);

}
