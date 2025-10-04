package com.mjdsilva.cliente.service.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mjdsilva.cliente.service.exception.BusinessException;
import com.mjdsilva.cliente.service.model.Contatos;
import com.mjdsilva.cliente.service.repository.IContatosRepository;
import com.mjdsilva.cliente.service.services.IContatosService;

@Service
public class ContatosServiceImpl implements IContatosService{
	
	private IContatosRepository contatosRepository;
	
	public ContatosServiceImpl(IContatosRepository repository) {
		this.contatosRepository = repository;
	}
	
	@Override
	public Contatos cadastrar(Contatos contatos) {
		if(contatosRepository.findByEmail(contatos.getEmail()) != null) {
			throw new BusinessException("email já existe");
		}
		return contatosRepository.save(contatos);
	}

	@Override
	public Contatos atualizar(Contatos contatos) {
		if(contatos.getId() == null || contatos.getEmail() == null || contatos.getTel() == null) {
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
	public Optional<Contatos> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Contatos> buscarPorClienteId(Long id) {
		return contatosRepository.findById(id);
	}

	@Override
	public Page<Contatos> bucar(Pageable pageable) {
		return contatosRepository.findAll(pageable);
	}
	
	
}
