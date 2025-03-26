package com.medconecct.medconecct.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medconecct.medconecct.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    // Métodos básicos de consulta
    List<Receita> findByPacienteId(Long pacienteId);

    List<Receita> findByMedicoId(Long medicoId);

    List<Receita> findByConsultaId(Long consultaId);

    // Consultas por período
    List<Receita> findByDataEmissaoBetween(LocalDate inicio, LocalDate fim);

    // Consulta por medicamento (contém texto)
    List<Receita> findByMedicamentosContaining(String medicamento);

    // Consulta personalizada com JPQL
    @Query("SELECT r FROM Receita r WHERE r.paciente.id = :pacienteId AND r.medico.id = :medicoId")
    List<Receita> findByPacienteAndMedico(
            @Param("pacienteId") Long pacienteId,
            @Param("medicoId") Long medicoId);

    // Consulta para relatórios (agrupando por médico)
    @Query("SELECT r.medico.nome, COUNT(r) FROM Receita r GROUP BY r.medico.nome")
    List<Object[]> countReceitasPorMedico();

    // Consulta nativa para busca complexa
    @Query(value = """
            SELECT r.* FROM receitas r
            JOIN pacientes p ON r.paciente_id = p.id
            WHERE p.nome LIKE CONCAT('%', :nomePaciente, '%')
            AND r.data_emissao >= :dataInicio
            """, nativeQuery = true)
    List<Receita> findReceitasAvancadas(
            @Param("nomePaciente") String nomePaciente,
            @Param("dataInicio") LocalDate dataInicio);
}