/* Copyright notes... */
package dev.ronaldomarques.algafood.api.controller;


import javax.persistence.TransactionRequiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.ronaldomarques.algafood.domain.exception.EntidadeEmUsoException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.ronaldomarques.algafood.domain.exception.EntidadeNaoPersistidaException;
import dev.ronaldomarques.algafood.domain.model.entity.CidadeEntity;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;
import dev.ronaldomarques.algafood.domain.service.CidadeCadastroService;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;



/**
 * This is a simple didadic project. A RESTful-API built with on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    CozinhaController, RestauranteController, EstadoController, FormaPagamentoController, PermissaoController...
 *         // TODO Terminar de listar demais 'controllers' na Javadocs tag 'see'.
 * @since  2020-09-09.
 */

@RestController // @Controller + @ResponseBody + Outras...
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private CidadeCadastroService cidadeCadastroServ;
	
	
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody CidadeEntity cidadeEntity) {
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(cidadeCadastroServ.salvar(cidadeEntity));
		}
		catch (EntidadeNaoPersistidaException excep) { // De: .salvar() <- .buscar() <- .find().
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					"Objeto a ser adicionado precisa, obrigatóriamente, ter o atributo 'id' nulo."
							+ excep.getMessage());
		}
		catch (ArgumentoIlegalException excep) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excep.getMessage());
		}
		
	}
	
	
	
	@GetMapping
	public ResponseEntity<?> listar() {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(cidadeRepo.findAll());
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(excep.getMessage());
		}
		
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		
		try {
			/* Didático: Como .buscar() .procurar() .pegar() não alteram o status da aplicação, então pode acessar
			 * diretamente o repositório. Nesta abordagem, se houver regras de negócio na operação de buscar um recurso,
			 * estas regras ficam na camada 'service', devendo então, usar o método a baixo: */
			// return ResponseEntity.status(HttpStatus.OK).body(cidadeCadastroServ.procurar(id));
			System.out.println("debug 1");
			return ResponseEntity.ok(cidadeRepo.findById(id).get());
		}
		catch (EntidadeNaoEncontradaException excep) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excep.getMessage());
		}
		catch (ArgumentoIlegalException excep) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excep.getMessage());
		}
		catch (Exception excep) {
			// Se chegar este ponto escapou de todas condições e exceções previtas: refatorar code.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro interno: 'EveryExceptionEcaped'. Contate desenvolvedor da API.\n" + excep.getMessage());
		}
		
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CidadeEntity cidadeNova) {
		
		cidadeNova.setId(id);
		
		try {
			return ResponseEntity.ok(cidadeCadastroServ.salvar(cidadeNova));
		}
		catch (EntidadeNaoPersistidaException excep) { // id do recurso end-point não encontrado.
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excep.getMessage());
		}
		catch (ArgumentoIlegalException excep) { // objeto estado não managed não pode atualizar.
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excep.getMessage());
		}
		catch (TransactionRequiredException excep) { // falta de @transactional na chamada do método.
			/* Conforme comentário nos escopos mais internos, esta falha tende a nunca acontecer, porém vide
			 * comentários em 'EstadoRepositoryImlp.java'. */
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro interno: 'EveryExceptionEcaped'. Contate desenvolvedor da API.\n" + excep.getMessage());
		}
		catch (Exception excep) {
			// Se chegar este ponto escapou de todas condições e exceções previtas: refatorar code.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro interno: 'EveryExceptionEcaped'. Contate desenvolvedor da API.\n" + excep.getMessage());
		}
		
	}
	
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		try {
			cidadeCadastroServ.excluir(id);
			return ResponseEntity.noContent().build();
		}
		catch (EntidadeNaoEncontradaException excep) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excep.getMessage());
		}
		catch (EntidadeEmUsoException excep) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(excep.getMessage());
		}
		catch (Exception excep) {
			// Se chegar este ponto escapou de todas condições e exceções previtas: refatorar code.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro interno: Contate desenvolvedor da API.\n" + excep.getMessage());
		}
		
	}
	
}
