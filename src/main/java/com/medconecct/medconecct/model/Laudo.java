package com.medconecct.medconecct.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "laudos")
public class Laudo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @OneToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;
    
    @OneToOne
    @JoinColumn(name = "exame_id")
    private Exame exame;
    
    @Column(nullable = false)
    private LocalDate dataEmissao;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    private String diagnostico;
}