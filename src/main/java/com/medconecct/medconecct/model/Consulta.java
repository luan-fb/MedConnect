package com.medconecct.medconecct.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @Column(nullable = false)
    private LocalDateTime dataHora;
    
    @Enumerated(EnumType.STRING)
    private StatusConsulta status;
    
    private String observacoes;
    
    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL)
    private Receita receita;
    
    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL)
    private Laudo laudo;
    
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL)
    private List<Exame> exames;
    
    public enum StatusConsulta {
        AGENDADA, CONCLUIDA, CANCELADA, EM_ANDAMENTO
    }
}