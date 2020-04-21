package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.RestauranteMapper;
import com.algajv.jvfoods.api.model.dto.RestauranteDTO;
import com.algajv.jvfoods.api.model.inputdto.RestauranteInputDTO;
import com.algajv.jvfoods.domain.exception.*;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import com.algajv.jvfoods.domain.service.RestauranteService;
import com.algajv.jvfoods.domain.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private RestauranteService service;

    @Autowired
    private SmartValidator smartValidator;

    @Autowired
    private RestauranteMapper mapper;

    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public List<RestauranteDTO> listar() {
        return mapper.toListDTO(repository.findAll());
    }

    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteDTO> listarResumido() {
        return listar();
    }

    @GetMapping("/{restrt_id}")
    public RestauranteDTO buscar(@PathVariable(name="restrt_id") Long id) {
        Restaurante restaurante = service.getByIdOrFail(id);
        RestauranteDTO restauranteDTO = mapper.toDTO(restaurante);

        return restauranteDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInput) {
        try {

            Restaurante restaurante = mapper.inputToEntity(restauranteInput);
            return mapper.toDTO(service.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restrt_id}")
    public RestauranteDTO atualizar(@PathVariable(name="restrt_id") Long id,
                                       @RequestBody @Valid RestauranteInputDTO restauranteInput) {

        try {

            Restaurante restauranteEncontrado = service.getByIdOrFail(id);
            mapper.copyToEntity(restauranteInput, restauranteEncontrado); // mais seguro que o BeanUtils
//            BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id", "formaPagamentos", "endereco", "dataCadastro", "produtos");


            return mapper.toDTO(service.salvar(restauranteEncontrado));
        } catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

//    @PatchMapping("/{restrt_id}")
//    public Restaurante atualizarParcial(@PathVariable(name="restrt_id") Long id, @RequestBody Map<String, Object> campos, HttpServletRequest servletRequest) {
//        Restaurante restauranteEncontrado = service.getByIdOrFail(id);
//        merge(campos, restauranteEncontrado, servletRequest);
//        validate(restauranteEncontrado, "restaurante");
//
//        return atualizar(id,restauranteEncontrado);
//    }
//
//    private void validate(Restaurante restaurante, String objectName) {
//        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//        smartValidator.validate(restaurante, bindingResult);
//
//        if(bindingResult.hasErrors()) {
//            throw new ValidacaoException(bindingResult);
//        }
//    }
//
//
//    private void merge(Map<String, Object> campos, Restaurante restauranteDestino, HttpServletRequest servletRequest) {
//
//        // Pegando um ServletServerHttpRequest para usar o construtor não depreciado do HttpMessageNotReadableException, no catch!
//        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(servletRequest);
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//            // Converter corretamente os tipos dos campos passados no Map aos tipos dos campos da classe Restaurante.
//            Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
//
//            campos.forEach((propriedade, valor) -> {
//                Field campo = ReflectionUtils.findField(Restaurante.class, propriedade);
//                campo.setAccessible(true); // Permitir que atributos privados da classe Restaurante possam ser acessados.
//                Object valorValido = ReflectionUtils.getField(campo, restauranteOrigem);
//
//                ReflectionUtils.setField(campo, restauranteDestino, valorValido);
//            });
//        } catch (IllegalArgumentException ex) {
//            Throwable rootCause = ExceptionUtils.getRootCause(ex);
//            throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, servletServerHttpRequest);
//        }
//    }


    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable(value = "restauranteId") Long restauranteId) {
        service.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable(value = "restauranteId") Long restauranteId) {
        service.desativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable(value = "restauranteId") Long restauranteId) {
        service.fechar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable(value = "restauranteId") Long restauranteId) {
        service.abrir(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            service.ativar(restauranteIds);
        } catch(RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            service.desativarMultiplos(restauranteIds);
        } catch(RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}
