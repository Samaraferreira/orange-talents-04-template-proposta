package br.com.zupacademy.samara.propostas.cartao;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cartao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private LocalDateTime emitidoEm;

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
}
