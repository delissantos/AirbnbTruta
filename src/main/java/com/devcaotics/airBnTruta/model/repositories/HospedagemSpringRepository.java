package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HospedagemSpringRepository extends JpaRepository<Hospedagem, Integer> {
    List<Hospedagem> findByHospedeiro_Codigo(Integer hospdeiroId);
    List<Hospedagem> findByFugitivo_Codigo(Integer fugitivoId);
}
