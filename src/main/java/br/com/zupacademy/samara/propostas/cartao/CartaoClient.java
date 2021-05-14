package br.com.zupacademy.samara.propostas.cartao;

import br.com.zupacademy.samara.propostas.cartao.bloqueio.BloqueioRequest;
import br.com.zupacademy.samara.propostas.schedule.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${cartao.url}", name = "cartao")
public interface CartaoClient {

    @GetMapping
    public CartaoResponse getCartao(@RequestParam(name = "idProposta") String idProposta);

    @PostMapping("/{id}/bloqueios")
    public CartaoResponse bloquearCartao(@PathVariable(name = "id") String id, @RequestBody BloqueioRequest request);
}

