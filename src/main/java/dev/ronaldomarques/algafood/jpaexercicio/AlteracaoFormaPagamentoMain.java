package dev.ronaldomarques.algafood.jpaexercicio;



import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.FormaPagamento;
import dev.ronaldomarques.algafood.domain.model.repository.FormaPagamentoRepository;



public class AlteracaoFormaPagamentoMain {
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
		FormaPagamentoRepository formaPagRepo = appContxt.getBean(FormaPagamentoRepository.class);
		/* Printar os objetos persistidos com 'id' pra conferir. */
		System.out.println("\nLista de forma pagamento:");
		
		for (FormaPagamento forFormaPag : formaPagRepo.listar()) {
			System.out.printf("\t%d: %s.\n", forFormaPag.getId(), forFormaPag.getDescricao());
		}
		
		System.out.println("Alterar forma pagamento:");
		/* Criar objetos exemplos para alterar. */
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setId(1L);
		formaPagamento.setDescricao("FINANC.CONSIG.24X");
		/* Persistir o objeto exemplo com ID já pré-definido, ira sobrescrever o
		 * bjeto persistido anteriormente com este mesmo ID. */
		formaPagRepo.gravar(formaPagamento);
		
		/* Printar os objetos persistidos com 'id' pra conferir. */
		System.out.println("\nLista de forma pagamento:");
		
		for (FormaPagamento forFormaPag : formaPagRepo.listar()) {
			System.out.printf("\t%d: %s.\n", forFormaPag.getId(), forFormaPag.getDescricao());
		}
		
	}
	
}
