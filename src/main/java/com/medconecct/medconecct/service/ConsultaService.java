package com.medconecct.medconecct.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.medconecct.medconecct.dto.ConsultaDTO;
import com.medconecct.medconecct.dto.ConsultaResponseDTO;
import com.medconecct.medconecct.model.Consulta;
import com.medconecct.medconecct.model.Consulta.StatusConsulta;
import com.medconecct.medconecct.model.Medico;
import com.medconecct.medconecct.model.Paciente;
import com.medconecct.medconecct.repository.ConsultaRepository;
import com.medconecct.medconecct.repository.MedicoRepository;
import com.medconecct.medconecct.repository.PacienteRepository;

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

        Consulta consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDataHora(dto.getDataHora());

        Consulta salva = consultaRepository.save(consulta);

        ConsultaResponseDTO response = new ConsultaResponseDTO();
        response.setId(salva.getId());
        response.setMedico(medico.getNome());
        response.setPaciente(paciente.getNome());
        response.setDataHora(salva.getDataHora());

        return response;
    }

    public List<ConsultaResponseDTO> listarPorMedico(Long medicoId) {
        return consultaRepository.findByMedicoId(medicoId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ConsultaResponseDTO> listarPorPaciente(Long pacienteId) {
        return consultaRepository.findByPacienteId(pacienteId).stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ConsultaResponseDTO mapToDTO(Consulta consulta) {
        ConsultaResponseDTO dto = new ConsultaResponseDTO();
        dto.setId(consulta.getId());
        dto.setMedico(consulta.getMedico().getNome());
        dto.setPaciente(consulta.getPaciente().getNome());
        dto.setDataHora(consulta.getDataHora());ot
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
