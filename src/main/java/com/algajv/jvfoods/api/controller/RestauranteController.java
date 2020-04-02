package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.exception.NegocioException;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import com.algajv.jvfoods.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        try {
            return service.salvar(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restrt_id}")
    public Restaurante atualizar(@PathVariable(name="restrt_id") Long id,
                                       @RequestBody Restaurante restaurante) {

             Restaurante restauranteEncontrado = service.getByIdOrFail(id);
            BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id", "formaPagamentos", "endereco", "dataCadastro", "produtos");
            try {
                return service.salvar(restauranteEncontrado);
            } catch(EntidadeNaoEncontradaException e) {
                throw new NegocioException(e.getMessage(), e);
            }
    }

    @PatchMapping("/{restrt_id}")
    public Restaurante atualizarParcial(@PathVariable(name="restrt_id") Long id, @RequestBody Map<String, Object> campos, HttpServletRequest servletRequest) {
        Restaurante restauranteEncontrado = service.getByIdOrFail(id);
        merge(campos, restauranteEncontrado, servletRequest);
        return atualizar(id,restauranteEncontrado);
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino, HttpServletRequest servletRequest) {

        // Pegando um ServletServerHttpRequest para usar o construtor não depreciado do HttpMessageNotReadableException, no catch!
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(servletRequest);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            // Converter corretamente os tipos dos campos passados no Map aos tipos dos campos da classe Restaurante.
            Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);

            campos.forEach((propriedade, valor) -> {
                Field campo = ReflectionUtils.findField(Restaurante.class, propriedade);
                campo.setAccessible(true); // Permitir que atributos privados da classe Restaurante possam ser acessados.
                Object valorValido = ReflectionUtils.getField(campo, restauranteOrigem);

                ReflectionUtils.setField(campo, restauranteDestino, valorValido);
            });
        } catch (IllegalArgumentException ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, servletServerHttpRequest);
        }
    }
}
