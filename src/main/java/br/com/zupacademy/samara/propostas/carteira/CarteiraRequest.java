package br.com.zupacademy.samara.propostas.carteira;


import br.com.zupacademy.samara.propostas.cartao.Cartao;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private TipoCarteira tipoCarteira;

    public CarteiraRequest(String email, TipoCarteira tipoCarteira) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public Carteira toModel(Cartao cartao) {
        return new Carteira(this.email, this.tipoCarteira, cartao);
    }
}
