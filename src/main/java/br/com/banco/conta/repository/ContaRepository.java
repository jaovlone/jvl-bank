package br.com.banco.conta.repository;

import br.com.banco.conta.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {

    boolean existsByNumero(String numero);

    List<Conta> findByAtivaTrue();

    List<Conta> findByClienteId(UUID clienteId);
}