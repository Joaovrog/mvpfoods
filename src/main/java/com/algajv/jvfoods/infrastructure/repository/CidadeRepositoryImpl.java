package com.algajv.jvfoods.infrastructure.repository;

import com.algajv.jvfoods.domain.model.Cidade;
import com.algajv.jvfoods.domain.repository.CidadeRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cidade> listar() {
        TypedQuery<Cidade> query = manager.createQuery("from Cidade", Cidade.class);
        return query.getResultList();
    }

    @Override
    public Cidade buscar(Long id) {
        return manager.find(Cidade.class, id);
    }

    @Override
    public Cidade salvar(Cidade cidade) {
        return manager.merge(cidade);
    }

    @Override
    public void remover(Cidade cidade) {
        cidade = buscar(cidade.getId());
        manager.remove(cidade);
    }
}
