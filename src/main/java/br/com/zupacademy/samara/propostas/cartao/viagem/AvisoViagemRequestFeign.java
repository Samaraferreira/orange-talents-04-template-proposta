package br.com.zupacademy.samara.propostas.cartao.viagem;

import java.time.LocalDate;

public class AvisoViagemRequestFeign {

    private String destino;
    private LocalDate validoAte;

    public AvisoViagemRequestFeign(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
