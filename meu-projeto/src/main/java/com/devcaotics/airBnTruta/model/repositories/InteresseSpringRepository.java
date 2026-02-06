package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Interesse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InteresseSpringRepository extends JpaRepository<Interesse, Integer> {
    List<Interesse> findByFugitivo_Codigo(Integer fugitivoId);
    List<Interesse> findByHospedagem_Codigo(Integer hospedagemId);
}
