package dev.ronaldomarques.algafood.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import dev.ronaldomarques.algafood.domain.exception.EntidadeEmUsoException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.model.entity.CidadeEntity;
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;



@Service
public class CidadeCadastroService {
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	
	
	public CidadeEntity salvar(CidadeEntity cidadeNova) {
		/**
		 * Aplica regras de negócio e salva a insancia 'estado' na base de dados, como novo registro se seu
		 * atributo 'cidade.id' é nulo, ou como atualização se seu atributo 'cidade.id' possui valor.
		 * @throws EntidadeNaoPersistidaException se atributo 'id' possui valor não-nulo porém inexistente na entidade
		 *                                        'EstadoEntity' persistida.
		 * @throws ArgumentoIlegalException       argumento não é uma instância em estado 'managed',
		 *                                        ou não é instância de objeto.
		 */
		
		/* Didático: Aqui vão as regras de negócio. Tais como condições obrigatórias, validações. */
		
		try {
			/* Regra de negócio: para salvar um objeto 'cidade' é obrigatório atribuir ao atributo 'estado' do objeto
			 * 'cidade' um estado existente na DB. */
			EstadoEntity estadoEntity = estadoRepo.findById(cidadeNova.getEstado().getId())
					.orElseThrow(() -> new EmptyResultDataAccessException(
							"estadoRepo.findById(cidade.getEstado().getId()) = EstadoEntity não encontrado. Ou cidade está"
									+ " sem estado. Só pode salvar com estado existente no DB.",
							1));
			
			cidadeNova.setEstado(estadoEntity);
			
			return cidadeRepo.save(cidadeNova);
		}
		catch (IllegalArgumentException excep) { // Somente se chamada pela Controller.atualizar().
			/* Até o presente momento, não há regras de negócio aplicáveis neste escopo (service) para sanar de forma
			 * programática as possíveis EXCEPTIONS, sendo assim, as mesmas serão repassadas ao escopo superior
			 * (controller), porém, traduzidas, para melhor exepriência do usuários consumidor da API. */
			throw new ArgumentoIlegalException(
					"Entidade cidade com atributo 'id=" + cidadeNova.getId()
							+ "' passada como argumento em 'cadastroService.salva()' não está em estado 'managed'.\n");
		}
		
	}
	
	
	
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
