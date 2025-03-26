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

import com.medconecct.medconecct.model.Receita;
import com.medconecct.medconecct.repository.ReceitaRepository;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @PostMapping
    public ResponseEntity<Receita> emitirReceita(@RequestBody Receita receita) {
        try {
            Receita novaReceita = receitaRepository.save(receita);
            return new ResponseEntity<>(novaReceita, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Receita>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(receitaRepository.findByPacienteId(pacienteId));
    }
}