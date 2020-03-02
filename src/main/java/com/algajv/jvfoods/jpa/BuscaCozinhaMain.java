package com.algajv.jvfoods.jpa;

import com.algajv.jvfoods.JvfoodsApplication;
import com.algajv.jvfoods.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class BuscaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(JvfoodsApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepositoryImpl cadastroCozinha = applicationContext.getBean(CozinhaRepositoryImpl.class);
        Cozinha cozinha1 = cadastroCozinha.buscar(1L);

        System.out.println(cozinha1.getNome());

    }
}
