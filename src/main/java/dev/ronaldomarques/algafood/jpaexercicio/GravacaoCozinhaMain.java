package dev.ronaldomarques.algafood.jpaexercicio;



import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Cozinha;
import dev.ronaldomarques.algafood.domain.model.repository.CozinhaRepository;



public class GravacaoCozinhaMain {
	/* Este é um método main para teste/exercício de HIBERNATE. Esta classe main
	 * tem a função de iniciar a application para testarmos debugarmos, e
	 * encerrar a application. Diferentemente da application iniciada por um
	 * controler que ficará em ambiente web até que um fator externo a encerre. */
	public static void main(String[] args) {
		/* Preciso iniciar uma aplicação-spring, mas não aplicação-spring-web
		 * tradicional, e sim uma aplicação que inicie e termine no console, por
		 * isso uso o método spr..Appli..BUILDER() para montar uma aplicação com
		 * parâmetros que fará uma aplicação em console. */
		ApplicationContext applicationContext =
				// "...parâmetros que fará uma aplicação em console."
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		/* Desta forma consigo solicitar ao spring (instanciar) beans através do
		 * 'applicationContext'. */
		CozinhaRepository cozinhaRepo = applicationContext.getBean(CozinhaRepository.class);
		
		System.out.println("Adicionar cozinha:");
		/* Criar objetos exemplos para adicionar. */
		Cozinha cozinha1, cozinha2;
		cozinha1 = new Cozinha("Italiana");
		cozinha2 = new Cozinha("Mexicana");
		
		/* Persistir os objetos exemplos. */
		cozinha1 = cozinhaRepo.gravar(cozinha1);
		cozinha2 = cozinhaRepo.gravar(cozinha2);
		
		/* Printar os objetos persistidos com 'id' agora. */
		System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
		System.out.printf("%d - %s\n", cozinha2.getId(), cozinha2.getNome());
		
	}
	
}
