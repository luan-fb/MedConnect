package com.medconecct.medconecct.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medconecct.medconecct.model.Laudo;

@Repository
public interface LaudoRepository extends JpaRepository<Laudo, Long> {

    // Métodos básicos de consulta
    List<Laudo> findByPacienteId(Long pacienteId);
    
    List<Laudo> findByMedicoId(Long medicoId);
    
    List<Laudo> findByConsultaId(Long consultaId);
    
    List<Laudo> findByExameId(Long exameId);

    // Consultas por período
    List<Laudo> findByDataEmissaoBetween(LocalDate inicio, LocalDate fim);
    
    // Consulta por diagnóstico (contém texto)
    List<Laudo> findByDiagnosticoContaining(String diagnostico);

    // Consulta personalizada com JPQL
    @Query("SELECT l FROM Laudo l WHERE l.paciente.id = :pacienteId AND l.medico.especialidade = :especialidade")
    List<Laudo> findByPacienteAndEspecialidadeMedica(
            @Param("pacienteId") Long pacienteId,
            @Param("especialidade") String especialidade
    );

    // Consulta para dashboard (laudos recentes)
    @Query("SELECT l FROM Laudo l WHERE l.dataEmissao >= :data ORDER BY l.dataEmissao DESC")
    List<Laudo> findLaudosRecentes(@Param("data") LocalDate data);
    
    // Consulta nativa para relatórios
    @Query(value = """
        SELECT l.* FROM laudos l 
        JOIN pacientes p ON l.paciente_id = p.id 
        WHERE p.nome LIKE CONCAT('%', :nomePaciente, '%')
        AND l.data_emissao BETWEEN :dataInicio AND :dataFim
        """, nativeQuery = true)
    List<Laudo> findLaudosPorPacienteEPeriodo(
            @Param("nomePaciente") String nomePaciente,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );

    // Contagem de laudos por médico
    @Query("SELECT l.medico.nome, COUNT(l) FROM Laudo l GROUP BY l.medico.nome")
    List<Object[]> countLaudosPorMedico();
}