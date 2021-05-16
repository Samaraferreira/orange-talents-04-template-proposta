package br.com.zupacademy.samara.propostas.cartao.bloqueio;

public class BloqueioResponseFeign {

    private String status;

    @Deprecated
    public BloqueioResponseFeign() {
    }

    public BloqueioResponseFeign(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
