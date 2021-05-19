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
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoPersistidaException;
import dev.ronaldomarques.algafood.domain.model.entity.CidadeEntity;
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;



/**
 * A simple didadic project. An RESTful-API based on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    ...
 * @since  ...
 */

@Service
public class CidadeCadastroService {
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	
	
	/**
	 * Aplica regras de negócio e salva a insancia 'estado' na base de dados, como novo registro se
	 * seu atributo 'cidade.id' é nulo, ou como atualização se seu atributo 'cidade.id' possui
	 * valor.
	 * @param  cidadeNova                     // TODO:
	 * @throws EntidadeNaoPersistidaException se atributo 'id' possui valor não-nulo porém
	 *                                        inexistente na entidade
	 *                                        'EstadoEntity' persistida.
	 * @throws ArgumentoIlegalException       argumento não é uma instância em estado 'managed',
	 *                                        ou não é instância de objeto.
	 * @return                                // TODO:
	 */
	public CidadeEntity salvar(CidadeEntity cidadeNova) {
		/* Didático: Aqui vão as regras de negócio. Tais como condições obrigatórias, validações.
		 * 
		 * Regra de negócio #1: para salvar um objeto 'CidadeEntity' é obrigatório atribuir a seu
		 * atributo 'estado' um
		 * objeto 'EstadoEntity' existente na DB. */
		
		try {
			/* Até o presente momento, não há regras de negócio aplicáveis neste escopo (service)
			 * para sanar de forma
			 * programática as possíveis EXCEPTIONS, sendo assim, as mesmas serão repassadas ao
			 * escopo superior
			 * (controller), porém, traduzidas, para melhor exepriência do usuários consumidor da
			 * API. */
			EstadoEntity estado = estadoRepo.findById(cidadeNova.getEstado().getId())
					.orElseThrow(() -> new EmptyResultDataAccessException(
							"estadoRepo.findById(cidade.getEstado().getId()) = EstadoEntity não encontrado. Ou cidade"
									+ " está sem estado. Só pode salvar com estado existente no DB.",
							1));
			
			cidadeNova.setEstado(estado);
			
			return cidadeRepo.save(cidadeNova);
		}
		catch (IllegalArgumentException excep) { // Somente se chamada pela Controller.atualizar().
			throw new ArgumentoIlegalException(
					"Entidade cidade com atributo 'id=" + cidadeNova.getId()
							+ "' passada como argumento em 'cadastroService.salvar()' não está em estado 'managed'.\n");
		}
		catch (EntityNotFoundException excep) { // De: .save().
			throw new EntidadeNaoEncontradaException("\n"
					+ "Entidade:\tRestaurante;\n"
					+ "Operação:\t'atribuir';\n"
					+ "Status:\t\tFalhou! Regra de negócio: Como atributo de 'Cidades' somente as entidades"
					+ " 'EstadoEntity' previamente existente na base de dados podem ser atribuidas.\n"
					+ "Causa:\t\tObjeto com identificador 'cidade.estado.id = " + cidadeNova.getEstado().getId()
					+ "' não encontrado na base de dados. \n"
					+ "Sugestões:\tVerifique se o valor do identificador está correto, ou considere adiocioná-lo à base"
					+ " de dados antes de atribuí-lo.\n");
		}
		
	}
	
	
	
	/**
	 * Descrição
	 * @param  id //TODO:
	 * @return    //TODO:
	 */
	public CidadeEntity excluir(Long id) {
		
		/* Aqui vão as regras de negócio.
		 * Tais como condições obrigatórias, validações. */
		
		try {
			CidadeEntity tmpReturnCidade =
					cidadeRepo.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
							"Entidade com id=" + id.toString() + "não encontrada no 'cidadeRepo.findById(id);'"));
			cidadeRepo.deleteById(id);
			return tmpReturnCidade;
		}
		catch (EmptyResultDataAccessException excep) {
			throw new EntidadeNaoEncontradaException(
					String.format("CidadeEntity 'id=%d' não pode ser removido, pois não existe. ", id));
		}
		catch (DataIntegrityViolationException excep) {
			throw new EntidadeEmUsoException(
					String.format("CidadeEntity 'id=%d' não pode ser removido, pois está em uso. ", id));
		}
		catch (Exception excep) {
			throw new RuntimeException(
					String.format("Erro INESPERADO na exclusão CidadeEntity 'id=%d'. ", id));
		}
		
	}
	
}
