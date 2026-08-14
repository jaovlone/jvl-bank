package br.com.banco.conta.service;

import br.com.banco.cliente.domain.Cliente;
import br.com.banco.cliente.repository.ClienteRepository;
import br.com.banco.conta.domain.Conta;
import br.com.banco.conta.dto.ContaRequest;
import br.com.banco.conta.dto.ContaResponse;
import br.com.banco.conta.dto.DepositoRequest;
import br.com.banco.conta.mapper.ContaMapper;
import br.com.banco.conta.repository.ContaRepository;
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

    public ContaService(
            ContaRepository contaRepository,
            ClienteRepository clienteRepository,
            ContaMapper mapper
    ) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
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
    public ContaResponse depositar(UUID contaId, DepositoRequest request) {

        Conta conta = buscarEntidadePorId(contaId);

        conta.depositar(request.getValor());

        Conta contaAtualizada = contaRepository.save(conta);

        return mapper.toResponse(contaAtualizada);
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