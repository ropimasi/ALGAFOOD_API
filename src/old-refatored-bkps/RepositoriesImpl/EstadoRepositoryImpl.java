package dev.ronaldomarques.algafood.infrastructure.repository;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoPersistidaException;
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;
import dev.ronaldomarques.algafood.infrastructure.exception.PercistenciaException;




@Component
public class EstadoRepositoryImpl implements EstadoRepository {
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
	public Estado gravar(Estado objArgPersitir) {
		/**
		 * Persiste os dados do objeto-argumento <b>estado</b> dentro de sua respectiva entidade do atual contexto de
		 * persistência, como um novo registro se seu atributo <b>id</b> é nulo, ou como atualização do registro
		 * existente se seu atributo <b>id</b> possui valor:
		 * @param   estado                         instância de entidade;
		 * @returns                                uma instância em estado <b><i>managed</i></b> da entidade que foi
		 *                                         mesclada à persistência;
		 * @throws  EntidadeNaoPersistidaException se atributo 'id' possui valor não-nulo porém inexistente na entidade
		 *                                         'Estado' persistida.
		 * @throws  IllegalArgumentException       se argumento não é instancia em estado 'managed'.
		 * @throws  TransactionRequiredException   método chamador não está em modo 'Transactional'.
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
			
			if (objArgPersitir instanceof Estado) {
				
				/* As 2 condições para condições aceitáveis para persistir o objeto-argumento na persistência. */
				if ((objArgPersitir.getId() == null)
						|| ((objArgPersitir.getId() != null)
								&& (manager.find(Estado.class, objArgPersitir.getId()) != null))) {
					
					try {
						return manager.merge(objArgPersitir);
					}
					catch (TransactionRequiredException excep) {
						/* DOUBT: Necessário algum tipo de rollBack() aqui ??? */
						excep.printStackTrace();
						System.out.println(excep.getMessage());
						throw new TransactionRequiredException(
								"Erro interno: TransactionRequiredException. Contate desenvolvedor da API.");
						/* Esta EXCEPTION é destinada somente aos desenvolvedores do projeto, por isso não necessita
						 * tradução. Como o atual método já está implementado em modo 'Transactional' (vide assinatura
						 * do método) este tratamento de exceção nunca será executado, porém é implementado para
						 * interroper o processamento deste método sem retornar nenhum objeto em 'return' em vez de
						 * 'null' (meio pelo qual estaria ocultando uma falha, vide '*1' explicação posterior) e ajudar
						 * desenvolvedores encontrar possíveis falhas em possíveis futuras modificações deste algoritmo.
						 * Recomendo NÃO tratá-la nos níveis de escopo superiores, apenas expô-la.
						 * 
						 * *1: Quando ocorrer, se esta exception for ignorada, ou ocultada programaticamente pode
						 * acarretar erro grave na funcionalidade da API, já que poderá retornar um objeto 'null' sem
						 * avisos de erro concluindo os métodos chamados sem persistir o objeto passado, e
						 * usuário-consumidor da API sem notificação sobre a falha. */
					}
					
				}
				else {
					throw new EntidadeNaoPersistidaException("\n"
							+ "Entidade:\tEstado;\n"
							+ "Operação:\t.gravar();\n"
							+ "Status:\t\tFalho;"
							+ "Causa:\t\tImpossível persistir objeto na entidade em modo 'UPDATE' com chave-primária"
							+ " '" + objArgPersitir.getClass() + ".id=" + objArgPersitir.getId() + "' não existente."
							+ "Sugestões:\tVerifique se o valor do atributo 'id' está correto, ou considere adiocioná-lo"
							+ " à base de dados antes de atualizá-lo.\n");
				}
				
			}
			else {
				throw new IllegalArgumentException();
			}
			
		}
		catch (IllegalArgumentException excep) {
			throw new ArgumentoIlegalException("\n"
					+ "Entidade:\tEstado;\n"
					+ "Operação:\t.gravar();\n"
					+ "Status:\t\tFalho;\n"
					+ "Causa:\t\tA instância do recurso representado por [Estado.id=" + objArgPersitir.getId()
					+ "] não está"
					+ "em estado 'managed', ou a instância não é uma entidade deste contexto de persistência;\n"
					+ "Sugestões:\tVerifique se o valor do identificador do recurso [Estado.id = "
					+ objArgPersitir.getId() + "] está correto, ou considere adiocioná-lo à base de dados antes de"
					+ " atualizá-lo.");
		}
		catch (PersistenceException excep) {
			/* DOUBT Há diferença entre de implementar esses 2 tratamentos execpts ?:
			 * java.sql.SQLIntegrityConstraintViolationException
			 * org.hibernate.exception.ConstraintViolationException */
			throw new PercistenciaException("\n"
					+ "Entidade:\tEstado;\n"
					+ "Operação:\t.gravar();\n"
					+ "Status:\t\tFalho;\n"
					+ "Causa:\t\tA instância passada como argumento desta operação é instância de 'tipo' diferente do"
					+ " objeto esperado, ou, um ou mais de seus atributos ferem regras de integridade desta entidade"
					+ " junto à base de dados. Possivelmente 'valores nulos inaceitáveis';\n"
					+ "Sugestões:\tVerifique se o objeto passado em 'BodyResquest' possui 'tipo' adequado, ou se seus"
					+ " atributos possuem valores corretos.");
		}
		
	}
	
	
	
	@Override
	public List<Estado> listar() {
		/**
		 * Lista todos os registros da entidade 'Estado' persistidas na base de dados.
		 * @returns um objeto do tipo 'List<Estado>'.
		 */
		return manager.createQuery("FROM Estado", Estado.class).getResultList();
	}
	
	
	
	@Override
	public Estado buscar(Long id) {
		/**
		 * Busca um registro persistido na base de dados, na endidade 'Estado', com valor de chave-primária igual
		 * argumento passado 'id'.
		 * @param   id                             número inteiro-longo representando chave-primária na entidade de
		 *                                         persistência;
		 * @throws  EntidadeNaoEncontradaException se o valor do argumento passado 'id' é válido porém não há registro
		 *                                         na base de dados com tal valor;
		 * @throws  TipoInapropriadoException      se argumento passado 'id' não for de mesmo tipo que a chave primária
		 *                                         da entidade buscada, neste caso o tipo 'Long';
		 * @returns                                uma instância em estado <i>managed</i> da entidade <b>Cozinha</b>, se
		 *                                         houver uma, cuja o valor de seu atributo <b>id</b> (chave-primária)
		 *                                         tenha o mesmo valor que o <b><i>param</i> id</b>, caso não haja
		 *                                         registro com atributo <b>id</b> de valor procurado, retorna
		 *                                         <b><i>null</i></b>;
		 * @see                                    Manager.find().
		 */
		Estado estado;
		
		try {
			estado = manager.find(Estado.class, id);
			
			if (estado != null) {
				return estado;
			}
			else {
				throw new EntidadeNaoEncontradaException(
						"\tEntidade:\tEstado;\n"
								+ "\tMétodo:\t\t.buscar(id);\n"
								+ "\tStatus:\t\tFalho;\n"
								+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
								+ " de valor igual o argumento passado 'id=" + id + "';\n"
								+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto,"
								+ " ou considere adicionar o registro com tal chave primária antes de buscá-lo.\n");
			}
			
		}
		catch (IllegalArgumentException excep) {
			throw new ArgumentoIlegalException(
					"\tEntidade:\tEstado;\n"
							+ "\tMétodo:\t\t.buscar(id);\n"
							+ "\tStatus:\t\tFalho;\n"
							+ "\tCausa:\t\tO valor passado como argumento em 'id=" + id + "' não é convertível em"
							+ " tipo 'Long';\n"
							+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=\" + id + \"' é correto, ou"
							+ " considere usar método '.listar()'.\n");
		}
		
	}
	
	
	
	@Transactional
	@Override
	public Estado remover(Long id) {
		Estado estado = buscar(id);
		
		if (estado == null) {
			throw new EmptyResultDataAccessException("Nenhum recurso na base de dados para ser removido. ", 1);
		}
		else {
			
			try {
				manager.remove(estado);
				return estado;
			}
			catch (DataIntegrityViolationException excep) {
				throw new DataIntegrityViolationException(
						String.format("Violação de integridade ao remover Estado 'id=%d'. 'ForeignKey'. ", id));
			}
			
		}
		
	}
}
