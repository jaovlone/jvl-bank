package br.com.banco.movimentacao.repository;

import br.com.banco.movimentacao.domain.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, UUID> {

    List<Movimentacao> findByContaIdOrderByDataMovimentacaoDesc(UUID contaId);
}
