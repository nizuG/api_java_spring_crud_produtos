package com.example.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(max = 120, message = "O nome deve possuir no máximo 120 caracteres")
    @Column(nullable = false, length = 120)
    private String nome;

    @Size(max = 500, message = "A descrição deve possuir no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(
            value = "0.01",
            message = "O preço deve ser maior ou igual a R$ 0,01"
    )
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa")
    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @NotBlank(message = "A categoria é obrigatória")
    @Size(max = 80, message = "A categoria deve possuir no máximo 80 caracteres")
    @Column(nullable = false, length = 80)
    private String categoria;

    @NotNull(message = "O status do produto é obrigatório")
    @Column(nullable = false)
    private Boolean ativo;
}
