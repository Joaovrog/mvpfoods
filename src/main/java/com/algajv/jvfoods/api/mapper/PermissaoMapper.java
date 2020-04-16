package com.algajv.jvfoods.api.mapper;

import com.algajv.jvfoods.api.model.dto.PermissaoDTO;
import com.algajv.jvfoods.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO toDTO(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toListDTO(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(permissao -> toDTO(permissao))
                .collect(Collectors.toList());
    }


}
