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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;


    @Column(name="taxa_frete", nullable = false)
    private BigDecimal taxaFrete;


//    @JsonIgnoreProperties("hibernateLazyInitializer") // ignora o campo 'hibernateLazyInitializer' da classe proxy de Cozinha (criada pelo Hibernate em tempo de execução por conta do fetch Lazy), evitando exception de serialização dessa propriedade.
    @ManyToOne //(fetch = FetchType.LAZY) // mudando o tipo de fetch padrão!
//    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    private Boolean ativo = Boolean.TRUE;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
        joinColumns = @JoinColumn(name="restaurante_id"), inverseJoinColumns = @JoinColumn(name="forma_pagamento_id"))
    private Set<FormaPagamento> formaPagamentos = new HashSet<>(); // Set = conjunto. Não aceita elementos duplicados.

    @OneToMany(mappedBy = "restaurante") // Qual o nome da propriedade do dono do relaçionamento (Produto) que se relaciona com Cozinha?
    private List<Produto> produtos = new ArrayList<>();

    private Boolean aberto = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name="restaurante_id"), inverseJoinColumns = @JoinColumn(name="usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>(); // Set = conjunto. Não aceita elementos duplicados.


    public void ativar() {
        setAtivo(true);
    }

    public void desativar() {
        setAtivo(false);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return getFormaPagamentos().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormaPagamentos().add(formaPagamento);
    }

    public boolean associarResponsavel(Usuario usuario) {
        return getResponsaveis().add(usuario);
    }

    public boolean desassociarResponsavel(Usuario usuario) {
        return getResponsaveis().remove(usuario);
    }

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return getFormaPagamentos().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }


}
