
package com.devcaotics.airBnTruta.model.entities;

import jakarta.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "interesse")
public class Interesse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    
    @Column(nullable = false)
    private Long realizado;
    
    @Column(length = 255)
    private String proposta;
    
    @Column
    private Integer tempoPermanencia;
    
    @ManyToOne
    @JoinColumn(name = "fugitivo_id", nullable = false)
    private Fugitivo fugitivo;
    
    @ManyToOne
    @JoinColumn(name = "hospedagem_id", nullable = false)
    private Hospedagem hospedagem;
    
    public Interesse(){
        this.realizado = new Date().getTime();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Long getRealizado() {
        return realizado;
    }

    public void setRealizado(Long realizado) {
        this.realizado = realizado;
    }

    public String getProposta() {
        return proposta;
    }

    public void setProposta(String proposta) {
        this.proposta = proposta;
    }

    public Integer getTempoPermanencia() {
        return tempoPermanencia;
    }

    public void setTempoPermanencia(Integer tempoPermanencia) {
        this.tempoPermanencia = tempoPermanencia;
    }

    public Fugitivo getFugitivo() {
        return fugitivo;
    }

    public void setFugitivo(Fugitivo fugitivo) {
        this.fugitivo = fugitivo;
    }

    public Hospedagem getHospedagem() {
        return hospedagem;
    }

    public void setHospedagem(Hospedagem hospedagem) {
        this.hospedagem = hospedagem;
    }
    
    // Alias para getFugitivo() - compatibilidade com templates
    public Fugitivo getInteressado() {
        return this.fugitivo;
    }
    
    public void setRealizado(String data){
        try {
            this.realizado = new SimpleDateFormat("dd/MM/yyyy").parse(data).getTime();
        } catch (ParseException ex) {
            System.getLogger(Interesse.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public String getRealizadoFormatado(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(this.realizado));
    }
}
