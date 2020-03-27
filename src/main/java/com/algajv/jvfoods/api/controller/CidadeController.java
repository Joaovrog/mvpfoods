package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.CidadeRepository;
import com.algajv.jvfoods.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService service;

    @Autowired
    private CidadeRepository repository;

    @GetMapping
    public List<Cidade> listar() {
        return repository.findAll();
    }

    @GetMapping("/{cidade_id}")
    public Cidade buscar(@PathVariable(name="cidade_id") Long id) {
        return service.getByIdOrFail(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade) {
        return service.salvar(cidade);
    }

    @PutMapping("/{cidade_id}")
    public Cidade atualizar(@PathVariable(name="cidade_id") Long id,
                                       @RequestBody Cidade cidade) {

        Cidade cidadeEncontrada = service.getByIdOrFail(id);
        BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");
        return service.salvar(cidadeEncontrada);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cidade_id}")
    public void remover(@PathVariable(name="cidade_id") Long id) {
        service.excluir(id);
    }
}
