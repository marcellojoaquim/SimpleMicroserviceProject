package com.mjdsilva.cliente.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mjdsilva.cliente.service.model.Contatos;

@Repository
public interface IContatosRepository extends JpaRepository<Contatos, Long>{
	
	Contatos findByIdCliente(Long id);
	Contatos findByEmail(String email);
}
