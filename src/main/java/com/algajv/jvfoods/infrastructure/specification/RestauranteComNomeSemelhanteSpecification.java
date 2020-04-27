package com.algajv.jvfoods.infrastructure.specification;

import com.algajv.jvfoods.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RestauranteComNomeSemelhanteSpecification implements Specification<Restaurante> {

    private String nome;

    public RestauranteComNomeSemelhanteSpecification(String nome) {
        this.nome = nome;
    }

    @Override
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get("nome"), '%'+nome+'%');
    }
}
