package com.algajv.jvfoods.api.mapper;

import com.algajv.jvfoods.api.model.dto.CozinhaDTO;
import com.algajv.jvfoods.api.model.dto.RestauranteDTO;
import com.algajv.jvfoods.api.model.inputdto.CozinhaInputDTO;
import com.algajv.jvfoods.api.model.inputdto.RestauranteInputDTO;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaMapper {

    @Autowired
    private ModelMapper modelMapper;  // a partir do Bean anotado em classe de configuração


    public CozinhaDTO toDTO(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> toListDTO(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toDTO(cozinha))
                .collect(Collectors.toList());
    }

    public Cozinha inputToEntity(CozinhaInputDTO cozinhaInputDTO) {
        return modelMapper.map(cozinhaInputDTO, Cozinha.class);
    }

    public void copyToEntity(CozinhaInputDTO cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}
