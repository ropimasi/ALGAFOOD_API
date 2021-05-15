/* Copyright notes... */
package dev.ronaldomarques.algafood.domain.model.entity;


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@JsonRootName("restaurante")
@Table(name = "restaurante")
/* Lembrete didático: (name = "restaurante") é opcional; padrão vincular 'tabela' com mesmo nome que a 'classe'
 * assumindo os
 * padrões de nomenclatura: DB=lowcase_underscore; Java=CamelCase;. */
public class RestauranteEntity {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "id") // (name = "id") é opcional; padrão vincular 'campo' com mesmo nome que o 'atributo'.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/* !!! IMPORTANTE lembrar que algumas notações para o Hibernate, tais
	 * como @JoinColumn @ManyToOne @Column @GeneratedValue são destinadas para 'CompilationTime', onde o framework
	 * spring-com-Hibernate gera o DDL necessário para implementar a BD. Uma vez criada a base de dados as anotações são
	 * obsoletas, mesmo que o 'SourceCode' seja recompilado, ou rode em forma de testes na IDE. As anotações de
	 * HIBERNATE só serão necessária novamente nos momentos que o desenvolvedor quiser construir ou editar alguma
	 * estrutura/regra dentro do BDatravés destas anotações em 'SourceCode' por meio da DDL gerada pelo Hibernate. */
	
	/* Lembrete didático: Altera o nome da propriedade do objeto serializado, tando JSON quanto em XML, NÃO-TEXT!
	 * 
	 * @JsonProperty("nome_restaur")
	 * (name = "nome") é opcional; padrão vincular 'campo' com mesmo nome que o 'atributo'.
	 * (length = 128) é opcional no ato de gerar a 'tabela' no modelo relacional-db.
	 * (nullable = false) é opcional pra determinar atributo como 'notnull'. */
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@ManyToOne
	// Este objeto 'cozinha.getId()' cede valor do 'id' para coluna 'conzina_id' na tabela do BD.
	@JoinColumn(name = "cozinha_id", nullable = false)
	private CozinhaEntity cozinha;
	
	
	
	public RestauranteEntity() {}
	
	
	
	public RestauranteEntity(String nome) {
		
		this();
		this.nome = nome;
		
	}
	
}
