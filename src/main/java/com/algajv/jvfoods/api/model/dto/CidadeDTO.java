package com.algajv.jvfoods.api.model.dto;

import com.algajv.jvfoods.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDTO {

    private Long id;
    private String nome;
    private EstadoDTO estado;
}
