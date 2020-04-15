package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.UsuarioMapper;
import com.algajv.jvfoods.api.model.dto.UsuarioDTO;
import com.algajv.jvfoods.api.model.inputdto.UsuarioInputDTO;
import com.algajv.jvfoods.api.model.inputdto.UsuarioUpdateInputDTO;
import com.algajv.jvfoods.api.model.inputdto.UsuarioUpdateSenhaInputDTO;
import com.algajv.jvfoods.domain.exception.SenhaAtualInvalidaException;
import com.algajv.jvfoods.domain.model.Usuario;
import com.algajv.jvfoods.domain.repository.UsuarioRepository;
import com.algajv.jvfoods.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpClient;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioMapper mapper;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return mapper.toListDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscar(@PathVariable(value = "id") Long id) {
        Usuario usuario = service.getByIdOrFail(id);
        return mapper.toDTO(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO salvar(@RequestBody @Valid UsuarioInputDTO usuarioInput) {
        Usuario usuario = mapper.inputToEntity(usuarioInput);
        usuario = service.salvar(usuario);
        return mapper.toDTO(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid UsuarioUpdateInputDTO usuarioUpdateInput) {
        Usuario usuario = service.getByIdOrFail(id);
        mapper.copyInputToEntity(usuarioUpdateInput, usuario);

        usuario = service.salvar(usuario);
        return mapper.toDTO(usuario);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable(value = "id") Long id, @RequestBody @Valid UsuarioUpdateSenhaInputDTO usuarioUpdateSenhaInput) {
        service.atualizaSenha(id, usuarioUpdateSenhaInput.getSenhaAtual(), usuarioUpdateSenhaInput.getNovaSenha());
    }


}
