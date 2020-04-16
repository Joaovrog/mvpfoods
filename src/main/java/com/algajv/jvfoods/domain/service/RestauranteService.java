package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.RestauranteNaoEncontradoException;
import com.algajv.jvfoods.domain.model.*;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class RestauranteService {


    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private UsuarioService usuarioService;

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

    @Transactional  // não deixar a transacao do 'ativar(Long restauranteId)' trabalhar, pois pode dar erro em algum objeto. Colocamos toda a operação em massa na transacao.
    public void ativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
    }

    @Transactional
    public void desativar(Long restauranteId) {
        Restaurante restauranteEncontrado = getByIdOrFail(restauranteId);
        restauranteEncontrado.desativar();

    }

    @Transactional
    public void desativarMultiplos(List<Long> restauranteIds) {
        restauranteIds.forEach(this::desativar);

    }


    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {

        Restaurante restaurante = getByIdOrFail(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.getByIdOrFail(formaPagamentoId);
        restaurante.removerFormaPagamento(formaPagamento);
    }


    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {

        Restaurante restaurante = getByIdOrFail(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.getByIdOrFail(formaPagamentoId);
        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {

        Restaurante restaurante = getByIdOrFail(restauranteId);
        Usuario responsavel = usuarioService.getByIdOrFail(usuarioId);
        restaurante.associarResponsavel(responsavel);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {

        Restaurante restaurante = getByIdOrFail(restauranteId);
        Usuario responsavel = usuarioService.getByIdOrFail(usuarioId);
        restaurante.desassociarResponsavel(responsavel);
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = getByIdOrFail(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = getByIdOrFail(restauranteId);
        restaurante.fechar();
    }

    public Restaurante getByIdOrFail(Long restauranteId) {
        return repository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}
