package br.com.zupacademy.samara.propostas.proposta.cartao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CartaoResponse {

    private @NotBlank String id;

    @JsonCreator
    public CartaoResponse(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId () {
        return id;
    }
}
