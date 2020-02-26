package com.algajv.jvfoods.domain.repository;


import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();
    Estado buscar(Long id);
    Estado salvar(Estado estado);
    void remover(Estado estado);
}
