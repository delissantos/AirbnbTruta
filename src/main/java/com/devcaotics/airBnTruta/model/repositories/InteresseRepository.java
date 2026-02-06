package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Interesse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class InteresseRepository implements Repository<Interesse,Integer>{

    protected InteresseRepository(){}

    @Override
    public void create(Interesse i) throws SQLException {
        String sql = "INSERT INTO interesse (realizado, proposta, tempo_permanencia, fugitivo_id, hospedagem_id) "
                   + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setLong(1, i.getRealizado());
        stmt.setString(2, i.getProposta());
        stmt.setInt(3, i.getTempoPermanencia());
        stmt.setInt(4, i.getFugitivo().getCodigo());
        stmt.setInt(5, i.getHospedagem().getCodigo());

        stmt.execute();
    }

    @Override
    public void update(Interesse c) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Interesse read(Integer k) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public void delete(Integer k) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Interesse> readAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

    public List<Interesse> filterByFugitivo(int idFugitivo) throws SQLException {
        String sql = "SELECT i.*, h.codigo as h_codigo, h.descricao_curta, h.localizacao, h.diaria " +
                     "FROM interesse i " +
                     "JOIN hospedagem h ON i.hospedagem_id = h.codigo " +
                     "WHERE i.fugitivo_id = ? AND (h.fugitivo_id IS NULL OR h.fugitivo_id = 0)";

        List<Interesse> lista = new ArrayList<>();

        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setInt(1, idFugitivo);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Interesse i = new Interesse();
                    i.setCodigo(rs.getInt("codigo"));
                    i.setProposta(rs.getString("proposta"));
                    i.setTempoPermanencia(rs.getInt("tempo_permanencia"));
                    i.setRealizado(rs.getLong("realizado"));

                    Hospedagem h = new Hospedagem();
                    h.setCodigo(rs.getInt("h_codigo"));
                    h.setDescricaoCurta(rs.getString("descricao_curta"));
                    h.setLocalizacao(rs.getString("localizacao"));
                    h.setDiaria(rs.getDouble("diaria"));
                    
                    i.setHospedagem(h); 

                    lista.add(i);
                }
            }
        }
        return lista;
    }

    public List<Interesse> filterByHospedagem(int idHospedagem) throws SQLException {
        String sql = "SELECT * FROM interesse WHERE hospedagem_id = ?";
        List<Interesse> lista = new ArrayList<>();

        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setInt(1, idHospedagem);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Interesse i = new Interesse();
                    i.setCodigo(rs.getInt("codigo"));
                    i.setProposta(rs.getString("proposta"));
                    i.setTempoPermanencia(rs.getInt("tempo_permanencia"));

                    Fugitivo f = new FugitivoRepository().read(rs.getInt("fugitivo_id"));
                    i.setFugitivo(f);
                    
                    lista.add(i);
                }
            }
        }
        return lista;
    }

}
