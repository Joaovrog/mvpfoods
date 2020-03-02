package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.CozinhaRepository;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long idCozinha = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(idCozinha).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha" +
                " com o código %d", idCozinha)));


        restaurante.setCozinha(cozinha);
        return repository.salvar(restaurante);
    }
}
