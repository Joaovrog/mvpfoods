package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.GrupoMapper;
import com.algajv.jvfoods.api.model.dto.GrupoDTO;
import com.algajv.jvfoods.domain.model.Usuario;
import com.algajv.jvfoods.domain.repository.UsuarioRepository;
import com.algajv.jvfoods.domain.service.GrupoService;
import com.algajv.jvfoods.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoMapper grupoMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<GrupoDTO> listar(@PathVariable(value = "usuarioId") Long usuarioId) {
        Usuario usuario = usuarioService.getByIdOrFail(usuarioId);

        return grupoMapper.toListDTO(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable(value = "usuarioId") Long usuarioId, @PathVariable(value = "grupoId") Long grupoId) {
        usuarioService.associarGrupo(usuarioId,grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable(value = "usuarioId") Long usuarioId, @PathVariable(value = "grupoId") Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId,grupoId);
    }
}
