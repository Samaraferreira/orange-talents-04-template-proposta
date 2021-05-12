package br.com.zupacademy.samara.propostas.biometria;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BiometriaRequest {

    @NotBlank
    private String impressaoDigital;

    @JsonCreator
    public BiometriaRequest(@JsonProperty(value = "impressaoDigital") @NotBlank String impressaoDigital) {
        this.impressaoDigital = impressaoDigital;
    }

    public Biometria toModel(@NotNull Cartao cartao) {
        return new Biometria(impressaoDigital, cartao);
    }
}
