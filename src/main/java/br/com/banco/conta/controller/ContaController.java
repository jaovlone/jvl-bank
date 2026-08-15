package br.com.banco.conta.controller;

import br.com.banco.conta.dto.TransferenciaRequest;
import br.com.banco.conta.dto.SaqueRequest;
import br.com.banco.conta.dto.ContaRequest;
import br.com.banco.conta.dto.ContaResponse;
import br.com.banco.conta.dto.DepositoRequest;
import br.com.banco.conta.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contas")
@Tag(
        name = "Contas",
        description = "Operações para gerenciamento de contas bancárias"
)
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    @Operation(summary = "Abrir uma nova conta")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaResponse abrirConta(
            @RequestBody @Valid ContaRequest request
    ) {
        return service.abrirConta(request);
    }

    @Operation(summary = "Buscar conta por ID")
    @GetMapping("/{id}")
    public ContaResponse buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Listar contas ativas")
    @GetMapping
    public List<ContaResponse> listarAtivas() {
        return service.listarAtivas();
    }

    @Operation(summary = "Listar contas de um cliente")
    @GetMapping("/cliente/{clienteId}")
    public List<ContaResponse> listarPorCliente(
            @PathVariable UUID clienteId
    ) {
        return service.listarPorCliente(clienteId);
    }

    @Operation(summary = "Realizar depósito em uma conta")
    @PostMapping("/{id}/depositos")
    public ContaResponse depositar(
            @PathVariable UUID id,
            @RequestBody @Valid DepositoRequest request
    ) {
        return service.depositar(id, request);
    }

    @Operation(summary = "Inativar uma conta")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable UUID id) {
        service.inativar(id);
    }

    @Operation(summary = "Realizar transferência entre contas")
    @PostMapping("/{id}/transferencias")
    public ContaResponse transferir(
            @PathVariable UUID id,
            @RequestBody @Valid TransferenciaRequest request
    ) {
        return service.transferir(id, request);
    }

    @Operation(summary = "Realizar saque em uma conta")
    @PostMapping("/{id}/saques")
    public ContaResponse sacar(
            @PathVariable UUID id,
            @RequestBody @Valid SaqueRequest request
    ) {
        return service.sacar(id, request);
    }




}