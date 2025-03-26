package com.medconecct.medconecct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medconecct.medconecct.model.Consulta;

@Repository
public interface ConsultaRepository extends  JpaRepository<Consulta, Long> {

}
