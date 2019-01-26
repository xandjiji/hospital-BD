/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.bolao2.dao;

import br.ufscar.dc.bolao2.beans.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.naming.NamingException;
import javax.sql.DataSource;


@RequestScoped
public class MedicoDAO {

    private final static String CRIAR_MEDICO_SQL = "insert into Medico"
            + " (crm, nome, senha, especialidade)"
            + " values (?,?,?,?)";

    private final static String BUSCAR_MEDICO_SQL = "select"
            + " crm, nome, senha, especialidade"
            + " from Medico"
            + " where crm=?";
    
    private final static String LISTAR_MEDICOS_SQL = "select "
            + " crm, nome, especialidade"
            + " from Medico";
    
    private final static String LISTAR_MEDICOS_SQL_ESPECIALIDADE = "select "
            + " crm, nome, especialidade"
            + " from Medico"
            + " where especialidade=?";

    @Resource(name = "jdbc/Bolao2DBLocal")
    DataSource dataSource;

    public Medico gravarMedico(Medico u) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(CRIAR_MEDICO_SQL, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, u.getCrm());
            ps.setString(2, u.getNome());
            ps.setString(3, u.getSenha());
            ps.setString(4, u.getEspecialidade());
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                u.setCrm(rs.getInt(1));
            }
        }
        return u;
    }

    public Medico buscarMedico(int crm) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(BUSCAR_MEDICO_SQL)) {
            ps.setInt(1, crm);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                Medico u = new Medico();
                u.setCrm(rs.getInt("crm"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                u.setEspecialidade(rs.getString("especialidade"));
                return u;
            }
        }
    }
    public List<Medico> listarTodosMedicos() throws SQLException, NamingException {
        List<Medico> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTAR_MEDICOS_SQL)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medico m = new Medico();
                    m.setCrm(rs.getInt("crm"));
                    m.setNome(rs.getString("nome"));
                    m.setEspecialidade(rs.getString("especialidade"));
                    ret.add(m);
                }
            }
        }
        return ret;
    }
    
    public List<Medico> listarTodosMedicosPorEspecialidade(String especialidade) throws SQLException, NamingException {
        List<Medico> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTAR_MEDICOS_SQL_ESPECIALIDADE)) {

            ps.setString(1, especialidade);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medico m = new Medico();
                    m.setCrm(rs.getInt("crm"));
                    m.setNome(rs.getString("nome"));
                    m.setEspecialidade(rs.getString("especialidade"));
                    ret.add(m);
                }
            }
        }
        return ret;
    }
}
