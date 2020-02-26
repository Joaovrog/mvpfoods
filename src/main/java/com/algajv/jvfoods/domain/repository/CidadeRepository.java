package com.algajv.jvfoods.domain.repository;

import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.model.Estado;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();
    Cidade buscar(Long id);
    Cidade salvar(Cidade cidade);
    void remover(Cidade cidade);
}
