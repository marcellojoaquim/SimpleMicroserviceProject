package com.mjdsilva.cliente.service.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente_contatos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Contatos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ctt_seq")
	@SequenceGenerator(name = "ctt_seq", sequenceName = "seq_contt", initialValue = 1)
	private Long id;
	
	@Column(name = "telefone", nullable = false, unique = true)
	private Integer tel;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_cliente_fk", 
				foreignKey = @ForeignKey(name = "fk_contatos_cliente"),
				referencedColumnName = "id", nullable = false)
	private Cliente cliente;
}
