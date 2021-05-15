package br.com.zupacademy.samara.propostas.cartao;

import br.com.zupacademy.samara.propostas.proposta.Proposta;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Cartao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private LocalDateTime emitidoEm;

    @Enumerated(EnumType.STRING)
    private StatusCartao status = StatusCartao.OK;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String numero, LocalDateTime emitidoEm) {
        this.numero = numero;
        this.emitidoEm = emitidoEm;
    }

    public Long getId () {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public StatusCartao getStatus() {
        return status;
    }

    public void setStatus(StatusCartao status) {
        this.status = status;
    }

}
