package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.entities.Interesse;
import com.devcaotics.airBnTruta.model.entities.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FacadeJpa {

    @Autowired
    private FugitivoSpringRepository fugitivoRepository;
    
    @Autowired
    private HospedeiroSpringRepository hospedeiroRepository;
    
    @Autowired
    private HospedagemSpringRepository hospedagemRepository;
    
    @Autowired
    private ServicoSpringRepository servicoRepository;
    
    @Autowired
    private InteresseSpringRepository interesseRepository;

    // Fugitivo
    public void create(Fugitivo f) {
        fugitivoRepository.save(f);
    }

    public void update(Fugitivo f) {
        fugitivoRepository.save(f);
    }

    public Fugitivo readFugitivo(Integer codigo) {
        return fugitivoRepository.findById(codigo).orElse(null);
    }

    public Fugitivo loginFugitivo(String vulgo, String senha) {
        List<Fugitivo> fugitivos = fugitivoRepository.findByVulgo(vulgo);
        if (fugitivos != null && !fugitivos.isEmpty()) {
            for (Fugitivo f : fugitivos) {
                if (f.getSenha().equals(senha)) {
                    return f;
                }
            }
        }
        return null;
    }

    public List<Fugitivo> readAllFugitivo() {
        return fugitivoRepository.findAll();
    }

    // Hospedeiro
    public void create(Hospedeiro h) {
        hospedeiroRepository.save(h);
    }

    public void update(Hospedeiro h) {
        hospedeiroRepository.save(h);
    }

    public Hospedeiro readHospedeiro(Integer codigo) {
        return hospedeiroRepository.findById(codigo).orElse(null);
    }

    public Hospedeiro loginHospedeiro(String vulgo, String senha) {
        List<Hospedeiro> hospedeiros = hospedeiroRepository.findByVulgo(vulgo);
        if (hospedeiros != null && !hospedeiros.isEmpty()) {
            for (Hospedeiro h : hospedeiros) {
                if (h.getSenha().equals(senha)) {
                    return h;
                }
            }
        }
        return null;
    }

    // Hospedagem
    public void create(Hospedagem h) {
        hospedagemRepository.save(h);
    }

    public void update(Hospedagem h) {
        hospedagemRepository.save(h);
    }

    public Hospedagem readHospedagem(Integer codigo) {
        return hospedagemRepository.findById(codigo).orElse(null);
    }

    public List<Hospedagem> readAllHospedagem() {
        return hospedagemRepository.findAll();
    }

    public List<Hospedagem> filterHospedagemByAvailable() {
        List<Hospedagem> all = hospedagemRepository.findAll();
        return all.stream()
                .filter(h -> h.getFugitivo() == null)
                .toList();
    }

    public List<Hospedagem> filterHospedagemByAvailable(String loc, Double maxPrice) {
        List<Hospedagem> all = hospedagemRepository.findAll();
        return all.stream()
                .filter(h -> h.getFugitivo() == null)
                .filter(h -> loc == null || h.getLocalizacao() == null || 
                           h.getLocalizacao().toLowerCase().contains(loc.toLowerCase()))
                .filter(h -> maxPrice == null || h.getDiaria() <= maxPrice)
                .toList();
    }

    public List<Hospedagem> filterByHospedeiro(Integer hospedeiroId) {
        return hospedagemRepository.findByHospedeiro_Codigo(hospedeiroId);
    }

    // Servico
    public void create(Servico s) {
        servicoRepository.save(s);
    }

    public void update(Servico s) {
        servicoRepository.save(s);
    }

    public Servico readServico(Integer codigo) {
        return servicoRepository.findById(codigo).orElse(null);
    }

    public List<Servico> readAllServico() {
        return servicoRepository.findAll();
    }

    // Interesse
    public void registrarInteresse(Interesse i) {
        interesseRepository.save(i);
    }

    public List<Interesse> filterByFugitivo(Integer fugitivoId) {
        return interesseRepository.findByFugitivo_Codigo(fugitivoId);
    }

    public List<Interesse> filterByHospedagem(Integer hospedagemId) {
        return interesseRepository.findByHospedagem_Codigo(hospedagemId);
    }
}
