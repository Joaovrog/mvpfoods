package com.algajv.jvfoods.api.model.dto;

import com.algajv.jvfoods.domain.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteDTO {

    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private Long id;

    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaDTO cozinha;

    private Boolean ativo;
    private EnderecoDTO endereco;
    private Boolean aberto;
    private List<UsuarioDTO> responsaveis;
}
