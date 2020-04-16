package com.algajv.jvfoods.api.mapper;

import com.algajv.jvfoods.api.model.dto.ProdutoDTO;
import com.algajv.jvfoods.api.model.inputdto.ProdutoInputDTO;
import com.algajv.jvfoods.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO toDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toListDTO(Collection<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toDTO(produto))
                .collect(Collectors.toList());
    }

    public Produto inputToEntity(ProdutoInputDTO produtoInputDTO) {
        return modelMapper.map(produtoInputDTO, Produto.class);
    }

    public void copyToEntity(ProdutoInputDTO produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}
