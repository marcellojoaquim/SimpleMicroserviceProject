package com.mjdsilva.produto.service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mjdsilva.produto.service.domain.Produto;
import com.mjdsilva.produto.service.domain.Produto.Status;

@Repository
public interface IProdutoRepository extends MongoRepository<Produto, String> {
	
	Optional<Produto> findByCodigo(String codigo);
	
	Page<Produto> findAllByStatus(Pageable pageable, Status status);
}
