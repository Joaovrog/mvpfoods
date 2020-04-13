package com.algajv.jvfoods.api.mapper;

import com.algajv.jvfoods.api.model.dto.CidadeDTO;
import com.algajv.jvfoods.api.model.dto.CozinhaDTO;
import com.algajv.jvfoods.api.model.inputdto.CidadeInputDTO;
import com.algajv.jvfoods.api.model.inputdto.CozinhaInputDTO;
import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeMapper {

    @Autowired
    private ModelMapper modelMapper;  // a partir do Bean anotado em classe de configuração


    public CidadeDTO toDTO(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> toListDTO(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toDTO(cidade))
                .collect(Collectors.toList());
    }

    public Cidade inputToEntity(CidadeInputDTO cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToEntity(CidadeInputDTO cidadeInput, Cidade cidade) {
        modelMapper.map(cidadeInput, cidade);
    }
}
