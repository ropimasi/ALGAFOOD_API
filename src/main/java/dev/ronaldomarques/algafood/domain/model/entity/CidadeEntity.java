/* Copyright notes... */
package dev.ronaldomarques.algafood.domain.model.entity;


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
@JsonRootName("cidade")
@Table(name = "cidade")
public class CidadeEntity {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/* !!! IMPORTANTE: Algumas notações para o Hibernate, tais como @JoinColumn @ManyToOne @Column @GeneratedValue são
	 * destinadas para 'CompilationTime', onde o framework spring-com-Hibernate gera o DDL necessário para implementar a
	 * BD. Uma vez criada a base de dados as anotações são obsoletas, mesmo que o 'SourceCode' seja recompilado, ou rode
	 * em forma de testes na IDE. As anotações de HIBERNATE só serão necessária novamente nos momentos que o
	 * desenvolvedor quiser construir ou editar alguma estrutura/regra dentro do BD através destas anotações em
	 * 'SourceCode' por meio da DDL gerada pelo Hibernate. Situação esta muito frequentemente usada durante todo o
	 * processo 'dev' de desenvolvimento. Sendo assim, mantê-las */
	
	/* Lembrete didático: Altera o nome da propriedade do objeto serializado, tando JSON quanto em XML, NÃO-TEXT!
	 * @JsonProperty("nome_restaur")
	 * (name = "nome") é opcional; padrão vincular 'campo' com mesmo nome que o 'atributo'.
	 * (length = 128) é opcional no ato de gerar a 'tabela' no modelo relacional-db.
	 * (nullable = false) é opcional pra determinar atributo como 'notnull'. */
	@Column(name = "nome", length = 128, nullable = false)
	private String nome;
	
	@ManyToOne
	// Este objeto 'EstadoEntity.getId()' cede valor do 'id' para coluna 'estado_id' na tabela do BD.
	@JoinColumn(name = "estado_id")
	private EstadoEntity estado;
	
	
	
	public CidadeEntity() {
		super();
	}
	
	
	
	public CidadeEntity(String nome) {
		super();
		this.nome = nome;
	}
	
	
	
	public CidadeEntity(String nome, EstadoEntity estadoEntity) {
		super();
		this.nome = nome;
		this.estado = estadoEntity;
	}
}
