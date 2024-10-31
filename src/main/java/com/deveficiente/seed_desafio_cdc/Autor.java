package com.deveficiente.seed_desafio_cdc;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataInscricao = LocalDateTime.now();

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 400, message = "O tamanho máximo da descrição é de 400 caracteres.")
    private String descricao;

    @Email(message = "O e-mail está em formato inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Column(unique=true)
    private String email;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    private Autor(String descricao, String email, String nome) {
        this.descricao = descricao;
        this.email = email;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "[id=%s, nome=%s, email=%s, descricao=%s]".formatted(id, nome, email, descricao);
    }
}
