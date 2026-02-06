package com.devcaotics.airBnTruta.model.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "fugitivo")
public class Fugitivo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(length = 100)
    private String vulgo;
    
    @Column(length = 150)
    private String especialidade;
    
    @Column(length = 100)
    private String faccao;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(nullable = false, length = 100)
    private String senha;
    
    @Transient
    @OneToMany(mappedBy = "fugitivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hospedagem> hospedagens;
    
    @Transient
    @OneToMany(mappedBy = "fugitivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interesse> interesses;

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getVulgo() {
        return vulgo;
    }
    public void setVulgo(String vulgo) {
        this.vulgo = vulgo;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    public String getFaccao() {
        return faccao;
    }
    public void setFaccao(String faccao) {
        this.faccao = faccao;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Integer getCodigo() {
        return codigo;
    }
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public List<Hospedagem> getHospedagens() {
        return hospedagens;
    }
    public void setHospedagens(List<Hospedagem> hospedagens) {
        this.hospedagens = hospedagens;
    }
    
    public List<Interesse> getInteresses() {
        return interesses;
    }
    public void setInteresses(List<Interesse> interesses) {
        this.interesses = interesses;
    }
}
