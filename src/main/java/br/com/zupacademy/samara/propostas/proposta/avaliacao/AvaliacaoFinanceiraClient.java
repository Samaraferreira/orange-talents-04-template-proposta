package br.com.zupacademy.samara.propostas.proposta.avaliacao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:9999/api", name = "analise")
public interface AvaliacaoFinanceiraClient {

    @PostMapping("/solicitacao")
    public AvaliacaoFinanceiraResponse avaliacaoFinanceira(AvaliacaoFinanceiraRequest request);
}
