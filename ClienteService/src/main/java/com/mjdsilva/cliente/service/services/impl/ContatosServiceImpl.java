package com.mjdsilva.cliente.service.services.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	public Contatos atualizar(Contatos contatos) {
		if(contatos.getId() == null || contatos.getEmail() == null || contatos.getTel() == null || contatos.getCliente().getId() != null) {
			throw new IllegalArgumentException("Propriedade obrigatórias nulas");
		}
		return contatosRepository.save(contatos);
	}

	@Override
	public void remover(Long id) {
		if(id == null ) {
			throw new IllegalArgumentException("ID não pode ser nulo");
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
	public Page<Contatos> bucar(Pageable pageable) {
		return contatosRepository.findAll(pageable);
	}
	
	
}
