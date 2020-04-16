package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.ProdutoNaoEncontradoNoRestauranteException;
import com.algajv.jvfoods.domain.exception.RestauranteNaoEncontradoException;
import com.algajv.jvfoods.domain.model.Produto;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Produto getByIdOrFail(Long restauranteId, Long produtoId) {
        return repository.findById(restauranteId, produtoId).orElseThrow(() -> new ProdutoNaoEncontradoNoRestauranteException(restauranteId, produtoId));
    }


}
