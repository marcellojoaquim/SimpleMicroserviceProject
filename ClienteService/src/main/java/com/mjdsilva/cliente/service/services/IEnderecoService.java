package com.mjdsilva.cliente.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mjdsilva.cliente.service.model.Endereco;
import com.mjdsilva.cliente.service.model.dto.EnderecoDto;
import com.mjdsilva.cliente.service.model.dto.EnderecoResponseDto;

public interface IEnderecoService {
	
	EnderecoResponseDto cadastrar(EnderecoDto endereco);
	EnderecoResponseDto atualizar(Long id, EnderecoDto endereco);
	void remover(Long id);
	EnderecoResponseDto buscarPorClienteId(Long id);
	EnderecoResponseDto buscarPorId(Long id);
	Page<Endereco> bucar(Endereco filter, Pageable pageable);
}
