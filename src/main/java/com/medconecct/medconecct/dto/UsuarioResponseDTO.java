
package com.medconecct.medconecct.dto;

import com.medconecct.medconecct.model.Usuario;

public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String tipo;

    public UsuarioResponseDTO(Long id, String nome, String email, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public static UsuarioResponseDTO from(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTipo().name()
        );
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo;
    }
}
