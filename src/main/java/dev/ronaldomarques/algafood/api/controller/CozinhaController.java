/* Copyright notes... */
package dev.ronaldomarques.algafood.api.controller;


import static dev.ronaldomarques.algafood.infrastructure.exception.DescritorDeException.descreverExcecao;
import static dev.ronaldomarques.algafood.infrastructure.exception.DescritorDeException.descreverInesperadaException;
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
import dev.ronaldomarques.algafood.domain.model.entity.CozinhaEntity;
import dev.ronaldomarques.algafood.domain.model.repository.CozinhaRepository;
import dev.ronaldomarques.algafood.domain.service.CozinhaCadastroService;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;
import dev.ronaldomarques.algafood.infrastructure.exception.PercistenciaException;



/**
 * This is a simple didadic project. A RESTful-API built with on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    RestauranteController, CidadeController, EstadoController, FormaPagamentoController, PermissaoController...
 *         // TODO Terminar de listar demais 'controllers' na Javadocs tag 'see'.
 * @since  2020-09-09.
 */

@RestController // @Controller + @ResponseBody + Outras...
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepo;
	
	@Autowired
	private CozinhaCadastroService cozinhaCadastroServ;
	
	
	
	@PostMapping
	/* Didático: @ResponseStatus(HttpStatus.CREATED) esta notação pode ser colocada para definir o status padrão no caso
	 * de método realizado com sucesso, porém, o método não pode possuir outras possibilidades de status já que esta
	 * notação sobrescreve outros status vindos pelos 'ResponseEntity' or 'redirect'. */
	public ResponseEntity<?> adicionar(@RequestBody CozinhaEntity cozinhaEntity) {
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaCadastroServ.salvar(cozinhaEntity));
		}
		catch (ArgumentoIlegalException excep) { // De: .salvar() <- .gravar() <- .merge().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (PercistenciaException excep) { // De: .salvar() <- .gravar() <- .merge().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (Exception excep) { // De: origem inesperada.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					descreverInesperadaException(excep));
		}
		
	}
	
	
	
	@GetMapping
	public ResponseEntity<?> listar() {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(cozinhaRepo.findAll());
		}
		catch (ArgumentoIlegalException excep) { // De: .listar() <- .getResultList().
			/* Este é um INTERNAL_SERVER_ERROR conforme descrição em seu '___RepositoryImpl.java' */
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverExcecao(excep));
		}
		catch (Exception excep) { // De: origem inesperada.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		
		try {
			/* Didático: Como .buscar() .procurar() .pegar() não alteram o status da aplicação, então pode acessar
			 * diretamente o repositório. */
			return ResponseEntity.status(HttpStatus.OK).body(cozinhaRepo.findById(id).get());
			/* Didático: se houver regras de negócio na operação de buscar um recurso, estas regras ficam na camada
			 * 'service', devendo então, usar o método a baixo: */
			// return ResponseEntity.status(HttpStatus.OK).body(cozinhaCadastroServ.procurar(id));
		}
		catch (EntidadeNaoEncontradaException excep) { // De: .findById() <- .find().
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(descreverExcecao(excep));
		}
		catch (ArgumentoIlegalException excep) { // De: .pegar() <- .find().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (Exception excep) { // De: origem inesperada.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CozinhaEntity cozinhaNova) {
		
		/**
		 * Atualiza a entidade 'CozinhaEntity' que possui valor 'id' em seu atributo 'id' na base de dados, salvando a
		 * nova entidade 'CozinhaEntity' representada por 'cozinhaNova' em seu lugar.
		 */
		try {
			cozinhaNova.setId(id);
			return ResponseEntity.status(HttpStatus.OK).body(cozinhaCadastroServ.salvar(cozinhaNova));
		}
		catch (EntidadeNaoEncontradaException excep) { // De: .buscar() <- .find().
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(descreverExcecao(excep));
		}
		catch (ArgumentoIlegalException excep) { // De: .buscar() <- .find().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (PercistenciaException excep) { // De: .salvar() <- .gravar() <- .merge().
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
		 * Atualiza parcialmente, na entidade 'CozinhaEntity', aquele objeto que possui seu atributo 'CozinhaEntity.id'
		 * com
		 * valor do argumento 'id', salvando os novos atributos passados no corpo da requisição e representados pelo
		 * 'Map atributosValores' sobre seus atributos.
		 */
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(cozinhaCadastroServ.salvarParcial(id, atributosValores));
		}
		catch (EntidadeNaoEncontradaException excep) { // De: .salvarParcial() <- .pegar().
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(descreverExcecao(excep));
		}
		catch (ArgumentoIlegalException excep) { // De: .salvarParcial() <- .pegar() || [.gravar() <- .merge()].
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (PercistenciaException excep) { // De: .salvarParcial() <- .gravar() <- .merge().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(descreverExcecao(excep));
		}
		catch (Exception excep) { // De: origem inesperada.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		
		try {
			if (cozinhaCadastroServ.excluir(id) != null) return ResponseEntity.noContent().build();
			else return ResponseEntity.notFound().build();
		}
		catch (EntidadeNaoEncontradaException excep) { // De: .excluir() <- .remover().
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(descreverExcecao(excep));
		}
		catch (EntidadeEmUsoException excep) { // De: .excluir() <- .remover().
			return ResponseEntity.status(HttpStatus.CONFLICT).body(descreverExcecao(excep));
		}
		catch (Exception excep) { // De: origem inesperada.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					descreverInesperadaException(excep));
		}
		
	}
	
}

/* Didático:
 * Alternativas de return em 'controllers':
 * 
 * return ResponseEntity.status(HttpStatus.OK).body(cozinha);
 * return ResponseEntity.status(HttpStatus.OK).build();
 * return ResponseEntity.ok(cozinha);
 * 
 * Exemplo com redirecionamento de um caso FOUND:
 * 
 * HttpHeaders headers = new HttpHeaders();
 * headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
 * 
 * return ResponseEntity.status(HttpStatus.FOUND).headers(headers).body(cozinha); */
