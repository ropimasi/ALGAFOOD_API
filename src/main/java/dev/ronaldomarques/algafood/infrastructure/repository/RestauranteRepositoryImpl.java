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
import dev.ronaldomarques.algafood.domain.model.entity.Cozinha;
import dev.ronaldomarques.algafood.domain.model.entity.Restaurante;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;
import dev.ronaldomarques.algafood.infrastructure.exception.PercistenciaException;




@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {
	@PersistenceContext
	private EntityManager manager;
	
	
	
	@Transactional
	@Override
	public Restaurante gravar(Restaurante restaurante) {
		/**
		 * Persistir o objeto-argumento 'restaurante' na base de dados como novo registro se seu atributo 'id' é nulo,
		 * ou como atualização se seu atributo 'id' possui valor.
		 * @throws EntidadeNaoPersistidaException na operação de atualiazção, se atributo 'id' do argumento
		 *                                        'restaurante' não está persistido na base de dados;
		 * @throws TransactionRequiredException   método chamador não está em modo 'Transactional';
		 * @see                                   EntityManager.merge().
		 */
		
		/* Aqui vão regras de projeto especificamente pertinentes à persistência de dados.
		 * 
		 * Minha ferramenta de persistência (Hibernate+MySQL), a qual, durante execução do 'merge()' assume uma
		 * entidade com 'id=null' sendo inclusão de novo registro, e assume uma entidade com 'id<>null' sendo
		 * atualização de um registro já existente, no entanto, se 'id=X', onde 'X'=número-não-nulo-ainda-não-registrado
		 * então Hibernate assume a entidade como um novo registro (tal como se fosse null), já que não pode atualizar
		 * algo inexistente, e Hibernate é construido para atender também as entidades não-auto-incrementais. Apesar da
		 * assertividade do Hibernate, especificamente para as regras de negócio e integridade e dados deste projeto,
		 * seria desejável que Hibernante retornasse 'EXCEPTION' neste caso. Sendo assim, pela visão de que nem
		 * desenvolvedor nem o software devem assumir a autonomia/responsabilidade de decidir mudar a operação de
		 * 'atualizar' para 'criar novo' frente ao usuário final da API que é o proprietário desta decisão, sobretudo
		 * tal mudança de operação pode acarretar erro grave nos resultados de uma tarefa essencial proposta na API:
		 * cadastrar restaurante sobressalente igual, semelhante, ou diferente para um mesmo
		 * usuário-consumidor-restaurante da API sem que o mesmo tenha essa intenção, ou até mesmo nem fique ciente do
		 * fato. Enfim, analogamente a API estaria recebendo comando 'atualizar' (o qual o usuário está esperando que
		 * assim seja) porém a API estaria executando um comando que, nem se quer, foi modelado... um suposto
		 * 'salvar_como'.
		 * 
		 * Dado contexto descrito acima, o assumo neste projeto, e neste escopo 'repository' a seginte regra:
		 * ---- Esta é a camada mais próxima e relacionada à persistência de dados então cuida da questão;
		 * ---- Verificar se existe o registro que representa o argumento.atributo passado 'restaurante.id' ,
		 * -------- Se existe merge();
		 * -------- Se não existe lançar new EntidadeNaoPersistidaException(); */
		
		
		
		try {
			
			if (restaurante instanceof Restaurante) {
				return manager.merge(restaurante);
			}
			else {
				throw new IllegalArgumentException();
			}
			
			/* Didátido: Este método pega um objeto Restaurante como argumento e faz sua persistência no
			 * repositorio/storage, ao mesmo tempo que retorna o objeto persistido, ou seja, o argumento passado possui
			 * o atributo 'id' nulo, pois não passou pelo banco de dados que é o responsável por gera-lo neste projeto
			 * (devido ao auto-increment), enquanto o objeto Restaurante retornado veio do banco de dado, já persistido,
			 * então ele possui 'id' != null. */
		}
		catch (IllegalArgumentException excep) {
			throw new ArgumentoIlegalException("\n"
					+ "Entidade:\tRestaurante;\n"
					+ "Operação:\t.gravar(restaurante);\n"
					+ "Status:\t\tFalho;\n"
					+ "Causa:\t\tA instância do recurso representado por 'estado.id=" + restaurante.getId() + "' não está"
					+ "em estado 'managed', ou a instância não é uma entidade deste contexto de persistência;\n"
					+ "Sugestões:\tVerifique se o valor do Objeto.atributo 'estado.id = " + restaurante.getId() + "' está"
					+ " correto, ou considere adiocioná-lo à base de dados antes de atualizá-lo.");
		}
		catch (PersistenceException excep) {
			/* DOUBT Há diferença entre de implementar esses 2 tratamentos execpts ?:
			 * java.sql.SQLIntegrityConstraintViolationException
			 * org.hibernate.exception.ConstraintViolationException */
			throw new PercistenciaException("\n"
					+ "Entidade:\tRestaurante;\n"
					+ "Operação:\t.gravar(restaurante);\n"
					+ "Status:\t\tFalho;\n"
					+ "Causa:\t\tA instância passada como argumento desta operação é instância de 'tipo' diferente do"
					+ " objeto esperado, ou, um ou mais de seus atributos ferem regras de integridade desta entidade"
					+ " junto à base de dados. Possivelmente 'valores nulos inaceitáveis';\n"
					+ "Sugestões:\tVerifique se o objeto passado em 'BodyResquest' possui 'tipo' adequado, ou se seus"
					+ " atributos possuem valores corretos.");
		}
	}
	
	/* uai ? acho que havia parado a codificação aqui
	 * 
	 * 
	 * 
	 * if ((restaurante.getId() == null)
	 * || ((restaurante.getId() != null) && (manager.find(Restaurante.class, restaurante.getId()) != null))) {
	 * 
	 * try {
	 * return manager.merge(restaurante);
	 * }
	 * catch (TransactionRequiredException excep) {
	 * DOUBT: Necessário algum tipo de rollBack(); aqui ???
	 * excep.printStackTrace();
	 * System.out.println(excep.getMessage());
	 * throw new TransactionRequiredException(
	 * "Erro interno: TransactionRequiredException. Contate desenvolvedor da API.");
	 * Esta EXCEPTION é destinada somente aos desenvolvedores do projeto, por isso não necessita tradução.
	 * Como o atual método já está implementado em modo 'Transactional' (vide assinatura do método) este
	 * tratamento de exceção nunca será executado. Porém é implementado para interroper o processamento
	 * deste método sem retornar nenhum objeto (meio o qual estaria ocultando uma falha) e ajudar
	 * desenvolvedores encontrar falha em futuras modificações. Recomendo NÃO tratá-la nos níveis superiores
	 * de escopo, apenas expô-la.
	 * }
	 * 
	 * }
	 * else {
	 * throw new EntidadeNaoPersistidaException("\n"
	 * + "Entidade:\tRestaurante;\n"
	 * + "Operação:\tgravar();\n"
	 * +
	 * "Status:\t\t[FALHO] Regra técnica: Somente as entidades 'Retaurante' já persistidas na base de dados podem ser atualizadas.\n"
	 * + "Causa:\t\tIdentificador-destino [restaurante.id=" + restaurante.getId()
	 * + "] não está persistido na base de dados. Então impossível atualizá-lo.\n"
	 * + "Sugestões:\tVerifique se o valor do identificador-destino está correto,"
	 * + "ou considere adiocioná-lo à base de dados antes de atualizá-lo.\n");
	 * }
	 * 
	 * } */
	
	
	
	@Override
	public List<Restaurante> listar() {
		/**
		 * Lista todos os registros da entidade 'Restaurante' persistidas na base de dados.
		 * @returns um objeto do tipo 'List<Restaurante>'.
		 */
		return manager.createQuery("FROM Restaurante", Restaurante.class).getResultList();
	}
	
	
	
	@Override
	public Restaurante buscar(Long id) {
		/**
		 * Recupera os dados de uma entidade <b>Restaurante</b> de sua respectiva entidade no atual contexto de
		 * persistência.
		 * @param   id                             número inteiro-longo representando chave-primária na entidade de
		 *                                         persistência;
		 * @throws  EntidadeNaoEncontradaException se o valor do argumento passado 'id' é válido porém não há registro
		 *                                         na base de dados com tal valor;
		 * @throws  TipoInapropriadoException      se argumento passado 'id' não for de mesmo tipo que a chave primária
		 *                                         da entidade buscada, neste caso o tipo 'Long';
		 * @returns                                uma instância em estado <i>managed</i> da entidade
		 *                                         <b>Restaurante</b>, se houver uma, cuja o valor de seu atributo
		 *                                         <b>id</b> (chave-primária) tenha o mesmo valor que o <b><i>param</i>
		 *                                         id</b>, caso não haja registro com atributo <b>id</b> de valor
		 *                                         procurado, retorna <b><i>null</i></b>;
		 * @see                                    Manager.find().
		 */
		Restaurante restaurante;
		
		try {
			restaurante = manager.find(Restaurante.class, id);
			
			if (restaurante != null) {
				return restaurante;
			}
			else {
				throw new EntidadeNaoEncontradaException(
						"\tEntidade:\tRestaurante;\n"
								+ "\tMétodo:\t\t.buscar(id);\n"
								+ "\tStatus:\t\tFalho;\n"
								+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
								+ " de valor igual o argumento passado 'id=" + id + "';\n"
								+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto,"
								+ " ou considere adicionar o registro com tal chave primária antes de buscá-lo.");
			}
			
		}
		catch (IllegalArgumentException excep) {
			throw new ArgumentoIlegalException(
					"\tEntidade:\tRestaurante;\n"
							+ "\tMétodo:\t\t.buscar(id);\n"
							+ "\tStatus:\t\tFalho;\n"
							+ "\tCausa:\t\tO valor passado como argumento em 'id=" + id + "' não é convertível em"
							+ " tipo 'Long';\n"
							+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto, ou"
							+ " considere usar método '.listar()'.");
		}
		
	}
	
	
	
	@Transactional
	@Override
	public Restaurante remover(Long id) {
		Restaurante restaurante = buscar(id);
		
		if (restaurante == null) {
			throw new EmptyResultDataAccessException("Recursos disponíveis MENOR que quantidade mínima esperada. ", 1);
		}
		else {
			
			try {
				manager.remove(restaurante);
				return restaurante;
			}
			catch (DataIntegrityViolationException excep) {
				throw new DataIntegrityViolationException(
						String.format("Violação de integridade ao remover Restaurante[id=%d]. 'ForeignKey'. ", id));
			}
			
		}
		
	}
}
