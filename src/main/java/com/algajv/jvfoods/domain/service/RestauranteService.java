package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.RestauranteNaoEncontradoException;
import com.algajv.jvfoods.domain.model.Cidade;
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

    @Autowired
    private CidadeService cidadeService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long idCozinha = restaurante.getCozinha().getId();
        Long idCidade = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.getByIdOrFail(idCozinha);
        Cidade cidade = cidadeService.getByIdOrFail(idCidade);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return repository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restauranteEncontrado = getByIdOrFail(restauranteId);
        restauranteEncontrado.ativar();
        // Aqui, essa instância do restaurante fica num estado gerenciado pelo contexto de persistência do JPA, fazendo qualquer alteração ser sincronizada com o banco.
        // Não precisamos chamar o save pra salvar essa alteração no banco de dados.
    }

    @Transactional
    public void desativar(Long restauranteId) {
        Restaurante restauranteEncontrado = getByIdOrFail(restauranteId);
        restauranteEncontrado.desativar();

    }

    public Restaurante getByIdOrFail(Long restauranteId) {
        return repository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}
