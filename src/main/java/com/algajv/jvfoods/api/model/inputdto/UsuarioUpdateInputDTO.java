package com.algajv.jvfoods.api.model.inputdto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioUpdateInputDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;
}
