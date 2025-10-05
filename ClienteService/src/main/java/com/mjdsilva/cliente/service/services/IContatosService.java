package com.mjdsilva.cliente.service.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mjdsilva.cliente.service.model.Contatos;
import com.mjdsilva.cliente.service.model.dto.ContatoResponseDto;
import com.mjdsilva.cliente.service.model.dto.ContatosDto;

import jakarta.persistence.EntityNotFoundException;

public interface IContatosService {
	
	ContatosDto cadastrar(ContatosDto contatosDto);
	Contatos atualizar(Contatos contatos);
	void remover(Long id);
	ContatoResponseDto buscarPorId(Long id);
	ContatoResponseDto buscarPorClienteId(Long clienteId) throws EntityNotFoundException;
	Page<Contatos> bucar(Pageable pageable);
}
