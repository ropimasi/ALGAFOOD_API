package dev.ronaldomarques.algafood.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import dev.ronaldomarques.algafood.domain.exception.EntidadeEmUsoException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;



@Service
public class EstadoCadastroService {
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	
	
	public EstadoEntity salvar(EstadoEntity estadoEntity) {
		/**
		 * Aplica regras de negócio e salva a insancia 'estado' na base de dados, como novo registro se seu
		 * atributo 'estado.id' é nulo, ou como atualização se seu atributo 'estado.id' possui valor.
		 * @throws EntidadeNaoPersistidaException se atributo 'id' possui valor não-nulo porém
		 *                                        inexistente na entidade 'EstadoEntity' persistida.
		 * @throws ArgumentoIlegalException       argumento não é uma instância em estado 'managed',
		 *                                        ou não é instância de objeto.
		 */
		
		/* Aqui vão as regras de negócio.
		 * Tais como condições obrigatórias, validações. */
		
		try {
			return estadoRepo.save(estadoEntity);
		}
		catch (IllegalArgumentException excep) {
			/* Até o presente momento, não há regras de negócio aplicáveis neste escopo (service) para sanar de forma
			 * programática as possíveis EXCEPTIONS, sendo assim, as mesmas serão repassadas ao escopo superior
			 * (controller), porém, traduzidas, para melhor exepriência do usuários consumidor da API. */
			throw new ArgumentoIlegalException(
					"Entidade passada como argumento não está em estado 'managed'.\n");
		}
		
	}
	
	
	
	public EstadoEntity excluir(Long id) {
		
		/* Aqui vão as regras de negócio.
		 * Tais como condições obrigatórias, validações. */
		try {
			EstadoEntity tmpReturnEstado =
					estadoRepo.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(id.toString()));
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
		} /* catch (Exception excep) {
			 * throw new RuntimeException(
			 * String.format("Erro INESPERADO na exclusão EstadoEntity 'id=%d'. ", id));
			 * } */
		
	}
	
}
