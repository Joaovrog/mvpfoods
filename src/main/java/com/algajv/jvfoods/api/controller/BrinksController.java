package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import com.algajv.jvfoods.infrastructure.repository.specification.RestauranteComFreteGratisSpecification;
import com.algajv.jvfoods.infrastructure.repository.specification.RestauranteComNomeSemelhanteSpecification;
import static com.algajv.jvfoods.infrastructure.repository.specification.RestauranteSpecifications.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class BrinksController {

    @Autowired
    private RestauranteRepository repository;

//    @GetMapping
//    public List<Restaurante> testeContaining(@RequestParam("nome") String nome, @RequestParam("cozinhaId") Long cozinhaId) {
//        return repository.consultarPorNome(nome, cozinhaId);
//    }

    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(@RequestParam("nome") String nome) {
        return repository.findComFreteGratis(nome);
    }


}
