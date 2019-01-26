package br.ufscar.dc.bolao2.views;

import java.io.Serializable;

public class MensagemBootstrap implements Serializable {
    public enum TipoMensagem {
        TIPO_SUCESSO("alert-success"),
        TIPO_INFO("alert-info"),
        TIPO_AVISO("alert-warning"),
        TIPO_ERRO("alert-danger");
        
        private final String classeBootstrap;

        private TipoMensagem(String classeBootstrap) {
            this.classeBootstrap = classeBootstrap;
        }

        @Override
        public String toString() {
            return classeBootstrap;
        }
    }
    
    private boolean mostrar;
    private String texto;
    private TipoMensagem tipoMensagem;

    public MensagemBootstrap() {
        mostrar = false;
        texto = "";
        tipoMensagem = TipoMensagem.TIPO_SUCESSO;
    }
    
    public void setMensagem(boolean mostrar, String texto, TipoMensagem tipoMensagem) {
        this.mostrar = mostrar;
        this.texto = texto;
        this.tipoMensagem = tipoMensagem;
    }
    
    public boolean isMostrar() {
        return mostrar;
    }

    public String getTexto() {
        return texto;
    }

    public String getTipoMensagem() {
        return tipoMensagem.toString();
    }  
}
