package com.medconecct.medconecct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medconecct.medconecct.model.Paciente;

@Repository
public interface PacienteRepository extends  JpaRepository<Paciente, Long> {

}
