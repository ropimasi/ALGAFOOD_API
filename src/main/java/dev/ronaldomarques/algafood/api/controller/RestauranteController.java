/* Copyright notes... */
package dev.ronaldomarques.algafood.api.controller;


import static dev.ronaldomarques.algafood.infrastructure.exception.DescritorDeException.descreverExcecao;
import static dev.ronaldomarques.algafood.infrastructure.exception.DescritorDeException.descreverInesperadaException;
import static dev.ronaldomarques.algafood.infrastructure.service.MescladorAtributos.mesclarAtributos;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.ronaldomarques.algafood.domain.exception.EntidadeEmUsoException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoPersistidaException;
import dev.ronaldomarques.algafood.domain.model.entity.RestauranteEntity;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;
import dev.ronaldomarques.algafood.domain.service.RestauranteCadastroService;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;
import dev.ronaldomarques.algafood.infrastructure.exception.PercistenciaException;



/**
 * This is a simple didadic project. A RESTful-API built with on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    CozinhaController, CidadeController, EstadoController, FormaPagamentoController, PermissaoController...
 *         // TODO Terminar de listar demais 'controllers' na Javadocs tag 'see'.
 * @since  2020-09-09.
 */

@RestController // @Controller + @ResponseBody + Outras...
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepo;
	
	@Autowired
	private RestauranteCadastroService restauranteCadastroServ;
	
	
	
	@PostMapping
	/* Didático: @ResponseStatus(HttpStatus.CREATED) esta notação pode ser colocada para definir o status padrão no caso
	 * de método realizado com sucesso, porém, o método não pode possuir outras possibilidades de status já que esta
	 * notação sobrescreve outros status vindos pelos 'ResponseEntity' or 'redirect'. */
	public ResponseEntity<?> adicionar(@RequestBody RestauranteEntity restauranteEntity) {
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(restauranteCadastroServ.salvar(restauranteEntity));
		}
		catch (EntidadeNaoEncontradaException excep) { // De: .salvar().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excep.getMessage());
		}
		catch (EntidadeNaoPersistidaException excep) { // De: .salvar() <- .gravar().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excep.getMessage());
		}
		catch (ArgumentoIlegalException excep) { // De: .salvar() <- .gravar() <- .merge().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (PercistenciaException excep) { // De: .salvar() <- .gravar() <- .merge().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (Exception excep) { // De: origem inesperada.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(excep.getMessage());
		}
		
	}
	
	
	
	@GetMapping
	public ResponseEntity<?> listar() {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(restauranteRepo.findAll());
		}
		catch (ArgumentoIlegalException excep) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (PercistenciaException excep) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		
		var tmpReturnRestaurante = new RestauranteEntity();
		
		try {
			tmpReturnRestaurante = restauranteRepo.findById(id)
					.orElseThrow(() -> new EntidadeNaoEncontradaException(
							"\tEntidade:\tRestaurante;\n"
									+ "\tMétodo:\t\t.findbyId(id);\n"
									+ "\tStatus:\t\tFalho;\n"
									+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
									+ " de valor igual o argumento passado 'id=" + id + "';\n"
									+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id
									+ "' é correto,"
									+ " ou considere adicionar o registro com tal chave primária antes de buscá-lo."));
		}
		catch (EntidadeNaoEncontradaException excep) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(descreverExcecao(excep));
		}
		catch (ArgumentoIlegalException excep) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(descreverInesperadaException(excep));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(tmpReturnRestaurante);
		
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody RestauranteEntity restauranteNovo) {
		
		/**
		 * Atualiza a entidade 'RestauranteEntity' que possui valor 'id' em seu atributo 'id' na base de dados, salvando
		 * a nova entidade 'RestauranteEntity' representada por 'restauranteNovo' em seu lugar.
		 */
		
		var restauranteAtual = new RestauranteEntity();
		
		try {
			restauranteAtual = restauranteRepo.findById(id)
					.orElseThrow(() -> new EntidadeNaoEncontradaException(
							"\tEntidade:\tRestaurante;\n"
									+ "\tMétodo:\t\t.atualizar(id);\n"
									+ "\tStatus:\t\tFalho;\n"
									+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
									+ " de valor igual o argumento passado 'id=" + id + "';\n"
									+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id
									+ "' é correto,"
									+ " ou considere adicionar o registro com tal chave primária antes de atualizá-lo."));
		}
		catch (EntidadeNaoEncontradaException excep) { // De: .buscar() <- .find()
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(descreverExcecao(excep));
		}
		catch (PercistenciaException excep) { // De: .salvar() <- .gravar() <- .merge().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (ArgumentoIlegalException excep) { // De: .salvar() <- .gravar() <- .merge().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (Exception excep) { // De: origem inesperada.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					descreverInesperadaException(excep));
		}
		
		restauranteNovo.setId(restauranteAtual.getId());
		return ResponseEntity.status(HttpStatus.OK).body(restauranteCadastroServ.salvar(restauranteNovo));
		
	}
	
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id,
			@RequestBody Map<String, Object> atributosValores) {
		
		/**
		 * Atualiza parcialmente, na entidade 'RestauranteEntity', aquele objeto que possui seu atributo
		 * 'RestauranteEntity.id' com
		 * valor do argumento 'id', salvando os novos atributos passados no corpo da requisição e representados pelo
		 * 'Map atributosValores' sobre seus atributos.
		 */
		
		var restauranteAtual = new RestauranteEntity();
		
		try {
			restauranteAtual = restauranteRepo.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
					"\tEntidade:\tRestaurante;\n"
							+ "\tMétodo:\t\t.atualizarParcial(id);\n"
							+ "\tStatus:\t\tFalho;\n"
							+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
							+ " de valor igual o argumento passado 'id=" + id + "';\n"
							+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto,"
							+ " ou considere adicionar o registro com tal chave primária antes de atualizá-lo."));
		}
		catch (EntidadeNaoEncontradaException excep) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(descreverExcecao(excep));
		}
		catch (ArgumentoIlegalException excep) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(descreverInesperadaException(excep));
		}
		
		mesclarAtributos(atributosValores, restauranteAtual);
		return atualizar(id, restauranteAtual); // Isto fara: return ResponseEntity.ok(restauranteAtual).body()...etc;
		
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		try {
			if (restauranteCadastroServ.excluir(id) != null) return ResponseEntity.noContent().build();
			else return ResponseEntity.notFound().build();
		}
		catch (EntidadeNaoEncontradaException excep) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(descreverExcecao(excep));
		}
		catch (EntidadeEmUsoException excep) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(descreverExcecao(excep));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					descreverInesperadaException(excep));
		}
		
	}
	
}
