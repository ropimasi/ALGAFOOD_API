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
package dev.ronaldomarques.algafood.domain.service;


import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import dev.ronaldomarques.algafood.domain.exception.EntidadeEmUsoException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.model.entity.CozinhaEntity;
import dev.ronaldomarques.algafood.domain.model.entity.RestauranteEntity;
import dev.ronaldomarques.algafood.domain.model.repository.CozinhaRepository;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;



/**
 * A simple didadic project. An RESTful-API based on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    ...
 * @since  ...
 */

@Service
public class RestauranteCadastroService {
	
	@Autowired
	private RestauranteRepository restauranteRepo;
	
	@Autowired
	private CozinhaRepository cozinhaRepo;
	
	
	
	public RestauranteEntity salvar(RestauranteEntity restauranteNovo) {
		/**
		 * Aplica regras de negócio-domínio e salva entidade 'restaurante' na base de dados, como novo registro se seu
		 * atributo 'id' é nulo, ou como atualização se seu atributo 'id' possui valor:
		 * @throws ArgumentoIlegalException       argumento não é instância em estado 'managed', ou não é instância de
		 *                                        objeto;
		 * @throws EntidadeNaoEncontradaException se o objeto do tipo <b>CozinhaEntity</b> atribuido ao objeto.atributo
		 *                                        <b>restaurante.cozinha</b> passado no argumento não estiver persistido
		 *                                        na entidade <b>CozinhaEntity</b> da base de dados;
		 */
		
		/* Didático: Aqui vão as regras de negócio. Tais como condições obrigatórias, validações.
		 * 
		 * Regra de negócio #1: para salvar um objeto 'RestauranteEntity' é obrigatório atribuir a seu atributo
		 * 'CozinhaEntity' um objeto 'cozinha' existente na DB. */
		
		try {
			/* Até o presente momento, não há regras de negócio aplicáveis neste escopo (service) para sanar de forma
			 * programática as possíveis EXCEPTIONS, sendo assim, as mesmas serão repassadas ao escopo superior
			 * (controller), porém, traduzidas para melhor exepriência do usuários consumidor da API. */
			CozinhaEntity cozinha = cozinhaRepo.findById(restauranteNovo.getId())
					.orElseThrow(() -> new EmptyResultDataAccessException(
							"cozinhaRepo.findById(restaurante.getCozinha().getId()) = CozinhaEntity não encontrado. Ou"
									+ " restaurante está sem cozinha. Só pode salvar com cozinha existente no DB.",
							1));
			
			restauranteNovo.setCozinha(cozinha);
			
			return restauranteRepo.save(restauranteNovo);
		}
		catch (IllegalArgumentException excep) { // Somente se chamada pela Controller.atualizar().
			throw new ArgumentoIlegalException(
					"Entidade restaurante com atributo 'id=" + restauranteNovo.getId()
							+ "' passada como argumento em 'cadastroService.salvar()' não está em estado 'managed'.\n");
		}
		catch (EntityNotFoundException excep) { // De: .save().
			throw new EntidadeNaoEncontradaException("\n"
					+ "Entidade:\tCozinha;\n"
					+ "Operação:\t'atribuir';\n"
					+ "Status:\t\tFalhou! Regra de negócio: Como atributo de 'Restaurantes' somente as entidades"
					+ " 'CozinhaEntity' previamente existente na base de dados podem ser atribuidas.\n"
					+ "Causa:\t\tObjeto com identificador 'restaurante.cozinha.id = "
					+ restauranteNovo.getCozinha().getId()
					+ "' não encontrado na base de dados. \n"
					+ "Sugestões:\tVerifique se o valor do identificador está correto, ou considere adiocioná-lo à base"
					+ " de dados antes de atribuí-lo.\n");
		}
		
	}
	
	
	
	public RestauranteEntity excluir(Long id) {
		
		/* Aqui vão as regras de negócio.
		 * Tais como condições obrigatórias, validações. */
		
		try {
			RestauranteEntity tmpReturnRestaurante =
					restauranteRepo.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
							"Entidade com id=" + id.toString() + "não encontrada no 'restauranteRepo.findById(id);'"));
			restauranteRepo.deleteById(id);
			return tmpReturnRestaurante;
		}
		catch (EmptyResultDataAccessException excep) {
			throw new EntidadeNaoEncontradaException(
					String.format("RestauranteEntity[id=%d] não pode ser removido, pois não existe. ", id));
		}
		catch (DataIntegrityViolationException excep) {
			throw new EntidadeEmUsoException(
					String.format("RestauranteEntity[id=%d] não pode ser removido, pois está em uso. ", id));
		}
		catch (Exception excep) {
			throw new RuntimeException(
					String.format("Erro INESPERADO na exclusão RestauranteEntity[id=%d]. ", id));
		}
		
	}
	
}
