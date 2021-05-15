package dev.ronaldomarques.algafood.jpaexercicio;



import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Cozinha;
import dev.ronaldomarques.algafood.domain.model.repository.CozinhaRepository;



public class AlteracaoCozinhaMain {
	/*
	 * Este é um método main para teste/exercício de HIBERNATE. Esta classe main
	 * tem a função de iniciar a application para testarmos debugarmos, e
	 * encerrar a application. Diferentemente da application iniciada por um
	 * controler que ficará em ambiente web até que um fator externo a encerre.
	 */
	public static void main(String[] args) {
		/*
		 * Preciso iniciar uma aplicação-spring, mas não aplicação-spring-web
		 * tradicional, e sim uma aplicação que inicie e termine no console, por
		 * isso uso o método spr..Appli..BUILDER() para montar uma aplicação com
		 * parâmetros que fará uma aplicação em console.
		 */
		ApplicationContext applicationContext = new SpringApplicationBuilder(
				AlgafoodApiApplication.class)
						.web(WebApplicationType.NONE) // "...parâmetros que fará
														// uma aplicação em
														// console."
						.run(args);
		
		/*
		 * Desta forma consigo solicitar ao spring (instanciar) beans através do
		 * 'applicationContext'.
		 */
		CozinhaRepository cozinhaRepo = applicationContext.getBean(CozinhaRepository.class);
		
		System.out.println("Alterar cozinha:");
		/* Criar objetos exemplos para alterar. */
		Cozinha cozinha = new Cozinha("Italiana");
		cozinha.setId(1L);
		/*
		 * Persistir o objeto exemplo com ID já pré-definido, ira sobrescrever o
		 * bjeto persistido anteriormente com este mesmo ID.
		 */
		cozinhaRepo.gravar(cozinha);
		
		/* Printar os objetos persistidos com 'id' pra conferir. */
		System.out.println("\nLista de cozinha:");
		
		for (Cozinha forCozinha : cozinhaRepo.listar()) {
			System.out.printf("\t%d = %s\n", forCozinha.getId(), forCozinha.getNome());
		}
		
	}
	
}
