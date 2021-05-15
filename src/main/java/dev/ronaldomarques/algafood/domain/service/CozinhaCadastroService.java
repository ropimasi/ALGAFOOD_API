/* Copyright notes... */
package dev.ronaldomarques.algafood.domain.service;


import static dev.ronaldomarques.algafood.infrastructure.service.MescladorAtributos.mesclarAtributos;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import dev.ronaldomarques.algafood.domain.exception.EntidadeEmUsoException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.model.entity.Cozinha;
import dev.ronaldomarques.algafood.domain.model.repository.CozinhaRepository;




/**
 * A simple didadic project. An RESTful-API based on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    ...
 * @since  ...
 */

@Service
public class CozinhaCadastroService {
	@Autowired
	private CozinhaRepository cozinhaRepo;
	
	
	
	public Cozinha salvar(Cozinha cozinhaNova) {
		/**
		 * Persiste os dados do argumento 'cozinha' dentro de sua respectiva entidade do atual contexto de persistência,
		 * tanto em modo 'create'(ou adicionar novo) quanto modo 'update'(ou atualizar existente).
		 * @param  cozinha                        instância de entidade;
		 * @throws EntidadeNaoEncontradaException no caso de .salvar() por atualização: se o valor do objeto.atributo
		 *                                        passado como argumento <b>cozinhaNova.id</b> é não-nulo porém não há
		 *                                        registro na base de dados com tal valor;
		 * @return                                uma instância em estado <b><i>managed</i></b> da entidade que foi
		 *                                        salva na persistência;
		 * @see                                   CozinhaRepository.gravar(), CozinhaRepository.pegar().
		 */
		
		/* Didático: Aqui vão as regras de negócio, tais como condições obrigatórias, validações. */
		/* Regra de negócio #1: para acrescentar um novo objeto, deve ser enviado um objeto com atributo 'id' nulo para
		 * a camada de persistência, e para atualiazar, um objeto, um objeto com atributo 'id' valorado.
		 * 
		 * Regra de negócio #2: Devido à funcionalidade auto-incremento na base de dados deste projeto, somente é
		 * permitido atualizar objetos com atributo 'id' de valor já existente, nunca um valor ainda não persistido. */
		
		Long id = cozinhaNova.getId();
		
		if (id == null) {
			/* Objeto-argumento oriundo da 'controller.adicionar()'. */
			// DEBUG
			System.out.println("debug cozinhaCadastroServ salvar( cozinhaNova.id == null ); ");
			return cozinhaRepo.gravar(cozinhaNova);
		}
		else {
			/* Objeto-argumento oriundo da 'controller.atualizar()'. */
			
			Cozinha cozinhaAtual = cozinhaRepo.pegar(id);
			
			if (cozinhaAtual != null) { // Existe a cozinha para atualiza-la.
				return cozinhaRepo.gravar(cozinhaNova);
			}
			else {
				/* Não existe a cozinha para atualizá-la: ERRO. */
				/* Didático: Abordagem alterniativa de tratar como uma EXCEPTION para padronizar as mensagens de erro e
				 * retorno para o consumidor da API. */
				
				throw new EntidadeNaoEncontradaException(
						"\tEntidade:\tCozinha;\n"
								+ "\tMétodo:\t\t.salvar(cozinha);\n"
								+ "\tStatus:\t\tFalho;\n"
								+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
								+ " de valor igual o argumento passado 'id=" + id + "';\n"
								+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto,"
								+ " ou considere adicionar o registro com tal chave primária antes de atualizá-lo.");
			}
			
		}
		
	}
	
	
	
	public Cozinha salvarParcial(Long id, Map<String, Object> atributosValores) {
		/**
		 * Prepara condições necessárias para a persistência dos dados do Map-argumento 'atributosValores' dentro da
		 * entidade 'Cozinha' do atual contexto de persistência, no registro com atributo 'id' de valor passado no
		 * argumento 'id'.
		 * @param  id                             número inteiro-longo representando chave-primária na entidade de
		 *                                        persistência;
		 * @throws EntidadeNaoEncontradaException se não for encontrado objeto persistido na entidade 'Cozinha' com o
		 *                                        atributo 'id' de valor igual do argumento 'id' passado;
		 * @return                                uma instância em estado <b><i>managed</i></b> da entidade que foi
		 *                                        salva na persistência;
		 * @see                                   CozinhaRepository.gravar(), CozinhaRepository.pegar().
		 */
		
		/* Didático: Aqui vão as regras de negócio, tais como condições obrigatórias, validações. */
		/* Regra de negócio #1: para acrescentar um novo objeto, deve ser enviado um objeto com atributo 'id' nulo para
		 * a camada de persistência, e para atualiazar, um objeto, um objeto com atributo 'id' valorado.
		 * 
		 * Regra de negócio #2: Devido à funcionalidade auto-incremento na base de dados deste projeto, somente é
		 * permitido atualizar objetos com atributo 'id' de valor já existente, nunca um valor ainda não persistido. */
		Cozinha cozinhaAtual = cozinhaRepo.pegar(id);
		
		if (cozinhaAtual != null) {
			mesclarAtributos(atributosValores, cozinhaAtual);
			return salvar(cozinhaAtual); // Isto fara: return 'Cozinha';
		}
		else {
			/* Didático: Abordagem alterniativa de tratar como uma EXCEPTION para padronizar as mensagens de erro e
			 * retorno para o consumidor da API.
			 * return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); */
			throw new EntidadeNaoEncontradaException(
					"\tEntidade:\tCozinha;\n"
							+ "\tMétodo:\t\t.salvarParcial(cozinhaAtual, map);\n"
							+ "\tStatus:\t\tFalho;\n"
							+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
							+ " de valor igual o argumento passado 'id=" + id + "';\n"
							+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto,"
							+ " ou considere adicionar o registro com tal chave primária antes de atualizá-lo.");
		}
		
	}
	
	
//  Este método procurar(...) é uma abordagem alternativa.	
//	public Cozinha procurar(Long id) {
//		/* Didático: aqui vão regras de negócio se houver. E...
//		 * Como .buscar() .procurar() .pegar() , por enquanto neste projeto, não alteram o status da aplicação, então a
//		 * 'controller' pode acessar diretamente o 'repository', não utilizando este método, mas manterei-o aqui como
//		 * referência de uma possível implementação diferente, que contenha regras de negócios. */
//		return cozinhaRepo.pegar(id);
//	}
	
	
	
	public Cozinha excluir(Long id) {
		/**
		 * Persiste os dados do argumento 'cozinha' dentro de sua respectiva entidade do atual contexto de persistência,
		 * tanto em modo 'create'(ou adicionar novo) quanto modo 'update'(ou atualizar existente).
		 * @param  id                             número inteiro-longo representando chave-primária na entidade de
		 *                                        persistência;
		 * @return                                uma instância em estado <b><i>managed</i></b> da entidade que foi
		 *                                        salva na persistência;
		 * @throws EntidadeNaoEncontradaException // TODO esta descrição de throws...
		 * @throws EntidadeEmUsoException         // TODO esta descrição de throws...
		 */
		
		/* Didático: Aqui vão as regras de negócio, tais como condições obrigatórias, validações. */
		
		
		
		try {
			return cozinhaRepo.remover(id);
		}
		catch (EmptyResultDataAccessException excep) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha 'id = %d' não pode ser removida, pois não existe. ", id));
		}
		catch (DataIntegrityViolationException excep) {
			throw new EntidadeEmUsoException("Cozinha 'id = " + id + "' não pode ser removida, pois está em uso. ");
		}
		
	}
}
