package dev.ronaldomarques.algafood.jpaexercicio;



import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.FormaPagamento;
import dev.ronaldomarques.algafood.domain.model.repository.FormaPagamentoRepository;



public class GravacaoFormaPagamentoMain {
	
	public static void main(String[] args) {
		
		ApplicationContext appContxt =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		FormaPagamentoRepository formaPagamentoRepo = appContxt.getBean(FormaPagamentoRepository.class);
		
		System.out.println("Lista inicial:");
		
		for (FormaPagamento forFormaPag : formaPagamentoRepo.listar()) {
			System.out.printf("%d: %s.\n", forFormaPag.getId(), forFormaPag.getDescricao());
		}
		
		System.out.println("Adicionar FormaPagamento 1...");
		FormaPagamento formaPagamento1 = new FormaPagamento();
		formaPagamento1.setDescricao("CARTAO CREDITO");
		formaPagamentoRepo.gravar(formaPagamento1);
		
		System.out.println("Adicionar FormaPagamento 2...");
		FormaPagamento formaPagamento2 = new FormaPagamento();
		formaPagamento2.setDescricao("TRANSFERENCIA");
		formaPagamentoRepo.gravar(formaPagamento2);
		
		System.out.println("Lista final:");
		
		for (FormaPagamento forFormaPag : formaPagamentoRepo.listar()) {
			System.out.printf("%d: %s.\n", forFormaPag.getId(), forFormaPag.getDescricao());
		}
		
	}
	
}
