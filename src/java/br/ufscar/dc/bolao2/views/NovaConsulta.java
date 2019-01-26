package br.ufscar.dc.bolao2.views;

import br.ufscar.dc.bolao2.beans.Consulta;
import br.ufscar.dc.bolao2.beans.Medico;
import br.ufscar.dc.bolao2.beans.Paciente;
import br.ufscar.dc.bolao2.dao.ConsultaDAO;
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
public class NovaConsulta implements Serializable {

//    @Inject
//    UsuarioDAO usuarioDao;
//    @Inject
//    PalpiteDAO palpiteDao;
    
    @Inject
    ConsultaDAO consultaDao;
    
    @Inject
    Login loginobj;
    
    Consulta dadosConsulta;

    MensagemBootstrap mensagem;

    public ConsultaDAO getConsultaDao() {
        return consultaDao;
    }

    public void setConsultaDao(ConsultaDAO consultaDao) {
        this.consultaDao = consultaDao;
    }

    public Consulta getDadosConsulta() {
        return dadosConsulta;
    }

    public void setDadosConsulta(Consulta dadosConsulta) {
        this.dadosConsulta = dadosConsulta;
    }

    public MensagemBootstrap getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagemBootstrap mensagem) {
        this.mensagem = mensagem;
    }


    
    public NovaConsulta() {
        mensagem = new MensagemBootstrap();
        dadosConsulta = new Consulta();
    }

    public void confirmarConsulta() throws NamingException {
        simularDemora();
        
        try {
            int pac_cpf = Integer.parseInt(loginobj.getUser());
            dadosConsulta.setCpf(pac_cpf);
            consultaDao.gravarConsulta(dadosConsulta);           
            mensagem.setMensagem(true, "Consulta agendada com sucesso", MensagemBootstrap.TipoMensagem.TIPO_SUCESSO);

        } catch (SQLException ex) {
            Logger.getLogger(NovaConsulta.class.getName()).log(Level.SEVERE, null, ex);
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
