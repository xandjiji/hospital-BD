package br.ufscar.dc.bolao2.views;

import br.ufscar.dc.bolao2.beans.Medico;
import br.ufscar.dc.bolao2.beans.Paciente;
import br.ufscar.dc.bolao2.dao.MedicoDAO;
import br.ufscar.dc.bolao2.dao.PacienteDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

@Named
@ViewScoped
public class NovoPaciente implements Serializable {

//    @Inject
//    UsuarioDAO usuarioDao;
//    @Inject
//    PalpiteDAO palpiteDao;
    
    @Inject
    PacienteDAO pacienteDao;
    
    Paciente dadosPaciente;

    MensagemBootstrap mensagem;

    public PacienteDAO getPacienteDao() {
        return pacienteDao;
    }

    public void setPacienteDao(PacienteDAO pacienteDao) {
        this.pacienteDao = pacienteDao;
    }

    public Paciente getDadosPaciente() {
        return dadosPaciente;
    }

    public void setDadosPaciente(Paciente dadosPaciente) {
        this.dadosPaciente = dadosPaciente;
    }

    public MensagemBootstrap getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagemBootstrap mensagem) {
        this.mensagem = mensagem;
    }

    
    public NovoPaciente() {
        mensagem = new MensagemBootstrap();
        dadosPaciente = new Paciente();
    }

    public void confirmarPaciente() throws NamingException {
        simularDemora();
        
        try {
            pacienteDao.gravarPaciente(dadosPaciente);           
            mensagem.setMensagem(true, "Paciente cadastrado com sucesso", MensagemBootstrap.TipoMensagem.TIPO_SUCESSO);

        } catch (SQLException ex) {
            Logger.getLogger(NovoPaciente.class.getName()).log(Level.SEVERE, null, ex);
            mensagem.setMensagem(true, "Ocorreu um problema!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
        }
    }

    private void simularDemora() {
//        // Para testar chamadas AJAX
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(NovoPalpite.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
