package br.com.banco.conta.domain;

import br.com.banco.cliente.domain.Cliente;
import br.com.banco.cliente.shared.exception.RegraNegocioException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 4)
    private String agencia;

    @Column(nullable = false, unique = true, length = 10)
    private String numero;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoConta tipo;

    @Column(nullable = false)
    private boolean ativa;

    @Column(name = "data_abertura", nullable = false, updatable = false)
    private LocalDateTime dataAbertura;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @PrePersist
    public void prePersist() {
        this.saldo = BigDecimal.ZERO;
        this.ativa = true;
        this.dataAbertura = LocalDateTime.now();
    }

    public void sacar(BigDecimal valor) {

        if (!ativa) {
            throw new RegraNegocioException("Conta inativa");
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException(
                    "O valor do saque deve ser maior que zero"
            );
        }

        if (saldo.compareTo(valor) < 0) {
            throw new RegraNegocioException("Saldo insuficiente");
        }

        this.saldo = this.saldo.subtract(valor);
    }


    public void depositar(BigDecimal valor) {

        if (!ativa) {
            throw new RegraNegocioException("Conta inativa");
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException(
                    "O valor do depósito deve ser maior que zero"
            );
        }

        this.saldo = this.saldo.add(valor);
    }

    public void inativar() {
        this.ativa = false;
    }

    public void ativar() {
        this.ativa = true;
    }

    public UUID getId() {
        return id;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}