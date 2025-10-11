package com.mjsilva.vendas.repository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mjsilva.vendas.domain.Produto;
import com.mjsilva.vendas.domain.Venda;
import com.mjsilva.vendas.domain.Venda.Status;
import com.mjsilva.vendas.domain.dto.VendaDto;
import com.mjsilva.vendas.exception.EntityNotFoundException;
import com.mjsilva.vendas.service.ClienteService;
import com.mjsilva.vendas.service.IProdutoService;

import jakarta.validation.Valid;

@Service
public class CadastrarVenda {

	private IVendaRepository vendaRepository;
	private IProdutoService produtoService;
	private ClienteService clienteService;
	private ModelMapper modelMapper;
	
	public CadastrarVenda(IVendaRepository vendaRepository, IProdutoService produtoService,
			ClienteService clienteService, ModelMapper modelMapper) {
		this.vendaRepository = vendaRepository;
		this.produtoService = produtoService;
		this.clienteService = clienteService;
		this.modelMapper = modelMapper;
	}
	
	
	public Venda cadastrar(@Valid VendaDto vendaDto) {
		Venda venda = modelMapper.map(vendaDto, Venda.class);
		validarCliente(venda.getClienteId());
		venda.recalcularValorTotalVenda();
		return this.vendaRepository.insert(venda);
	}
	
	public Venda atualizar(@Valid Venda venda) {
		return vendaRepository.save(venda);
	}
	
	public Venda finalizar(String id) {
		Venda venda = buscarVenda(id);
		venda.validarStatus();
		venda.setStatus(Status.CONCLUIDA);
		return this.vendaRepository.save(venda);
		
	}
	
	public Venda cancelar(String id) {
		Venda venda = buscarVenda(id);
		venda.validarStatus();
		venda.setStatus(Status.CANCELADA);
		return this.vendaRepository.save(venda);
	}
	
	public Venda adicionarProduto(String id, String codigoProduto, Integer quantidade) {
		Venda venda = buscarVenda(id);
		Produto produto = buscarProduto(codigoProduto);
		venda.validarStatus();
		venda.adicionarProduto(produto, quantidade);
		return this.vendaRepository.save(venda);
	}

	public Venda removerProduto(String id, String codigoProduto, Integer quantidade) {
		Venda venda = buscarVenda(id);
		Produto produto = buscarProduto(codigoProduto);
		venda.validarStatus();
		venda.removerProduto(produto, quantidade);
		return this.vendaRepository.save(venda);
	}
	
	private Produto buscarProduto(String codigoProduto) {
		Produto produto = produtoService.buscaProduto(codigoProduto);
		if(produto == null) {
			throw new EntityNotFoundException(Produto.class, "codigo: ", codigoProduto);
		}
		return produto;
	}


	private void validarCliente(String clienteId) {
		Boolean isCadastrado = this.clienteService.isClienteCadastrado(clienteId);
		if(!isCadastrado) {
			new EntityNotFoundException(Venda.class, "clienteId", clienteId);
		}
	}
	
	private Venda buscarVenda(String id) {
		return vendaRepository.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(Venda.class, "id", id));
	}
	
}
