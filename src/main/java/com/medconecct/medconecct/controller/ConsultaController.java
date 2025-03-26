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

import com.medconecct.medconecct.model.Consulta;
import com.medconecct.medconecct.repository.ConsultaRepository;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    public ResponseEntity<Consulta> agendarConsulta(@RequestBody Consulta consulta) {
        try {
            Consulta novaConsulta = consultaRepository.save(consulta);
            return new ResponseEntity<>(novaConsulta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listarConsultas() {
        return ResponseEntity.ok(consultaRepository.findAll());
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Consulta>> listarPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(consultaRepository.findByMedicoId(medicoId));
    }
}