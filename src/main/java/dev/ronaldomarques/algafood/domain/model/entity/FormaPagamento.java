/* Copyright notes... */
package dev.ronaldomarques.algafood.domain.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;




/**
 * A simple didadic project. An RESTful-API based on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    ...
 * @since  ...
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@JsonRootName("formaPagamento")
@Table(name = "forma_pagamento")
public class FormaPagamento {
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "id") // (name = "id") é opcional; padrão vincular 'campo' com mesmo nome que o 'atributo'.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	/* Lembrete didático: Altera o nome da propriedade do objeto serializado, tando JSON quanto em XML, NÃO-TEXT!
	 * @JsonProperty("nome_culinaria")
	 * (name = "nome") é opcional; padrão vincular 'campo' com mesmo nome que o 'atributo'.
	 * (length = 64) é opcional no ato de gerar a 'tabela' no modelo relacional-db.
	 * (nullable = false) é opcional pra determinar atributo como 'notnull'. */
	@Column(name = "descricao", length = 64, nullable = false)
	private String descricao;
}
