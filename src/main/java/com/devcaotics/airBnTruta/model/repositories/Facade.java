package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.entities.Interesse;
import com.devcaotics.airBnTruta.model.entities.Servico;
import java.sql.SQLException;
import java.util.List;

import java.util.Locale;
import java.util.stream.Collectors;


@org.springframework.stereotype.Repository
public class Facade {

    private static Facade myself = null;

    private Repository<Servico,Integer> rServico;
    private Repository<Fugitivo, Integer> rFugitivo;
    private Repository<Hospedeiro, Integer> rHospedeiro;
    private Repository<Hospedagem, Integer> rHospedagem;
    private Repository<Interesse, Integer> rInteresse;

    public Facade(){
        rServico = new ServicoRepository();
        this.rFugitivo = new FugitivoRepository();
        this.rHospedeiro = new HospedeiroRepository();
        this.rHospedagem = new HospedagemRepository();
        this.rInteresse = new InteresseRepository();
    }

    public static Facade getCurrentInstance(){

        if(myself == null)
            myself = new Facade();

        return myself;
    }

    public void create(Servico s) throws SQLException{
        this.rServico.create(s);
    }

    public void update(Servico s) throws SQLException{
        this.rServico.update(s);
    }

    public Servico readServico(int codigo) throws SQLException{
        return this.rServico.read(codigo);
    }

    public void deleteServico(int codigo) throws SQLException{
        this.rServico.delete(codigo);
    }

    public List<Servico> readAllServico() throws SQLException{
        return this.rServico.readAll();
    }

    public void create(Fugitivo f) throws SQLException{
        this.rFugitivo.create(f);
    }

    public void update(Fugitivo f) throws SQLException{
        this.rFugitivo.update(f);
    }

    public Fugitivo readFugitivo(int codigo) throws SQLException{
        return this.rFugitivo.read(codigo);
    }

    public Fugitivo loginFugitivo(String vulgo, String senha) throws SQLException{
        return ((FugitivoRepository)this.rFugitivo).login(vulgo,senha);
    }

    public void create(Hospedeiro h) throws SQLException{
        this.rHospedeiro.create(h);
    }

    public Hospedeiro loginHospedeiro(String vulgo, String senha) throws SQLException{
        return ((HospedeiroRepository)this.rHospedeiro).login(vulgo, senha);

    }

    public void create(Hospedagem h) throws SQLException{
        this.rHospedagem.create(h);
    }

    public Hospedagem readHospedagem(int codigo) throws SQLException{
        return this.rHospedagem.read(codigo);
    }

    public List<Hospedagem> filterHospedagemByAvailable() throws SQLException{
        return ((HospedagemRepository)this.rHospedagem).filterByAvailable();
    }

    public List<Hospedagem> filterHospedagemByAvailable(String loc, Double maxPrice) throws SQLException {
        // usa método já existente que traz apenas disponíveis
        List<Hospedagem> disponiveis = this.filterHospedagemByAvailable();
        if (disponiveis == null) {
            return List.of();
        }

        if ((loc == null || loc.isBlank()) && maxPrice == null) {
            return disponiveis;
        }

        String locLower = (loc == null) ? null : loc.trim().toLowerCase(Locale.ROOT);

        return disponiveis.stream()
            .filter(h -> {
                // filtra por local (contains, case-insensitive)
                if (locLower != null && !locLower.isEmpty()) {
                    String localizacao = h.getLocalizacao() == null ? "" : h.getLocalizacao().toLowerCase(Locale.ROOT);
                    if (!localizacao.contains(locLower)) return false;
                }

                // filtra por preço máximo
                if (maxPrice != null) {
                    Double diaria = h.getDiaria();
                    if (diaria > maxPrice) return false;
                }

                return true;
            })
            .collect(Collectors.toList());
    }

    public List<Hospedagem> filterHospedagemByHospedeiro(int codigoHospedeiro) throws SQLException{
        return ((HospedagemRepository)this.rHospedagem).filterByHospedeiro(codigoHospedeiro);
    }

    public List<Hospedagem> filterHospedagem(String local, Double preco) throws SQLException {
        return ((HospedagemRepository)this.rHospedagem).filterAvailableByLocationAndPrice(local, preco);
    }

    public void registrarInteresse(Interesse i) throws SQLException {
        this.rInteresse.create(i); 
    }

    public List<Interesse> meusInteresses(int idFugitivo) throws SQLException {
        return ((InteresseRepository)this.rInteresse).filterByFugitivo(idFugitivo);
    }

    public List<Interesse> verInteressesDaHospedagem(int idHospedagem) throws SQLException {
        return ((InteresseRepository)this.rInteresse).filterByHospedagem(idHospedagem);
    }

    public void aceitarFugitivo(int idHospedagem, int idFugitivo) throws SQLException {
        ((HospedagemRepository)this.rHospedagem).alugar(idHospedagem, idFugitivo);
    }

}
