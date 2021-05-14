package br.com.zupacademy.samara.propostas.cartao.bloqueio;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;

import static javax.persistence.CascadeType.*;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String userAgent;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime instante;

    @OneToOne(cascade = MERGE, fetch = FetchType.EAGER)
    private Cartao cartao;

    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String ip, String userAgent, Cartao cartao) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }
}
