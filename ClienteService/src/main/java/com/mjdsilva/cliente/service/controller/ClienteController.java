package com.mjdsilva.cliente.service.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mjdsilva.cliente.service.model.Cliente;
import com.mjdsilva.cliente.service.model.dto.ClienteDto;
import com.mjdsilva.cliente.service.services.IClienteService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	
	private final IClienteService clienteService;
	private final ModelMapper modelMapper;
	
	public ClienteController(IClienteService clienteService, ModelMapper modelMapper) {
		this.clienteService = clienteService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(description = "Cadastra usuario")
	public ClienteDto cadastar(@RequestBody @Valid ClienteDto dto) {
		Cliente cliente = modelMapper.map(dto, Cliente.class);
		cliente = clienteService.cadastrar(cliente);
		return modelMapper.map(cliente, ClienteDto.class);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Buscar cliente por Id")
	public ClienteDto buscarPorId(@PathVariable Long id) {
		return clienteService
				.buscarPorId(id).map(cliente -> modelMapper.map(cliente, ClienteDto.class))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping
	@Operation(description = "Busca todos")
	public PageImpl<ClienteDto> buscarTodos(ClienteDto dto, Pageable pageable) {
		Cliente filter = modelMapper.map(dto, Cliente.class);
		Page<Cliente> result = clienteService.bucar(filter, pageable);
		List<ClienteDto>list = result.getContent().stream()
				.map(cliente -> modelMapper.map(cliente, ClienteDto.class)).collect(Collectors.toList());
		
		return new PageImpl<ClienteDto>(list, pageable, result.getTotalElements());
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Atualiza um cliente")
	public ClienteDto atualizar(@PathVariable Long id, @RequestBody @Valid ClienteDto dto) {
		return clienteService.buscarPorId(id).map(cliente -> {
			cliente.setNome(dto.getNome());
			cliente.setCpf(dto.getCpf());
			cliente.setSegundoNome(dto.getSegundoNome());
			cliente = clienteService.atualizar(cliente);
			return modelMapper.map(cliente, ClienteDto.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("/{id}")
	@Operation(description = "Remove um cliente")
	public void remover(@PathVariable Long id) throws Exception {
		Cliente cliente = clienteService.buscarPorId(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		clienteService.remover(cliente);
	}
	
	@GetMapping(value = "isCadastrado/{id}")
	public ResponseEntity<Boolean> isCadastrado(@PathVariable(value = "id", required = true) Long id) {
		return ResponseEntity.ok(clienteService.isCadastrado(id));
	}
	
}
