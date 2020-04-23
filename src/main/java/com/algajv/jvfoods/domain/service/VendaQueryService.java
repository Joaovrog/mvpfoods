package com.algajv.jvfoods.domain.service;
import com.algajv.jvfoods.domain.filter.VendaDiariaFilter;
import com.algajv.jvfoods.domain.model.representacao.VendaDiaria;

import java.util.List;
public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
