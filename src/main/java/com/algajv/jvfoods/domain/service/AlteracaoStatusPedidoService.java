package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AlteracaoStatusPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = pedidoService.getByIdOrFail(codigoPedido);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = pedidoService.getByIdOrFail(codigoPedido);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = pedidoService.getByIdOrFail(codigoPedido);
        pedido.cancelar();
    }
}
