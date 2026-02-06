package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.entities.Servico;
import com.devcaotics.airBnTruta.model.repositories.Facade;
import com.devcaotics.airBnTruta.model.repositories.FacadeJpa;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/hospedagem")
public class HospedagemController {
    @Autowired
    private Facade facade;
    
    @Autowired
    private FacadeJpa facadeJpa;
    
    @Autowired
    private HttpSession session;

    @GetMapping("/new")
    public String newHospedagem(Model m) {

        m.addAttribute("hospedagem", new Hospedagem());
        try {
            m.addAttribute("servicos", facadeJpa.readAllServico());
            
        } catch (Exception e) {
            m.addAttribute("msg", "Erro ao carregar os servicos: " + e.getMessage());
            e.printStackTrace();
        }
        return "hospedeiro/newhospedagem";
    }

    @PostMapping("/new")
    public String postNewHospedagem(Model m, Hospedagem h,
        @RequestParam(value = "servs", required = false) String[] servs) {
        
        List<Servico> servicos = new ArrayList<>();
        
        if (servs != null && servs.length > 0) {
            servicos = Arrays.stream(servs)
                .map(c -> {
                    try {
                        return facadeJpa.readServico(Integer.parseInt(c));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(s -> s != null)
                .collect(Collectors.toList());
        }

        h.setServicos(servicos);

        h.setHospedeiro((Hospedeiro)session.getAttribute("hospedeiroLogado"));

        try {
            facadeJpa.create(h);
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "Erro ao criar hospedagem: " + e.getMessage());
        }
        
        return "redirect:/hospedeiro";
    }
    
    

}
