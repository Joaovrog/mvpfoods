package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.service.AlteracaoStatusPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class StatusPedidoController {

    @Autowired
    private AlteracaoStatusPedidoService statusPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable(value = "pedidoId") String codigoPedido) {
        statusPedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable(value = "pedidoId") String codigoPedido) {
        statusPedidoService.entregar(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable(value = "pedidoId") String codigoPedido) {
        statusPedidoService.cancelar(codigoPedido);
    }
}
