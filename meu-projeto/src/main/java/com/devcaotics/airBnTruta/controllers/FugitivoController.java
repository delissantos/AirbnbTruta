package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Interesse;
import com.devcaotics.airBnTruta.model.repositories.Facade;
import com.devcaotics.airBnTruta.model.repositories.FacadeJpa;
import com.devcaotics.airBnTruta.model.repositories.InteresseRepository;


import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/fugitivo")
public class FugitivoController {

    @Autowired
    private Facade facade;

    @Autowired
    private FacadeJpa facadeJpa;

    @Autowired
    private HttpSession session;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("codigo", "hospedagens", "interesses");
    }

    @PostMapping("")
    public String index(Model m, 
                        @RequestParam(required = false) String loc, 
                        @RequestParam(required = false) Double maxPrice) {
        
        Object fugitivoObj = session.getAttribute("fugitivoLogado");
        if (fugitivoObj == null) {
            return "redirect:/fugitivo/login";
        }

        try {
            List<Hospedagem> resultados;

            if ((loc != null && !loc.isEmpty()) || (maxPrice != null && maxPrice > 0)) {
                resultados = facadeJpa.filterHospedagemByAvailable(loc, maxPrice);
            } else {
                resultados = facadeJpa.filterHospedagemByAvailable();
            }
            
            if (resultados == null) resultados = List.of();
            m.addAttribute("hospedagens", resultados);
            m.addAttribute("fugitivo", fugitivoObj);
            
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "Erro ao filtrar os esconderijos: " + e.getMessage());
            m.addAttribute("hospedagens", List.of());
            m.addAttribute("fugitivo", fugitivoObj);
        }

        return "fugitivo/index";
    } 

    /* @GetMapping("")
    public String index(Model m) {

        if (session.getAttribute("fugitivoLogado") == null) {
            return "redirect:/fugitivo/login";
        }

        try {
            List<Hospedagem> disponiveis = facade.filterHospedagemByAvailable();
            m.addAttribute("hospedagens", disponiveis);

            Fugitivo logado = (Fugitivo) session.getAttribute("fugitivoLogado");
            m.addAttribute("fugitivo", logado);
            
        } catch (SQLException e) {
            e.printStackTrace();
            m.addAttribute("msg", "Erro na busca por esconderijos!");
        }

        return "fugitivo/index";
    } */

    @GetMapping({"", "/"})
    public String index (Model model){

        Object fugitivoObj = session.getAttribute("fugitivoLogado");
        if (fugitivoObj == null) {
            return "redirect:/fugitivo/login";
        }

        try{
            List<Hospedagem> hospedagens = facadeJpa.filterHospedagemByAvailable();
            if (hospedagens == null) hospedagens = List.of();
            model.addAttribute("hospedagens", hospedagens);
            model.addAttribute("fugitivo", fugitivoObj);

            return "fugitivo/index";
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg", "Erro ao carregar esconderijos: " + e.getMessage());
            List<Hospedagem> hospedagens = List.of();
            model.addAttribute("hospedagens", hospedagens);
            model.addAttribute("fugitivo", fugitivoObj);
            return "fugitivo/index";
        }
    }


    @GetMapping("/login")
    public String loginPage(Model m) {
        if (session.getAttribute("fugitivoLogado") != null) {
            return "redirect:/fugitivo";
        }
        
        m.addAttribute("fugitivo", new Fugitivo()); 
        return "fugitivo/login";
    }

    /* @PostMapping("/login")
    public String realizarLogin(@RequestParam String vulgo, @RequestParam String senha, RedirectAttributes attr) {
        try {
            Fugitivo f = facade.loginFugitivo(vulgo, senha);
            if (f != null) {
                session.setAttribute("fugitivoLogado", f);
                session.setAttribute("fugitivo", f); // opcional: compatibilidade com templates antigos
                return "redirect:/fugitivo";
            } else {
                attr.addFlashAttribute("msg", "Vulgo ou senha incorretos!");
                return "redirect:/fugitivo/login";
            }
        } catch (SQLException e) {
            attr.addFlashAttribute("msg", "Erro no banco de dados!");
            return "redirect:/fugitivo/login";
        }
    } */

    @PostMapping("/login")
    public String realizarLogin(@RequestParam String vulgo, @RequestParam String senha, RedirectAttributes attr) {
        try {
            Fugitivo f = facadeJpa.loginFugitivo(vulgo, senha);
            if (f != null) {
                session.setAttribute("fugitivoLogado", f);
                session.setAttribute("fugitivo", f); // opcional: compatibilidade com templates antigos
                return "redirect:/fugitivo";
            } else {
                attr.addFlashAttribute("msg", "Vulgo ou senha incorretos!");
                return "redirect:/fugitivo/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("msg", "Erro ao fazer login: " + e.getMessage());
            return "redirect:/fugitivo/login";
        }
    }

    @PostMapping("/save")
    public String autoRegistrar(Fugitivo f, RedirectAttributes attr) {
        try {
            facadeJpa.create(f);
            attr.addFlashAttribute("msg", "Cadastro realizado! Pode logar agora, " + f.getVulgo());
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = "Erro ao cadastrar: " + e.getMessage();
            System.err.println(errorMsg);
            attr.addFlashAttribute("msg", errorMsg);
        }
        return "redirect:/fugitivo/login";
    }


    @PostMapping("/interesse/new")
    public String registrarInteresse(Interesse interesse, 
                                     @RequestParam("hospedagemId") int hospId, 
                                     RedirectAttributes attr) {
        
        Fugitivo logado = (Fugitivo) session.getAttribute("fugitivoLogado");
        if (logado == null) return "redirect:/fugitivo/login";

        try {
            interesse.setFugitivo(logado);
            
            Hospedagem h = new Hospedagem();
            h.setCodigo(hospId);
            interesse.setHospedagem(h);

            facadeJpa.registrarInteresse(interesse);

            attr.addFlashAttribute("msg", "Interesse registrado! O dono vai analisar sua proposta.");
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("msg", "Erro ao registrar interesse: " + e.getMessage());
        }

        return "redirect:/fugitivo";
    }

    @GetMapping("/interesses")
    public String verMeusInteresses(Model m) {
        Fugitivo logado = (Fugitivo) session.getAttribute("fugitivoLogado");
        if (logado == null) return "redirect:/fugitivo/login";

        try {
            List<Interesse> lista = facadeJpa.filterByFugitivo(logado.getCodigo());
            m.addAttribute("interesses", lista);
            m.addAttribute("fugitivo", logado);
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "Erro ao carregar interesses: " + e.getMessage());
            m.addAttribute("interesses", List.of());
            m.addAttribute("fugitivo", logado);
        }

        return "fugitivo/interesses";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/fugitivo/login";
    }
}