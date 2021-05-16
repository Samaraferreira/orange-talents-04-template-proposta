package br.com.zupacademy.samara.propostas.cartao.viagem;

public class AvisoViagemResponseFeign {

    private String resultado;

    @Deprecated
    public AvisoViagemResponseFeign() {
    }

    public AvisoViagemResponseFeign(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
