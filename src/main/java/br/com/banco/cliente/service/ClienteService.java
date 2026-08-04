package br.com.banco.cliente.service;

import br.com.banco.cliente.shared.exception.RegraNegocioException;
import br.com.banco.cliente.domain.Cliente;
import br.com.banco.cliente.dto.ClienteResponse;
import br.com.banco.cliente.dto.ClienteRequest;
import br.com.banco.cliente.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ClienteResponse cadastrar(ClienteRequest request) {

        if (repository.existsByCpf(request.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado");        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new RegraNegocioException("E-mail já cadastrado");        }

        Cliente cliente = new Cliente();

        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());

        Cliente clienteSalvo = repository.save(cliente);
        return toResponse(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(UUID id) {
        Cliente cliente = buscarEntidadePorId(id);
        return toResponse(cliente);
    }


    @Transactional(readOnly = true)
    public List<ClienteResponse> listarAtivos() {
        return repository.findByAtivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    @Transactional
    public ClienteResponse atualizar(UUID id, ClienteRequest request) {
        Cliente cliente = buscarEntidadePorId(id);

        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());

        Cliente clienteAtualizado = repository.save(cliente);

        return toResponse(clienteAtualizado);
    }

    @Transactional
    public void excluirLogicamente(UUID id) {
        Cliente cliente = buscarEntidadePorId(id);
        cliente.inativar();
        repository.save(cliente);
    }
    private Cliente buscarEntidadePorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não encontrado"));
    }
    private ClienteResponse toResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();

        response.setId(cliente.getId());
        response.setNome(cliente.getNome());
        response.setCpf(cliente.getCpf());
        response.setEmail(cliente.getEmail());
        response.setTelefone(cliente.getTelefone());
        response.setDataCadastro(cliente.getDataCadastro());
        response.setAtivo(cliente.isAtivo());

        return response;
    }

}
