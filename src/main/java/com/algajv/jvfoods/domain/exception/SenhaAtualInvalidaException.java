package com.algajv.jvfoods.domain.exception;


public class SenhaAtualInvalidaException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public SenhaAtualInvalidaException(String mensagem) {
        super( mensagem);
    }

    public SenhaAtualInvalidaException() {
        this(String.format("Senha atual informada não coincide com a senha do usuário."));
    }
}
