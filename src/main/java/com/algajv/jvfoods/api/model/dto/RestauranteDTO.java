package com.algajv.jvfoods.api.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaDTO cozinha;
    private Boolean ativo;
    private EnderecoDTO endereco;
    private Boolean aberto;
    private List<UsuarioDTO> responsaveis;
}
