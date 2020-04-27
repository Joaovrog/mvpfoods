package com.algajv.jvfoods.infrastructure.service.query;

import com.algajv.jvfoods.domain.filter.VendaDiariaFilter;
import com.algajv.jvfoods.domain.model.Pedido;
import com.algajv.jvfoods.domain.model.StatusPedido;
import com.algajv.jvfoods.domain.model.representacao.VendaDiaria;
import com.algajv.jvfoods.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        var builder = entityManager.getCriteriaBuilder();
        var query  = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);
        var predicates = new ArrayList<Predicate>();
        var statusDesejados = Arrays.asList(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE);

        predicates.add(root.get("status").in(statusDesejados));

        if(filtro.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
        }

        if(filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }

        if(filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }


        var functionConvertTimezoneDataCriacao = builder.function("convert_tz", Date.class, root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(timeOffset));
        var functionDateDataCriacao = builder.function("date", Date.class, functionConvertTimezoneDataCriacao);  // utilizando a função 'date' do MySQL

        var selection = builder.construct(VendaDiaria.class,       // o resultado da pesquisa servirá pra chamar um construtor de VendaDiaria
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return entityManager.createQuery(query).getResultList();
    }
}
