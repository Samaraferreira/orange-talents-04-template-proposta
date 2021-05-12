package br.com.zupacademy.samara.propostas.biometria;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import br.com.zupacademy.samara.propostas.cartao.CartaoRepository;
import br.com.zupacademy.samara.propostas.utils.ExecutorTransacao;
import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/biometria")
public class BiometriaController {

    private BiometriaRepository biometriaRepository;
    private CartaoRepository cartaoRepository;
    private ExecutorTransacao executorTransacao;

    @Autowired
    public BiometriaController(BiometriaRepository biometriaRepository, CartaoRepository cartaoRepository, ExecutorTransacao executorTransacao) {
        this.biometriaRepository = biometriaRepository;
        this.cartaoRepository = cartaoRepository;
        this.executorTransacao = executorTransacao;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> criarBiometria(@RequestBody @Valid BiometriaRequest biometriaRequest,
                                            @PathVariable("id") Long cartaoId, UriComponentsBuilder builder) {
        Optional<Cartao> cartao = executorTransacao.executa(() -> cartaoRepository.findById(cartaoId));

        if (cartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Biometria biometria = biometriaRequest.toModel(cartao.get());

        executorTransacao.salvaEComita(biometria);

        URI uri = builder.path("api/biometria/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
