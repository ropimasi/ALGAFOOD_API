/**
 * My test controller.
 */
package dev.ronaldomarques.algafood.api.controller;

import static dev.ronaldomarques.algafood.infrastructure.exception.DescritorDeException.descreverInesperadaException;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.ronaldomarques.algafood.domain.model.repository.CozinhaRepository;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;



/**
 * This is a test copntroller.
 * @author Ronaldo Marques.
 */

@RestController
@RequestMapping(value = "/testes", produces = MediaType.APPLICATION_JSON_VALUE)
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepo;
	
	@Autowired
	private RestauranteRepository restauranteRepo;
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/cozinhas/nome")
	public ResponseEntity<?> cozinhaNome(@RequestParam("nome") String nome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(cozinhaRepo.nome(nome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/cozinhas/pornome")
	public ResponseEntity<?> cozinhaPorNome(@RequestParam("nome") String nome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(cozinhaRepo.findByNome(nome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/cozinhas/listapornome")
	public ResponseEntity<?> cozinhaListaPorNome(@RequestParam("nome") String nome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(cozinhaRepo.findListaByNome(nome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/cozinhas/listapornomecontendo")
	public ResponseEntity<?> cozinhaListaPorNomeContendo(@RequestParam("nome") String nome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(cozinhaRepo.findListaByNomeContaining(nome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/pornomecontaining")
	public ResponseEntity<?> restaurantePorNomeContaining(@RequestParam("nome") String nome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(restauranteRepo.findAllByNomeContaining(nome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/taxafrete")
	public ResponseEntity<?> restauranteTaxaFrete(@RequestParam("taxafrete") BigDecimal taxaFrete) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(restauranteRepo.taxaFrete(taxaFrete));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/portaxafrete")
	public ResponseEntity<?> restaurantePorTaxaFrete(@RequestParam("taxafrete") BigDecimal taxaFrete) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(restauranteRepo.findByTaxaFrete(taxaFrete));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/listaportaxafrete")
	public ResponseEntity<?> restauranteListaPorTaxaFrete(@RequestParam("taxafrete") BigDecimal taxaFrete) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(restauranteRepo.findListaByTaxaFrete(taxaFrete));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/listaportaxafreteentre")
	public ResponseEntity<?> restauranteListaPorTaxaFreteEntre(
			@RequestParam("taxafreteinicial") BigDecimal taxaFreteInicial,
			@RequestParam("taxafretefinal") BigDecimal taxaFreteFinal) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(restauranteRepo.findListaByTaxaFreteBetween(taxaFreteInicial, taxaFreteFinal));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/listaportaxafreteentre-e-cozinhanome")
	public ResponseEntity<?> restauranteListaPorTaxaFreteEntreEPorCozinhaNome(
			@RequestParam("taxafreteinicial") BigDecimal taxaFreteInicial,
			@RequestParam("taxafretefinal") BigDecimal taxaFreteFinal,
			@RequestParam("cozinhanome") String cozinhaNome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(restauranteRepo.findListaByTaxaFreteBetweenAndCozinhaNomeContaining(taxaFreteInicial,
							taxaFreteFinal, cozinhaNome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/top2pornomecontaining")
	public ResponseEntity<?> restauranteTop2PorNomeContaining(
			@RequestParam("nome") String nome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(restauranteRepo.findTop2ByNomeContaining(nome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/existsbynome")
	public ResponseEntity<?> restauranteExistsByNome(
			@RequestParam("nome") String nome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(restauranteRepo.existsByNome(nome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/existsbytaxafrete")
	public ResponseEntity<?> restauranteExistsByTaxaFrete(
			@RequestParam("taxafrete") BigDecimal taxaFrete) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(restauranteRepo.existsByTaxaFrete(taxaFrete));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/portaxaecozinha")
	public ResponseEntity<?> restaurantePorTaxaECozinha(
			@RequestParam("taxafreteinicial") BigDecimal taxaFreteInicial,
			@RequestParam("taxafretefinal") BigDecimal taxaFreteFinal,
			@RequestParam("cozinhanome") String cozinhaNome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(restauranteRepo.porTaxaECozinha(taxaFreteInicial, taxaFreteFinal, cozinhaNome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/portaxaecozinhaext")
	public ResponseEntity<?> restaurantePorTaxaECozinhaExt(
			@RequestParam("taxafreteinicial") BigDecimal taxaFreteInicial,
			@RequestParam("taxafretefinal") BigDecimal taxaFreteFinal,
			@RequestParam("cozinhanome") String cozinhaNome) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(restauranteRepo.porTaxaECozinhaExt(taxaFreteInicial, taxaFreteFinal, cozinhaNome));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	
	
	
	/* ##### MY TESTS #### */
	@GetMapping(value = "/restaurantes/reposdjcustom")
	public ResponseEntity<?> restauranteRepositoryCustomized(
			@RequestParam("cozinha") String cozinhaNome,
			@RequestParam("taxamenor") BigDecimal taxaFreteMenor) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(restauranteRepo.buscarCozinhaFreteMenor(cozinhaNome, taxaFreteMenor));
		}
		catch (Exception excep) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descreverInesperadaException(excep));
		}
		
	}
	//
	//
	//
	
}
//
//
//
//
//
//
//
//
//
