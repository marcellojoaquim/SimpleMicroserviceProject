package com.mjdsilva.cliente.service.services.impl;



import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjdsilva.cliente.service.exception.BusinessException;
import com.mjdsilva.cliente.service.model.Cliente;
import com.mjdsilva.cliente.service.model.Contatos;
import com.mjdsilva.cliente.service.model.dto.ContatoResponseDto;
import com.mjdsilva.cliente.service.model.dto.ContatosDto;
import com.mjdsilva.cliente.service.repository.IClienteRepository;
import com.mjdsilva.cliente.service.repository.IContatosRepository;
import com.mjdsilva.cliente.service.services.IContatosService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContatosServiceImpl implements IContatosService{
	
	private IContatosRepository contatosRepository;
	private IClienteRepository clienteRepository;
	private ModelMapper modelMapper;
	
	public ContatosServiceImpl(IContatosRepository repository, ModelMapper modelMapper, IClienteRepository clienteRepository) {
		this.contatosRepository = repository;
		this.clienteRepository = clienteRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ContatosDto cadastrar(ContatosDto contatosDto) {
		
		if(contatosRepository.findByEmail(contatosDto.getEmail()) != null) {
			throw new BusinessException("email já existe");
		} 
		if(contatosRepository.findByCliente_Id(contatosDto.getClienteId()) != null) {
			throw new BusinessException("Cliente ja possui contatos cadastrados");
		}
		Cliente cliente = clienteRepository.findById(contatosDto.getClienteId())
				.orElseThrow(()-> new EntityNotFoundException("Cliente não encontrado"));
		
		Contatos contatos = new Contatos();

		contatos.setEmail(contatosDto.getEmail());
		contatos.setTel(contatosDto.getTel());
		contatos.setCliente(cliente);
		
		contatosRepository.save(contatos);
		return modelMapper.map(contatos, ContatosDto.class);
	}

	@Override
	@Transactional
	public ContatoResponseDto atualizar(Long id, ContatosDto contatosDto) {
		Contatos contatos = contatosRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Contatos não encontrados para o id informado"));
		contatos.setEmail(contatosDto.getEmail());
		contatos.setTel(contatosDto.getTel());
		
		contatosRepository.save(contatos);
		return modelMapper.map(contatos, ContatoResponseDto.class); 
	}

	@Override
	public void remover(Long id) {
		if(id == null ) {
			throw new BusinessException("ID não pode ser nulo");
		}
		contatosRepository.deleteById(id);
	}

	@Override
	public ContatoResponseDto buscarPorId(Long id) {
		Contatos ctt = contatosRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contatos não encontrado"));
		return modelMapper.map(ctt, ContatoResponseDto.class);
	}

	@Override
	public ContatoResponseDto buscarPorClienteId(Long clienteId) {
		Contatos ctt = contatosRepository.findByCliente_Id(clienteId);
		if(clienteRepository.findById(clienteId) == null) {
			throw new EntityNotFoundException("CLiente não existe para este Id");
		}
		
		if(ctt == null) {
			throw new EntityNotFoundException("Contatos não encontrados");
		}
		
		return modelMapper.map(ctt, ContatoResponseDto.class);
	}

	@Override
	public Page<Contatos> bucar(Contatos filter, Pageable pageable) {
		Example<Contatos> example = Example.of(filter, ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withIgnoreNullValues()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
		return contatosRepository.findAll(example, pageable);
	}
	
	
}
