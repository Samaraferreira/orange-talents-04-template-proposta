package br.com.zupacademy.samara.propostas.cartao.viagem;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import br.com.zupacademy.samara.propostas.cartao.CartaoClient;
import br.com.zupacademy.samara.propostas.cartao.CartaoRepository;
import br.com.zupacademy.samara.propostas.cartao.StatusCartao;
import br.com.zupacademy.samara.propostas.proposta.PropostaController;
import br.com.zupacademy.samara.propostas.utils.ExecutorTransacao;
import feign.FeignException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/viagens")
public class AvisoViagemController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private CartaoRepository cartaoRepository;
    private ExecutorTransacao executorTransacao;
    private CartaoClient cartaoClient;

    @Autowired
    public AvisoViagemController(CartaoRepository cartaoRepository, ExecutorTransacao executorTransacao,
                                 CartaoClient cartaoClient) {
        this.cartaoRepository = cartaoRepository;
        this.executorTransacao = executorTransacao;
        this.cartaoClient = cartaoClient;
    }

    @PostMapping("/{id}")
    public ResponseEntity criarAvisoViagem(@RequestBody @Valid AvisoViagemRequest avisoViagemRequest,
                                 @PathVariable("id") Long id, HttpServletRequest httpRequest) {

        Optional<Cartao> cartaoOpt = cartaoRepository.findById(id);

        if (cartaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = cartaoOpt.get();

        if (cartao.getStatus() == StatusCartao.BLOQUEADO) {
            return ResponseEntity.badRequest().build();
        }

        AvisoViagem avisoViagem = avisoViagemRequest.toModel(cartao, httpRequest.getRemoteAddr(), httpRequest.getHeader("User-Agent"));

        try {
            AvisoViagemRequestFeign avisoRequest = new AvisoViagemRequestFeign(avisoViagem.getDestino(), avisoViagem.getDataTermino());
            cartaoClient.avisoViagem(cartao.getNumero(), avisoRequest);
            cartao.setStatus(StatusCartao.VIAJANDO);
            executorTransacao.salvaEComita(avisoViagem);
            executorTransacao.atualizaEComita(cartao);
            logger.info("Aviso de viagem salvo para o cartão {}", cartao.getNumero());
        } catch (FeignException e) {
            logger.error("Não foi possível salvar o aviso");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
