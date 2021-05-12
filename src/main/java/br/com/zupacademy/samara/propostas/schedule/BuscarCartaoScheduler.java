package br.com.zupacademy.samara.propostas.schedule;

import br.com.zupacademy.samara.propostas.cartao.CartaoClient;
import br.com.zupacademy.samara.propostas.cartao.CartaoResponse;
import br.com.zupacademy.samara.propostas.proposta.Proposta;
import br.com.zupacademy.samara.propostas.proposta.PropostaRepository;
import br.com.zupacademy.samara.propostas.proposta.StatusProposta;
import br.com.zupacademy.samara.propostas.utils.ExecutorTransacao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

@Component
public class BuscarCartaoScheduler {

    private final Logger logger = LoggerFactory.getLogger(BuscarCartaoScheduler.class);

    private CartaoClient cartaoClient;
    private PropostaRepository propostaRepository;
    private ExecutorTransacao executorTransacao;

    @Autowired
    public BuscarCartaoScheduler(CartaoClient cartaoClient, PropostaRepository propostaRepository, ExecutorTransacao executorTransacao) {
        this.cartaoClient = cartaoClient;
        this.propostaRepository = propostaRepository;
        this.executorTransacao = executorTransacao;
    }

    @Scheduled(fixedDelayString = "${periodicidade.executa-operacao}")
    @Transactional
    private void buscarCartoes() {
        logger.info("Verificando cartões para propostas");

        Set<Proposta> propostasElegiveis = propostaRepository.findByStatusAndCartaoIdIsNull(StatusProposta.ELEGIVEL);

        propostasElegiveis.forEach(proposta -> {
            try {
                CartaoResponse cartaoResponse = cartaoClient.getCartao(proposta.getId().toString());
                proposta.setCartao(cartaoResponse.toModel());
                executorTransacao.atualizaEComita(proposta);
                logger.info("Cartão da proposta {} foi criado", proposta.getId());
            } catch (FeignException e) {
                logger.warn("Cartão da proposta {} não foi criado", proposta.getId());
            } catch (Exception e) {
                logger.error("Um erro inexperado aconteceu, causa: {} e mensagem: {}", e.getCause(), e.getMessage());
            }
        });
    }
}

