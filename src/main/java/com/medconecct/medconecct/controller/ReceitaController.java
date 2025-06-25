package com.medconecct.medconecct.controller;

import com.medconecct.medconecct.dto.ReceitaDTO;
import com.medconecct.medconecct.dto.ReceitaResponseDTO;
import com.medconecct.medconecct.model.Usuario;
import com.medconecct.medconecct.service.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping
    public ResponseEntity<ReceitaResponseDTO> criarReceita(
            @Valid @RequestBody ReceitaDTO receitaDTO,
            @AuthenticationPrincipal Usuario medicoLogado) {

        ReceitaResponseDTO novaReceita = receitaService.criarReceita(receitaDTO, medicoLogado);
        URI uri = URI.create("/receitas/" + novaReceita.getId());
        return ResponseEntity.created(uri).body(novaReceita);
    }
}