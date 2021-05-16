package br.com.zupacademy.samara.propostas.carteira;

import br.com.zupacademy.samara.propostas.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    boolean existsByCartaoAndTipoCarteira(Cartao cartao, TipoCarteira tipoCarteira);
}
