/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.bolao2.dao;

import br.ufscar.dc.bolao2.beans.Consulta;
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
public class ConsultaDAO {

    private final static String CRIAR_CONSULTA_SQL = "insert into Consulta"
            + " (CPF, CRM, DATAEXAME)"
            + " values (?,?,?)";
    
    private final static String LISTAR_CONSULTAS_PACIENTE_SQL = "select "
            + " CPF, CRM, DATAEXAME"
            + " from Consulta"
            + " where CPF=?";
    
    private final static String LISTAR_CONSULTAS_MEDICO_SQL = "select "
            + " CPF, CRM, DATAEXAME"
            + " from Consulta"
            + " where CRM=?";
    
    private final static String CHECAR_DISPONIBILIDADE = "select "
            + " count(*) as rowcount"
            + " from Consulta"
            + " where (CRM=? OR CPF=?) AND DATAEXAME=?";    

    @Resource(name = "jdbc/Bolao2DBLocal")
    DataSource dataSource;

    public Consulta gravarConsulta(Consulta u) throws SQLException, NamingException {
        int rowcount = -1;
        try (Connection con = dataSource.getConnection();
                
                PreparedStatement ps = con.prepareStatement(CHECAR_DISPONIBILIDADE, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, u.getCrm());
                ps.setInt(2, u.getCpf());                
                ps.setDate(3, new java.sql.Date(u.getData().getTime()));
                
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                rowcount = rs.getInt("rowcount");
            }
        }
        
        if (rowcount == 0){
            try (Connection con = dataSource.getConnection();
                    PreparedStatement ps = con.prepareStatement(CRIAR_CONSULTA_SQL, Statement.RETURN_GENERATED_KEYS);) {
                ps.setInt(1, u.getCpf());
                ps.setInt(2, u.getCrm());
                ps.setDate(3, new java.sql.Date(u.getData().getTime()));
                ps.execute();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    rs.next();
                    u.setCpf(rs.getInt(1));
                }
            }
        }
        else{
            throw new SQLException("unavailable");
        }
        
        return u;
    }
    
    public List<Consulta> listarTodosConsultas(int codigo, String tipo_pessoa) throws SQLException, NamingException {
        List<Consulta> ret = new ArrayList<>();
        String sql_command = null;
        if (tipo_pessoa.equals("medico"))
            sql_command = LISTAR_CONSULTAS_MEDICO_SQL;
        else if (tipo_pessoa.equals("paciente"))
            sql_command = LISTAR_CONSULTAS_PACIENTE_SQL;
        
        
        try (Connection con = dataSource.getConnection();              
                PreparedStatement ps = con.prepareStatement(sql_command)) {
                ps.setInt(1, codigo);
                
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta m = new Consulta();
                    m.setCpf(rs.getInt("cpf"));
                    m.setCrm(rs.getInt("crm"));
                    m.setData(new Date(rs.getDate("dataexame").getTime()));
                    ret.add(m);
                }
            }
        }
        return ret;
    }
}
