package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.RestauranteNaoEncontradoException;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RestauranteService {


    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CozinhaService cozinhaService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long idCozinha = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.getByIdOrFail(idCozinha);
        restaurante.setCozinha(cozinha);
        return repository.save(restaurante);
    }

    public Restaurante getByIdOrFail(Long restauranteId) {
        return repository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}
