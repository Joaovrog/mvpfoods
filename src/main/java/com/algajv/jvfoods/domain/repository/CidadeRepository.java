package com.algajv.jvfoods.domain.repository;

import com.algajv.jvfoods.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
