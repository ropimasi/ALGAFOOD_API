/* Copyright notes... */
package dev.ronaldomarques.algafood.infrastructure.repository;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TypedQuery;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.ronaldomarques.algafood.domain.model.entity.Cozinha;
import dev.ronaldomarques.algafood.domain.model.repository.CozinhaRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;
import dev.ronaldomarques.algafood.infrastructure.exception.PercistenciaException;




/**
 * A simple didadic project. An RESTful-API based on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 */

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {
	/* Didátido: @PersistenceContext é anotação semelhante à @Autowired. Faz injeção da dependência, porém
	 * oferece recursos adicionais. Esta é mais indicada para usar aqui em EntityManager. */
	@PersistenceContext
	private EntityManager manager;
	
	
	
	/* Didátido: Na ocasião em que se usa um 'EntityManager' compartilhado em diferentes Objetos ou Métodos
	 * como é o caso deste projeto deve-se atentar-se com os métodos que provocam alterações na ENTIDADE(exemplo insert
	 * ou update), pois elas precisam ser executadas com recursos da anotação @TRANSACTIONAL do
	 * org.springframework.transactional... */
	@Transactional
	@Override
	public Cozinha gravar(Cozinha cozinha) {
		/**
		 * Persiste os dados do objeto-argumento <b>cozinha</b> dentro de sua respectiva entidade do atual contexto de
		 * persistência, como um novo registro se seu atributo <b>id</b> é nulo, ou como atualização do registro
		 * existente se seu atributo <b>id</b> possui valor:
		 * @param   cozinha                  instância de entidade;
		 * @returns                          uma instância em estado <b><i>managed</i></b> da entidade que foi mesclada
		 *                                   à persistência;
		 * @throws  ArgumentoIlegalException se argumento <b>cozinha</b> não está em estado <b><i>managed</i></b>, ou a
		 *                                   instância não é uma entidade deste contexto de persistência;
		 * @throws  PercistenciaException    se srgumento <b>cozinha</b> é objeto de tipo diferente de <b>Cozinha</b>,
		 *                                   ou, se um ou mais de seus <b>atributos</b> ferem regras de integridade
		 *                                   desta entidade na persistência de dados.
		 */
		
		/* Didático: Aqui vão regras de projeto especificamente pertinentes à persistência de dados.
		 * 
		 * A atual ferramenta de persistência deste projeto é ibernate+MySQL, a qual, durante execução do 'merge()'
		 * assume uma entidade com 'id=null' sendo inclusão de novo registro, e assume uma entidade com 'id!=null' sendo
		 * atualização de um registro já existente, no entanto, se 'id=X', onde 'X'=número-não-nulo-ainda-não-registrado
		 * então Hibernate assume a entidade como um novo registro a ser adicionado (tal como se fosse null), já que não
		 * pode atualizar algo inexistente, e Hibernate é construido para atender também as entidades
		 * não-auto-incrementais. Apesar da assertividade do Hibernate com modelos de base de dados
		 * não-auto-incrementais, este projeto especificamente para atender suas próprias regras de negócio e
		 * integridade e dados, retorna 'EXCEPTION' na situação citada sobre 'id=X', onde
		 * 'X'=número-não-nulo-ainda-não-registrado. Sendo assim, pela visão de que nem desenvolvedor nem o software
		 * devem assumir a autonomia/responsabilidade de decidir mudar a operação de 'atualizar' para 'criar novo'
		 * frente ao usuário final da API que é o proprietário desta decisão e, sobretudo tal mudança de operação pode
		 * acarretar erro grave nos resultados de uma tarefa essencial proposta pela API: registrar na entidade, um
		 * objeto igual e sobressalente para o mesmo usuário-consumidor da API sem que o mesmo tenha essa intenção, ou
		 * até mesmo nem fique ciente do fato. Enfim, analogamente a API estaria recebendo comando 'atualizar' (o qual o
		 * usuário está esperando que assim seja executado ou retorno erro com instruções) porém a API estaria
		 * executando um comando que, nem se quer, foi modelado... um suposto 'salvar_como'.
		 * 
		 * Dado contexto descrito acima, assume-se neste projeto, e neste escopo 'repository' a seginte regra:
		 * ---- Esta é a camada mais próxima e relacionada à persistência de dados então cuida da questão;
		 * ---- Verificar se existe o registro que representa o objeto-argumento passado,
		 * -------- Se existe merge();
		 * -------- Se não existe lançar new EntidadeNaoPersistidaException(); */
		
		try {
			
			if (cozinha instanceof Cozinha) {
				return manager.merge(cozinha);
			}
			else {
				throw new IllegalArgumentException();
			}
			
			/* Didátido: Este método pega um objeto Cozinha como argumento e faz sua persistência no
			 * repositorio/storage, ao mesmo tempo que retorna o objeto persistido, ou seja, o argumento passado possui
			 * o atributo 'id' nulo, pois não passou pelo banco de dados que é o responsável por gera-lo neste projeto
			 * (devido ao auto-increment), enquanto o objeto Cozinha retornado veio do banco de dado, já persistido,
			 * então ele possui 'id' != null. */
		}
		catch (IllegalArgumentException excep) {
			throw new ArgumentoIlegalException("\n"
					+ "Entidade:\tCozinha;\n"
					+ "Operação:\t.gravar(cozinha);\n"
					+ "Status:\t\tFalho;\n"
					+ "Causa:\t\tA instância do recurso representado por 'cozinha.id=" + cozinha.getId() + "' não está"
					+ "em estado 'managed', ou a instância não é uma entidade deste contexto de persistência;\n"
					+ "Sugestões:\tVerifique se o valor do Objeto.atributo 'cozinha.id = " + cozinha.getId() + "' está"
					+ " correto, ou considere adiocioná-lo à base de dados antes de atualizá-lo.");
		}
		catch (PersistenceException excep) {
			/* DOUBT Há diferença entre de implementar esses 2 tratamentos execpts ?:
			 * java.sql.SQLIntegrityConstraintViolationException
			 * org.hibernate.exception.ConstraintViolationException */
			throw new PercistenciaException("\n"
					+ "Entidade:\tCozinha;\n"
					+ "Operação:\t.gravar(restaurante);\n"
					+ "Status:\t\tFalho;\n"
					+ "Causa:\t\tA instância passada como argumento desta operação é instância de 'tipo' diferente do"
					+ " objeto esperado, ou, um ou mais de seus atributos ferem regras de integridade desta entidade"
					+ " junto à base de dados. Possivelmente 'valores nulos inaceitáveis';\n"
					+ "Sugestões:\tVerifique se o objeto passado em 'BodyResquest' possui 'tipo' adequado, ou se seus"
					+ " atributos possuem valores corretos.");
		}
		
	}
	
	
	
	@Override
	public List<Cozinha> listar() {
		/**
		 * Recupera os dados de todas entidades 'Cozinha' de sua respectiva entidade no atual contexto de persistência.
		 * @return                          uma lista de objetos 'Cozinha' em estado 'managed';
		 * @throws IllegalArgumentException if the query string is found to be invalid or if the query result is found
		 *                                  to not be assignable to the specified type
		 * @throws QueryTimeoutException    if...// descrição de throws...
		 * @throws LockTimeoutException     if...// descrição de throws...
		 * @throws PessimisticLockException if...// descrição de throws...
		 * @throws PersistenceException     if...// descrição de throws...
		 */
		
		TypedQuery<Cozinha> tQueryCozinha = null;
		
		try {
			/* Lembrete didátido: Graças ao Hibernate e JPA aqui usamos a JPQL "from Cozinha" para criar uma 'Query' no
			 * onjeto 'manager' que resultará numa 'ResultList' de 'Cozinha.class'. */
			tQueryCozinha = manager.createQuery("from Cozinha", Cozinha.class);
			return tQueryCozinha.getResultList();
			// Ou simplesmente: return manager.createQuery("form Cozinha",Cozinha.class).getResultList();
		}
		catch (IllegalArgumentException excep) { // De: .createQuery().
			throw new ArgumentoIlegalException(
					"\tEntidade:\tCozinha;\n"
							+ "\tMétodo:\t\t.listar()\n"
							+ "\tStatus:\t\tFalho;\n"
							+ "\tCausa:\t\tA string 'fromCozinha' e o tipo 'Cozinha.class' estão conflitantes;\n"
							/* DOUBT testar se existe tQueryCozinha.getParameterValue("qlString"). */
							+ "\tSugestões:\tContade desenvolvedor do projeto. Este é um erro interno com parametros"
							+ "definidos diretamente em código fonte. Não acessíveis/opcionais ao consumidor da API.");
		}
		catch (QueryTimeoutException excep) { // De: .getResultList().
			/* if the query execution exceeds the query timeout value set and only the statement (within this query) is
			 * rolled back, not the whole transaction. */
			// TODO Tratamento da exception retornando uma exception criada neste projeto, em português.
			return null;
		}
		catch (LockTimeoutException excep) { // De: .getResultList().
			/* if pessimistic locking fails and only the statement is rolled back */
			// TODO Tratamento da exception retornando uma exception criada neste projeto, em português.
			return null;
		}
		catch (PessimisticLockException excep) { // De: .getResultList().
			/* if pessimistic locking fails and the transaction is rolled back */
			// TODO Tratamento da exception retornando uma exception criada neste projeto, em português.
			return null;
		}
		catch (PersistenceException excep) { // De: .getResultList().
			/* if the query execution exceeds the query timeout value set and the transaction is rolled back */
			// TODO Tratamento da exception retornando uma exception criada neste projeto, em português.
			return null;
		}
		
	}
	
	
	
	@Override
	public Cozinha pegar(Long id) {
		/**
		 * Recupera os dados de uma entidade <b>Cozinha</b> de sua respectiva entidade no atual contexto de
		 * persistência.
		 * @param   id                       número inteiro-longo representando chave-primária na entidade de
		 *                                   persistência;
		 * @throws  ArgumentoIlegalException se argumento passado 'id' não for de mesmo tipo que a chave primária
		 *                                   da entidade buscada, neste caso o tipo 'Long';
		 * @returns                          uma instância em estado <i>managed</i> da entidade <b>Cozinha</b>, se
		 *                                   houver uma, cuja o valor de seu atributo <b>id</b> (chave-primária)
		 *                                   tenha o mesmo valor que o <b><i>param</i> id</b>, caso não haja
		 *                                   registro com atributo <b>id</b> de valor procurado, retorna
		 *                                   <b><i>null</i></b>;
		 * @see                              EntityManager.find().
		 */
		Cozinha cozinha;
		
		try {
			cozinha = manager.find(Cozinha.class, id);
			return cozinha;
		}
		catch (IllegalArgumentException excep) {
			throw new ArgumentoIlegalException(
					"\tEntidade:\tCozinha;\n"
							+ "\tMétodo:\t\t.pegar(id)\n"
							+ "\tStatus:\t\tFalho;\n"
							+ "\tCausa:\t\tO valor passado como argumento em 'id=" + id + "' não é convertível em"
							+ " tipo 'Long';\n"
							+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto, ou"
							+ " considere usar método '.listar()'.");
		}
		
	}
	
	
	
	@Transactional
	@Override
	public Cozinha remover(Long id) {
		/**
		 * Remove os dados de uma entidade <b>Cozinha</b> de sua respectiva entidade no atual contexto de persistência.
		 * @param   id                              número inteiro-longo representando chave-primária na entidade de
		 *                                          persistência;
		 * @returns                                 uma cópia da instância <b>removida</b> da entidade <b>Cozinha</b>,
		 *                                          em
		 *                                          estado <i>detached</i>,
		 *                                          se houver uma, cuja o valor de seu atributo <b>id</b>
		 *                                          (chave-primária)
		 *                                          tenha o mesmo valor que
		 *                                          o <b><i>param</i> id</b>;
		 * @throws  EmptyResultDataAccessException  se a entidade da base de dados está vazia, sem registros para ser
		 *                                          removido;
		 * @throws  DataIntegrityViolationException se a ação violar alguma restrição (regra) de integridade de dados
		 *                                          declarada na base de dados para esta entidade em operação.
		 */
		
		/* Se apenas der manager.remove(cozinha); aqui nesta linha do método, teremos exception, pois aquele objeto
		 * cozinha que criamos e passamos como argumento, oviamente está em estado 'TRANSIENT' pois acabamos de
		 * instaciar sem passar pelo HIBERNATE. Sendo assim, primeiro buscaremos aquele mesmo objeto, passado como
		 * argumento no método, na base de dados, para que ele venha no estado 'MANAGED', então poderemos removê-lo. */
		try {
			Cozinha cozinha = pegar(id);
			
			if (cozinha != null) {
				manager.remove(cozinha);
				return cozinha;
			}
			else {
				throw new EmptyResultDataAccessException(
						"Recursos disponíveis em quantidade MENOR que mínima esperada. ", 1);
			}
			
		}
		catch (DataIntegrityViolationException excep) {
			throw new DataIntegrityViolationException(
					String.format("Violação de integridade ao remover Cozinha[id='%d']. Possível 'ForeignKey'. ",
							id));
		}
		
	}
}
