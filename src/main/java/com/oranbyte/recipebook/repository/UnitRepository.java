package com.oranbyte.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

}
