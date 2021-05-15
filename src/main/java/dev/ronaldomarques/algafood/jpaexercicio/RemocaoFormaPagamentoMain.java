package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.FormaPagamento;
import dev.ronaldomarques.algafood.domain.model.repository.FormaPagamentoRepository;



public class RemocaoFormaPagamentoMain {
	public static void main(String[] args) {
		ApplicationContext appContxt = new SpringApplicationBuilder(
				AlgafoodApiApplication.class)
						.web(WebApplicationType.NONE) // Parâmetros que fará uma aplicação em console.
						.run(args);
		
		FormaPagamentoRepository formaPagamentoRepo = appContxt.getBean(FormaPagamentoRepository.class);
		
		System.out.println("\nListar primeiramente Forma Pagamento para conferir:");
		for (FormaPagamento forFormaPagamento : formaPagamentoRepo.listar()) {
			System.out.printf("\t%d: %s.\n", forFormaPagamento.getId(), forFormaPagamento.getDescricao());
		}
		
		System.out.println("Remover forma pagamento:");
		// Buscar uma instância registrada/persistida em 'mananged state'.
		FormaPagamento formaPagRem = formaPagamentoRepo.buscarId(2L);
		formaPagamentoRepo.remover(formaPagRem);
		// ou...
		//formaPagamentoRepo.remover(formaPagamentoRepo.buscarId(2L));
		
		// Printar os objetos persistidos com 'id' pra conferir.
		System.out.println("\nListar posteriormente Forma Pagamento para conferir:");
		for (FormaPagamento forFormaPagamento : formaPagamentoRepo.listar()) {
			System.out.printf("\t%d: %s.\n", forFormaPagamento.getId(), forFormaPagamento.getDescricao());
		}
	}
}
