package com.dr_alan_turing.webdash_cw.service.impl;

import com.dr_alan_turing.webdash_cw.domain.User;
import com.dr_alan_turing.webdash_cw.service.DashboardService;
import com.dr_alan_turing.webdash_cw.domain.Dashboard;
import com.dr_alan_turing.webdash_cw.repository.DashboardRepository;
import com.dr_alan_turing.webdash_cw.web.rest.errors.CustomParameterizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Dashboard.
 */
@Service
@Transactional
public class DashboardServiceImpl implements DashboardService{

    private final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Inject
    private DashboardRepository dashboardRepository;

    /**
     * Save a dashboard.
     *
     * @param dashboard the entity to save
     * @return the persisted entity
     */
    public Dashboard save(Dashboard dashboard) {
        log.debug("Request to save Dashboard : {}", dashboard);
        Dashboard existingDashboard = dashboardRepository.findOneByUserId( dashboard.getUser() == null ? null : dashboard.getUser().getId() );
        if(existingDashboard != null && existingDashboard.getId() != dashboard.getId()){
            throw new CustomParameterizedException("The given user already has a dashboard.");
        }
        Dashboard result = dashboardRepository.save(dashboard);
        return result;
    }

    /**
     * Create a dashboard for given user.
     *
     * @param user the user for which to create a dashboard
     * @return the created dashboard
     */
    public Dashboard createForUser(User user) {
        log.debug("Request to create Dashboard for User : {}", user);
        Dashboard dashboard = new Dashboard();
        dashboard.setUser(user);
        return save(dashboard);
    }

    /**
     *  Get all the dashboards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Dashboard> findAll(Pageable pageable) {
        log.debug("Request to get all Dashboards");
        Page<Dashboard> result = dashboardRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one dashboard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Dashboard findOne(Long id) {
        log.debug("Request to get Dashboard : {}", id);
        Dashboard dashboard = dashboardRepository.findOne(id);
        return dashboard;
    }

    /**
     *  Get one dashboard by userId.
     *
     *  @param userId the id of the user
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Dashboard findOneByUserId(Long userId) {
        log.debug("Request to get Dashboard of User with id : {}", userId);
        Dashboard dashboard = dashboardRepository.findOneByUserId(userId);
        return dashboard;
    }


    /**
     *  Delete dashboard by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Dashboard : {}", id);
        dashboardRepository.delete(id);
    }

    /**
     *  Delete dashboard by userId.
     *
     *  @param userId the id of the user
     */
    public void deleteByUserId(Long userId) {
        log.debug("Request to delete Dashboard of User with id : {}", userId);
        dashboardRepository.deleteByUserId(userId);
    }

}
