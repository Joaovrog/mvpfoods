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
        return repository.listar();
    }

    @GetMapping("/{cidade_id}")
    public ResponseEntity<Cidade> buscar(@PathVariable(name="cidade_id") Long id) {
        Cidade cidade = repository.buscar(id);
        if (cidade != null) return ResponseEntity.ok(cidade);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {

        try {
            cidade =  service.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cidade_id}")
    public ResponseEntity<?> atualizar(@PathVariable(name="cidade_id") Long id,
                                       @RequestBody Cidade cidade) {

        Cidade cidadeEncontrada = repository.buscar(id);
        if(cidadeEncontrada != null) {
            BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");
            try {
                cidadeEncontrada = service.salvar(cidadeEncontrada);
                return ResponseEntity.ok(cidadeEncontrada);
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cidade_id}")
    public ResponseEntity<Restaurante> remover(@PathVariable(name="cidade_id") Long id) {
        Cidade cidade = repository.buscar(id);
        if(cidade != null) {
            repository.remover(cidade);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
