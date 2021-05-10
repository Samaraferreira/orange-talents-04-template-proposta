package br.com.zupacademy.samara.propostas.proposta.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${cartao.url}", name = "cartao")
public interface CartaoClient {

    @GetMapping
    public CartaoResponse getCartao(@RequestParam(name = "idProposta") String idProposta);

}

