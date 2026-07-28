package com.example.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoStatusRequest {

    @NotNull(message = "O status do produto é obrigatório")
    private Boolean ativo;
}