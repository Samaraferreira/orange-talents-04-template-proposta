package br.com.zupacademy.samara.propostas.cartao.bloqueio;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import br.com.zupacademy.samara.propostas.cartao.CartaoClient;
import br.com.zupacademy.samara.propostas.cartao.CartaoRepository;
import br.com.zupacademy.samara.propostas.cartao.StatusCartao;
import br.com.zupacademy.samara.propostas.proposta.PropostaController;
import br.com.zupacademy.samara.propostas.proposta.PropostaRepository;
import br.com.zupacademy.samara.propostas.utils.ExecutorTransacao;
import feign.FeignException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bloqueios")
public class BloqueioController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private BloqueioRepository bloqueioRepository;
    private CartaoRepository cartaoRepository;
    private CartaoClient cartaoClient;
    private ExecutorTransacao executorTransacao;

    @Autowired
    public BloqueioController(BloqueioRepository bloqueioRepository, CartaoRepository cartaoRepository,
                              CartaoClient cartaoClient, ExecutorTransacao executorTransacao) {
        this.bloqueioRepository = bloqueioRepository;
        this.cartaoRepository = cartaoRepository;
        this.cartaoClient = cartaoClient;
        this.executorTransacao = executorTransacao;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> bloquearCartao(@AuthenticationPrincipal Jwt usuario,
                                            HttpServletRequest httpRequest,
                                            @PathVariable("id") Long id) {

        Optional<Cartao> cartaoOpt = cartaoRepository.findById(id);
        Cartao cartao = cartaoOpt.get();
        var email = usuario.getClaims().get("email");

        if (cartaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (cartao.getStatus() == StatusCartao.BLOQUEADO) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (!email.equals(cartao.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Bloqueio bloqueio = new Bloqueio(httpRequest.getRemoteAddr(), httpRequest.getHeader("User-Agent"), cartao);
            BloqueioRequest bloqueioRequest = new BloqueioRequest();
            cartaoClient.bloquearCartao(cartao.getNumero(), bloqueioRequest);
            cartao.setStatus(StatusCartao.BLOQUEADO);
            executorTransacao.executa(()-> bloqueioRepository.save(bloqueio));
            executorTransacao.executa(()-> cartaoRepository.save(cartao));
            logger.info("Cartão {} foi bloqueado", cartao.getNumero());
        } catch (FeignException e) {
            logger.error("Falha ao bloquear o cartão {}", cartao.getNumero());
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok().build();
    }

}
