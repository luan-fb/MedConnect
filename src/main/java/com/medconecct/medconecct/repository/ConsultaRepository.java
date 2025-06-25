package com.medconecct.medconecct.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.medconecct.medconecct.model.Consulta;
import com.medconecct.medconecct.model.Consulta.StatusConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Page<Consulta> findByMedicoId(Long medicoId, Pageable pageable);

    Page<Consulta> findByPacienteId(Long pacienteId, Pageable pageable);

    Page<Consulta> findByPacienteIdAndStatus(Long pacienteId, StatusConsulta status, Pageable pageable);

    boolean existsByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);

    Page<Consulta> findByMedicoIdAndDataHoraBetween(Long medicoId, LocalDateTime dataInicio, LocalDateTime dataFim,
            Pageable pageable);

}