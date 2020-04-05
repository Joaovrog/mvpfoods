package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.exception.EntidadeEmUsoException;
import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.repository.CozinhaRepository;
import com.algajv.jvfoods.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService service;

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }


    @GetMapping("/{id_cozinha}")
    public Cozinha buscar(@PathVariable(name="id_cozinha") Long id) {
        return service.getByIdOrFail(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha)   {
        return service.salvar(cozinha);
    }


    @PutMapping("/{id_cozinha}")
    public Cozinha atualizar(@PathVariable(name="id_cozinha") Long id,
                                             @RequestBody @Valid Cozinha cozinha) {
        Cozinha cozinhaEncontrada = service.getByIdOrFail(id);
        BeanUtils.copyProperties(cozinha, cozinhaEncontrada, "id");
        return service.salvar(cozinhaEncontrada);

    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cozinha_id}")
    public void remover(@PathVariable(name="cozinha_id") Long id) {
            service.excluir(id);

    }

}
