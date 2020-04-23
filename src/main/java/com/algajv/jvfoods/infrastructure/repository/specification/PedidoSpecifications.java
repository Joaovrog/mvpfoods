package com.algajv.jvfoods.infrastructure.repository.specification;

import com.algajv.jvfoods.domain.model.Pedido;
import com.algajv.jvfoods.domain.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;


public class PedidoSpecifications {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filter) {
        return (root, query, builder) -> {
            //resolvendo o problema N+1 das consultas. Do mesmo modo que resolvemos com o JPQL.
            if(Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            var predicates = new ArrayList<Predicate>();
            if(filter.getClienteId() != null) {
                predicates.add(builder.equal(root.get("cliente"), filter.getClienteId()));
            }

            if(filter.getRestauranteId() != null) {
                predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
            }

            if(filter.getDataCriacaoInicio() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
            }

            if(filter.getDataCriacaoFim() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
