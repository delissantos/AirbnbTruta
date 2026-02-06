package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.entities.Interesse;
import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.repositories.Facade;
import com.devcaotics.airBnTruta.model.repositories.FacadeJpa;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/hospedeiro")
public class HospedeiroController {

    @Autowired
    private Facade facade;
    
    @Autowired
    private FacadeJpa facadeJpa;
    
    private String msg = null;
    
    @Autowired
    private HttpSession session;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("codigo", "hospedagens");
    }

    @GetMapping({"/",""})
    public String init(Model m) {

        if(session.getAttribute("hospedeiroLogado") != null){
            Hospedeiro logado = (Hospedeiro)this.session.getAttribute("hospedeiroLogado");
            try {
                List<Hospedagem> hospedagens = this.facadeJpa.filterByHospedeiro(logado.getCodigo());
                
                // Marcar hospedagens que têm interesses
                for (Hospedagem hosp : hospedagens) {
                    List<Interesse> interesses = facadeJpa.filterByHospedagem(hosp.getCodigo());
                    hosp.setTemInteresse(interesses != null && !interesses.isEmpty());
                }
                
                m.addAttribute("hospedagens", hospedagens);
            } catch (Exception e) {
                e.printStackTrace();
                m.addAttribute("msg", "não foi possível carregar suas hospedagens: " + e.getMessage());
            }

            return "hospedeiro/index";
        }

        m.addAttribute("hospedeiro", new Hospedeiro());
        m.addAttribute("msg", this.msg);
        this.msg=null;
        return "hospedeiro/login";
    }

    @PostMapping("/save")
    public String newHospedeiro(Model m, Hospedeiro h) {
        try {
            facadeJpa.create(h);
            this.msg="Parabéns! Seu cadastro foi realizado com sucesso! Agora faça o login, por favor, meu querido hospedeiro de minha vida!";
        } catch (Exception e) {
            e.printStackTrace();
            this.msg="Erro ao criar cadastro: " + e.getMessage();
        }
        return "redirect:/hospedeiro";
    }

    @PostMapping("/login")
    public String login(Model m,@RequestParam String vulgo,
        @RequestParam String senha
    ) {
        try {
            Hospedeiro logado = facadeJpa.loginHospedeiro(vulgo, senha);
            if(logado == null){
                this.msg = "Vulgo ou senha incorretos";
                return "redirect:/hospedeiro";
            }
            session.setAttribute("hospedeiroLogado", logado);
            return "redirect:/hospedeiro";
            
        } catch (Exception e) {
            e.printStackTrace();
            this.msg = "Erro ao logar: " + e.getMessage();
            return "redirect:/hospedeiro";
        }
    }

    @GetMapping("/interesses/{id}")
    public String verPropostas(@PathVariable("id") int idHospedagem, Model m) {
        try {
            Hospedagem h = facadeJpa.readHospedagem(idHospedagem);
            m.addAttribute("hospedagem", h);

            List<Interesse> interesses = facadeJpa.filterByHospedagem(idHospedagem);
            m.addAttribute("interesses", interesses);
            
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "Erro ao carregar propostas: " + e.getMessage());
        }
        return "hospedeiro/interesses";
    }

    @PostMapping("/aceitar")
    public String aceitarProposta(@RequestParam("hospedagemId") int hId, 
                                  @RequestParam("fugitivoId") int fId,
                                  RedirectAttributes attr) {
        try {
            // TODO: Implement aceitarFugitivo logic in FacadeJpa
            // For now, just update the hospedagem to associate with the fugitivo
            Hospedagem h = facadeJpa.readHospedagem(hId);
            Fugitivo f = facadeJpa.readFugitivo(fId);
            if (h != null && f != null) {
                h.setFugitivo(f);
                facadeJpa.update(h);
                attr.addFlashAttribute("msg", "Negócio fechado!");
            } else {
                attr.addFlashAttribute("msg", "Erro: Hospedagem ou Fugitivo não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("msg", "Erro ao aceitar proposta: " + e.getMessage());
        }
        return "redirect:/hospedeiro";
    }
    
    
    @GetMapping("/logout")
    public String logout(Model m) {

        session.removeAttribute("hospedeiroLogado");;

        return "redirect:/hospedeiro";
    }

    @GetMapping("/viewhospedagem/{id}")
    public String viewHospedagem(@PathVariable("id") int idHospedagem, Model m, RedirectAttributes attr) {
        try {
            Hospedagem h = facadeJpa.readHospedagem(idHospedagem);
            if (h == null) {
                attr.addFlashAttribute("msg", "Esconderijo n\u00e3o encontrado.");
                return "redirect:/hospedeiro";
            }
            m.addAttribute("hospedagem", h);
            return "hospedeiro/viewhospedagem";
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("msg", "Erro ao carregar detalhe da hospedagem: " + e.getMessage());
            return "redirect:/hospedeiro";
        }
    }
    

}
