package com.mjdsilva.cliente.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mjdsilva.cliente.service.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
	
	Optional<Cliente> findByCpf(Long cpf);
	boolean existsByCpf(Long cpf);

}
