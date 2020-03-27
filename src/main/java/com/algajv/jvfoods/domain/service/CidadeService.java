package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.EntidadeEmUsoException;
import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.model.Estado;
import com.algajv.jvfoods.domain.repository.CidadeRepository;
import com.algajv.jvfoods.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CidadeService {


    private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cadastro de cidade com o código %d";
    private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida pois está em uso.";
    @Autowired
    private CidadeRepository repository;

    @Autowired
    private EstadoService estadoService;


    public Cidade salvar(Cidade cidade) {
        Long idEstado = cidade.getEstado().getId();
        Estado estado = estadoService.getByIdOrFail(idEstado);
        cidade.setEstado(estado);
        return repository.save(cidade);
    }

    public void excluir(Long id) {
        try {
            repository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));

        }
    }

    public Cidade getByIdOrFail(Long cidadeId) {
        return repository.findById(cidadeId).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
    }


    }

