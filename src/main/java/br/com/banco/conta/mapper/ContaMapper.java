package br.com.banco.conta.mapper;

import br.com.banco.conta.domain.Conta;
import br.com.banco.conta.dto.ContaResponse;
import org.springframework.stereotype.Component;

@Component
public class ContaMapper {

    public ContaResponse toResponse(Conta conta) {

        ContaResponse response = new ContaResponse();

        response.setId(conta.getId());
        response.setAgencia(conta.getAgencia());
        response.setNumero(conta.getNumero());
        response.setSaldo(conta.getSaldo());
        response.setTipo(conta.getTipo());
        response.setAtiva(conta.isAtiva());
        response.setDataAbertura(conta.getDataAbertura());

        response.setClienteId(conta.getCliente().getId());
        response.setClienteNome(conta.getCliente().getNome());

        return response;
    }
}