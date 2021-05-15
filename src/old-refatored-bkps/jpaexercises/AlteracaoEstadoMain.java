package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;



public class AlteracaoEstadoMain {
	public static void main(String[] args) {
		ApplicationContext appContxt =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		EstadoRepository estadoRepo = appContxt.getBean(EstadoRepository.class);
		
		System.out.println("\nListar primenramente, Estados:");
		for (Estado estadoFor : estadoRepo.listar()) {
			System.out.printf("\t%d: %s.\n", estadoFor.getId(), estadoFor.getNome());
		}
		
		System.out.println("Alterar Estado:");
		/* Criar objetos exemplos para alterar. */
		Estado estadoAlt = new Estado();
		estadoAlt.setId(2L);
		estadoAlt.setNome("SC");
		/* Persistir o objeto exemplo com ID já pré-definido, ira sobrescrever o
		 * bjeto persistido anteriormente com este mesmo ID. */
		estadoRepo.gravar(estadoAlt);
		
		/* Printar os objetos persistidos com 'id' pra conferir. */
		System.out.println("\nListar depois, Estados:");
		for (Estado estadoFor : estadoRepo.listar()) {
			System.out.printf("\t%d: %s.\n", estadoFor.getId(), estadoFor.getNome());
		}
	}
}
