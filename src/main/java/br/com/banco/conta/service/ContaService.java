package br.com.banco.conta.service;

import br.com.banco.cliente.domain.Cliente;
import br.com.banco.cliente.repository.ClienteRepository;
import br.com.banco.cliente.shared.exception.RegraNegocioException;
import br.com.banco.conta.domain.Conta;
import br.com.banco.conta.dto.ContaRequest;
import br.com.banco.conta.dto.ContaResponse;
import br.com.banco.conta.dto.DepositoRequest;
import br.com.banco.conta.dto.SaqueRequest;
import br.com.banco.conta.dto.TransferenciaRequest;
import br.com.banco.conta.mapper.ContaMapper;
import br.com.banco.conta.repository.ContaRepository;
import br.com.banco.movimentacao.domain.TipoMovimentacao;
import br.com.banco.movimentacao.service.MovimentacaoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final ContaMapper mapper;
    private final MovimentacaoService movimentacaoService;

    public ContaService(
            ContaRepository contaRepository,
            ClienteRepository clienteRepository,
            ContaMapper mapper,
            MovimentacaoService movimentacaoService
    ) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
        this.movimentacaoService = movimentacaoService;
    }

    @Transactional
    public ContaResponse abrirConta(ContaRequest request) {

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não encontrado"));

        Conta conta = new Conta();

        conta.setAgencia("0001");
        conta.setNumero(gerarNumeroConta());
        conta.setTipo(request.getTipo());
        conta.setCliente(cliente);

        Conta contaSalva = contaRepository.save(conta);

        return mapper.toResponse(contaSalva);
    }

    @Transactional(readOnly = true)
    public ContaResponse buscarPorId(UUID id) {

        Conta conta = buscarEntidadePorId(id);

        return mapper.toResponse(conta);
    }

    @Transactional(readOnly = true)
    public List<ContaResponse> listarAtivas() {

        return contaRepository.findByAtivaTrue()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ContaResponse> listarPorCliente(UUID clienteId) {

        return contaRepository.findByClienteId(clienteId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public ContaResponse depositar(
            UUID contaId,
            DepositoRequest request
    ) {

        Conta conta = buscarEntidadePorId(contaId);

        conta.depositar(request.getValor());

        Conta contaAtualizada = contaRepository.save(conta);

        movimentacaoService.registrar(
                contaAtualizada,
                TipoMovimentacao.DEPOSITO,
                request.getValor()
        );

        return mapper.toResponse(contaAtualizada);
    }

    @Transactional
    public ContaResponse sacar(
            UUID contaId,
            SaqueRequest request
    ) {

        Conta conta = buscarEntidadePorId(contaId);

        conta.sacar(request.getValor());

        Conta contaAtualizada = contaRepository.save(conta);

        movimentacaoService.registrar(
                contaAtualizada,
                TipoMovimentacao.SAQUE,
                request.getValor()
        );

        return mapper.toResponse(contaAtualizada);
    }

    @Transactional
    public ContaResponse transferir(
            UUID contaOrigemId,
            TransferenciaRequest request
    ) {

        if (contaOrigemId.equals(request.getContaDestinoId())) {
            throw new RegraNegocioException(
                    "A conta de origem e destino não podem ser iguais"
            );
        }

        Conta contaOrigem = buscarEntidadePorId(contaOrigemId);
        Conta contaDestino = buscarEntidadePorId(
                request.getContaDestinoId()
        );

        contaOrigem.sacar(request.getValor());
        contaDestino.depositar(request.getValor());

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        movimentacaoService.registrar(
                contaOrigem,
                TipoMovimentacao.TRANSFERENCIA_ENVIADA,
                request.getValor()
        );

        movimentacaoService.registrar(
                contaDestino,
                TipoMovimentacao.TRANSFERENCIA_RECEBIDA,
                request.getValor()
        );

        return mapper.toResponse(contaOrigem);
    }

    @Transactional
    public void inativar(UUID id) {

        Conta conta = buscarEntidadePorId(id);

        conta.inativar();

        contaRepository.save(conta);
    }

    private Conta buscarEntidadePorId(UUID id) {

        return contaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Conta não encontrada"));
    }

    private String gerarNumeroConta() {

        String numero;

        do {
            numero = String.valueOf(
                    ThreadLocalRandom.current()
                            .nextInt(100000, 1000000)
            );
        } while (contaRepository.existsByNumero(numero));

        return numero;
    }
}