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
@Table(name = "tb_endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ende_seq")
	@SequenceGenerator(name = "ende_seq", sequenceName = "seq_end", initialValue = 1)
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 50)
	private String rua;
	
	@Column(name = "numero", nullable = false, length = 20)
	private String numero;
	
	@Column(name = "bairro", nullable = false, length = 20)
	private String bairro;
	
	@Column(name = "cidade", nullable = false, length = 20)
	private String cidade;
	
	@Column(name = "estado", nullable = false, length = 20)
	private String estado;
	
	@Column(name = "cep", nullable = false, length = 20)
	private String cep;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_cliente_fk", 
				foreignKey = @ForeignKey(name = "fk_cliente_endereco"), 
				referencedColumnName = "id", 
				nullable = false)
	private Cliente idCliente;
}
