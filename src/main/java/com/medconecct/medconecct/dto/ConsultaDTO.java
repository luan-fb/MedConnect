package com.medconecct.medconecct.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public class ConsultaDTO {
    @NotNull(message = "O ID do médico é obrigatório")
    private Long medicoId;

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long pacienteId;

    @Future(message = "A data e hora da consulta devem estar no futuro")
    private LocalDateTime dataHora;

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

}
