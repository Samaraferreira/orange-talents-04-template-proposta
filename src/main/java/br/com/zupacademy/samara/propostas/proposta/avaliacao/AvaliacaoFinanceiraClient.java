package br.com.zupacademy.samara.propostas.proposta.avaliacao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${analiseFinanceira.url}", name = "analise")
public interface AvaliacaoFinanceiraClient {

    @PostMapping
    public AvaliacaoFinanceiraResponse avaliacaoFinanceira(AvaliacaoFinanceiraRequest request);
}
