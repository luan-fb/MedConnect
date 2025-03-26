package com.medconecct.medconecct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medconecct.medconecct.model.Exame;

@Repository
public interface ExameRepository extends  JpaRepository<Exame, Long> {

}
