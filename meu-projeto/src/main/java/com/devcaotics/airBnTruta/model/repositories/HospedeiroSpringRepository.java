package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HospedeiroSpringRepository extends JpaRepository<Hospedeiro, Integer> {
    List<Hospedeiro> findByNome(String nome);
    List<Hospedeiro> findByVulgo(String vulgo);
}
