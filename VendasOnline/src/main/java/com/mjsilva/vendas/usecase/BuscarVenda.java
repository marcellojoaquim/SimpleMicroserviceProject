package com.mjsilva.vendas.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mjsilva.vendas.domain.Venda;
import com.mjsilva.vendas.exception.EntityNotFoundException;
import com.mjsilva.vendas.repository.IVendaRepository;

@Service
public class BuscarVenda {

	private IVendaRepository vendaRepository;

	public BuscarVenda(IVendaRepository vendaRepository) {
		this.vendaRepository = vendaRepository;
	}
	
	public Page<Venda> buscar(Pageable pageable) {
		return vendaRepository.findAll(pageable);
	}
	
	public Venda buscarPorCodigo(String codigo) {
		return vendaRepository.findByCodigo(codigo)
				.orElseThrow(() -> new EntityNotFoundException(Venda.class, "codigo: ", codigo));
	}
}
