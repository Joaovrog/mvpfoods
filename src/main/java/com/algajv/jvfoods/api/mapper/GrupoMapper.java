package com.algajv.jvfoods.api.mapper;

import com.algajv.jvfoods.api.model.dto.CidadeDTO;
import com.algajv.jvfoods.api.model.dto.GrupoDTO;
import com.algajv.jvfoods.api.model.inputdto.CidadeInputDTO;
import com.algajv.jvfoods.api.model.inputdto.GrupoInputDTO;
import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoMapper {

    @Autowired
    private ModelMapper modelMapper;  // a partir do Bean anotado em classe de configuração


    public GrupoDTO toDTO(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    public List<GrupoDTO> toListDTO(List<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toDTO(grupo))
                .collect(Collectors.toList());
    }

    public Grupo inputToEntity(GrupoInputDTO grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToEntity(GrupoInputDTO grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
