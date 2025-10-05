package com.mjdsilva.cliente.service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mjdsilva.cliente.service.model.Contatos;

import jakarta.persistence.EntityNotFoundException;

@Repository
public interface IContatosRepository extends JpaRepository<Contatos, Long>{
	
	Contatos findByCliente_Id(Long clienteId) throws EntityNotFoundException;
	Contatos findByEmail(String email);
}
