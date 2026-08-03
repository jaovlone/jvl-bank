package br.com.banco.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class ClienteRequest {

    @NotBlank
    @Size(max = 120)
    private String nome;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @Email
    @NotBlank
    @Size(max = 150)
    private String email;

    @Size(max = 11)
    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}