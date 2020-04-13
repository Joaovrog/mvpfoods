package com.algajv.jvfoods.api.model;

import com.algajv.jvfoods.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class CozinhaMixin {  // mixin apenas para configuração das anotações do Jackson

    @JsonIgnore // evitar o loop de serialização na hora de buscar os objetos visualmente.
    private List<Restaurante> restaurantes = new ArrayList<>();
}
