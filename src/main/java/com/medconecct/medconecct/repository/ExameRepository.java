package com.medconecct.medconecct.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medconecct.medconecct.model.Exame;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {

    // Busca exames por paciente
    List<Exame> findByPacienteId(Long pacienteId);

    // Busca exames por médico
    List<Exame> findByMedicoId(Long medicoId);

    // Busca exames por tipo
    List<Exame> findByTipoExame(String tipoExame);

    // Busca exames por data de realização
    List<Exame> findByDataRealizacao(LocalDate dataRealizacao);

    // Consulta personalizada com JPQL
    @Query("SELECT e FROM Exame e WHERE e.paciente.id = :pacienteId AND e.dataRealizacao BETWEEN :dataInicio AND :dataFim")
    List<Exame> findExamesPorPacienteEPeriodo(
            @Param("pacienteId") Long pacienteId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );

    // Consulta nativa para estatísticas
    @Query(value = "SELECT COUNT(*) FROM exames WHERE tipo_exame = :tipo AND data_realizacao >= :data", nativeQuery = true)
    Long countExamesByTipoAndData(
            @Param("tipo") String tipoExame,
            @Param("data") LocalDate data
    );
}