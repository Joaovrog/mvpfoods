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
import java.util.Optional;

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
    public ResponseEntity<Cidade> buscar(@PathVariable(name="cidade_id") Long id) {
        Optional<Cidade> cidade = repository.findById(id);
        if (cidade.isPresent()) return ResponseEntity.ok(cidade.get());
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

        Optional<Cidade> cidadeEncontrada = repository.findById(id);
        if(cidadeEncontrada.isPresent()) {
            BeanUtils.copyProperties(cidade, cidadeEncontrada.get(), "id");
            try {
                Cidade cidadeSalva = service.salvar(cidadeEncontrada.get());
                return ResponseEntity.ok(cidadeSalva);
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cidade_id}")
    public ResponseEntity<Restaurante> remover(@PathVariable(name="cidade_id") Long id) {
        Optional<Cidade> cidade = repository.findById(id);
        if(cidade.isPresent()) {
            repository.delete(cidade.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
