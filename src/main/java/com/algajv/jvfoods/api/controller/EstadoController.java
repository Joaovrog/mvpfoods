package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.exception.EntidadeEmUsoException;
import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Estado;
import com.algajv.jvfoods.domain.repository.EstadoRepository;
import com.algajv.jvfoods.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService service;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.listar();
    }

    @GetMapping("/{estado_id}")
    public ResponseEntity<Estado> buscar(@PathVariable(name="estado_id") Long id) {
        Estado estado = estadoRepository.buscar(id);
        if(estado!=null) return ResponseEntity.ok(estado);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado)   {
        return service.salvar(estado);
    }

    @PutMapping("/{estado_id}")
    public ResponseEntity<Estado> atualizar(@PathVariable(name="estado_id") Long id,
                                             @RequestBody Estado estado) {
        Estado estadoEncontrado = estadoRepository.buscar(id);
        if(estadoEncontrado != null) {
            BeanUtils.copyProperties(estado, estadoEncontrado, "id");
            estadoEncontrado = service.salvar(estadoEncontrado);
            return ResponseEntity.ok(estadoEncontrado);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{estado_id}")
    public ResponseEntity<?> remover(@PathVariable(name="estado_id") Long id) {

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
