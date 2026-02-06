package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FugitivoSpringRepository extends JpaRepository<Fugitivo, Integer> {
    List<Fugitivo> findByNome(String nome);
    List<Fugitivo> findByVulgo(String vulgo);
}
