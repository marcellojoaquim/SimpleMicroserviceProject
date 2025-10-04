package com.mjdsilva.cliente.service.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
	@SequenceGenerator(name = "cliente_seq", sequenceName = "seq_cliente", initialValue = 1)
	private Long id;
	
	@Column(name = "primeiro_nome", nullable = false, length = 56)
	private String nome;
	
	@Column(name = "segundo_nome", nullable = false, length = 56)
	private String segundoNome;
	
	@Column(name = "cliente_cpf", nullable = false, unique = true)
	private Long cpf;
	
	@OneToOne(mappedBy = "idCliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco endereco;
	
	@OneToOne(mappedBy = "idCliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private Contatos contatos;
	
}
