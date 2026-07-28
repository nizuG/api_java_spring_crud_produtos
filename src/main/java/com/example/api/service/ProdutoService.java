package com.example.api.service;

import com.example.api.model.Produto;


import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    Produto cadastrar(Produto produto);
    List<Produto> listarTodos();
    Optional<Produto> buscarPorId(Long id);
    Optional<Produto> atualizar(Long id, Produto produtoAtualizado);
    boolean excluir(Long id);
    Optional<Produto> alterarStatus(Long id, Boolean ativo);


}
