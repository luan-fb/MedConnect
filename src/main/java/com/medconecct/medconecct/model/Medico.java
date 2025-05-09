package com.medconecct.medconecct.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicos")
public class Medico extends Usuario {
    @Column(nullable = false, unique = true)
    private String crm;

    private String especialidade;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Receita> receitas;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Laudo> laudos;

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public List<Laudo> getLaudos() {
        return laudos;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public void setLaudos(List<Laudo> laudos) {
        this.laudos = laudos;
    }

}