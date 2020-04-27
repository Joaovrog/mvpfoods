package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
