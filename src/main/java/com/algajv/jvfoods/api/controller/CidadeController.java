package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.Groups;
import com.algajv.jvfoods.domain.exception.EstadoNaoEncontradoException;
import com.algajv.jvfoods.domain.exception.NegocioException;
import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.repository.CidadeRepository;
import com.algajv.jvfoods.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {

        try {
            return service.salvar(cidade);
        } catch(EstadoNaoEncontradoException e) {
            throw  new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidade_id}")
    public Cidade atualizar(@PathVariable(name="cidade_id") Long id,
                                       @RequestBody @Valid Cidade cidade) {

        Cidade cidadeEncontrada = service.getByIdOrFail(id);
        BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");

        try {
            return service.salvar(cidadeEncontrada);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cidade_id}")
    public void remover(@PathVariable(name="cidade_id") Long id) {
        service.excluir(id);
    }

}
