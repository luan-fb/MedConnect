package com.medconecct.medconecct.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReceitaDTO {

    @NotNull(message = "O ID da consulta é obrigatório")
    private Long consultaId;

    @NotBlank(message = "A descrição da receita é obrigatória")
    private String descricao;

    // Getters e Setters
    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}