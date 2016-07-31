package com.dr_alan_turing.webdash_cw.repository;

import com.dr_alan_turing.webdash_cw.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
