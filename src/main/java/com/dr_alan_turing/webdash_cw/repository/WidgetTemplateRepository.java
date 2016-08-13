package com.dr_alan_turing.webdash_cw.repository;

import com.dr_alan_turing.webdash_cw.domain.WidgetTemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WidgetTemplate entity.
 */
@SuppressWarnings("unused")
public interface WidgetTemplateRepository extends JpaRepository<WidgetTemplate,Long> {

    @Query("select widgetTemplate from WidgetTemplate widgetTemplate where widgetTemplate.creator.login = ?#{principal.username}")
    List<WidgetTemplate> findByCreatorIsCurrentUser();

    Page<WidgetTemplate> findAllByCreatorId(Long creatorId, Pageable pageable);

}
