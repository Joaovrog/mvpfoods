package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.PedidoMapper;
import com.algajv.jvfoods.api.mapper.PedidoResumoMapper;
import com.algajv.jvfoods.api.model.dto.PedidoDTO;
import com.algajv.jvfoods.api.model.dto.PedidoResumoDTO;
import com.algajv.jvfoods.api.model.inputdto.PedidoInputDTO;
import com.algajv.jvfoods.domain.model.Pedido;
import com.algajv.jvfoods.domain.model.Usuario;
import com.algajv.jvfoods.domain.repository.PedidoRepository;
import com.algajv.jvfoods.domain.service.PedidoService;
import com.algajv.jvfoods.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public List<PedidoResumoDTO> listar() {
        return pedidoResumoMapper.toListDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public PedidoDTO buscar(@PathVariable(value = "id") Long pedidoId) {
        Pedido pedido = pedidoService.getByIdOrFail(pedidoId);
        return mapper.toDTO(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO salvar(@RequestBody @Valid PedidoInputDTO pedidoInput) {
        Pedido pedido = mapper.inputToEntity(pedidoInput);
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        pedido.setCliente(usuario);
        pedido = pedidoService.emitir(pedido);
        return mapper.toDTO(pedido);
    }
}
