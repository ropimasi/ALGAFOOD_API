package dev.ronaldomarques.algafood.domain.service;


import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import dev.ronaldomarques.algafood.domain.exception.EntidadeEmUsoException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.model.entity.Restaurante;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;




@Service
public class RestauranteCadastroService {
	@Autowired
	private RestauranteRepository restauranteRepo;
	
	// @Autowired
	// private CozinhaRepository cozinhaRepo;
	
	
	
	public Restaurante salvar(Restaurante restaurante) {
		/**
		 * Aplica regras de negócio-domínio e salva entidade 'restaurante' na base de dados, como novo registro se seu
		 * atributo 'id' é nulo, ou como atualização se seu atributo 'id' possui valor:
		 * @throws ArgumentoIlegalException       argumento não é instância em estado 'managed', ou não é instância de
		 *                                        objeto;
		 * @throws EntidadeNaoEncontradaException se o objeto do tipo <b>Cozinha</b> atribuido ao objeto.atributo
		 *                                        <b>restaurante.cozinha</b> passado no argumento não estiver persistido
		 *                                        na entidade <b>Cozinha</b> da base de dados;
		 * @see                                   RestauranteRepository.gravar().
		 */
		
		/* Didático: Aqui vão as regras de negócio.
		 * Tais como condições obrigatórias, validações. */
		
		try {
			return restauranteRepo.gravar(restaurante);
		}
		catch (IllegalArgumentException excep) { // De: .gravar() <- .merge().
			/* Até o presente momento, não há regras de negócio aplicáveis neste escopo (service) para sanar de forma
			 * programática as possíveis EXCEPTIONS, sendo assim, as mesmas serão repassadas ao escopo superior
			 * (controller), porém, traduzidas para melhor exepriência do usuários consumidor da API. */
			throw new ArgumentoIlegalException(
					"Entidade passada como argumento não está em estado 'managed'.\n");
		}
		catch (EntityNotFoundException excep) { // De: .gravar().
			throw new EntidadeNaoEncontradaException("\n"
					+ "Entidade:\tCozinha;\n"
					+ "Operação:\t'atribuir';\n"
					+ "Status:\t\tFalhou! Regra de negócio: Como atributo de 'Restaurantes' somente as entidades"
					+ " 'Cozinha' previamente existente na base de dados podem ser atribuidas.\n"
					+ "Causa:\t\tObjeto com identificador 'restaurante.cozinha.id = " + restaurante.getCozinha().getId()
					+ "' não encontrado na base de dados. \n"
					+ "Sugestões:\tVerifique se o valor do identificador está correto, ou considere adiocioná-lo à base"
					+ " de dados antes de atribuí-lo.\n");
		}
		
	}
	
	
	
	public Restaurante excluir(Long id) {
		
		/* Aqui vão as regras de negócio.
		 * Tais como condições obrigatórias, validações. */
		try {
			return restauranteRepo.remover(id);
		}
		catch (EmptyResultDataAccessException excep) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante[id=%d] não pode ser removido, pois não existe. ", id));
		}
		catch (DataIntegrityViolationException excep) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante[id=%d] não pode ser removido, pois está em uso. ", id));
		}
		catch (Exception excep) {
			throw new RuntimeException(
					String.format("Erro INESPERADO na exclusão Restaurante[id=%d]. ", id));
		}
		
	}
}
