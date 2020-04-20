package com.algajv.jvfoods.api.model.dto;

import com.algajv.jvfoods.domain.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {


    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private UsuarioDTO cliente;
    private EnderecoDTO enderecoEntrega;
    private FormaPagamentoDTO formaPagamento;
    private RestauranteResumoDTO restaurante;
    private List<ItemPedidoDTO> itens;

}
