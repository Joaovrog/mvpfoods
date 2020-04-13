package com.algajv.jvfoods.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
//    @NotBlank
    private String nome;

//    @PositiveOrZero
//    @NotNull
    @Column(name="taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

//    @JsonIgnore
//    @JsonIgnoreProperties("hibernateLazyInitializer") // ignora o campo 'hibernateLazyInitializer' da classe proxy de Cozinha (criada pelo Hibernate em tempo de execução por conta do fetch Lazy), evitando exception de serialização dessa propriedade.
    @ManyToOne //(fetch = FetchType.LAZY) // mudando o tipo de fetch padrão!
//    @NotNull
    @Valid
//    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
        joinColumns = @JoinColumn(name="restaurante_id"), inverseJoinColumns = @JoinColumn(name="forma_pagamento_id"))
    private List<FormaPagamento> formaPagamentos = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante") // Qual o nome da propriedade do dono do relaçionamento (Produto) que se relaciona com Cozinha?
    private List<Produto> produtos = new ArrayList<>();

}
