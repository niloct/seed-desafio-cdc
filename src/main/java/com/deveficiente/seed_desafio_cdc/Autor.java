package com.deveficiente.seed_desafio_cdc;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class Autor {
    @NotNull
    public Date dataInscricao;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 400, message = "O tamanho máximo da descrição é de 400 caracteres.")
    public String descricao;

    @Email(message = "O e-mail está em formato inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    public String email;

    @NotBlank(message = "O nome é obrigatório.")
    public String nome;

    public Autor(String descricao, String email, String nome, Date dataInscricao) {
        this.descricao = descricao;
        this.email = email;
        this.nome = nome;
        this.dataInscricao = dataInscricao;
    }
}
