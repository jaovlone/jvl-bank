package br.com.banco.cliente.controller;

import br.com.banco.cliente.domain.Cliente;
import br.com.banco.cliente.dto.ClienteRequest;
import br.com.banco.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente cadastrar(@RequestBody @Valid ClienteRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<Cliente> listarAtivos() {
        return service.listarAtivos();
    }

    @PutMapping("/{id}")
    public Cliente atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid ClienteRequest request
    ) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirLogicamente(@PathVariable UUID id) {
        service.excluirLogicamente(id);
    }
}