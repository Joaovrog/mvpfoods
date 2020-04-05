package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.exception.EntidadeEmUsoException;
import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.exception.NegocioException;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Estado;
import com.algajv.jvfoods.domain.repository.EstadoRepository;
import com.algajv.jvfoods.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService service;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{estado_id}")
    public Estado buscar(@PathVariable(name="estado_id") Long id) {
        return service.getByIdOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody @Valid Estado estado)   {
       return service.salvar(estado);
    }


    @PutMapping("/{estado_id}")
    public Estado atualizar(@PathVariable(name="estado_id") Long id,
                                             @RequestBody @Valid Estado estado) {
            Estado estadoEncontrado = service.getByIdOrFail(id);
            BeanUtils.copyProperties(estado, estadoEncontrado, "id");
            return service.salvar(estadoEncontrado);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{estado_id}")
    public void remover(@PathVariable(name="estado_id") Long id) {
        service.excluir(id);

    }

}
