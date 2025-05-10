package com.medconecct.medconecct.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medconecct.medconecct.dto.ConsultaDTO;
import com.medconecct.medconecct.dto.ConsultaResponseDTO;
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
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorMedico(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.listarPorMedico(id));
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.listarPorPaciente(id));
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
