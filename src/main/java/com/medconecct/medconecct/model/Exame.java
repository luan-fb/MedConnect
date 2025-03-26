package com.medconecct.medconecct.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exames")
public class Exame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
    
    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;
    
    private String tipoExame;
    private String resultado;
    private LocalDate dataSolicitacao;
    private LocalDate dataRealizacao;
    private String observacoes;
}