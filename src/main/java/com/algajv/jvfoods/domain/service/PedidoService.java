package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.NegocioException;
import com.algajv.jvfoods.domain.exception.PedidoNaoEncontradoException;
import com.algajv.jvfoods.domain.model.*;
import com.algajv.jvfoods.domain.repository.PedidoRepository;
import com.algajv.jvfoods.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private UsuarioService usuarioService;



    @Transactional
    public Pedido emitir(Pedido pedido) {
        validaDadosPedido(pedido);
        validaItensPedido(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);

    }

    private void validaItensPedido(Pedido pedido) {
        pedido.getItens().forEach(itemPedido -> {
            Produto produto = produtoService.getByIdOrFail(pedido.getRestaurante().getId(), itemPedido.getProduto().getId());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.calculaPrecoTotalDoItemPedido();
        });
    }

    private void validaDadosPedido(Pedido pedido) {
        Cidade cidade = cidadeService.getByIdOrFail(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.getByIdOrFail(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.getByIdOrFail(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.getByIdOrFail(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        if(restaurante.naoAceitaFormaPagamento(pedido.getFormaPagamento())) {
            throw new NegocioException(String.format("Este restaurante não aceita %s como forma de pagamento.", pedido.getFormaPagamento().getDescricao()));
        }
    }

    public Pedido getByIdOrFail(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}
