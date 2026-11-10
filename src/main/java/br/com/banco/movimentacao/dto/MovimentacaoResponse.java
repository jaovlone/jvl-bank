package br.com.banco.movimentacao.dto;

import br.com.banco.movimentacao.domain.TipoMovimentacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class MovimentacaoResponse {

    private UUID id;
    private TipoMovimentacao tipo;
    private BigDecimal valor;
    private LocalDateTime dataMovimentacao;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }
}
