package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.RestauranteEntity;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;



public class BuscaRestauranteMain {
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
						.web(WebApplicationType.NONE) // "...parâmetros que fará
														// uma aplicação em
														// console."
						.run(args);
		
		/* Desta forma consigo solicitar ao spring (instanciar) beans através do
		 * 'applicationContext'. */
		RestauranteRepository restauranteRepo = appContxt.getBean(RestauranteRepository.class);
		
		System.out.println("Buscar restaurante:");
		/* Criar objetos exemplo com a consulta por ID. */
		Restaurante restaurante = restauranteRepo.buscar(2L);
		
		/* Printar os objetos persistidos com 'id' agora. */
		System.out.printf("%d = %s\n", restaurante.getId(), restaurante.getNome());
	}
}
