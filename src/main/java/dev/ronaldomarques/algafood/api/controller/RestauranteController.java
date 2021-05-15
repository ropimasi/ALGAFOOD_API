/* Copyright notes... */
package dev.ronaldomarques.algafood.api.controller;


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
import dev.ronaldomarques.algafood.domain.model.entity.Restaurante;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;
import dev.ronaldomarques.algafood.domain.service.RestauranteCadastroService;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;
import dev.ronaldomarques.algafood.infrastructure.exception.PercistenciaException;
import static dev.ronaldomarques.algafood.infrastructure.exception.DescritorDeException.*;
import static dev.ronaldomarques.algafood.infrastructure.service.MescladorAtributos.mesclarAtributos;




/**
 * This is a simple didadic project. A RESTful-API built with on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    CozinhaController, CidadeController, EstadoController, FormaPagamentoController, PermissaoController...
 *         // TODO Terminar de listar demais 'controllers' na Javadocs tag 'see'.
 * @since  2020-09-09.
 */

@RestController // @Controller + @ResponseBody + Outras...
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestauranteController {
	@Autowired
	private RestauranteRepository restauranteRepo;
	
	@Autowired
	private RestauranteCadastroService restauranteCadastroServ;
	
	
	
	@PostMapping
	/* Didático: @ResponseStatus(HttpStatus.CREATED) esta notação pode ser colocada para definir o status padrão no caso
	 * de método realizado com sucesso, porém, o método não pode possuir outras possibilidades de status já que esta
	 * notação sobrescreve outros status vindos pelos 'ResponseEntity' or 'redirect'. */
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(restauranteCadastroServ.salvar(restaurante));
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
			return ResponseEntity.status(HttpStatus.OK).body(restauranteRepo.listar());
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
		
		try {
			Restaurante restaurante = restauranteRepo.buscar(id);
			
			if (restaurante != null) {
				return ResponseEntity.status(HttpStatus.OK).body(restaurante);
			}
			else {
				/* Didático: Abordagem alterniativa de tratar como uma EXCEPTION para padronizar as mensagens de erro e
				 * retorno para o consumidor da API.
				 * 
				 * return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); */
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
		
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restauranteNovo) {
		
		/**
		 * Atualiza a entidade 'Restaurante' que possui valor 'id' em seu atributo 'id' na base de dados, salvando a
		 * nova entidade 'Restaurante' representada por 'restauranteNovo' em seu lugar.
		 */
		try {
			Restaurante restauranteAtual = restauranteRepo.buscar(id);
			
			if (restauranteAtual != null) {
				restauranteNovo.setId(restauranteAtual.getId());
				return ResponseEntity.status(HttpStatus.OK).body(restauranteCadastroServ.salvar(restauranteNovo));
			}
			else {
				/* Didático: Abordagem alterniativa de tratar como uma EXCEPTION para padronizar as mensagens de erro e
				 * retorno para o consumidor da API.
				 * 
				 * return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); */
				throw new EntidadeNaoEncontradaException(
						"\tEntidade:\tRestaurante;\n"
								+ "\tMétodo:\t\t.atualizar(id);\n"
								+ "\tStatus:\t\tFalho;\n"
								+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
								+ " de valor igual o argumento passado 'id=" + id + "';\n"
								+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto,"
								+ " ou considere adicionar o registro com tal chave primária antes de atualizá-lo.");
			}
			
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
		
	}
	
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id,
			@RequestBody Map<String, Object> atributosValores) {
		
		/**
		 * Atualiza parcialmente, na entidade 'Restaurante', aquele objeto que possui seu atributo 'Restaurante.id' com
		 * valor do argumento 'id', salvando os novos atributos passados no corpo da requisição e representados pelo
		 * 'Map atributosValores' sobre seus atributos.
		 */
		
		try {
			Restaurante restauranteAtual = restauranteRepo.buscar(id);
			
			if (restauranteAtual != null) {
				mesclarAtributos(atributosValores, restauranteAtual);
				return atualizar(id, restauranteAtual); // Isto fara:
														// return ResponseEntity.ok(restauranteAtual).body();
			}
			else {
				/* Didático: Abordagem alterniativa de tratar como uma EXCEPTION para padronizar as mensagens de erro e
				 * retorno para o consumidor da API.
				 * 
				 * return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); */
				throw new EntidadeNaoEncontradaException(
						"\tEntidade:\tRestaurante;\n"
								+ "\tMétodo:\t\t.atualizarParcial(id);\n"
								+ "\tStatus:\t\tFalho;\n"
								+ "\tCausa:\t\tNão há registro na base de dados, para esta entidade, com chave-primária"
								+ " de valor igual o argumento passado 'id=" + id + "';\n"
								+ "\tSugestões:\tVerifique se o valor do argumento passado 'id=" + id + "' é correto,"
								+ " ou considere adicionar o registro com tal chave primária antes de atualizá-lo.");
			}
			
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

/* Didático:
 * Alternativas de return em 'controllers':
 * 
 * return ResponseEntity.status(HttpStatus.OK).body(restaurante);
 * return ResponseEntity.status(HttpStatus.OK).build();
 * return ResponseEntity.ok(restaurante);
 * 
 * Exemplo com redirecionamento de um caso FOUND:
 * 
 * HttpHeaders headers = new HttpHeaders();
 * headers.add(HttpHeaders.LOCATION, "http://localhost:8080/restaurantes");
 * 
 * return ResponseEntity.status(HttpStatus.FOUND).headers(headers).body(restaurante); */
