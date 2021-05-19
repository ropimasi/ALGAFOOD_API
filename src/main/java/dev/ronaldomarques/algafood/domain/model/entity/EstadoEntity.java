/**
 * DIREITOS, LICENSA E ISENÇÃO DE RESPONSABILIDADE:
 * Este arquivo é parte integrante, indivisível, inseparável de um projeto particular, o qual tem
 * seu uso expressamente exclusivo à seu autor, Ronaldo Marques (ronaldomarques@email.com ,
 * http://ronaldomarques.dev);
 * É vetado qualquer utilização, venda, aluguél, distribuição, em partes ou integral deste projeto;
 * Este projeto tem finalidade exclusiva de demonstração de conhecimento e habilidades no
 * desenvolvimento de software para apresentação de portfólio e processos de recrutamento;
 * Sendo assim, o autor deste projeto (Ronaldo Marques) não reconhece nem assume qualquer
 * responsabilidade pela utilização do mesmo, tão pouco por qualquer possível reflexos ou
 * consequência de tal utilização.
 * ---
 * RIGHTS, LICENSE AND DISCLAIMER:
 * This file is an integral, indivisible, inseparable part of a particular project, which has its
 * use expressly exclusive to its author, Ronaldo Marques (ronaldomarques@email.com ,
 * http://ronaldomarques.dev);
 * Any use, sale, rental, distribution, in parts or integral of this project is prohibited;
 * This project has the sole purpose of demonstrating knowledge and skills in software development
 * for portfolio presentations and recruitment processes;
 * Therefore, the author of this project (Ronaldo Marques) does not recognize or assume any
 * responsibility for the use of it, neither for any possible reflexes or consequence of such use.
 */
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
@JsonRootName("estado")
@Table(name = "estado")
/* Lembrete didático: (name = "estado") é opcional; padrão vincular 'tabela' com mesmo nome que a 'classe'
 * assumindo os padrões de nomenclatura: DB=lowcase_underscore; Java=CamelCase;. */
public class EstadoEntity {
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "id") // (name = "id") é opcional; padrão vincular 'campo' com mesmo nome que o 'atributo'.
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
	 * @JsonProperty("unidade_federal")
	 * (name = "nome") é opcional; padrão vincular 'campo' com mesmo nome que o 'atributo'.
	 * (length = 3) é opcional no ato de gerar a 'tabela' no modelo relacional-db.
	 * (nullable = false) é opcional pra determinar atributo como 'notnull'. */
	@Column(name = "nome", length = 3, nullable = false)
	private String nome;
	
	
	
	public EstadoEntity() {
		super();
	}
	
	
	
	public EstadoEntity(String nome) {
		super();
		this.nome = nome;
	}
}
