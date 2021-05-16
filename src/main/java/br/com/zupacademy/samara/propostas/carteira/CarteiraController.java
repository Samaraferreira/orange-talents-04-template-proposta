package br.com.zupacademy.samara.propostas.carteira;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import br.com.zupacademy.samara.propostas.cartao.CartaoClient;
import br.com.zupacademy.samara.propostas.cartao.CartaoRepository;
import br.com.zupacademy.samara.propostas.cartao.StatusCartao;
import br.com.zupacademy.samara.propostas.utils.ExecutorTransacao;
import feign.FeignException;
import java.net.URI;
import java.util.Optional;
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
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/carteiras")
public class CarteiraController {

    private final Logger logger = LoggerFactory.getLogger(CarteiraController.class);

    private ExecutorTransacao executorTransacao;
    private CartaoClient cartaoClient;
    private CartaoRepository cartaoRepository;
    private CarteiraRepository carteiraRepository;

    @Autowired
    public CarteiraController(ExecutorTransacao executorTransacao, CartaoClient cartaoClient,
                              CartaoRepository cartaoRepository, CarteiraRepository carteiraRepository) {
        this.executorTransacao = executorTransacao;
        this.cartaoClient = cartaoClient;
        this.cartaoRepository = cartaoRepository;
        this.carteiraRepository = carteiraRepository;
    }

    @PostMapping("/{id}")
    public ResponseEntity adicionarCarteira(@PathVariable("id") Long id, @RequestBody @Valid CarteiraRequest carteiraRequest,
                                            UriComponentsBuilder uriComponentsBuilder) {
        Optional<Cartao> cartaoOpt = cartaoRepository.findById(id);
        Cartao cartao = cartaoOpt.get();

        if (cartaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (cartao.getStatus() == StatusCartao.BLOQUEADO) {
            return ResponseEntity.badRequest().build();
        }

        if (carteiraRepository.existsByCartaoAndTipoCarteira(cartao, carteiraRequest.getTipoCarteira())) {
            logger.error("Cartão já associado a carteira {}", carteiraRequest.getTipoCarteira());
            return ResponseEntity.unprocessableEntity().build();
        }

        try {
            Carteira carteira = carteiraRequest.toModel(cartao);
            var carteiraRequestFeign = new CarteiraRequestFeign(carteira);

            CarteiraResponseFeign responseFeign = cartaoClient.adicionarCarteira(cartao.getNumero(), carteiraRequestFeign);

            carteira.setAssociacaoId(responseFeign.getId());
            executorTransacao.salvaEComita(carteira);

            URI uriRetorno = uriComponentsBuilder.path("api/carteiras/{id}").build(carteira.getId());
            return ResponseEntity.created(uriRetorno).build();
        } catch (FeignException e) {
            logger.error("Erro ao salvar a nova carteira");
            return ResponseEntity.badRequest().build();
        }
    }
}
