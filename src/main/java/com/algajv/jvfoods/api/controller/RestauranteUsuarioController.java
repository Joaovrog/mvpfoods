package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.UsuarioMapper;
import com.algajv.jvfoods.api.model.dto.UsuarioDTO;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import com.algajv.jvfoods.domain.service.RestauranteService;
import com.algajv.jvfoods.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    public List<UsuarioDTO> listar(@PathVariable(value = "restauranteId") Long restauranteId) {
        Restaurante restaurante = restauranteService.getByIdOrFail(restauranteId);
        return usuarioMapper.toListDTO(restaurante.getResponsaveis());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable(value = "restauranteId") Long restauranteId, @PathVariable(value = "usuarioId") Long usuarioId) {
        restauranteService.associarResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deassociar(@PathVariable(value = "restauranteId") Long restauranteId, @PathVariable(value = "usuarioId") Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }
}
