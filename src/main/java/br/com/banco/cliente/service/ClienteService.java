package br.com.banco.cliente.service;

import br.com.banco.cliente.domain.Cliente;
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
    public Cliente cadastrar(ClienteRequest request) {

        if (repository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Cliente cliente = new Cliente();

        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());

        return repository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    @Transactional
    public Cliente atualizar(UUID id, ClienteRequest request) {
        Cliente cliente = buscarPorId(id);

        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());

        return repository.save(cliente);
    }

    @Transactional
    public void excluirLogicamente(UUID id) {
        Cliente cliente = buscarPorId(id);
        cliente.inativar();
        repository.save(cliente);
    }
}