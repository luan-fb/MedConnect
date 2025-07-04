package com.medconecct.medconecct.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.medconecct.medconecct.dto.ConsultaDTO;
import com.medconecct.medconecct.dto.ConsultaResponseDTO;
import com.medconecct.medconecct.exception.ConflitoHorarioException;
import com.medconecct.medconecct.model.Consulta;
import com.medconecct.medconecct.model.Consulta.StatusConsulta;
import com.medconecct.medconecct.model.Medico;
import com.medconecct.medconecct.model.Paciente;
import com.medconecct.medconecct.repository.ConsultaRepository;
import com.medconecct.medconecct.repository.MedicoRepository;
import com.medconecct.medconecct.repository.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import com.medconecct.medconecct.model.Consulta.StatusConsulta;
import jakarta.transaction.Transactional;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public ConsultaResponseDTO agendarConsulta(ConsultaDTO dto) {
        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // --- NOVA VALIDAÇÃO ---
        // Verifica se já existe uma consulta para o mesmo médico no mesmo horário
        if (consultaRepository.existsByMedicoIdAndDataHora(medico.getId(), dto.getDataHora())) {
            throw new ConflitoHorarioException("O médico já possui uma consulta agendada para este horário.");
        }
        // --- FIM DA VALIDAÇÃO ---

        Consulta consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDataHora(dto.getDataHora());
        consulta.setStatus(StatusConsulta.AGENDADA); // Define o status inicial

        Consulta salva = consultaRepository.save(consulta);

        // Reutilizando o método mapToDTO que já existe e é mais completo
        return mapToDTO(salva);
    }

    public Page<ConsultaResponseDTO> listarPorMedico(Long medicoId, LocalDateTime dataInicio, LocalDateTime dataFim,
            Pageable pageable) {
        medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com o ID: " + medicoId));

        Page<Consulta> consultas;

        // Se as datas não forem fornecidas, busca tudo. Se forem, filtra pelo período.
        if (dataInicio != null && dataFim != null) {
            consultas = consultaRepository.findByMedicoIdAndDataHoraBetween(medicoId, dataInicio, dataFim, pageable);
        } else {
            consultas = consultaRepository.findByMedicoId(medicoId, pageable);
        }

        return consultas.map(this::mapToDTO); // Mapeia a página de Consulta para uma página de DTO
    }

    public Page<ConsultaResponseDTO> listarPorPaciente(Long pacienteId, Consulta.StatusConsulta status,
            Pageable pageable) {
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com o ID: " + pacienteId));

        Page<Consulta> consultas;

        if (status != null) {
            consultas = consultaRepository.findByPacienteIdAndStatus(pacienteId, status, pageable);
        } else {
            consultas = consultaRepository.findByPacienteId(pacienteId, pageable);
        }

        return consultas.map(this::mapToDTO);
    }

    private ConsultaResponseDTO mapToDTO(Consulta consulta) {
        ConsultaResponseDTO dto = new ConsultaResponseDTO();
        dto.setId(consulta.getId());
        dto.setMedico(consulta.getMedico().getNome());
        dto.setPaciente(consulta.getPaciente().getNome());
        dto.setDataHora(consulta.getDataHora());
        dto.setStatus(consulta.getStatus().name());

        return dto;
    }

    public ConsultaResponseDTO buscarPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        return mapToDTO(consulta);
    }

    @Transactional
    public void marcarComoRealizada(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setStatus(StatusConsulta.AGENDADA);
        consultaRepository.save(consulta);
    }

    @Transactional
    public void cancelarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

}
