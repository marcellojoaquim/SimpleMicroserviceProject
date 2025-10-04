package com.mjdsilva.cliente.service.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mjdsilva.cliente.service.exception.BusinessException;
import com.mjdsilva.cliente.service.model.Cliente;
import com.mjdsilva.cliente.service.repository.IClienteRepository;
import com.mjdsilva.cliente.service.services.IClienteService;

@Service
public class CLienteServiceImpl implements IClienteService{
	
	private IClienteRepository clienteRepository;
	
	public CLienteServiceImpl(IClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	
	@Override
	public Cliente cadastrar(Cliente cliente) {
		if(clienteRepository.existsByCpf(cliente.getCpf())) {
			throw new BusinessException("Este cpf: "+cliente.getCpf()+" já existe.");
		}
		return clienteRepository.save(cliente);
	}

	@Override
	public Cliente atualizar(Cliente cliente) {
		if(cliente == null || cliente.getId() == null) {
			throw new IllegalArgumentException("Cliente ou Id não podem ser nulo");
		}
		return clienteRepository.save(cliente);
	}

	@Override
	public void remover(Cliente cliente) {
		if(cliente == null || cliente.getId() == null) {
			throw new IllegalArgumentException("Cliente ou Id não podem ser nulo");
		}
		clienteRepository.delete(cliente);
	}

	@Override
	public void removePorId(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("Id não podem ser nulo");
		}
		clienteRepository.deleteById(id);
		
	}

	@Override
	public Optional<Cliente> buscarPorCpf(Long cpf) {
		return clienteRepository.findByCpf(cpf);
	}

	@Override
	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepository.findById(id);
	}

	@Override
	public Page<Cliente> bucar(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

}
