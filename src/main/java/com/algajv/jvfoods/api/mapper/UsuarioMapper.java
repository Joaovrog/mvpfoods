package com.algajv.jvfoods.api.mapper;

import com.algajv.jvfoods.api.model.dto.UsuarioDTO;
import com.algajv.jvfoods.api.model.inputdto.UsuarioInputDTO;
import com.algajv.jvfoods.api.model.inputdto.UsuarioUpdateInputDTO;
import com.algajv.jvfoods.api.model.inputdto.UsuarioUpdateSenhaInputDTO;
import com.algajv.jvfoods.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO toDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> toListDTO(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toDTO(usuario))
                .collect(Collectors.toList());
    }

    public Usuario inputToEntity(UsuarioInputDTO usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyInputToEntity(UsuarioUpdateInputDTO usuarioUpdateInput, Usuario usuario) {
         modelMapper.map(usuarioUpdateInput, usuario);
    }

    public void copyUpdateSenhaInputToEntity(UsuarioUpdateSenhaInputDTO usuarioUpdateSenhaInput, Usuario usuario) {
        modelMapper.map(usuarioUpdateSenhaInput, usuario);
    }


}
