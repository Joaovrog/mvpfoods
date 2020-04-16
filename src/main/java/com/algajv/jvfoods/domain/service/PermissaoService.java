package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.PermissaoNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Permissao;
import com.algajv.jvfoods.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository repository;


    public Permissao getByIdOrFail(Long permissaoId) {
        return repository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
