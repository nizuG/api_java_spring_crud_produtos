package com.example.api.repository;

import com.example.api.model.Produto;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Example<Produto> id(Long id);
}
