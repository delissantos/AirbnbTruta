
package com.devcaotics.airBnTruta.model.entities;

import jakarta.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hospedagem")
public class Hospedagem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    
    @Column(nullable = false, length = 200)
    private String descricaoCurta;
    
    @Column(columnDefinition = "TEXT")
    private String descricaoLonga;
    
    @Column(length = 200)
    private String localizacao;
    
    @Column(nullable = false)
    private Double diaria;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date inicio;
    
    @Column
    @Temporal(TemporalType.DATE)
    private Date fim;

    @Transient
    private boolean temInteresse;
    
    @ManyToOne
    @JoinColumn(name = "hospedeiro_id", nullable = false)
    private Hospedeiro hospedeiro;
    
    @ManyToOne
    @JoinColumn(name = "fugitivo_id")
    private Fugitivo fugitivo;
    
    @ManyToMany
    @JoinTable(
        name = "hospedagem_servico",
        joinColumns = @JoinColumn(name = "hospedagem_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicos;
    
    @Transient
    @OneToMany(mappedBy = "hospedagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interesse> interesses;
    
    public Hospedagem(){
        this.inicio = new Date();
        this.servicos = new ArrayList<>();
        this.interesses = new ArrayList<>();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getDescricaoLonga() {
        return descricaoLonga;
    }

    public void setDescricaoLonga(String descricaoLonga) {
        this.descricaoLonga = descricaoLonga;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Double getDiaria() {
        return diaria;
    }

    public void setDiaria(Double diaria) {
        this.diaria = diaria;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Hospedeiro getHospedeiro() {
        return hospedeiro;
    }

    public void setHospedeiro(Hospedeiro hospedeiro) {
        this.hospedeiro = hospedeiro;
    }

    public Fugitivo getFugitivo() {
        return fugitivo;
    }

    public void setFugitivo(Fugitivo fugitivo) {
        this.fugitivo = fugitivo;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
    
    public void addServico(Servico s){
        this.servicos.add(s);
    }
    
    public String getInicioFormatado(){
        return new SimpleDateFormat("dd/MM/yyyy").format(this.inicio);
    }
    
    public String getFimFormatado(){
        if (this.fim == null) return "";
        return new SimpleDateFormat("dd/MM/yyyy").format(this.fim);
    }
    
    public void setInicio(String data){
        try {
            this.inicio = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException ex) {
            System.getLogger(Hospedagem.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void setFim(String data){
        try {
            this.fim = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException ex) {
            System.getLogger(Hospedagem.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public boolean isTemInteresse() {
        return temInteresse;
    }

    public void setTemInteresse(boolean b) {
        this.temInteresse = b;
    }
    
    public List<Interesse> getInteresses() {
        return interesses;
    }

    public void setInteresses(List<Interesse> interesses) {
        this.interesses = interesses;
    }
}
