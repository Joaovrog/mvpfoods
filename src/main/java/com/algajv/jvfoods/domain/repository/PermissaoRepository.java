package com.algajv.jvfoods.domain.repository;

import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
