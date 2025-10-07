package com.mjdsilva.cliente.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mjdsilva.cliente.service.model.Endereco;

import jakarta.persistence.EntityNotFoundException;

@Repository
public interface IEnderecoRepository extends JpaRepository<Endereco, Long>{
	
	Endereco findByIdCliente_Id(Long ClienteId) throws EntityNotFoundException;
}
