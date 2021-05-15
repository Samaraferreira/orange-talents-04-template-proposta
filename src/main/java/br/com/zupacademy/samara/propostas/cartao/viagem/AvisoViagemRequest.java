package br.com.zupacademy.samara.propostas.cartao.viagem;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import java.time.LocalDate;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate termino;

    public AvisoViagemRequest(String destino, LocalDate termino) {
        this.destino = destino;
        this.termino = termino;
    }

    public AvisoViagem toModel(Cartao cartao, String ip, String agente) {
        return new AvisoViagem(this.destino, this.termino, ip, agente, cartao);
    }
}
