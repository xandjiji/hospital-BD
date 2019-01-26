package br.ufscar.dc.bolao2.views;

import br.ufscar.dc.bolao2.beans.Medico;
import br.ufscar.dc.bolao2.dao.MedicoDAO;
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
public class NovoMedico implements Serializable {

//    @Inject
//    UsuarioDAO usuarioDao;
//    @Inject
//    PalpiteDAO palpiteDao;
    
    @Inject
    MedicoDAO medicoDao;
    
    Medico dadosMedico;

    MensagemBootstrap mensagem;

    public MedicoDAO getMedicoDao() {
        return medicoDao;
    }

    public void setMedicoDao(MedicoDAO medicoDao) {
        this.medicoDao = medicoDao;
    }

    public Medico getDadosMedico() {
        return dadosMedico;
    }

    public void setDadosMedico(Medico dadosMedico) {
        this.dadosMedico = dadosMedico;
    }

    public MensagemBootstrap getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagemBootstrap mensagem) {
        this.mensagem = mensagem;
    }
    
    public NovoMedico() {
        mensagem = new MensagemBootstrap();
        dadosMedico = new Medico();
    }

    public void confirmarMedico() throws NamingException {
        simularDemora();
        
        try {
            medicoDao.gravarMedico(dadosMedico);           
            mensagem.setMensagem(true, "Medico cadastrado com sucesso", MensagemBootstrap.TipoMensagem.TIPO_SUCESSO);

        } catch (SQLException ex) {
            Logger.getLogger(NovoMedico.class.getName()).log(Level.SEVERE, null, ex);
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
