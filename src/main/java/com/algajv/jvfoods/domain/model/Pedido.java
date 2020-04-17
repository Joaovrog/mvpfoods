package com.algajv.jvfoods.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @Column(nullable = true)
    private OffsetDateTime dataConfirmacao;

    @Column(nullable = true)
    private OffsetDateTime dataCancelamento;

    @Column(nullable = true)
    private OffsetDateTime dataEntrega;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @Embedded
    @JsonIgnore
    private Endereco enderecoEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL) // ao salvar o pedido, queremos salvar os itens do pedido também, em cascata.
    private List<ItemPedido> itens = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;


    public void calcularValorTotal() {
        this.subtotal = getItens().stream()
                .map(item -> item.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void definirFrete() {
        setTaxaFrete(getRestaurante().getTaxaFrete());
    }

    public void atribuirPedidoAosItens() {
        getItens().forEach(item -> item.setPedido(this));
    }

}
