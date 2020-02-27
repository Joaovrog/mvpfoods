package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }


    @GetMapping("/{id_cozinha}")
    public ResponseEntity<Cozinha> buscar(@PathVariable(name="id_cozinha") Long id) {
        Cozinha cozinha = cozinhaRepository.buscar(id);
        if(cozinha!=null) return ResponseEntity.ok(cozinha);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha)   {
        return cozinhaRepository.salvar(cozinha);
    }

    @PutMapping("/{id_cozinha}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable(name="id_cozinha") Long id,
                                             @RequestBody Cozinha cozinha) {
        Cozinha cozinhaEncontrada = cozinhaRepository.buscar(id);
        if(cozinhaEncontrada != null) {
            BeanUtils.copyProperties(cozinha, cozinhaEncontrada, "id");
            cozinhaEncontrada = cozinhaRepository.salvar(cozinhaEncontrada);
            return ResponseEntity.ok(cozinhaEncontrada);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{cozinha_id}")
    public ResponseEntity<Cozinha> remover(@PathVariable(name="cozinha_id") Long id) {

        try {
            Cozinha cozinha = cozinhaRepository.buscar(id);
            if(cozinha != null) {
                cozinhaRepository.remover(cozinha);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
