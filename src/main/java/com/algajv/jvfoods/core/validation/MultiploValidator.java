package com.algajv.jvfoods.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultipo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {    // prepara o validador para chamadas futuras do metodo isValid
        numeroMultipo = constraintAnnotation.numero(); // para o isValid pegar o parametro 'numero' para saber se o valor passado é multiplo de 'numero'.
    }

    @Override
    public boolean isValid(Number valorInformado, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        if(valorInformado != null) {
            BigDecimal valorInformadoDecimal = BigDecimal.valueOf(valorInformado.doubleValue());
            BigDecimal parametroMultiploDecimal = BigDecimal.valueOf(this.numeroMultipo);
            BigDecimal resto = valorInformadoDecimal.remainder(parametroMultiploDecimal);

            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }
        return valido;
    }
}
