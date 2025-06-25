package com.medconecct.medconecct.dto;

import com.medconecct.medconecct.model.Receita;
import java.time.LocalDateTime;

public class ReceitaResponseDTO {

    private Long id;
    private String descricao;
    private LocalDateTime dataEmissao;
    private Long consultaId;
    private String nomeMedico;
    private String nomePaciente;

    public ReceitaResponseDTO(Receita receita) {
        this.id = receita.getId();
        this.descricao = receita.getDescricao();
        this.dataEmissao = receita.getDataEmissao();
        this.consultaId = receita.getConsulta().getId();
        this.nomeMedico = receita.getMedico().getNome();
        this.nomePaciente = receita.getConsulta().getPaciente().getNome();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public Long getConsultaId() {
        return consultaId;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }
}