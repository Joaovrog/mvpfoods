package com.algajv.jvfoods.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize maxSize;

    @Override
    public void initialize(FileSize constraintAnnotation) {    // prepara o validador para chamadas futuras do metodo isValid
         maxSize = DataSize.parse(constraintAnnotation.max());// para o isValid pegar o parametro 'numero' para saber se o valor passado é multiplo de 'numero'.
    }

    @Override
    public boolean isValid(MultipartFile valorInformado, ConstraintValidatorContext constraintValidatorContext) {
        return valorInformado == null || valorInformado.getSize() <= this.maxSize.toBytes();
    }
}
