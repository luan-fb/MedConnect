package com.medconecct.medconecct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medconecct.medconecct.model.Laudo;

@Repository
public interface LaudoRepository extends  JpaRepository<Laudo, Long> {

}
