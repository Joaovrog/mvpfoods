package com.algajv.jvfoods.domain.repository;

import com.algajv.jvfoods.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
