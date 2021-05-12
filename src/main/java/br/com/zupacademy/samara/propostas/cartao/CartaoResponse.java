package br.com.zupacademy.samara.propostas.cartao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;

public class CartaoResponse {

    @NotBlank
    private String id;
    @NotBlank
    private LocalDateTime emitidoEm;

    public CartaoResponse(String id, LocalDateTime emitidoEm) {
        this.id = id;
        this.emitidoEm = emitidoEm;
    }

    public Cartao toModel() {
        return new Cartao(id, emitidoEm);
    }
}
