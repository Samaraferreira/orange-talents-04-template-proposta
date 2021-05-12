package br.com.zupacademy.samara.propostas.cartao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
