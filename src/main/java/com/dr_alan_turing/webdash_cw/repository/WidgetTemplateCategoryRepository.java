package com.dr_alan_turing.webdash_cw.repository;

import com.dr_alan_turing.webdash_cw.domain.WidgetTemplateCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WidgetTemplateCategory entity.
 */
@SuppressWarnings("unused")
public interface WidgetTemplateCategoryRepository extends JpaRepository<WidgetTemplateCategory,Long> {

}
