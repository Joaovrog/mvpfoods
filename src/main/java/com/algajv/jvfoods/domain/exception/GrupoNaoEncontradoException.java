package com.algajv.jvfoods.domain.exception;


public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public GrupoNaoEncontradoException(String mensagem) {
        super( mensagem);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        this(String.format("Não existe grupo com o código %d.", grupoId));
    }
}
