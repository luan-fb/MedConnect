package com.medconecct.medconecct.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medconecct.medconecct.dto.UsuarioDTO;
import com.medconecct.medconecct.dto.UsuarioResponseDTO;
import com.medconecct.medconecct.model.Medico;
import com.medconecct.medconecct.model.Paciente;
import com.medconecct.medconecct.model.Usuario;
import com.medconecct.medconecct.model.Usuario.TipoUsuario;
import com.medconecct.medconecct.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Listar todos os usuários (sem expor senhas)
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        usuarios.forEach(usuario -> usuario.setSenha(null)); // Remove a senha antes de enviar a resposta
        return ResponseEntity.ok(usuarios);
    }

    // Buscar um usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setSenha(null);
                    return ResponseEntity.ok(usuario);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioDTO dto) {
    if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
        return ResponseEntity.badRequest().body("Erro: E-mail já cadastrado!");
    }

    if (dto.getSenha() == null || dto.getSenha().isEmpty()) {
        return ResponseEntity.badRequest().body("Erro: A senha é obrigatória!");
    }

    Usuario usuario;

    if ("medico".equalsIgnoreCase(dto.getTipo())) {
        Medico medico = new Medico();
        medico.setCrm(dto.getCrm());
        medico.setEspecialidade(dto.getEspecialidade());
        medico.setTipo(TipoUsuario.MEDICO);
        usuario = medico;
    } else if ("paciente".equalsIgnoreCase(dto.getTipo())) {
        Paciente paciente = new Paciente();
        paciente.setCpf(dto.getCpf());
        paciente.setEndereco(dto.getEndereco());
        paciente.setTelefone(dto.getTelefone());
        paciente.setTipo(TipoUsuario.PACIENTE);
        usuario = paciente;
    } else {
        return ResponseEntity.badRequest().body("Tipo de usuário inválido. Use 'medico' ou 'paciente'.");
    }

    usuario.setNome(dto.getNome());
    usuario.setEmail(dto.getEmail());
    usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

    Usuario novoUsuario = usuarioRepository.save(usuario);
    novoUsuario.setSenha(null);

    return ResponseEntity.ok(UsuarioResponseDTO.from(novoUsuario));
}

    // Atualizar um usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());

            // Se a senha foi alterada, criptografa novamente
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
            }

            Usuario atualizado = usuarioRepository.save(usuario);
            atualizado.setSenha(null); // Remove a senha antes de enviar a resposta
            return ResponseEntity.ok(atualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
