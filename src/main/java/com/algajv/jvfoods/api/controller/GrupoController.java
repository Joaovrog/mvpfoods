package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.GrupoMapper;
import com.algajv.jvfoods.api.model.dto.GrupoDTO;
import com.algajv.jvfoods.api.model.inputdto.GrupoInputDTO;
import com.algajv.jvfoods.domain.model.Grupo;
import com.algajv.jvfoods.domain.repository.GrupoRepository;
import com.algajv.jvfoods.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService service;

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private GrupoMapper mapper;

    @GetMapping
    public List<GrupoDTO> listar() {
        return mapper.toListDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public GrupoDTO buscar(@PathVariable(value = "id") Long id) {
        Grupo grupo = service.getByIdOrFail(id);

        return mapper.toDTO(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO salvar(@RequestBody @Valid GrupoInputDTO grupoInput) {
        Grupo grupo = mapper.inputToEntity(grupoInput);
        grupo = service.salvar(grupo);

        return mapper.toDTO(grupo);
    }

    @PutMapping("/{id}")
    public GrupoDTO atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid GrupoInputDTO grupoInput) {
        Grupo grupo = service.getByIdOrFail(id);
        mapper.copyToEntity(grupoInput, grupo);

        grupo = service.salvar(grupo);
        return mapper.toDTO(grupo);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable(value = "id") Long id) {
        service.excluir(id);
    }
}
