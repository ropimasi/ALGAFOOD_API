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
package dev.ronaldomarques.algafood.domain.model.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import dev.ronaldomarques.algafood.domain.model.entity.RestauranteEntity;



/**
 * This is a simple didadic project. A RESTful-API built with on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 */
@Repository
public abstract interface RestauranteRepository extends
		JpaRepository<RestauranteEntity, Long>,
		RestauranteRepositoryCmzQrs {
	/* Este ERA um 'repositório orientado à persistencia': 'POR'. */
	/* Quando um 'repository' é orientado à 'collection' ele tem como uma das abstrações imitar uma
	 * 'collection'. E os
	 * tipos 'collections' seguem padrões de nomenclatura próprios, por exemplo:
	 * public abstract RestauranteEntity guardar(RestauranteEntity restaurante);
	 * public abstract void eliminar(RestauranteEntity restaurante);
	 * public abstract RestauranteEntity porId(Long id);
	 * public abstract List<RestauranteEntity> todos(); */
	
	/* Métodos comentados para dar lugar ao modo JpaHibernate: */
	/* public abstract RestauranteEntity gravar(RestauranteEntity restauranteEntity);
	 * public abstract RestauranteEntity remover(Long id);
	 * public abstract RestauranteEntity buscar(Long id);
	 * public abstract List<RestauranteEntity> listar(); */
	
	/* Simple test using the 'property name'. */
	RestauranteEntity taxaFrete(BigDecimal taxaFrete);
	/* Simple test using the 'find' prefixe, 'By' key-word, and 'Property name'. */
	Optional<RestauranteEntity> findByTaxaFrete(BigDecimal taxaFrete);
	/* Simple test using the 'find' prefixe, 'By' key-word, and 'Property name'. */
	List<RestauranteEntity> findListaByTaxaFrete(BigDecimal taxaFrete);
	/* Simple test using the 'find' prefixe, 'By' key-word, and 'Property name'. */
	List<RestauranteEntity> findListaByTaxaFreteBetween(BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	/* Simple test. */
	List<RestauranteEntity> findListaByTaxaFreteBetweenAndCozinhaNomeContaining(BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal, String cozinhaNome);
	/* Simple test. */
	List<RestauranteEntity> findAllByNomeContaining(String nome);
	/* Simple test. */
	List<RestauranteEntity> findTop2ByNomeContaining(String nome);
	/* Simple test. */
	boolean existsByNome(String nome);
	/* Simple test. */
	boolean existsByTaxaFrete(BigDecimal taxaFrete);
	
	/* Simple test de NamedQuery com @Query. */
	@Query("FROM RestauranteEntity WHERE (taxaFrete BETWEEN :taxaInicial AND :taxaFinal) "
			+ "AND (cozinha.nome LIKE %:cozinhaNome%) ")
	List<RestauranteEntity> porTaxaECozinha(
			@Param("taxaInicial") BigDecimal taxaFreteInicial,
			@Param("taxaFinal") BigDecimal taxaFreteFinal,
			@Param("cozinhaNome") String cozinhaNome);
	
	/* EXTERNAL a simple test de NamedQuery. */
	List<RestauranteEntity> porTaxaECozinhaExt(
			@Param("taxaInicial") BigDecimal taxaFreteInicial,
			@Param("taxaFinal") BigDecimal taxaFreteFinal,
			@Param("cozinhaNome") String cozinhaNome);
	
}
//
//
//
//
//
//
//
//
//
//
//
//
