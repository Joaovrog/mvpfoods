package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.PermissaoMapper;
import com.algajv.jvfoods.api.model.dto.PermissaoDTO;
import com.algajv.jvfoods.domain.model.Grupo;
import com.algajv.jvfoods.domain.repository.PermissaoRepository;
import com.algajv.jvfoods.domain.service.GrupoService;
import com.algajv.jvfoods.domain.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/grupos")
public class GrupoPermissaoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private PermissaoMapper mapper;

    @Autowired
    private PermissaoRepository permissaoRepository;


    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable(value = "grupoId") Long grupoId) {
        Grupo grupo = grupoService.getByIdOrFail(grupoId);
        return mapper.toListDTO(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable(value = "grupoId") Long grupoId, @PathVariable(value = "permissaoId") Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable(value = "grupoId") Long grupoId, @PathVariable(value = "permissaoId") Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }
}
