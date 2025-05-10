package com.medconecct.medconecct.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medconecct.medconecct.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByMedicoId(Long medicoId);

    List<Consulta> findByPacienteId(Long pacienteId);

    boolean existsByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);

}