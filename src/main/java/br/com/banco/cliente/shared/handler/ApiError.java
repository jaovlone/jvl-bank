package br.com.banco.cliente.shared.handler;

import java.time.LocalDateTime;

public class ApiError {

    private LocalDateTime dataHora;
    private int status;
    private String mensagem;
    private String caminho;

    public ApiError(
            LocalDateTime dataHora,
            int status,
            String mensagem,
            String caminho
    ) {
        this.dataHora = dataHora;
        this.status = status;
        this.mensagem = mensagem;
        this.caminho = caminho;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public int getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCaminho() {
        return caminho;
    }
}