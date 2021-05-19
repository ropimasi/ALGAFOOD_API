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


import static dev.ronaldomarques.algafood.infrastructure.service.MescladorAtributos.mesclarAtributos;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import dev.ronaldomarques.algafood.domain.exception.EntidadeEmUsoException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;



/**
 * A simple didadic project. An RESTful-API based on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    ...
 * @since  ...
 */

@Service
public class EstadoCadastroService {
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	
	
	public EstadoEntity salvar(EstadoEntity estadoNovo) {
		/**
		 * Aplica regras de negócio e salva a insancia 'estado' na base de dados, como novo registro se seu
		 * atributo 'estado.id' é nulo, ou como atualização se seu atributo 'estado.id' possui valor.
		 * @param  estadoNovo                     instância da entidade <b>EstatoEntity</b>;
		 * @throws EntidadeNaoEncontradaException no caso de .salvar() por atualização: se o valor do objeto.atributo
		 *                                        passado como argumento <b>estadoNova.id</b> é não-nulo porém não há
		 *                                        registro na base de dados com tal valor;
		 *                                        uma instância em estado <b><i>managed</i></b> da entidade que foi
		 *                                        salva na persistência;
		 */
		
		/* Didático: Aqui vão as regras de negócio, tais como condições obrigatórias, validações.
		 * 
		 * Regra de negócio #1: para acrescentar um novo objeto, deve ser enviado um objeto com atributo 'id' nulo para
		 * a camada de persistência, e para atualiazar, um objeto, um objeto com atributo 'id' valorado.
		 * 
		 * Regra de negócio #2: Devido à funcionalidade auto-incremento na base de dados deste projeto, somente é
		 * permitido atualizar objetos com atributo 'id' de valor já existente, nunca um valor ainda não persistido. */
		
		if (estadoNovo.getId() == null) { /* SEM ID: Objeto-argumento oriundo da 'controller.adicionar()'. */
			return estadoRepo.save(estadoNovo);
		}
		else { /* COM ID: Objeto-argumento oriundo da 'controller.atualizar()'. */
			estadoRepo.findById(estadoNovo.getId()).orElseThrow(() -> new EntidadeNaoEncontradaException(
					"\tEntidade:\tEstado;\n"
							+ "\tMétodo:\t\t.salvar(estadoNovo);\n"
							+ "\tStatus:\t\tFalho;\n"
							+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
							+ " de valor igual o argumento passado 'id=" + estadoNovo.getId() + "';\n"
							+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + estadoNovo.getId()
							+ "' é correto, ou considere adicionar o registro com tal chave primária antes de atualizá-lo."));
			
			return estadoRepo.save(estadoNovo);
		}
		
	}
	
	
	
	public EstadoEntity salvarParcial(Long id, Map<String, Object> atributosValores) {
		/**
		 * Prepara condições necessárias para a persistência dos dados do Map-argumento 'atributosValores' dentro da
		 * entidade 'EstadoEntity' do atual contexto de persistência, no registro com atributo 'id' de valor passado no
		 * argumento 'id'.
		 * @param  id                             número inteiro-longo representando chave-primária na entidade de
		 *                                        persistência;
		 * @throws EntidadeNaoEncontradaException se não for encontrado objeto persistido na entidade 'EstadoEntity'
		 *                                        com o atributo 'id' de valor igual do argumento 'id' passado;
		 * @return                                uma instância em estado <b><i>managed</i></b> da entidade que foi
		 *                                        salva na persistência;
		 */
		
		/* Didático: Aqui vão as regras de negócio, tais como condições obrigatórias, validações.
		 * 
		 * Regra de negócio #1: para acrescentar um novo objeto, deve ser enviado um objeto com atributo 'id' nulo para
		 * a camada de persistência, e para atualiazar, um objeto, um objeto com atributo 'id' valorado.
		 * 
		 * Regra de negócio #2: Devido à funcionalidade auto-incremento na base de dados deste projeto, somente é
		 * permitido atualizar objetos com atributo 'id' de valor já existente, nunca um valor ainda não persistido. */
		
		EstadoEntity estadoAtual = estadoRepo.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
				"\tEntidade:\tEstado;\n"
						+ "\tMétodo:\t\t.salvarParcial(estadoAtual, map);\n"
						+ "\tStatus:\t\tFalho;\n"
						+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
						+ " de valor igual o argumento passado 'id=" + id + "';\n"
						+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto,"
						+ " ou considere adicionar o registro com tal chave primária antes de atualizá-lo."));
		
		mesclarAtributos(atributosValores, estadoAtual);
		return salvar(estadoAtual); // Isto fara: return 'EstadoEntity';
		
	}
	
	
	
	public EstadoEntity excluir(Long id) {
		/**
		 * Exclui na base de dados o registro persistido com a chave-primária <b>id</b> de mesmo valor que o argumento
		 * <b>id<b> dentro de sua respectiva entidade do atual contexto de persistência.
		 * @param  id                             número inteiro-longo representando chave-primária na entidade de
		 *                                        persistência;
		 * @return                                uma instância em estado <b><i>removed</i></b> da entidade que foi
		 *                                        excluida na base de dados;
		 * @throws EntidadeNaoEncontradaException // TODO: esta descrição de throws...
		 * @throws EntidadeEmUsoException         // TODO: esta descrição de throws...
		 */
		
		/* Didático: Aqui vão as regras de negócio, tais como condições obrigatórias, validações. */
		
		try {
			EstadoEntity tmpReturnEstado =
					estadoRepo.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
							"Entidade com id=" + id.toString() + "não encontrada no 'estadoRepo.findById(id);'"));
			estadoRepo.deleteById(id);
			return tmpReturnEstado;
		}
		catch (EmptyResultDataAccessException excep) {
			throw new EntidadeNaoEncontradaException(
					String.format("EstadoEntity 'id=%d' não pode ser removido, pois não existe. ", id));
		}
		catch (DataIntegrityViolationException excep) {
			throw new EntidadeEmUsoException(
					String.format("EstadoEntity 'id=%d' não pode ser removido, pois está em uso. ", id));
		}
		catch (Exception excep) {
			throw new RuntimeException(
					String.format("Erro INESPERADO na exclusão EstadoEntity 'id=%d'. ", id));
		}
		
	}
	
}
