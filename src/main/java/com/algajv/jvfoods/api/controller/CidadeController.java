package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.CidadeMapper;
import com.algajv.jvfoods.api.model.dto.CidadeDTO;
import com.algajv.jvfoods.api.model.inputdto.CidadeInputDTO;
import com.algajv.jvfoods.domain.exception.EstadoNaoEncontradoException;
import com.algajv.jvfoods.domain.exception.NegocioException;
import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.repository.CidadeRepository;
import com.algajv.jvfoods.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService service;

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CidadeMapper mapper;

    @GetMapping
    public List<CidadeDTO> listar() {
        return mapper.toListDTO(repository.findAll());
    } 

    @GetMapping("/{cidade_id}")
    public CidadeDTO buscar(@PathVariable(name="cidade_id") Long id) {
        return mapper.toDTO(service.getByIdOrFail(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInput) {

        try {
            Cidade cidade = mapper.inputToEntity(cidadeInput);
            cidade = service.salvar(cidade);
            return mapper.toDTO(cidade);
        } catch(EstadoNaoEncontradoException e) {
            throw  new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidade_id}")
    public CidadeDTO atualizar(@PathVariable(name="cidade_id") Long id,
                                       @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidadeEncontrada = service.getByIdOrFail(id);
            mapper.copyToEntity(cidadeInputDTO, cidadeEncontrada);
            cidadeEncontrada = service.salvar(cidadeEncontrada);
            return mapper.toDTO(cidadeEncontrada);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cidade_id}")
    public void remover(@PathVariable(name="cidade_id") Long id) {
        service.excluir(id);
    }

}
