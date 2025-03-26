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

import com.medconecct.medconecct.model.Exame;
import com.medconecct.medconecct.repository.ExameRepository;

@RestController
@RequestMapping("/api/exames")
public class ExameController {

    @Autowired
    private ExameRepository exameRepository;

    @PostMapping
    public ResponseEntity<Exame> solicitarExame(@RequestBody Exame exame) {
        try {
            Exame novoExame = exameRepository.save(exame);
            return new ResponseEntity<>(novoExame, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Exame>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(exameRepository.findByPacienteId(pacienteId));
    }
}