package com.medconecct.medconecct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medconecct.medconecct.model.Receita;

@Repository
public interface ReceitaRepository extends  JpaRepository<Receita, Long>{

}
