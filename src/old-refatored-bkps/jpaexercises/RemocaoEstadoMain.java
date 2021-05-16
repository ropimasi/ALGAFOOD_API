package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;



public class RemocaoEstadoMain {
	public static void main(String[] args) {
		ApplicationContext appContxt = new SpringApplicationBuilder(
				AlgafoodApiApplication.class)
						.web(WebApplicationType.NONE)
						.run(args);
		
		EstadoRepository estadoRepo = appContxt.getBean(EstadoRepository.class);
		
		System.out.println("\nListar primeiramente, Estados:");
		for (Estado forEstado : estadoRepo.listar()) {
			System.out.printf("\t%d: %s.\n", forEstado.getId(), forEstado.getNome());
		}
		
		System.out.println("Remover Estado:");
		try {
			Estado estadoRem = estadoRepo.buscar(3L);
			estadoRepo.remover(estadoRem.getId());
		} catch (Exception excep) {
			System.out.println("Erro ao excluir Estado:\n\t" + excep.getMessage() + "\n");
			excep.printStackTrace();
		}
		
		/* Printar os objetos persistidos com 'id' pra conferir. */
		System.out.println("\nListar depois, Estado:");
		for (Estado forEstado : estadoRepo.listar()) {
			System.out.printf("\t%d: %s.\n", forEstado.getId(), forEstado.getNome());
		}
	}
}
