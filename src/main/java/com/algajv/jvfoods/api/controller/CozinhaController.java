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
    public ResponseEntity<Cozinha> buscar(@PathVariable(name="id_cozinha") Long id) {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
        if(cozinha.isPresent()) return ResponseEntity.ok(cozinha.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha)   {
        return service.salvar(cozinha);
    }

    @PutMapping("/{id_cozinha}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable(name="id_cozinha") Long id,
                                             @RequestBody Cozinha cozinha) {
        Optional<Cozinha> cozinhaEncontrada = cozinhaRepository.findById(id);
        if(cozinhaEncontrada.isPresent()) {
            BeanUtils.copyProperties(cozinha, cozinhaEncontrada.get(), "id");
            Cozinha cozinhaSalva = service.salvar(cozinhaEncontrada.get());
            return ResponseEntity.ok(cozinhaSalva);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{cozinha_id}")
    public ResponseEntity<?> remover(@PathVariable(name="cozinha_id") Long id) {

        try {
            service.excluir(id);
            return ResponseEntity.noContent().build();

//            return ResponseEntity.notFound().build();
        } catch (EntidadeNaoEncontradaException ex){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

}
