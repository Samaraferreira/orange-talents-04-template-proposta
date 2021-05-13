package br.com.zupacademy.samara.propostas.biometria;

import br.com.zupacademy.samara.propostas.cartao.Cartao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String impressaoDigital;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String impressaoDigital, Cartao cartao) {
        this.impressaoDigital = impressaoDigital;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
