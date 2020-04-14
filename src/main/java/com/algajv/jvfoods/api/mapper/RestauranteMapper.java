package com.algajv.jvfoods.api.mapper;

import com.algajv.jvfoods.api.model.dto.CozinhaDTO;
import com.algajv.jvfoods.api.model.dto.RestauranteDTO;
import com.algajv.jvfoods.api.model.inputdto.RestauranteInputDTO;
import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteMapper {

    @Autowired
    private ModelMapper modelMapper;  // a partir do Bean anotado em classe de configuração


    public RestauranteDTO toDTO(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public List<RestauranteDTO> toListDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toDTO(restaurante))
                .collect(Collectors.toList());
    }

    public Restaurante inputToEntity(RestauranteInputDTO restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToEntity(RestauranteInputDTO restauranteInput, Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha()); // resolver o problema do map em que o JPA entende que, ao mudar de cozinha, estamos tentando mudar o id da propria cozinha.

        if(restaurante.getEndereco() !=null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }
        modelMapper.map(restauranteInput, restaurante);
    }
}
