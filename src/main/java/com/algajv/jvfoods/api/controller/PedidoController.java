package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.PedidoMapper;
import com.algajv.jvfoods.api.mapper.PedidoResumoMapper;
import com.algajv.jvfoods.api.model.dto.PedidoDTO;
import com.algajv.jvfoods.api.model.dto.PedidoResumoDTO;
import com.algajv.jvfoods.api.model.inputdto.PedidoInputDTO;
import com.algajv.jvfoods.core.data.PageableTranslator;
import com.algajv.jvfoods.domain.model.Pedido;
import com.algajv.jvfoods.domain.model.Usuario;
import com.algajv.jvfoods.domain.repository.PedidoRepository;
import com.algajv.jvfoods.domain.filter.PedidoFilter;
import com.algajv.jvfoods.domain.service.PedidoService;
import com.algajv.jvfoods.domain.service.RestauranteService;
import com.algajv.jvfoods.infrastructure.repository.specification.PedidoSpecifications;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filter, Pageable pageable) {
        pageable =  traduzirPageable(pageable);

        Page<Pedido> pedidos = repository.findAll(PedidoSpecifications.usandoFiltro(filter), pageable);
        List<PedidoResumoDTO> pedidoResumoDTOS = pedidoResumoMapper.toListDTO(pedidos.getContent());
        Page<PedidoResumoDTO> pedidoResumoDTOSPage = new PageImpl<>(pedidoResumoDTOS, pageable, pedidos.getTotalElements());

        return pedidoResumoDTOSPage;
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable(value = "codigoPedido") String codigoPedido) {
        Pedido pedido = pedidoService.getByIdOrFail(codigoPedido);
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

    public Pageable traduzirPageable(Pageable pageable) {

        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "nomeCliente", "cliente.nome",
                "restaurante.nome", "restaurante.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }

}
