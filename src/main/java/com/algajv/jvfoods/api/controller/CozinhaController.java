package com.algajv.jvfoods.api.controller;

import com.algajv.jvfoods.api.mapper.CozinhaMapper;
import com.algajv.jvfoods.api.model.dto.CozinhaDTO;
import com.algajv.jvfoods.api.model.inputdto.CozinhaInputDTO;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.repository.CozinhaRepository;
import com.algajv.jvfoods.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService service;

    @Autowired
    private CozinhaMapper mapper;

    @GetMapping
    public Page<CozinhaDTO> listar(Pageable pageable){
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        List<CozinhaDTO> cozinhaDTOS = mapper.toListDTO(cozinhasPage.getContent());

        Page<CozinhaDTO> cozinhasDTOPage = new PageImpl<>(cozinhaDTOS, pageable, cozinhasPage.getTotalElements());
        return cozinhasDTOPage;
    }


    @GetMapping("/{id_cozinha}")
    public CozinhaDTO buscar(@PathVariable(name="id_cozinha") Long id) {
        return mapper.toDTO(service.getByIdOrFail(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInput)   {
        Cozinha cozinha = mapper.inputToEntity(cozinhaInput);
        cozinha = service.salvar(cozinha);

        return mapper.toDTO(cozinha);
    }


    @PutMapping("/{id_cozinha}")
    public CozinhaDTO atualizar(@PathVariable(name="id_cozinha") Long id,
                                             @RequestBody @Valid CozinhaInputDTO cozinhaInput) {
        Cozinha cozinhaEncontrada = service.getByIdOrFail(id);
        mapper.copyToEntity(cozinhaInput, cozinhaEncontrada);
        return mapper.toDTO(service.salvar(cozinhaEncontrada));

    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cozinha_id}")
    public void remover(@PathVariable(name="cozinha_id") Long id) {
            service.excluir(id);

    }

}
