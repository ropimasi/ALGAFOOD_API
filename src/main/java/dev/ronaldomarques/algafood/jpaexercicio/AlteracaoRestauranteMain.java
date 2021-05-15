package dev.ronaldomarques.algafood.jpaexercicio;

import java.math.BigDecimal;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Restaurante;
import dev.ronaldomarques.algafood.domain.model.repository.CozinhaRepository;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;



public class AlteracaoRestauranteMain {
	/* Este é um método main para teste/exercício de HIBERNATE. Esta classe main
	 * tem a função de iniciar a application para testarmos debugarmos, e
	 * encerrar a application. Diferentemente da application iniciada por um
	 * controler que ficará em ambiente web até que um fator externo a encerre. */
	public static void main(String[] args) {
		/* Preciso iniciar uma aplicação-spring, mas não aplicação-spring-web
		 * tradicional, e sim uma aplicação que inicie e termine no console, por
		 * isso uso o método spr..Appli..BUILDER() para montar uma aplicação com
		 * parâmetros que fará uma aplicação em console. */
		ApplicationContext appContxt = new SpringApplicationBuilder(
				AlgafoodApiApplication.class)
						.web(WebApplicationType.NONE) // "...parâmetros que fará uma aplicação em console."
						.run(args);
		
		/* Desta forma consigo solicitar ao spring (instanciar) beans através do
		 * 'applicationContext'. */
		RestauranteRepository restauranteRepo = appContxt.getBean(RestauranteRepository.class);
		CozinhaRepository cozinhaRepo = appContxt.getBean(CozinhaRepository.class);
		
		/* Printar os objetos persistidos com 'id' pra conferir. */
		System.out.println("\nLista de restaurante:");
		
		for (Restaurante forRestaurante : restauranteRepo.listar()) {
			System.out.printf("\t%d: %s: R$%.2f [Culinária %s]\n", forRestaurante.getId(), forRestaurante.getNome(),
					forRestaurante.getTaxaFrete(), forRestaurante.getCozinha().getNome());
		}
		
		System.out.println("Alterar restaurante:");
		/* Criar objetos exemplos para alterar. */
		Restaurante restauranteNovo = new Restaurante();
		 restauranteNovo.setId(33L); // Quero alterar aquele com id=3.
		restauranteNovo.setNome("Cê Qui Cisservice-se à sí mesmo yourself");
		restauranteNovo.setTaxaFrete(new BigDecimal(30));
		restauranteNovo.setCozinha(cozinhaRepo.pegar(3L));
		/* Persistir o objeto exemplo com ID já pré-definido, ira sobrescrever o
		 * bjeto persistido anteriormente com este mesmo ID. */
		restauranteRepo.gravar(restauranteNovo); // Aqui dentro fará o merge();
		
		/* Printar os objetos persistidos com 'id' pra conferir. */
		System.out.println("\nLista de restaurante:");
		
		for (Restaurante forRestaurante : restauranteRepo.listar()) {
			System.out.printf("\t%d: %s: R$%.2f [Culinária %s]\n", forRestaurante.getId(), forRestaurante.getNome(),
					forRestaurante.getTaxaFrete(), forRestaurante.getCozinha().getNome());
		}
	}
}
