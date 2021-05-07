package br.com.zupacademy.samara.propostas.utils.error;

import java.util.Collection;

public class ApiErrorDto {

    private Collection<String> mensagens;

    public ApiErrorDto(Collection<String> mensagens) {
        this.mensagens = mensagens;
    }

    public Collection<String> getMensagens() {
        return mensagens;
    }

    public void setMensagens(Collection<String> mensagens) {
        this.mensagens = mensagens;
    }
}
