package br.com.zupacademy.samara.propostas.proposta;

import java.math.BigDecimal;

public class PropostaResponse {

    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
    private StatusProposta statusProposta;
    private String numeroCartao;

    public PropostaResponse(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.salario = proposta.getSalario();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.statusProposta = proposta.getStatus();
        this.numeroCartao = proposta.getNumeroCartao();
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

    public String getNumeroCartao() {
        return numeroCartao;
    }
}
