package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.repository.CozinhaRepository;
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
    private CozinhaRepository repository;

    @GetMapping
    public List<Cozinha> testeContaining(@RequestParam("nome") String nome) {
        return repository.findCozinhasByNomeContaining(nome);
    }
}
