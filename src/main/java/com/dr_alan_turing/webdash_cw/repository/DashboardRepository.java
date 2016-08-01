package com.dr_alan_turing.webdash_cw.repository;

import com.dr_alan_turing.webdash_cw.domain.Dashboard;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Dashboard entity.
 */
@SuppressWarnings("unused")
public interface DashboardRepository extends JpaRepository<Dashboard,Long> {

    @Query("select dashboard from Dashboard dashboard where dashboard.user.login = ?#{principal.username}")
    List<Dashboard> findByUserIsCurrentUser();

}
