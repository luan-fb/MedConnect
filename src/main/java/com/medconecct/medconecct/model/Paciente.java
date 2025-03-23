package com.medconecct.medconecct.model;

import java.util.List;

import jakarta.persistence.OneToMany;

public class Paciente extends Usuario {

    private String cpf;
    private String endereco;

    
    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;
}
