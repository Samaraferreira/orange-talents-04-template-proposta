package br.com.zupacademy.samara.propostas.proposta;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import java.math.BigDecimal;

public class PropostaResponse {

    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
    private StatusProposta statusProposta;
    private Cartao cartao;

    public PropostaResponse(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.salario = proposta.getSalario();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.statusProposta = proposta.getStatus();
        this.cartao = proposta.getCartao();
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
