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
    public Restaurante buscar(@PathVariable(name="restrt_id") Long id) {
        return service.getByIdOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody Restaurante restaurante) {
        return service.salvar(restaurante);
    }

    @PutMapping("/{restrt_id}")
    public Restaurante atualizar(@PathVariable(name="restrt_id") Long id,
                                       @RequestBody Restaurante restaurante) {

             Restaurante restauranteEncontrado = service.getByIdOrFail(id);
            BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id", "formaPagamentos", "endereco", "dataCadastro", "produtos");
            return service.salvar(restauranteEncontrado);
    }

    @PatchMapping("/{restrt_id}")
    public Restaurante atualizarParcial(@PathVariable(name="restrt_id") Long id, @RequestBody Map<String, Object> campos) {
        Restaurante restauranteEncontrado = service.getByIdOrFail(id);
        merge(campos, restauranteEncontrado);
        return atualizar(id,restauranteEncontrado);
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
