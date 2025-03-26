package com.medconecct.medconecct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medconecct.medconecct.model.Paciente;
import com.medconecct.medconecct.repository.PacienteRepository;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    
    @PostMapping
    public ResponseEntity<Paciente> criarPaciente(@RequestBody Paciente paciente) {
        try {
            Paciente savedPaciente = pacienteRepository.save(paciente);
            return new ResponseEntity<>(savedPaciente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Listar Todos - OK
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteRepository.findAll());
    }

    // Buscar por ID - OK
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}