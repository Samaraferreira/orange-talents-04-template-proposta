package br.com.zupacademy.samara.propostas.proposta;

import br.com.zupacademy.samara.propostas.proposta.avaliacao.AvaliacaoFinanceiraClient;
import br.com.zupacademy.samara.propostas.proposta.avaliacao.AvaliacaoFinanceiraRequest;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    @Autowired
    private PropostaRepository repository;
    @Autowired
    private AvaliacaoFinanceiraClient avaliacaoFinanceiraClient;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid PropostaRequest request) {
        Boolean existeDocumento = repository.findByDocumento(request.getDocumento()).isEmpty();

        if (!existeDocumento) {
            logger.warn("Proposta com documento já existente recebida. Documento={}", request.getDocumento());
            return ResponseEntity.unprocessableEntity().build();
        }

        Proposta proposta = request.toModel();

        repository.save(proposta);

        logger.info("Proposta documento={} salário={} criada com sucesso!", proposta.getDocumento(), proposta.getSalario());

        avaliacaoProposta(proposta);

        repository.save(proposta);

        logger.info("Proposta após avaliação: documento={} salário={} status={}", proposta.getDocumento(), proposta.getSalario(), proposta.getStatus());

        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(proposta.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }

    public void avaliacaoProposta(Proposta proposta) {
        try {
            avaliacaoFinanceiraClient.avaliacaoFinanceira(new AvaliacaoFinanceiraRequest(proposta));
            proposta.setStatus(StatusProposta.ELEGIVEL);
        } catch (FeignException.UnprocessableEntity e) {
            logger.warn("Proposta não elegível documento={}",  proposta.getDocumento());
            proposta.setStatus(StatusProposta.NAO_ELEGIVEL);
        }
    }
}
