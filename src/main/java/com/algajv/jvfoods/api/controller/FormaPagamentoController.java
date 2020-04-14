package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.FormaPagamentoMapper;
import com.algajv.jvfoods.api.model.dto.FormaPagamentoDTO;
import com.algajv.jvfoods.api.model.inputdto.FormaPagamentoInputDTO;
import com.algajv.jvfoods.domain.model.FormaPagamento;
import com.algajv.jvfoods.domain.repository.FormaPagamentoRepository;
import com.algajv.jvfoods.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService service;

    @Autowired
    private FormaPagamentoRepository repository;

    @Autowired
    private FormaPagamentoMapper mapper;

    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        return mapper.toListDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public FormaPagamentoDTO buscar(@PathVariable(value = "id") Long id) {
        FormaPagamento formaPagamento = service.getByIdOrFail(id);
        return mapper.toDTO(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput) {
        FormaPagamento formaPagamento = mapper.inputToEntity(formaPagamentoInput);
        formaPagamento = service.salvar(formaPagamento);
        return mapper.toDTO(formaPagamento);
    }

    @PutMapping("/{ìd}")
    public FormaPagamentoDTO atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput) {
        FormaPagamento formaPagamentoEncontrada = service.getByIdOrFail(id);
        mapper.copyToEntity(formaPagamentoInput, formaPagamentoEncontrada);
        formaPagamentoEncontrada = service.salvar(formaPagamentoEncontrada);

        return mapper.toDTO(formaPagamentoEncontrada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable(value = "id") Long id) {
        service.excluir(id);
    }
}
