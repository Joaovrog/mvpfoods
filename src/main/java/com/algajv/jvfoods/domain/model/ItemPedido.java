package com.algajv.jvfoods.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private Integer quantidade;
    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    public void calculaPrecoTotalDoItemPedido() {

        if (getPrecoUnitario() == null) {
            this.precoUnitario = BigDecimal.ZERO;
        }

        if (getQuantidade() == null) {
            this.quantidade = 0;
        }
        this.precoTotal = precoUnitario.multiply(new BigDecimal(quantidade));
    }


}
