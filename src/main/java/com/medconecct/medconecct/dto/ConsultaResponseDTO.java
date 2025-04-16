package com.medconecct.medconecct.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaResponseDTO {
    private Long id;
    private String medico;
    private String paciente;
    private LocalDateTime dataHora;
}
