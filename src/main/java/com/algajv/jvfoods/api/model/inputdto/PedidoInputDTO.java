package com.algajv.jvfoods.api.model.inputdto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInputDTO {

    @NotNull
    @Valid
    private RestauranteIdInputDTO restaurante;

    @NotNull
    @Valid
    private FormaPagamentoIdInput formaPagamento;

    @NotNull
    @Valid
    private EnderecoInputDTO enderecoEntrega;

    @Valid
    @Size(min=1)
    @NotNull
    private List<ItemPedidoInputDTO> itens;
}
