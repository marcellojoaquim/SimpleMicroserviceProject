package com.mjdsilva.cliente.service.controller;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mjdsilva.cliente.service.model.dto.ContatoResponseDto;
import com.mjdsilva.cliente.service.model.dto.ContatosDto;
import com.mjdsilva.cliente.service.services.IClienteService;
import com.mjdsilva.cliente.service.services.IContatosService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contatos")
public class ContatosController {
	
	private IContatosService contatosService;
	private IClienteService clienteService;
	
	public ContatosController(IContatosService contatosService, IClienteService clienteService) {
		this.contatosService = contatosService;
		this.clienteService = clienteService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(description = "Cadastra os contatos")
	public ContatosDto cadastrar(@RequestBody @Valid ContatosDto contatosDto) {
		
		return contatosService.cadastrar(contatosDto);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Buscar contatos por id")
	public ContatoResponseDto buscar(@PathVariable Long id) {
		 return contatosService.buscarPorId(id);
	}
	
	@GetMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Buscar contatos por id")
	public ContatoResponseDto buscarPorClienteId(@PathVariable Long id) {
		 return contatosService.buscarPorClienteId(id);
	}
	
	
	
}
