/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.bolao2.dao;

import br.ufscar.dc.bolao2.beans.Medico;
import br.ufscar.dc.bolao2.beans.Paciente;
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
public class PacienteDAO {

    private final static String CRIAR_PACIENTE_SQL = "insert into Paciente"
            + " (CPF, nome, senha, sexo, telefone, dataDeNascimento)"
            + " values (?,?,?,?,?,?)";

    private final static String BUSCAR_PACIENTE_SQL = "select"
            + " CPF, nome, sexo, telefone, dataDeNascimento, senha"
            + " from Paciente"
            + " where CPF=?";
    
    private final static String LISTAR_PACIENTES_SQL = "select "
            + " CPF, nome, sexo, telefone, dataDeNascimento"
            + " from Paciente";

    @Resource(name = "jdbc/Bolao2DBLocal")
    DataSource dataSource;

    public Paciente gravarPaciente(Paciente u) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(CRIAR_PACIENTE_SQL, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, u.getCpf());
            ps.setString(2, u.getNome());
            ps.setString(3, u.getSenha());
            ps.setString(4, u.getSexo());
            ps.setString(5, u.getTelefone());
            ps.setDate(6, new java.sql.Date(u.getDataDeNascimento().getTime()));
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                u.setCpf(rs.getInt(1));
            }
        }
        return u;
    }

    public Paciente buscarPaciente(int cpf) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(BUSCAR_PACIENTE_SQL)) {
            ps.setInt(1, cpf);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                Paciente u = new Paciente();
                u.setCpf(rs.getInt("cpf"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                u.setSexo(rs.getString("sexo"));
                u.setTelefone(rs.getString("telefone"));
                u.setDataDeNascimento(new Date(rs.getDate("dataDeNascimento").getTime()));
                return u;
            }
        }
    }
    public List<Paciente> listarTodosPacientes() throws SQLException, NamingException {
        List<Paciente> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTAR_PACIENTES_SQL)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Paciente m = new Paciente();
                    m.setCpf(rs.getInt("cpf"));
                    m.setNome(rs.getString("nome"));
                    m.setSenha(rs.getString("senha"));
                    m.setSexo(rs.getString("sexo"));
                    m.setTelefone(rs.getString("telefone"));
                    m.setDataDeNascimento(new Date(rs.getDate("dataDeNascimento").getTime()));
                    ret.add(m);
                }
            }
        }
        return ret;
    }
}
