package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.PedidoNaoEncontradoException;
import com.algajv.jvfoods.domain.model.Pedido;
import com.algajv.jvfoods.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido getByIdOrFail(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}
