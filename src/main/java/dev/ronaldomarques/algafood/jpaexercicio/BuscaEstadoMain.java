package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Estado;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;



public class BuscaEstadoMain {
	public static void main(String[] args) {
		ApplicationContext appContxt = new SpringApplicationBuilder(
				AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		EstadoRepository estadoRepo = appContxt.getBean(EstadoRepository.class);
		
		System.out.println("Buscar estado:");
		/* Criar objetos exemplo com a consulta por ID. */
		Estado estado = estadoRepo.buscar(2L);
		System.out.printf("%d: %s.\n", estado.getId(), estado.getNome());
	}
}
