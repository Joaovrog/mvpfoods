package com.algajv.jvfoods.api.mapper;

import com.algajv.jvfoods.api.model.dto.PedidoDTO;
import com.algajv.jvfoods.api.model.inputdto.PedidoInputDTO;
import com.algajv.jvfoods.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO toDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toListDTO(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toDTO(pedido))
                .collect(Collectors.toList());
    }

    public Pedido inputToEntity(PedidoInputDTO pedidoInputDTO) {
        return modelMapper.map(pedidoInputDTO, Pedido.class);
    }

    public void copyToEntity(PedidoInputDTO pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }



}
