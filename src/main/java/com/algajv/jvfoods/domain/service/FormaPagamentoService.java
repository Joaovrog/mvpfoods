package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.CozinhaNaoEncontradaException;
import com.algajv.jvfoods.domain.exception.EntidadeEmUsoException;
import com.algajv.jvfoods.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.FormaPagamento;
import com.algajv.jvfoods.domain.repository.CozinhaRepository;
import com.algajv.jvfoods.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removida pois está em uso.";

    @Autowired
    private FormaPagamentoRepository repository;

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return repository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));

        }
    }

    public FormaPagamento getByIdOrFail(Long formaPagamentoId) {
        return repository.findById(formaPagamentoId).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
    }

}
