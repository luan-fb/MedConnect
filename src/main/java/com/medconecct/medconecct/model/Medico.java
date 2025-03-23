package com.medconecct.medconecct.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Medico extends Usuario{
    private String crm;
    private String especialidade;
    
    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;
}
