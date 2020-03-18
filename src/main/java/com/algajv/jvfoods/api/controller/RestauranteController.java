package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import com.algajv.jvfoods.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private RestauranteService service;

    @GetMapping
    public List<Restaurante> listar() {
        return repository.findAll();
    }

    @GetMapping("/{restrt_id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable(name="restrt_id") Long id) {
        Optional<Restaurante> restaurante = repository.findById(id);
        if (restaurante.isPresent()) return ResponseEntity.ok(restaurante.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {

        try {
            restaurante =  service.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restrt_id}")
    public ResponseEntity<?> atualizar(@PathVariable(name="restrt_id") Long id,
                                       @RequestBody Restaurante restaurante) {

        Optional<Restaurante> restauranteEncontrado = repository.findById(id);
        if(restauranteEncontrado.isPresent()) {
            BeanUtils.copyProperties(restaurante, restauranteEncontrado.get(), "id");
            try {
                Restaurante restauranteSalvo = service.salvar(restauranteEncontrado.get());
                return ResponseEntity.ok(restauranteSalvo);
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{restrt_id}")
    public ResponseEntity<Restaurante> remover(@PathVariable(name="restrt_id") Long id) {
        Optional<Restaurante> restaurante = repository.findById(id);
        if(restaurante.isPresent()) {
            repository.delete(restaurante.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{restrt_id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable(name="restrt_id") Long id, @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> restauranteEncontrado = repository.findById(id);

        if(restauranteEncontrado.isEmpty() ) return ResponseEntity.notFound().build();

        merge(campos, restauranteEncontrado.get());
        return atualizar(id,restauranteEncontrado.get());
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino) {
        // Converter corretamente os tipos dos campos passados no Map aos tipos dos campos da classe Restaurante.
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);

        campos.forEach((propriedade,valor) -> {
            Field campo = ReflectionUtils.findField(Restaurante.class, propriedade);
            campo.setAccessible(true); // Permitir que atributos privados da classe Restaurante possam ser acessados.
            Object valorValido = ReflectionUtils.getField(campo, restauranteOrigem);

            ReflectionUtils.setField(campo, restauranteDestino, valorValido);
        });
    }
}
