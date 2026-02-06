package com.devcaotics.airBnTruta.model.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "hospedeiro")
public class Hospedeiro{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(length = 100)
    private String vulgo;
    
    @Column(length = 150)
    private String contato;
    
    @Column(nullable = false, length = 100)
    private String senha;
    
    @Transient
    @OneToMany(mappedBy = "hospedeiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hospedagem> hospedagens;
    
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
    public String getVulgo() {
        return vulgo;
    }
    public void setVulgo(String vulgo) {
        this.vulgo = vulgo;
    }
    public String getContato() {
        return contato;
    }
    public void setContato(String contato) {
        this.contato = contato;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public List<Hospedagem> getHospedagens() {
        return hospedagens;
    }
    public void setHospedagens(List<Hospedagem> hospedagens) {
        this.hospedagens = hospedagens;
    }
}
