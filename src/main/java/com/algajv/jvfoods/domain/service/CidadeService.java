package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.model.Estado;
import com.algajv.jvfoods.domain.repository.CidadeRepository;
import com.algajv.jvfoods.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private EstadoRepository estadoRepository;


    public Cidade salvar(Cidade cidade) {
        Long idEstado = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(idEstado);
        if(estado==null) throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro" +
                " de estado com o código %d", idEstado));

        cidade.setEstado(estado);
        return repository.salvar(cidade);
    }
}
