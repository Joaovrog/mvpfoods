package com.algajv.jvfoods.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime dataCadastro;

    @ManyToMany
    @JoinTable(name="usuario_grupo", joinColumns = @JoinColumn(name="usuario_id"), inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    private Set<Grupo> grupos = new HashSet<>();

    public boolean senhaCoincide(String senha) {
        return getSenha().equals(senha);
    }

    public boolean senhaNaoCoincide(String senha) {
        return !getSenha().equals(senha);
    }

    public boolean incluirAoGrupo(Grupo grupo) {
        return getGrupos().add(grupo);
    }

    public boolean removerDoGrupo(Grupo grupo) {
        return getGrupos().remove(grupo);
    }

}
