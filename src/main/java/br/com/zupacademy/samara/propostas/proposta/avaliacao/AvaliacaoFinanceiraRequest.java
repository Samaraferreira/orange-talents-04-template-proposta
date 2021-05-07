package br.com.zupacademy.samara.propostas.proposta.avaliacao;

import br.com.zupacademy.samara.propostas.proposta.Proposta;

public class AvaliacaoFinanceiraRequest {

    private String idProposta;
    private String documento;
    private String nome;

    public AvaliacaoFinanceiraRequest(Proposta proposta) {
        this.idProposta = proposta.getId().toString();
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
    }

    public String getIdProposta() {
        return idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }
}
