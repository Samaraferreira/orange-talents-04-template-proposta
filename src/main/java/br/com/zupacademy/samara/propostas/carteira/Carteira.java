package br.com.zupacademy.samara.propostas.carteira;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String associacaoId;

    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Carteira() {
    }

    public Carteira(String email, TipoCarteira tipoCarteira, Cartao cartao) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public void setAssociacaoId(String associacaoId) {
        this.associacaoId = associacaoId;
    }
}
