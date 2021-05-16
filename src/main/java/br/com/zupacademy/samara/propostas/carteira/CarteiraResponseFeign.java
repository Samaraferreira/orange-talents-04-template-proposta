package br.com.zupacademy.samara.propostas.carteira;

public class CarteiraResponseFeign {

    private String resultado;
    private String id;

    @Deprecated
    public CarteiraResponseFeign() {
    }

    public CarteiraResponseFeign(String resultado, String id) {
        this.id = id;
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public String getResultado() {
        return resultado;
    }
}
