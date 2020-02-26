package com.algajv.jvfoods.jpa;

import com.algajv.jvfoods.JvfoodsApplication;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.infrastructure.repository.CozinhaRepositoryImpl;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(JvfoodsApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepositoryImpl cadastroCozinha = applicationContext.getBean(CozinhaRepositoryImpl.class);
        List<Cozinha> cozinhas = cadastroCozinha.listar();
        for(Cozinha cozinha : cozinhas) {
            System.out.println(cozinha.getNome());
        }

    }
}
