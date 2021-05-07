package br.com.zupacademy.samara.propostas.proposta.avaliacao;

public class AvaliacaoFinanceiraResponse {

    private String idProposta;
    private String documento;
    private String nome;
    private ResultadoAvaliacao resultadoAvaliacao;

    public AvaliacaoFinanceiraResponse(String idProposta, String documento, String nome, ResultadoAvaliacao resultadoAvaliacao) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;
        this.resultadoAvaliacao = resultadoAvaliacao;
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

    public ResultadoAvaliacao getResultadoSolicitacao() {
        return resultadoAvaliacao;
    }
}
