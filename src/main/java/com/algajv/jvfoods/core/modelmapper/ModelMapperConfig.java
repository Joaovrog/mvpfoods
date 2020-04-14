package com.algajv.jvfoods.core.modelmapper;

import com.algajv.jvfoods.api.model.dto.EnderecoDTO;
import com.algajv.jvfoods.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class)
                .<String>addMapping(
                        src -> src.getCidade().getEstado().getNome(),
                        (destino, valor) -> destino.getCidade().setEstado(valor));

        return modelMapper;
    }

}
