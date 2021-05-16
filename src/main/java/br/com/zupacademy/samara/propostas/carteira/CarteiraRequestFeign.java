package br.com.zupacademy.samara.propostas.carteira;

public class CarteiraRequestFeign {

    private String email;
    private String carteira;

    @Deprecated
    public CarteiraRequestFeign() {
    }

    public CarteiraRequestFeign(Carteira carteira) {
        this.email = carteira.getEmail();
        this.carteira = carteira.getTipoCarteira().toString();
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
