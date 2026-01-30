package com.keleybolos.api.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data // O Lombok cria os Getters, Setters e toString() automaticamente
@Entity // Diz pro Spring: "Isso é uma tabela do banco"
@Table(name = "tb_cliente") // O nome EXATO da tabela que criamos no MySQL
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Diz que o ID é auto-incremento
    private Long id;

    private String nome;

    private String telefone;
}