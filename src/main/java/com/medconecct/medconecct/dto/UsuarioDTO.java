package com.medconecct.medconecct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private String tipo; // "paciente" ou "medico"

    // Campos específicos de Paciente
    private String cpf;
    private String endereco;
    private String telefone;

    // Campos específicos de Medico
    private String crm;
    private String especialidade;
}
