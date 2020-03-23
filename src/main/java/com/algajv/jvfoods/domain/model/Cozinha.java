package com.algajv.jvfoods.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Cozinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @JsonIgnore // evitar o loop de serialização na hora de buscar os objetos visualmente.
    @OneToMany(mappedBy = "cozinha") // Qual o nome da propriedade do dono do relaçionamento (Restaurante) que se relaciona com Cozinha?
   private List<Restaurante> restaurantes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cozinha cozinha = (Cozinha) o;
        return Objects.equals(id, cozinha.id) &&
                Objects.equals(nome, cozinha.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
