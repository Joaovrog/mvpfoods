package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.PedidoMapper;
import com.algajv.jvfoods.api.mapper.PedidoResumoMapper;
import com.algajv.jvfoods.api.model.dto.PedidoDTO;
import com.algajv.jvfoods.api.model.dto.PedidoResumoDTO;
import com.algajv.jvfoods.domain.model.Pedido;
import com.algajv.jvfoods.domain.repository.PedidoRepository;
import com.algajv.jvfoods.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoMapper mapper;

    @Autowired
    private PedidoResumoMapper pedidoResumoMapper;

    @Autowired
    private PedidoRepository repository;

    @GetMapping
    public List<PedidoResumoDTO> listar() {
        return pedidoResumoMapper.toListDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public PedidoDTO buscar(@PathVariable(value = "id") Long pedidoId) {
        Pedido pedido = pedidoService.getByIdOrFail(pedidoId);
        return mapper.toDTO(pedido);
    }
}
