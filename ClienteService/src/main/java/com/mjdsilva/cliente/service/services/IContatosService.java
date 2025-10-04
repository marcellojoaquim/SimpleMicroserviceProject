package com.mjdsilva.cliente.service.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mjdsilva.cliente.service.model.Contatos;

public interface IContatosService {
	
	Contatos cadastrar(Contatos contatos);
	Contatos atualizar(Contatos contatos);
	void remover(Long id);
	Optional<Contatos> buscarPorId(Long id);
	Optional<Contatos> buscarPorClienteId(Long id);
	Page<Contatos> bucar(Pageable pageable);
}
