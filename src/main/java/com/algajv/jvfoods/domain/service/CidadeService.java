package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.model.Estado;
import com.algajv.jvfoods.domain.repository.CidadeRepository;
import com.algajv.jvfoods.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private EstadoRepository estadoRepository;


    public Cidade salvar(Cidade cidade) {
        Long idEstado = cidade.getEstado().getId();
        Optional<Estado> estado = estadoRepository.findById(idEstado);
        if(estado.isEmpty()) throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro" +
                " de estado com o código %d", idEstado));

        cidade.setEstado(estado.get());
        return repository.save(cidade);
    }
}
