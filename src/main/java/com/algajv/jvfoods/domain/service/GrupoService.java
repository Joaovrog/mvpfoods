package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.CozinhaNaoEncontradaException;
import com.algajv.jvfoods.domain.exception.EntidadeEmUsoException;
import com.algajv.jvfoods.domain.exception.GrupoNaoEncontradoException;
import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Grupo;
import com.algajv.jvfoods.domain.model.Permissao;
import com.algajv.jvfoods.domain.repository.CozinhaRepository;
import com.algajv.jvfoods.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrupoService {

    private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido pois está em uso.";

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private PermissaoService permissaoService;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return repository.save(grupo);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));

        }
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = getByIdOrFail(grupoId);
        Permissao permissao = permissaoService.getByIdOrFail(permissaoId);

        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = getByIdOrFail(grupoId);
        Permissao permissao = permissaoService.getByIdOrFail(permissaoId);

        grupo.removerPermissao(permissao);
    }


    public Grupo getByIdOrFail(Long grupoId) {
        return repository.findById(grupoId).orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }

}
