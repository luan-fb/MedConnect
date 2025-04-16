package com.medconecct.medconecct.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaDTO {
    @NotNull(message = "O ID do médico é obrigatório")
    private Long medicoId;

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long pacienteId;

    @Future(message = "A data e hora da consulta devem estar no futuro")
    private LocalDateTime dataHora;
}
