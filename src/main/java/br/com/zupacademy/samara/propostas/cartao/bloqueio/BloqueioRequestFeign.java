package br.com.zupacademy.samara.propostas.cartao.bloqueio;

import javax.validation.constraints.NotBlank;

public class BloqueioRequestFeign {

    @NotBlank
    private String sistemaResponsavel = "propostas";

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
