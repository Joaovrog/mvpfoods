package com.algajv.jvfoods.api.model.inputdto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoIdInputDTO {

    @NotNull
    private Long id;
}
