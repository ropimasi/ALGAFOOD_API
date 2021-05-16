package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;



public class GravacaoEstadoMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		EstadoRepository estadoRepo = applicationContext.getBean(EstadoRepository.class);
		
		System.out.println("\nListar primeiramente, Estados:");
		for (Estado estadoFor : estadoRepo.listar()) {
			System.out.printf("\t%d: %s.\n", estadoFor.getId(), estadoFor.getNome());
		}
		
		System.out.println("Adicionar estado:");
		Estado estado1, estado2;
		estado1 = new Estado("RS");
		estado2 = new Estado("GO");
		estado1 = estadoRepo.gravar(estado1);
		estado2 = estadoRepo.gravar(estado2);
		
		System.out.println("\nListar primeiramente, Estados:");
		for (Estado estadoFor : estadoRepo.listar()) {
			System.out.printf("\t%d: %s.\n", estadoFor.getId(), estadoFor.getNome());
		}
	}
}
