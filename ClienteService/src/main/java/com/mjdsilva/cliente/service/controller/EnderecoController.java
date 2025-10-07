package com.mjdsilva.cliente.service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mjdsilva.cliente.service.model.Endereco;
import com.mjdsilva.cliente.service.model.dto.EnderecoDto;
import com.mjdsilva.cliente.service.model.dto.EnderecoResponseDto;
import com.mjdsilva.cliente.service.services.IEnderecoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
	
	private IEnderecoService enderecoService;
	private ModelMapper modelMapper;
	
	public EnderecoController(IEnderecoService enderecoService, ModelMapper modelMapper) {
		this.enderecoService = enderecoService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(description = "Cadastrar um endereco")
	public EnderecoResponseDto cadastrar(@RequestBody @Valid EnderecoDto enderecoDto) {
		return enderecoService.cadastrar(enderecoDto);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Deve atualizar um endereco")
	public EnderecoResponseDto atualizar(@PathVariable Long id, @RequestBody @Valid EnderecoDto dto) {
		return enderecoService.atualizar(id, dto);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Deve retornar um endereco por id")
	public EnderecoResponseDto bucarPorId(@PathVariable Long id) {
		return enderecoService.buscarPorId(id);
	}
	
	
	@GetMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Deve buscar um endereco pelo id do cliente")
	public EnderecoResponseDto buscarPorIdCliente(@PathVariable Long id) {
		return enderecoService.buscarPorClienteId(id);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Buscar todos os enderecos")
	public PageImpl<EnderecoDto> buscar(EnderecoDto dto, Pageable pageable){
		Endereco endereco = modelMapper.map(dto, Endereco.class);
		Page<Endereco> result = enderecoService.bucar(endereco, pageable);
		List<EnderecoDto> list = result.getContent().stream().map(endr -> modelMapper.map(endr, EnderecoDto.class)).collect(Collectors.toList());
		return new PageImpl<>(list, pageable, result.getTotalElements());
	}
}
