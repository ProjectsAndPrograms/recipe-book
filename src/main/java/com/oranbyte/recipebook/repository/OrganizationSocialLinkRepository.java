package com.oranbyte.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.OrganizationSocialLink;

@Repository
public interface OrganizationSocialLinkRepository extends JpaRepository<OrganizationSocialLink, Long>{

}
