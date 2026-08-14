package br.com.banco.conta.dto;

import br.com.banco.conta.domain.TipoConta;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ContaRequest {

    @NotNull
    private UUID clienteId;

    @NotNull
    private TipoConta tipo;

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }
}