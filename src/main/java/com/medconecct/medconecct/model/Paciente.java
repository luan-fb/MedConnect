package com.medconecct.medconecct.model;

import java.util.List;

import jakarta.persistence.OneToMany;

public class Paciente extends Usuario {

    private String cpf;
    private String endereco;

    
    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;


    public String getCpf() {
        return cpf;
    }


    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public String getEndereco() {
        return endereco;
    }


    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public List<Consulta> getConsultas() {
        return consultas;
    }


    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }


    
}
