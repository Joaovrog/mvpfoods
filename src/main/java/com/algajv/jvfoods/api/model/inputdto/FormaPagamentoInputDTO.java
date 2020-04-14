package com.algajv.jvfoods.api.model.inputdto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoInputDTO {

    @NotBlank
    private String descricao;
}
