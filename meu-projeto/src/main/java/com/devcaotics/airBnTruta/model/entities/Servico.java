package com.devcaotics.airBnTruta.model.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "servico")
public class Servico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    
    @Column(nullable = false, length = 25)
    private String nome;
    
    @Column(length = 20)
    private String tipo;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Transient
    @ManyToMany(mappedBy = "servicos")
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public List<Hospedagem> getHospedagens() {
        return hospedagens;
    }

    public void setHospedagens(List<Hospedagem> hospedagens) {
        this.hospedagens = hospedagens;
    }
}
