package br.com.zupacademy.samara.propostas.cartao.viagem;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDate dataTermino;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String userAgent;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime instanteCriacao;

    @ManyToOne
    private Cartao cartao;

    /**
     * @deprecated apenas para uso do hibernate
     */
    public AvisoViagem() {
    }

    public AvisoViagem(String destino, LocalDate dataTermino, String ip, String userAgent, Cartao cartao) {
        this.destino = destino;
        this.dataTermino = dataTermino;
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }
}
