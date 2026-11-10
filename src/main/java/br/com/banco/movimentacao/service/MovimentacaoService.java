package br.com.banco.movimentacao.service;

import br.com.banco.conta.domain.Conta;
import br.com.banco.movimentacao.domain.Movimentacao;
import br.com.banco.movimentacao.domain.TipoMovimentacao;
import br.com.banco.movimentacao.dto.MovimentacaoResponse;
import br.com.banco.movimentacao.mapper.MovimentacaoMapper;
import br.com.banco.movimentacao.repository.MovimentacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository repository;
    private final MovimentacaoMapper mapper;

    public MovimentacaoService(
            MovimentacaoRepository repository,
            MovimentacaoMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public void registrar(
            Conta conta,
            TipoMovimentacao tipo,
            BigDecimal valor
    ) {
        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setConta(conta);
        movimentacao.setTipo(tipo);
        movimentacao.setValor(valor);

        repository.save(movimentacao);
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoResponse> listarPorConta(UUID contaId) {

        return repository
                .findByContaIdOrderByDataMovimentacaoDesc(contaId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
