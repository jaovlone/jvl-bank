package br.com.banco.conta.service;

import br.com.banco.cliente.domain.Cliente;
import br.com.banco.cliente.repository.ClienteRepository;
import br.com.banco.cliente.shared.exception.RegraNegocioException;
import br.com.banco.conta.domain.Conta;
import br.com.banco.conta.domain.TipoConta;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ContaMapper mapper;

    @Mock
    private MovimentacaoService movimentacaoService;

    @InjectMocks
    private ContaService service;

    @Test
    void deveAbrirContaComSucesso() {

        // Arrange
        UUID clienteId = UUID.randomUUID();

        Cliente cliente = new Cliente();

        ContaRequest request = new ContaRequest();
        request.setClienteId(clienteId);
        request.setTipo(TipoConta.CORRENTE);

        ContaResponse responseEsperado = new ContaResponse();
        responseEsperado.setAgencia("0001");
        responseEsperado.setTipo(TipoConta.CORRENTE);

        when(clienteRepository.findById(clienteId))
                .thenReturn(Optional.of(cliente));

        when(contaRepository.existsByNumero(anyString()))
                .thenReturn(false);

        when(contaRepository.save(any(Conta.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(mapper.toResponse(any(Conta.class)))
                .thenReturn(responseEsperado);

        // Act
        ContaResponse response = service.abrirConta(request);

        // Assert
        assertNotNull(response);
        assertEquals("0001", response.getAgencia());
        assertEquals(TipoConta.CORRENTE, response.getTipo());

        ArgumentCaptor<Conta> captor =
                ArgumentCaptor.forClass(Conta.class);

        verify(contaRepository).save(captor.capture());

        Conta contaSalva = captor.getValue();

        assertEquals("0001", contaSalva.getAgencia());
        assertEquals(TipoConta.CORRENTE, contaSalva.getTipo());
        assertEquals(cliente, contaSalva.getCliente());
        assertNotNull(contaSalva.getNumero());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExistirAoAbrirConta() {

        UUID clienteId = UUID.randomUUID();

        ContaRequest request = new ContaRequest();
        request.setClienteId(clienteId);
        request.setTipo(TipoConta.CORRENTE);

        when(clienteRepository.findById(clienteId))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.abrirConta(request)
        );

        verify(contaRepository, never())
                .save(any(Conta.class));
    }

    @Test
    void deveDepositarComSucesso() {

        // Arrange
        UUID contaId = UUID.randomUUID();

        Conta conta = novaContaComSaldo("100.00");

        DepositoRequest request = new DepositoRequest();
        request.setValor(new BigDecimal("50.00"));

        ContaResponse responseEsperado = new ContaResponse();
        responseEsperado.setSaldo(new BigDecimal("150.00"));

        when(contaRepository.findById(contaId))
                .thenReturn(Optional.of(conta));

        when(contaRepository.save(conta))
                .thenReturn(conta);

        when(mapper.toResponse(conta))
                .thenReturn(responseEsperado);

        // Act
        ContaResponse response = service.depositar(contaId, request);

        // Assert
        assertEquals(
                new BigDecimal("150.00"),
                conta.getSaldo()
        );

        assertEquals(
                new BigDecimal("150.00"),
                response.getSaldo()
        );

        verify(contaRepository).save(conta);

        verify(movimentacaoService).registrar(
                conta,
                TipoMovimentacao.DEPOSITO,
                new BigDecimal("50.00")
        );
    }

    @Test
    void deveSacarComSucesso() {

        // Arrange
        UUID contaId = UUID.randomUUID();

        Conta conta = novaContaComSaldo("200.00");

        SaqueRequest request = new SaqueRequest();
        request.setValor(new BigDecimal("50.00"));

        ContaResponse responseEsperado = new ContaResponse();
        responseEsperado.setSaldo(new BigDecimal("150.00"));

        when(contaRepository.findById(contaId))
                .thenReturn(Optional.of(conta));

        when(contaRepository.save(conta))
                .thenReturn(conta);

        when(mapper.toResponse(conta))
                .thenReturn(responseEsperado);

        // Act
        ContaResponse response = service.sacar(contaId, request);

        // Assert
        assertEquals(
                new BigDecimal("150.00"),
                conta.getSaldo()
        );

        assertEquals(
                new BigDecimal("150.00"),
                response.getSaldo()
        );

        verify(contaRepository).save(conta);

        verify(movimentacaoService).registrar(
                conta,
                TipoMovimentacao.SAQUE,
                new BigDecimal("50.00")
        );
    }

    @Test
    void deveTransferirComSucesso() {

        // Arrange
        UUID origemId = UUID.randomUUID();
        UUID destinoId = UUID.randomUUID();

        Conta origem = novaContaComSaldo("150.00");
        Conta destino = novaContaComSaldo("0.00");

        TransferenciaRequest request = new TransferenciaRequest();
        request.setContaDestinoId(destinoId);
        request.setValor(new BigDecimal("50.00"));

        ContaResponse responseEsperado = new ContaResponse();
        responseEsperado.setSaldo(new BigDecimal("100.00"));

        when(contaRepository.findById(origemId))
                .thenReturn(Optional.of(origem));

        when(contaRepository.findById(destinoId))
                .thenReturn(Optional.of(destino));

        when(mapper.toResponse(origem))
                .thenReturn(responseEsperado);

        // Act
        ContaResponse response =
                service.transferir(origemId, request);

        // Assert
        assertEquals(
                new BigDecimal("100.00"),
                origem.getSaldo()
        );

        assertEquals(
                new BigDecimal("50.00"),
                destino.getSaldo()
        );

        assertEquals(
                new BigDecimal("100.00"),
                response.getSaldo()
        );

        verify(contaRepository).save(origem);
        verify(contaRepository).save(destino);

        verify(movimentacaoService).registrar(
                origem,
                TipoMovimentacao.TRANSFERENCIA_ENVIADA,
                new BigDecimal("50.00")
        );

        verify(movimentacaoService).registrar(
                destino,
                TipoMovimentacao.TRANSFERENCIA_RECEBIDA,
                new BigDecimal("50.00")
        );
    }

    @Test
    void naoDeveTransferirParaMesmaConta() {

        UUID contaId = UUID.randomUUID();

        TransferenciaRequest request = new TransferenciaRequest();
        request.setContaDestinoId(contaId);
        request.setValor(new BigDecimal("50.00"));

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> service.transferir(contaId, request)
        );

        assertEquals(
                "A conta de origem e destino não podem ser iguais",
                exception.getMessage()
        );

        verifyNoInteractions(contaRepository);
        verifyNoInteractions(movimentacaoService);
    }

    private Conta novaContaComSaldo(String saldo) {

        Conta conta = new Conta();
        conta.prePersist();

        BigDecimal valor = new BigDecimal(saldo);

        if (valor.compareTo(BigDecimal.ZERO) > 0) {
            conta.depositar(valor);
        }

        return conta;
    }
}