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

import com.medconecct.medconecct.model.Laudo;
import com.medconecct.medconecct.repository.LaudoRepository;

@RestController
@RequestMapping("/api/laudos")
public class LaudoController {

    @Autowired
    private LaudoRepository laudoRepository;

    @PostMapping
    public ResponseEntity<Laudo> emitirLaudo(@RequestBody Laudo laudo) {
        try {
            Laudo novoLaudo = laudoRepository.save(laudo);
            return new ResponseEntity<>(novoLaudo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Laudo>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(laudoRepository.findByPacienteId(pacienteId));
    }
}