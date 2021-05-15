package dev.ronaldomarques.algafood.infrastructure.repository;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoPersistidaException;
import dev.ronaldomarques.algafood.domain.model.entity.CidadeEntity;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;




@Component
public class CidadeRepositoryImpl implements CidadeRepository {
	@PersistenceContext
	private EntityManager manager;
	
	
	
	@Transactional
	@Override
	public Cidade gravar(Cidade cidade) {
		/**
		 * Persistir o objeto-argumento 'cidade' na base de dados como novo registro se seu atributo 'id' é nulo,
		 * ou como atualização se seu atributo 'id' possui valor.
		 * @throws EntidadeNaoPersistidaException se atributo 'id' possui valor não-nulo porém
		 *                                        inexistente na entidade 'Estado' persistida.
		 * @throws IllegalArgumentException       se argumento não é instancia em estado 'managed'.
		 * @throws TransactionRequiredException   método chamador não está em modo 'Transactional'.
		 */
		
		/* Aqui vão regras de projeto especificamente pertinentes à persistência de dados.
		 * 
		 * Minha ferramenta de persistência (Hibernate+MySQL), a qual, durante execução do 'merge()' assume uma
		 * entidade com 'id=null' sendo inclusão de novo registro, e assume uma entidade com 'id!=null' sendo
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
		 * ---- Verificar se existe o registro que representa o argumento.atributo passado 'cidade.id' ,
		 * -------- Se existe merge();
		 * -------- Se não existe lançar new EntidadeNaoPersistidaException(); */
		
		if ((cidade.getId() == null)
				|| ((cidade.getId() != null) && (manager.find(Cidade.class, cidade.getId()) != null))) {
			
			try {
				return manager.merge(cidade);
			}
			catch (TransactionRequiredException excep) {
				/* #DOUBT Necessário algum tipo de rollBack(); aqui ??? */
				excep.printStackTrace();
				System.out.println(excep.getMessage());
				throw new TransactionRequiredException(
						"Erro interno: TransactionRequiredException. Contate desenvolvedor da API.");
				/* Esta EXCEPTION é destinada somente aos desenvolvedores do projeto, por isso não necessita tradução.
				 * Como o atual método já está implementado em modo 'Transactional' (vide assinatura do método) este
				 * tratamento de exceção nunca será executado. Porém é implementado para interroper o processamento
				 * deste método sem retornar nenhum objeto (meio o qual estaria ocultando uma falha) e ajudar
				 * desenvolvedores encontrar falha em futuras modificações. Recomendo NÃO tratá-la nos níveis superiores
				 * de escopo, apenas expô-la. */
			}
			
		}
		else {
			throw new EntidadeNaoPersistidaException("\n"
					+ "Entidade:\tCidade;\n"
					+ "Operação:\tgravar();\n"
					+ "Status:\t\t'FALHO';  Regra técnica: Somente as entidades 'Cidade' já persistidas na base de dados podem ser atualizadas.\n"
					+ "Causa:\t\tIdentificador-destino 'cidade.id=" + cidade.getId()
					+ "' não está persistido na base de dados. Então impossível atualizá-lo.\n"
					+ "Sugestões:\tVerifique se o valor do identificador-destino está correto,"
					+ " ou considere adiocioná-lo à base de dados antes de atualizá-lo.\n");
		}
		
	}
	
	
	
	@Override
	public List<Cidade> listar() {
		/**
		 * Lista todos os registros da entidade 'Estado' persistidas na base de dados.
		 * @returns um objeto do tipo 'List<Cidade>'.
		 */
		return manager.createQuery("FROM Cidade", Cidade.class).getResultList();
	}
	
	
	
	@Override
	public Cidade buscar(Long id) {
		/**
		 * Busca um registro persistido na base de dados, na endidade 'Cidade', com valor de chave-primária igual
		 * argumento passado 'id'.
		 * @throws  TypeMismatchException          se argumento passado 'id' não for de mesmo tipo que a chave primária
		 *                                         da entidade buscada, neste caso o tipo 'Long'.
		 * @throws  EntidadeNaoEncontradaException se o valor do argumento passado 'id' é válido porém não há registro
		 *                                         na base de dados com tal valor.
		 * @returns                                uma instância da entidade 'Cidade' persistida na base de dados se
		 *                                         houver, caso contrário EntidadeNaoEncontradaException.
		 */
		Cidade cidade;
		
		try {
			cidade = manager.find(Cidade.class, id);
			
			if (cidade != null) {
				return cidade;
			}
			else {
				throw new EntidadeNaoEncontradaException("\n"
						+ "Entidade:\tCidade;\n"
						+ "Método:\t\t.buscar(id);\n"
						+ "Status:\t\t'FALHO';\n"
						+ "Causa:\t\tNão há registro na base de dados, nesta entidade, com chave-primária de valor"
						+ " igual o argumento passado 'id=" + id + "';\n"
						+ "Sugestões:\tVerifique se o valor do argumento passado em 'id=" + id + "' está correto,"
						+ " ou considere adicionar o registro com tal chave primária antes de buscá-la.\n");
			}
			
		}
		catch (IllegalArgumentException excep) {
			throw new ArgumentoIlegalException("\n"
					+ "Entidade:\tCidade;\n"
					+ "Método:\t\t.buscar(id);\n"
					+ "Status:\t\t'FALHO' Regra técnica: esta 'entidade.método()' exige o argumento 'id' sendo número"
					+ " tipo 'Long';\n"
					+ "Causa:\t\tO valor passado como argumento em 'id=" + id + "' não é convertível em tipo 'Long';\n"
					+ "Sugestões:\tVerifique se o valor do argumento passado em 'id' está correto, ou considere usar"
					+ " método '.listar()'.\n");
		}
		
	}
	
	
	
	@Transactional
	@Override
	public Cidade remover(Long id) {
		Cidade cidade = buscar(id);
		
		if (cidade == null) {
			throw new EmptyResultDataAccessException("Nenhum recurso na base de dados para ser removido. ", 1);
		}
		else {
			
			try {
				manager.remove(cidade);
				return cidade;
			}
			catch (DataIntegrityViolationException excep) {
				throw new DataIntegrityViolationException(
						String.format("Violação de integridade ao remover Cidade 'id=%d'. 'ForeignKey'. ", id));
			}
			
		}
		
	}
}

//
//
//
//
//
//
