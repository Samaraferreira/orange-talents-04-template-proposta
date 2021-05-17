package br.com.zupacademy.samara.propostas.proposta;

import br.com.zupacademy.samara.propostas.proposta.avaliacao.AvaliacaoFinanceiraClient;
import br.com.zupacademy.samara.propostas.proposta.avaliacao.AvaliacaoFinanceiraRequest;
import br.com.zupacademy.samara.propostas.utils.ExecutorTransacao;
import feign.FeignException;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/propostas")
public class PropostaController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private PropostaRepository repository;
    private AvaliacaoFinanceiraClient avaliacaoFinanceiraClient;
    private ExecutorTransacao executorTransacao;
    private MeterRegistry meterRegistry;
    private Tracer tracer;

    @Autowired
    public PropostaController(PropostaRepository repository, AvaliacaoFinanceiraClient avaliacaoFinanceiraClient,
                              ExecutorTransacao executorTransacao, MeterRegistry meterRegistry, Tracer tracer) {
        this.repository = repository;
        this.avaliacaoFinanceiraClient = avaliacaoFinanceiraClient;
        this.executorTransacao = executorTransacao;
        this.meterRegistry = meterRegistry;
        this.tracer = tracer;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> consultarProposta(@PathVariable("id") Long id) {
        return meterRegistry.timer("consultar_proposta").record(() -> {
            Optional<Proposta> proposta = executorTransacao.executa(() -> repository.findById(id));

            if (proposta.isEmpty()) {
                logger.warn("Solicitação de consulta a proposta de id inexistente realizada!");
                return ResponseEntity.notFound().build();
            }

            logger.info("Consulta a proposta de documento {} realizada!", proposta.get().getId(), proposta.get().getDocumento());

            return ResponseEntity.ok().body(new PropostaResponse(proposta.get()));
        });
    }

    @PostMapping
    public ResponseEntity<?> criarProposta(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder builder) {
        Boolean existeDocumento = executorTransacao.executa(() -> repository.findByDocumento(request.getDocumento()).isEmpty());

        if (!existeDocumento) {
            logger.warn("Proposta com documento já existente recebida. Documento={}", request.getDocumento());
            return ResponseEntity.unprocessableEntity().build();
        }

        Proposta proposta = request.toModel();
        executorTransacao.salvaEComita(proposta);
        logger.info("Proposta documento={} salário={} criada com sucesso!", proposta.getDocumento(), proposta.getSalario());

        avaliacaoProposta(proposta);
        executorTransacao.atualizaEComita(proposta);
        meterRegistry.counter("proposta_criada").increment();
        logger.info("Proposta após avaliação: documento={} salário={} status={}", proposta.getDocumento(), proposta.getSalario(), proposta.getStatus());

        URI uri = builder.path("api/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    public void avaliacaoProposta(Proposta proposta) {
        try {
            Span activeSpan = tracer.activeSpan();
            activeSpan.setBaggageItem("user.email", proposta.getEmail());
            avaliacaoFinanceiraClient.avaliacaoFinanceira(new AvaliacaoFinanceiraRequest(proposta));
            proposta.setStatus(StatusProposta.ELEGIVEL);
        } catch (FeignException.UnprocessableEntity e) {
            proposta.setStatus(StatusProposta.NAO_ELEGIVEL);
        } catch (FeignException.FeignServerException e) {

        }
    }
}
