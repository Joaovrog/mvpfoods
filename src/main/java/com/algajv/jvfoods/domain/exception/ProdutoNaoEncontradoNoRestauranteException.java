package com.algajv.jvfoods.domain.exception;


public class ProdutoNaoEncontradoNoRestauranteException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoNoRestauranteException(String mensagem) {
        super( mensagem);
    }

    public ProdutoNaoEncontradoNoRestauranteException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe cadastro de produto com o código %d. para o restaurante de código %d", produtoId, restauranteId));
    }
}
