package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.EntidadeEmUsoException;
import com.algajv.jvfoods.domain.exception.EstadoNaoEncontradoException;
import com.algajv.jvfoods.domain.model.Estado;
import com.algajv.jvfoods.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadoService {

    private static final String MSG_ESTADO_EM_USO = "Estao de código %d não pode ser removido pois está em uso.";

    @Autowired
    private EstadoRepository repository;

    @Transactional
    public Estado salvar(Estado estado) {
        return repository.save(estado);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));

        }
    }


    public Estado getByIdOrFail(Long estadoId) {
        return repository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }
}
