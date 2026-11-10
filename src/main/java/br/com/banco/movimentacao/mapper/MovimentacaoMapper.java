package br.com.banco.movimentacao.mapper;

import br.com.banco.movimentacao.domain.Movimentacao;
import br.com.banco.movimentacao.dto.MovimentacaoResponse;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoMapper {

    public MovimentacaoResponse toResponse(Movimentacao movimentacao) {

        MovimentacaoResponse response = new MovimentacaoResponse();

        response.setId(movimentacao.getId());
        response.setTipo(movimentacao.getTipo());
        response.setValor(movimentacao.getValor());
        response.setDataMovimentacao(movimentacao.getDataMovimentacao());

        return response;
    }
}
