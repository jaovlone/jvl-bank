package br.com.banco.movimentacao.controller;

import br.com.banco.movimentacao.dto.MovimentacaoResponse;
import br.com.banco.movimentacao.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movimentacoes")
@Tag(
        name = "Movimentações",
        description = "Operações para consulta de movimentações bancárias"
)
public class MovimentacaoController {

    private final MovimentacaoService service;

    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar movimentações de uma conta")
    @GetMapping("/conta/{contaId}")
    public List<MovimentacaoResponse> listarPorConta(
            @PathVariable UUID contaId
    ) {
        return service.listarPorConta(contaId);
    }
}
