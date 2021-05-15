package dev.ronaldomarques.algafood.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import dev.ronaldomarques.algafood.domain.exception.EntidadeEmUsoException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.model.entity.Cidade;
import dev.ronaldomarques.algafood.domain.model.entity.Estado;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;




@Service
public class CidadeCadastroService {
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	
	
	public Cidade salvar(Cidade cidade) {
		/**
		 * Aplica regras de negócio e salva a insancia 'estado' na base de dados, como novo registro se seu
		 * atributo 'cidade.id' é nulo, ou como atualização se seu atributo 'cidade.id' possui valor.
		 * @throws EntidadeNaoPersistidaException se atributo 'id' possui valor não-nulo porém inexistente na entidade
		 *                                        'Estado' persistida.
		 * @throws ArgumentoIlegalException       argumento não é uma instância em estado 'managed',
		 *                                        ou não é instância de objeto.
		 */
		
		/* Didático: Aqui vão as regras de negócio. Tais como condições obrigatórias, validações. */
		
		try {
			/* Regra de negócio: para salvar um objeto 'cidade' é obrigatório atribuir ao atributo 'estado' do objeto
			 * 'cidade' um estado existente na DB. */
			Estado estado = estadoRepo.buscar(cidade.getEstado().getId());
			cidade.setEstado(estado);
			return cidadeRepo.gravar(cidade);
		}
		catch (IllegalArgumentException excep) { // Somente se chamada pela Controller.atualizar().
			/* Até o presente momento, não há regras de negócio aplicáveis neste escopo (service) para sanar de forma
			 * programática as possíveis EXCEPTIONS, sendo assim, as mesmas serão repassadas ao escopo superior
			 * (controller), porém, traduzidas, para melhor exepriência do usuários consumidor da API. */
			throw new ArgumentoIlegalException(
					"Entidade cidade com atributo 'id=" + cidade.getId()
							+ "' passada como argumento em 'cadastroService.salva()' não está em estado 'managed'.\n");
		}
		
	}
	
	
	
	public Cidade excluir(Long id) {
		
		/* Aqui vão as regras de negócio.
		 * Tais como condições obrigatórias, validações. */
		try {
			return cidadeRepo.remover(id);
		}
		catch (EmptyResultDataAccessException excep) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cidade 'id=%d' não pode ser removido, pois não existe. ", id));
		}
		catch (DataIntegrityViolationException excep) {
			throw new EntidadeEmUsoException(
					String.format("Cidade 'id=%d' não pode ser removido, pois está em uso. ", id));
		} /* catch (Exception excep) {
			 * throw new RuntimeException(
			 * String.format("Erro INESPERADO na exclusão Cidade 'id=%d'. ", id));
			 * } */
		
	}
}
