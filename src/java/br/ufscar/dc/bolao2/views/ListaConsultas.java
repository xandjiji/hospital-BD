/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.bolao2.views;

import br.ufscar.dc.bolao2.beans.Consulta;
import br.ufscar.dc.bolao2.beans.Medico;
import br.ufscar.dc.bolao2.dao.ConsultaDAO;
import br.ufscar.dc.bolao2.dao.MedicoDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;


@Named
@ViewScoped
public class ListaConsultas implements Serializable {

    @Inject
    ConsultaDAO consultaDao;
    
    @Inject
    Login loginobj;

    private List<Consulta> consultas;
    private List<Consulta> consultasFiltrados;

    @PostConstruct
    public void init() {
        try {
            int cod = Integer.parseInt(loginobj.getUser());
            consultas = consultaDao.listarTodosConsultas(cod, loginobj.getUser_type());
        } catch (SQLException ex) {
            Logger.getLogger(ListaConsultas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ListaConsultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConsultaDAO getConsultaDao() {
        return consultaDao;
    }

    public void setConsultaDao(ConsultaDAO consultaDao) {
        this.consultaDao = consultaDao;
    }

    public Login getLoginobj() {
        return loginobj;
    }

    public void setLoginobj(Login loginobj) {
        this.loginobj = loginobj;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<Consulta> getConsultasFiltrados() {
        return consultasFiltrados;
    }

    public void setConsultasFiltrados(List<Consulta> consultasFiltrados) {
        this.consultasFiltrados = consultasFiltrados;
    }

    
}
