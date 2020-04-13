package com.algajv.jvfoods.domain.repository;

import com.algajv.jvfoods.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, JpaSpecificationExecutor<Restaurante> {

//    List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
//    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);
//    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
//    List<Restaurante> findTop2ByNomeContaining(String nome);

//    @Query("from Restaurante where nome like %:nome% and cozinha.id=:id")
//    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

    List<Restaurante> findComFreteGratis(String nome);

    @Query("from Restaurante r join  r.cozinha")
    List<Restaurante> findAll();

}
