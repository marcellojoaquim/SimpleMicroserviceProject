package com.mjdsilva.cliente.service.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mjdsilva.cliente.service.exception.BusinessException;
import com.mjdsilva.cliente.service.model.Endereco;
import com.mjdsilva.cliente.service.repository.IEnderecoRepository;
import com.mjdsilva.cliente.service.services.IEnderecoService;

@Service
public class EnderecoServiceImpl implements IEnderecoService{

	private IEnderecoRepository enderecoRepository;

	public EnderecoServiceImpl(IEnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	@Override
	public Endereco cadastrar(Endereco endereco) {
		if(endereco.getIdCliente() == null || endereco.getCep() == null) {
			throw new BusinessException("Propriedades obrigatorias estao nulas");
		}
		return enderecoRepository.save(endereco);
	}

	@Override
	public Endereco atualizar(Endereco endereco) {
		if(endereco.getIdCliente() == null || endereco.getCep() == null) {
			throw new BusinessException("Propriedades obrigatorias estao nulas");
		}
		return enderecoRepository.save(endereco);
	}

	@Override
	public void remover(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("Id não pode ser nulo");
		}
		enderecoRepository.deleteById(id);
	}

	@Override
	public Optional<Endereco> buscarPorId(Long id) {
		if(id==null) {
			throw new IllegalArgumentException("Id não pode ser nulo");
		}
		return enderecoRepository.findById(id);
	}

	@Override
	public Optional<Endereco> buscarPorClienteId(Long id) {
		if(id==null) {
			throw new IllegalArgumentException("Id não pode ser nulo");
		}
		return Optional.of(enderecoRepository.findByIdCliente(id));
	}

	@Override
	public Page<Endereco> bucar(Pageable pageable) {
		return enderecoRepository.findAll(pageable);
	}
	
	
}
