/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.bolao2.views;

import br.ufscar.dc.bolao2.beans.Medico;
import br.ufscar.dc.bolao2.beans.Paciente;
import br.ufscar.dc.bolao2.dao.MedicoDAO;
import br.ufscar.dc.bolao2.dao.PacienteDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;


@Named
@SessionScoped
public class Login implements Serializable {

    @Inject
    PacienteDAO pacdao;
    
    @Inject
    MedicoDAO meddao;
    
    private String user_type, user, pass;
    private boolean logado_admin, logado_medico, logado_paciente, nao_logado;

    private MensagemBootstrap mensagem;
    
    public Login(){
        logado_admin = logado_medico = logado_paciente = false;
        nao_logado = true;
    }
            
    public String fazerLogout(){
        System.out.println("fazendo logout");
        logado_admin = logado_medico = logado_paciente = false;
        user = pass = null;
        nao_logado = true;
        
        return "index";
    }
    
    public String fazerLogin() throws IOException, SQLException, NamingException{
        System.out.println("fazer login");
        if (user_type.equals("admin")){
            if (user.equals("admin") && pass.equals("admin")){
                logado_admin = true;
                nao_logado = false;
            }
        }
        
        if (user_type.equals("paciente")){                    
            Paciente pac = pacdao.buscarPaciente(Integer.parseInt(user));
            
            if (pac.getSenha().equals(pass)){
                logado_paciente = true;
                nao_logado = false;
            }
        }
        
        if (user_type.equals("medico")){                    
            Medico med = meddao.buscarMedico(Integer.parseInt(user));
            
            if (med.getSenha().equals(pass)){
                logado_medico = true;
                nao_logado = false;
            }
            else{
                mensagem.setMensagem(true, "Usuário ou senha inválidos.", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
            }      
        }
        
        return "index";
        
    }
    
    public void esta_logado_admin(ComponentSystemEvent event) throws IOException {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        System.out.println("passou por aqui");
        if (!logado_admin){
		ConfigurableNavigationHandler nav 
		   = (ConfigurableNavigationHandler) 
			fc.getApplication().getNavigationHandler();
		
		nav.performNavigation("loginForm");
        }
    }

    public void esta_logado_paciente(ComponentSystemEvent event) throws IOException {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        System.out.println("passou por aqui3");
        if (!logado_paciente){
		ConfigurableNavigationHandler nav 
		   = (ConfigurableNavigationHandler) 
			fc.getApplication().getNavigationHandler();
		
		nav.performNavigation("loginForm");
        }
    }
    
    public void esta_logado_consultas(ComponentSystemEvent event) throws IOException {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        System.out.println("passou por aqui2");
        if (!(logado_paciente | logado_medico)){
		ConfigurableNavigationHandler nav 
		   = (ConfigurableNavigationHandler) 
			fc.getApplication().getNavigationHandler();
		
		nav.performNavigation("loginForm");
        }
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isLogado_admin() {
        return logado_admin;
    }

    public void setLogado_admin(boolean logado_admin) {
        this.logado_admin = logado_admin;
    }

    public boolean isLogado_medico() {
        return logado_medico;
    }

    public void setLogado_medico(boolean logado_medico) {
        this.logado_medico = logado_medico;
    }

    public boolean isLogado_paciente() {
        return logado_paciente;
    }

    public void setLogado_paciente(boolean logado_paciente) {
        this.logado_paciente = logado_paciente;
    }

    public boolean isNao_logado() {
        return nao_logado;
    }

    public void setNao_logado(boolean nao_logado) {
        this.nao_logado = nao_logado;
    }
    
    
    



}
