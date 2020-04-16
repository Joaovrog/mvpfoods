package com.algajv.jvfoods.domain.service;

import com.algajv.jvfoods.domain.exception.*;
import com.algajv.jvfoods.domain.model.Grupo;
import com.algajv.jvfoods.domain.model.Permissao;
import com.algajv.jvfoods.domain.model.Usuario;
import com.algajv.jvfoods.domain.repository.GrupoRepository;
import com.algajv.jvfoods.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class UsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido pois está em uso.";

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EntityManager manager;

    @Autowired
    private GrupoService grupoService;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        manager.detach(usuario);
        Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
        if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {  // é um segundo usuario com o mesmo email, e não o mesmo que está sendo alterado.
            throw new NegocioException(String.format("Já existe usuário cadastrado com email %s.", usuario.getEmail()));
        }

        return repository.save(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, id));

        }
    }

    @Transactional
    public void atualizaSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = getByIdOrFail(id);
        if(usuario.senhaNaoCoincide(senhaAtual)) {
            throw new SenhaAtualInvalidaException();
        }
        usuario.setSenha(novaSenha);

    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = getByIdOrFail(usuarioId);
        Grupo grupo = grupoService.getByIdOrFail(grupoId);;

        usuario.removerDoGrupo(grupo);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = getByIdOrFail(usuarioId);
        Grupo grupo = grupoService.getByIdOrFail(grupoId);;

        usuario.incluirAoGrupo(grupo);
    }

    public Usuario getByIdOrFail(Long usuarioId) {
        return repository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }


}
