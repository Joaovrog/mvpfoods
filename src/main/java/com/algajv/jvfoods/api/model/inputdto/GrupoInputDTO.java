package com.algajv.jvfoods.api.model.inputdto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GrupoInputDTO {

    @NotBlank
    private String nome;
}
