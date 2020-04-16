package com.algajv.jvfoods.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoDTO {

    private Long id;
    private Integer quantidade;
    private BigDecimal precoTotal;
    private String observacao;
    private String produtoNome;
    private BigDecimal precoUnitario;
}
