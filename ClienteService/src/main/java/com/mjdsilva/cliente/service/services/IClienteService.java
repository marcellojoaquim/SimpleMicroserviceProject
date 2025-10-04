package com.mjdsilva.cliente.service.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mjdsilva.cliente.service.model.Cliente;

public interface IClienteService {
	
	Cliente cadastrar(Cliente cliente);
	Cliente atualizar(Cliente cliente);
	void remover(Cliente cliente);
	void removePorId(Long id);
	Optional<Cliente> buscarPorCpf(Long cpf);
	Optional<Cliente> buscarPorId(Long id);
	Page<Cliente> bucar(Cliente filter, Pageable pageable);
}
