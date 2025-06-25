package com.medconecct.medconecct.service;

import com.medconecct.medconecct.dto.ReceitaDTO;
import com.medconecct.medconecct.dto.ReceitaResponseDTO;
import com.medconecct.medconecct.model.Consulta;
import com.medconecct.medconecct.model.Medico;
import com.medconecct.medconecct.model.Receita;
import com.medconecct.medconecct.model.Usuario;
import com.medconecct.medconecct.repository.ConsultaRepository;
import com.medconecct.medconecct.repository.MedicoRepository;
import com.medconecct.medconecct.repository.ReceitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;

    public ReceitaService(ReceitaRepository receitaRepository, ConsultaRepository consultaRepository,
            MedicoRepository medicoRepository) {
        this.receitaRepository = receitaRepository;
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
    }

    @Transactional
    public ReceitaResponseDTO criarReceita(ReceitaDTO dto, Usuario medicoLogado) {
        Consulta consulta = consultaRepository.findById(dto.getConsultaId())
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com o ID: " + dto.getConsultaId()));

        // Validação de segurança: verifica se o médico logado é o mesmo médico da
        // consulta
        if (!consulta.getMedico().getId().equals(medicoLogado.getId())) {
            throw new SecurityException("Acesso negado: Você só pode criar receitas para suas próprias consultas.");
        }

        Receita receita = new Receita();
        receita.setDescricao(dto.getDescricao());
        receita.setDataEmissao(LocalDateTime.now());
        receita.setConsulta(consulta);
        receita.setMedico((Medico) medicoLogado); // Faz o cast do Usuario para Medico

        Receita novaReceita = receitaRepository.save(receita);
        return new ReceitaResponseDTO(novaReceita);
    }
}