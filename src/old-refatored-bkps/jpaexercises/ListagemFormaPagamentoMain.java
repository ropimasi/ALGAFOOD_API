package dev.ronaldomarques.algafood.jpaexercicio;



import java.util.List;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.FormaPagamentoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.FormaPagamentoRepository;



public class ListagemFormaPagamentoMain {
	
	public static void main(String[] args) {
		ApplicationContext appContxt =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		FormaPagamentoRepository formaPagamentoRepo = appContxt.getBean(FormaPagamentoRepository.class);
		List<FormaPagamento> formasPagamentos = formaPagamentoRepo.listar();
		
		System.out.println("");
		
		for (FormaPagamento forformaPag : formasPagamentos) {
			System.out.printf("\t%s\n", forformaPag.getDescricao());
		}
		
	}
	
}
