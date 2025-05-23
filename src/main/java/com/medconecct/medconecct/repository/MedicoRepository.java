package com.medconecct.medconecct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medconecct.medconecct.model.Medico;

@Repository
public interface MedicoRepository extends  JpaRepository<Medico, Long>{

}
