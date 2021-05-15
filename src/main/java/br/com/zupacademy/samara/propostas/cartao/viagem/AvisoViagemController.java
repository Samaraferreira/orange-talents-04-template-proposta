package br.com.zupacademy.samara.propostas.cartao.viagem;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import br.com.zupacademy.samara.propostas.cartao.CartaoRepository;
import br.com.zupacademy.samara.propostas.cartao.StatusCartao;
import br.com.zupacademy.samara.propostas.utils.ExecutorTransacao;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    private CartaoRepository cartaoRepository;
    private ExecutorTransacao executorTransacao;

    @Autowired
    public AvisoViagemController(CartaoRepository cartaoRepository,
                                 ExecutorTransacao executorTransacao) {
        this.cartaoRepository = cartaoRepository;
        this.executorTransacao = executorTransacao;
    }

    @PostMapping("/{id}")
    public ResponseEntity criarAvisoViagem(@RequestBody @Valid AvisoViagemRequest avisoViagemRequest,
                                 @PathVariable("id") Long id, HttpServletRequest httpRequest) {

        Optional<Cartao> cartaoOpt = cartaoRepository.findById(id);
        Cartao cartao = cartaoOpt.get();

        if (cartaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (cartao.getStatus() == StatusCartao.BLOQUEADO) {
            return ResponseEntity.badRequest().build();
        }

        AvisoViagem avisoViagem = avisoViagemRequest.toModel(cartao, httpRequest.getRemoteAddr(), httpRequest.getHeader("User-Agent"));
        executorTransacao.salvaEComita(avisoViagem);

        return ResponseEntity.ok().build();
    }
}
