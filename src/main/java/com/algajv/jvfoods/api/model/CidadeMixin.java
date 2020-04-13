package com.algajv.jvfoods.api.model;

import com.algajv.jvfoods.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {  // mixin apenas para configuração das anotações do Jackson

    @JsonIgnoreProperties(value="nome", allowGetters = true)
    private Estado estado;
}
