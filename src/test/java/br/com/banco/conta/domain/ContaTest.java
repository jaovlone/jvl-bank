package br.com.banco.conta.domain;

import br.com.banco.cliente.shared.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaTest {

    private Conta conta;

    @BeforeEach
    void prepararConta() {
        conta = new Conta();
        conta.prePersist();
    }

    @Test
    void deveDepositarComSucesso() {

        // Act
        conta.depositar(new BigDecimal("250.00"));

        // Assert
        assertEquals(
                new BigDecimal("250.00"),
                conta.getSaldo()
        );
    }

    @Test
    void deveSomarDepositosAoSaldoExistente() {

        conta.depositar(new BigDecimal("100.00"));
        conta.depositar(new BigDecimal("50.00"));

        assertEquals(
                new BigDecimal("150.00"),
                conta.getSaldo()
        );
    }

    @Test
    void deveSacarComSucesso() {

        conta.depositar(new BigDecimal("250.00"));

        conta.sacar(new BigDecimal("100.00"));

        assertEquals(
                new BigDecimal("150.00"),
                conta.getSaldo()
        );
    }

    @Test
    void deveLancarExcecaoQuandoSaldoForInsuficiente() {

        conta.depositar(new BigDecimal("100.00"));

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> conta.sacar(new BigDecimal("150.00"))
        );

        assertEquals(
                "Saldo insuficiente",
                exception.getMessage()
        );

        assertEquals(
                new BigDecimal("100.00"),
                conta.getSaldo()
        );
    }

    @Test
    void deveLancarExcecaoParaDepositoNegativo() {

        assertThrows(
                RegraNegocioException.class,
                () -> conta.depositar(new BigDecimal("-50.00"))
        );
    }

    @Test
    void deveLancarExcecaoParaSaqueZero() {

        assertThrows(
                RegraNegocioException.class,
                () -> conta.sacar(BigDecimal.ZERO)
        );
    }

    @Test
    void naoDevePermitirDepositoEmContaInativa() {

        conta.inativar();

        assertThrows(
                RegraNegocioException.class,
                () -> conta.depositar(new BigDecimal("100.00"))
        );
    }
}