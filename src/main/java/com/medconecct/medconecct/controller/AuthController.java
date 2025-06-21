package com.medconecct.medconecct.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medconecct.medconecct.dto.LoginRequest;
import com.medconecct.medconecct.dto.UsuarioDTO;
import com.medconecct.medconecct.dto.UsuarioResponseDTO;
import com.medconecct.medconecct.model.Medico;
import com.medconecct.medconecct.model.Paciente;
import com.medconecct.medconecct.model.Usuario;
import com.medconecct.medconecct.model.Usuario.TipoUsuario;
import com.medconecct.medconecct.repository.UsuarioRepository;
import com.medconecct.medconecct.security.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String senha = loginRequest.getPassword();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(senha, usuario.getPassword())) {
                String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRole());
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            }
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: E-mail já cadastrado!");
        }

        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
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
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        Usuario novoUsuario = usuarioRepository.save(usuario);
        novoUsuario.setPassword(null);

        return ResponseEntity.ok(UsuarioResponseDTO.from(novoUsuario));
    }
}
