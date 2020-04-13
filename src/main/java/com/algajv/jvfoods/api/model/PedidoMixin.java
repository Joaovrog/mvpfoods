package com.algajv.jvfoods.api.model;

import com.algajv.jvfoods.domain.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PedidoMixin {  // mixin apenas para configuração das anotações do Jackson

    @JsonIgnore
    private Endereco enderecoEntrega;
}
