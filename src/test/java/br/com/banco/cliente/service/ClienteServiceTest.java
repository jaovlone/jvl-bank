package br.com.banco.cliente.service;

import br.com.banco.cliente.shared.exception.RegraNegocioException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import br.com.banco.cliente.domain.Cliente;
import br.com.banco.cliente.dto.ClienteRequest;
import br.com.banco.cliente.dto.ClienteResponse;
import br.com.banco.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void deveCadastrarClienteComSucesso() {

        // Arrange
        ClienteRequest request = new ClienteRequest();
        request.setNome("Joao Victor");
        request.setCpf("12345678901");
        request.setEmail("joao@email.com");
        request.setTelefone("61999999999");

        when(repository.existsByCpf("12345678901"))
                .thenReturn(false);

        when(repository.existsByEmail("joao@email.com"))
                .thenReturn(false);

        Cliente clienteSalvo = new Cliente();
        clienteSalvo.setNome("Joao Victor");
        clienteSalvo.setCpf("12345678901");
        clienteSalvo.setEmail("joao@email.com");
        clienteSalvo.setTelefone("61999999999");
        clienteSalvo.prePersist();

        when(repository.save(any(Cliente.class)))
                .thenReturn(clienteSalvo);

        // Act
        ClienteResponse response = service.cadastrar(request);

        // Assert
        assertEquals("Joao Victor", response.getNome());
        assertEquals("12345678901", response.getCpf());
        assertEquals("joao@email.com", response.getEmail());
    }
    @Test
    void deveLancarExcecaoQuandoCpfJaExiste() {

        // Arrange
        ClienteRequest request = new ClienteRequest();
        request.setNome("Joao Victor");
        request.setCpf("12345678901");
        request.setEmail("joao@email.com");
        request.setTelefone("61999999999");

        when(repository.existsByCpf("12345678901"))
                .thenReturn(true);

        // Act + Assert
        assertThrows(
                RegraNegocioException.class,
                () -> service.cadastrar(request)
        );

        verify(repository, never())
                .save(any(Cliente.class));
    }





}



