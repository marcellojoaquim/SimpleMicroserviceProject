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

import com.mjdsilva.cliente.service.model.Contatos;
import com.mjdsilva.cliente.service.model.dto.ContatoResponseDto;
import com.mjdsilva.cliente.service.model.dto.ContatosDto;
import com.mjdsilva.cliente.service.services.IContatosService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contatos")
public class ContatosController {

	private IContatosService contatosService;
	private ModelMapper modelMapper;

	public ContatosController(IContatosService contatosService, ModelMapper modelMapper) {
		this.contatosService = contatosService;
		this.modelMapper = modelMapper;
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
	public ContatoResponseDto buscarPorId(@PathVariable Long id) {
		return contatosService.buscarPorId(id);
	}

	@GetMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Buscar contatos por id")
	public ContatoResponseDto buscarPorClienteId(@PathVariable Long id) {
		return contatosService.buscarPorClienteId(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Atualizar contatos")
	public ContatoResponseDto atualizar(@PathVariable Long id, @RequestBody @Valid ContatosDto contatos) {

		return contatosService.atualizar(id, contatos);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(description = "Buscar todos os contatos")
	public PageImpl<ContatosDto> buscar(ContatosDto contatosDto, Pageable pageable) {
		Contatos filter = modelMapper.map(contatosDto, Contatos.class);
		Page<Contatos> result = contatosService.bucar(filter, pageable);
		List<ContatosDto> list = result.getContent().stream().map(ctt -> modelMapper.map(ctt, ContatosDto.class))
				.collect(Collectors.toList());

		return new PageImpl<ContatosDto>(list, pageable, result.getTotalElements());
	}
}
