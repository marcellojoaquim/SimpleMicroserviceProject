package com.mjdsilva.cliente.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mjdsilva.cliente.service.exception.BusinessException;
import com.mjdsilva.cliente.service.model.Cliente;
import com.mjdsilva.cliente.service.model.Endereco;
import com.mjdsilva.cliente.service.model.dto.EnderecoDto;
import com.mjdsilva.cliente.service.model.dto.EnderecoResponseDto;
import com.mjdsilva.cliente.service.repository.IClienteRepository;
import com.mjdsilva.cliente.service.repository.IEnderecoRepository;
import com.mjdsilva.cliente.service.services.IEnderecoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EnderecoServiceImpl implements IEnderecoService{

	private IEnderecoRepository enderecoRepository;
	private IClienteRepository clienteRepository;
	private ModelMapper modelMapper;

	public EnderecoServiceImpl(IEnderecoRepository enderecoRepository, IClienteRepository clienteRepository, ModelMapper modelMapper) {
		this.enderecoRepository = enderecoRepository;
		this.clienteRepository = clienteRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public EnderecoResponseDto cadastrar(EnderecoDto dto) {
		if(enderecoRepository.findByIdCliente_Id(dto.getClienteId()) != null) {
			throw new BusinessException("Cliente já possui endereço cadastrado");
		}
		
		Endereco endereco = new Endereco();
		endereco = toEnderecoFromDto(endereco, dto);
		enderecoRepository.save(endereco);
		
		return modelMapper.map(endereco, EnderecoResponseDto.class);
	}

	@Override
	public EnderecoResponseDto atualizar(Long id, EnderecoDto dto) {
		Endereco endereco = enderecoRepository.findById(id).get();
		enderecoRepository.save(endereco);
		return modelMapper.map(endereco, EnderecoResponseDto.class);
	}

	@Override
	public void remover(Long id) {
		if(id == null) {
			throw new BusinessException("Id não pode ser nulo");
		}
		enderecoRepository.deleteById(id);
	}

	@Override
	public EnderecoResponseDto buscarPorId(Long id) {
		Endereco endereco = enderecoRepository.findById(id).get();
		
		if(endereco == null) {
			throw new EntityNotFoundException("Endereco não encontrado para o id informado");
		}
		
		return modelMapper.map(endereco, EnderecoResponseDto.class);
	}

	@Override
	public EnderecoResponseDto buscarPorClienteId(Long id) {
		Endereco endereco = enderecoRepository.findByIdCliente_Id(id);
		
		if(endereco == null) {
			throw new EntityNotFoundException("Endereco não encontrado para o id cliente informado");
		}
		
		return modelMapper.map(endereco, EnderecoResponseDto.class);
	}

	@Override
	public Page<Endereco> bucar(Endereco filter, Pageable pageable) {
		Example<Endereco> example = Example.of(filter, ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withIgnoreNullValues()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
		
		return enderecoRepository.findAll(example, pageable);
	}
	
	
	private Endereco toEnderecoFromDto(Endereco endereco, EnderecoDto dto) {
		
		Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
		
		endereco.setRua(dto.getRua());
		endereco.setNumero(dto.getNumero());
		endereco.setIdCliente(cliente);
		endereco.setEstado(dto.getEstado());
		endereco.setCidade(dto.getCidade());
		endereco.setCep(dto.getCep());
		endereco.setBairro(dto.getBairro());
		
		return endereco;
	}
}
