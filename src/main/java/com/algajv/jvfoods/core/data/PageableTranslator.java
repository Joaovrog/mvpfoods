package com.algajv.jvfoods.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.stream.Collectors;

public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> fieldMapping) {
        //transformando as propriedades de sort passadas pelo usuario nas propriedades de sort validas (validas: configuradas no ImmutableMap)
        var orders = pageable.getSort().stream()
                .filter(order -> fieldMapping.containsKey(order.getProperty()))  // ignorando propriedades que não existem.
                .map(order -> new Sort.Order(order.getDirection(), fieldMapping.get(order.getProperty())))
                .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by(orders));
    }
}
