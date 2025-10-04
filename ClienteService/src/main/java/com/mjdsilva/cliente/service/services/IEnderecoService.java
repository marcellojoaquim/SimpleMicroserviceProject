package com.mjdsilva.cliente.service.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mjdsilva.cliente.service.model.Endereco;

public interface IEnderecoService {
	
	Endereco cadastrar(Endereco endereco);
	Endereco atualizar(Endereco endereco);
	void remover(Long id);
	Optional<Endereco> buscarPorId(Long id);
	Optional<Endereco> buscarPorClienteId(Long id);
	Page<Endereco> bucar(Pageable pageable);
}
