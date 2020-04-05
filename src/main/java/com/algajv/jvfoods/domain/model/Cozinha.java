package com.algajv.jvfoods.domain.model;

import com.algajv.jvfoods.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull (groups = Groups.CozinhaId.class)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @JsonIgnore // evitar o loop de serialização na hora de buscar os objetos visualmente.
    @OneToMany(mappedBy = "cozinha") // Qual o nome da propriedade do dono do relaçionamento (Restaurante) que se relaciona com Cozinha?
   private List<Restaurante> restaurantes = new ArrayList<>();

}
