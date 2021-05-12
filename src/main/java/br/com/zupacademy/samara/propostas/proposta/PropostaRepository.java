package br.com.zupacademy.samara.propostas.proposta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Optional<Proposta> findByDocumento(String documento);
    Set<Proposta> findByStatusAndCartaoIdIsNull(StatusProposta status);
}
