package com.mjsilva.vendas.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mjsilva.vendas.domain.Venda;
import com.mjsilva.vendas.domain.Venda.Status;


@Repository
public interface IVendaRepository extends MongoRepository<Venda, String>{
	
	Optional<Venda> findByCodigo(String codigo);
	Optional<Venda> findByClienteIdAndStatus(String clienteId, Status status);

}
