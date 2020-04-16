package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.ProdutoMapper;
import com.algajv.jvfoods.api.model.dto.ProdutoDTO;
import com.algajv.jvfoods.api.model.inputdto.ProdutoInputDTO;
import com.algajv.jvfoods.domain.model.Produto;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.ProdutoRepository;
import com.algajv.jvfoods.domain.service.ProdutoService;
import com.algajv.jvfoods.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public List<ProdutoDTO> listar(@PathVariable(value = "restauranteId") Long restauranteId) {
        Restaurante restaurante = restauranteService.getByIdOrFail(restauranteId);
        List<Produto> produtos = produtoRepository.findByRestaurante(restaurante);
        return produtoMapper.toListDTO(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO  buscar(@PathVariable(value = "restauranteId") Long restauranteId, @PathVariable(value = "produtoId") Long produtoId) {
        Produto produto = produtoService.getByIdOrFail(restauranteId, produtoId);
        return produtoMapper.toDTO(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable("restauranteId") Long restauranteId, @RequestBody @Valid ProdutoInputDTO produtoInput) {
        Restaurante restaurante = restauranteService.getByIdOrFail(restauranteId);

        Produto produto = produtoMapper.inputToEntity(produtoInput);
        produto.setRestaurante(restaurante);

        produto = produtoService.salvar(produto);

        return produtoMapper.toDTO(produto);

    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable(value = "restauranteId") Long restauranteId, @PathVariable(value = "produtoId") Long produtoId,
                                @RequestBody @Valid ProdutoInputDTO produtoInput) {
        Produto produtoEncontrado = produtoService.getByIdOrFail(restauranteId, produtoId);
        produtoMapper.copyToEntity(produtoInput, produtoEncontrado);
        produtoEncontrado = produtoService.salvar(produtoEncontrado);

        return produtoMapper.toDTO(produtoEncontrado);
    }
}
