package br.com.banco.cliente.controller;

import br.com.banco.cliente.dto.ClienteRequest;
import br.com.banco.cliente.dto.ClienteResponse;
import br.com.banco.cliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@Tag(
        name = "Clientes",
        description = "Operações para cadastro e gerenciamento de clientes"
)
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo cliente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse cadastrar(
            @RequestBody @Valid ClienteRequest request
    ) {
        return service.cadastrar(request);
    }

    @Operation(summary = "Buscar cliente por ID")
    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Listar clientes ativos")
    @GetMapping
    public List<ClienteResponse> listarAtivos() {
        return service.listarAtivos();
    }

    @Operation(summary = "Atualizar um cliente")
    @PutMapping("/{id}")
    public ClienteResponse atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid ClienteRequest request
    ) {
        return service.atualizar(id, request);
    }

    @Operation(summary = "Inativar um cliente")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirLogicamente(@PathVariable UUID id) {
        service.excluirLogicamente(id);
    }
}