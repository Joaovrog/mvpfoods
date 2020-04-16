package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.FormaPagamentoMapper;
import com.algajv.jvfoods.api.mapper.RestauranteMapper;
import com.algajv.jvfoods.api.model.dto.FormaPagamentoDTO;
import com.algajv.jvfoods.api.model.dto.RestauranteDTO;
import com.algajv.jvfoods.api.model.inputdto.RestauranteInputDTO;
import com.algajv.jvfoods.domain.exception.CidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.exception.CozinhaNaoEncontradaException;
import com.algajv.jvfoods.domain.exception.NegocioException;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import com.algajv.jvfoods.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {


    @Autowired
    private RestauranteService service;

    @Autowired
    private FormaPagamentoMapper mapper;


    @GetMapping
    public List<FormaPagamentoDTO> listar(@PathVariable(value = "restauranteId") Long id) {
        Restaurante restaurante = service.getByIdOrFail(id);
        return mapper.toListDTO(restaurante.getFormaPagamentos());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable(value = "restauranteId") Long restauranteId, @PathVariable(value = "formaPagamentoId") Long formaPagamentoId) {
        service.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable(value = "restauranteId") Long restauranteId, @PathVariable(value = "formaPagamentoId") Long formaPagamentoId) {
        service.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

}
