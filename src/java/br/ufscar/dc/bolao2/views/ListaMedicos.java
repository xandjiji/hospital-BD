/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.bolao2.views;

import br.ufscar.dc.bolao2.beans.Medico;
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
public class ListaMedicos implements Serializable {

    @Inject
    MedicoDAO medicoDao;

    private List<Medico> medicos;
    private List<Medico> medicosFiltrados;

    @PostConstruct
    public void init() {
        try {
            medicos = medicoDao.listarTodosMedicos();
        } catch (SQLException ex) {
            Logger.getLogger(ListaMedicos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ListaMedicos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MedicoDAO getMedicoDao() {
        return medicoDao;
    }

    public void setMedicoDao(MedicoDAO medicoDao) {
        this.medicoDao = medicoDao;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    public List<Medico> getMedicosFiltrados() {
        return medicosFiltrados;
    }

    public void setMedicosFiltrados(List<Medico> medicosFiltrados) {
        this.medicosFiltrados = medicosFiltrados;
    }
    
    

}
