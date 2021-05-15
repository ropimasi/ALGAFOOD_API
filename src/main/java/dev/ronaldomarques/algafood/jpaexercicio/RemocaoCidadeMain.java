package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Cidade;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;



public class RemocaoCidadeMain {
	public static void main(String[] args) {
		ApplicationContext appContxt = new SpringApplicationBuilder(
				AlgafoodApiApplication.class)
						.web(WebApplicationType.NONE)
						.run(args);
		
		CidadeRepository cidadeRepo = appContxt.getBean(CidadeRepository.class);
		
		System.out.println("\nListar primeiramente, Cidades:");
		for (Cidade cidadeFor : cidadeRepo.listar()) {
			System.out.printf("\t%d: %s.\n", cidadeFor.getId(), cidadeFor.getNome());
		}
		
		System.out.println("Remover Estado:");
		try {
			cidadeRepo.remover(2L);
		} catch (Exception excep) {
			System.out.println("Erro ao excluir Cidade:\n\t" + excep.getMessage() + "\n");
			excep.printStackTrace();
		}
		
		/* Printar os objetos persistidos com 'id' pra conferir. */
		System.out.println("\nListar depois, Estado:");
		for (Cidade cidadeFor : cidadeRepo.listar()) {
			System.out.printf("\t%d: %s.\n", cidadeFor.getId(), cidadeFor.getNome());
		}
	}
}
