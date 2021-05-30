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
package dev.ronaldomarques.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import dev.ronaldomarques.algafood.domain.model.entity.RestauranteEntity;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepositoryCmzQrs;



/**
 * This is a simple didadic project. A RESTful-API built with on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 */
@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryCmzQrs {
	
	@PersistenceContext
	private EntityManager em;
	
	
	
	@Override
	public List<RestauranteEntity> buscarCozinhaFreteMenor(String cozinhaNome, BigDecimal taxaFreteMenor) {
		
		var jpql = "FROM RestauranteEntity WHERE (cozinha.nome LIKE :cozinhaNome AND taxaFrete <= :taxaFreteMenor)";
		
		return em.createQuery(jpql, RestauranteEntity.class)
				.setParameter("cozinhaNome", "%" + cozinhaNome + "%")
				.setParameter("taxaFreteMenor", taxaFreteMenor)
				.getResultList();
		
	}
	
}
