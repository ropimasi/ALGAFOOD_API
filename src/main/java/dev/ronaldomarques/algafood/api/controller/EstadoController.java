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
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;
import dev.ronaldomarques.algafood.domain.service.EstadoCadastroService;
import dev.ronaldomarques.algafood.infrastructure.exception.ArgumentoIlegalException;



/**
 * This is a simple didadic project. A RESTful-API built with on JAVA and Spring Framework.
 * @author Ronaldo Marques.
 * @see    CozinhaController, RestauranteController, CidadeController, FormaPagamentoController, PermissaoController...
 *         // TODO Terminar de listar demais 'controllers' na Javadocs tag 'see'.
 * @since  2020-09-09.
 */

@RestController // @Controller + @ResponseBody + Outras...
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	@Autowired
	private EstadoCadastroService estadoCadastroServ;
	
	
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody EstadoEntity estadoEntity) {
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(estadoCadastroServ.salvar(estadoEntity));
		}
		catch (EntidadeNaoPersistidaException excep) {
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
			return ResponseEntity.status(HttpStatus.OK).body(estadoRepo.findAll());
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(excep.getMessage());
		}
		
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		
		try {
			return ResponseEntity.ok(estadoRepo.findById(id).get());
		}
		catch (EntidadeNaoEncontradaException excep) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excep.getMessage());
		}
		catch (ArgumentoIlegalException excep) {
			return ResponseEntity.badRequest().body(excep.getMessage());
		}
		
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EstadoEntity estadoNovo) {
		
		estadoNovo.setId(id);
		
		try {
			return ResponseEntity.ok(estadoCadastroServ.salvar(estadoNovo));
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(excep.getMessage());
		}
		
	}
	
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		try {
			if (estadoCadastroServ.excluir(id) != null) return ResponseEntity.noContent().build();
		}
		catch (EntidadeNaoEncontradaException excep) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excep.getMessage());
		}
		catch (EntidadeEmUsoException excep) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(excep.getMessage());
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(excep.getMessage());
		}
		
		// Se chegar este ponto escapou de todas condições e exceções previtas: refatorar code.
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Erro interno: 'EveryExceptionEcaped'. Contate desenvolvedor da API.");
		
	}
	
}
