package com.medconecct.medconecct.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.medconecct.medconecct.dto.ConsultaDTO;
import com.medconecct.medconecct.dto.ConsultaResponseDTO;
import com.medconecct.medconecct.model.Consulta;
import com.medconecct.medconecct.model.Usuario;
import com.medconecct.medconecct.service.ConsultaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@Valid @RequestBody ConsultaDTO dto) {
        return ResponseEntity.ok(consultaService.agendarConsulta(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @GetMapping("/medico/{id}")
    public ResponseEntity<Page<ConsultaResponseDTO>> listarConsultasPorMedico(
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            Pageable pageable) {

        Page<ConsultaResponseDTO> consultas = consultaService.listarPorMedico(id, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<Page<ConsultaResponseDTO>> listarConsultasPorPaciente(
            @PathVariable Long id,
            @RequestParam(required = false) Consulta.StatusConsulta status,
            Pageable pageable) {

        Page<ConsultaResponseDTO> consultas = consultaService.listarPorPaciente(id, status, pageable);
        return ResponseEntity.ok(consultas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarConsulta(@PathVariable Long id) {
        consultaService.cancelarConsulta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/realizar")
    public ResponseEntity<Void> marcarComoRealizada(@PathVariable Long id) {
        consultaService.marcarComoRealizada(id);
        return ResponseEntity.noContent().build();
    }

}
