package com.medconecct.medconecct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medconecct.medconecct.model.Usuario;

public interface UsuarioRepository extends  JpaRepository<Usuario, Long>{

}
