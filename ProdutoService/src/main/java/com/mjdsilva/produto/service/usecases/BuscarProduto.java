package com.mjdsilva.produto.service.usecases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mjdsilva.produto.service.domain.Produto;
import com.mjdsilva.produto.service.domain.Produto.Status;
import com.mjdsilva.produto.service.exception.EntityNotFoundException;
import com.mjdsilva.produto.service.repository.IProdutoRepository;

@Service
public class BuscarProduto {
	
	private IProdutoRepository produtoRepository;
	
	
	public BuscarProduto(IProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public Page<Produto> buscar(Pageable pageable) {
		return produtoRepository.findAll(pageable);
	}
	
	public Page<Produto> buscarPorStatus(Pageable pageable, Status status){
		return produtoRepository.findAllByStatus(pageable, status);
	}
	
	public Produto buscarPorCodigo(String codigo) {
		return produtoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new EntityNotFoundException(Produto.class, "codigo", codigo));
	}
	
	public Produto buscarPorId(String id) {
		return produtoRepository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException(Produto.class, "id", id));
	}
}
