package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.CidadeEntity;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;



public class BuscaCidadeMain {
	public static void main(String[] args) {
		ApplicationContext appContxt = new SpringApplicationBuilder(
				AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		CidadeRepository cidadeRepo = appContxt.getBean(CidadeRepository.class);
		
		System.out.println("Buscar cidade:");
		/* Criar objetos exemplo com a consulta por ID. */
		Cidade cidade = cidadeRepo.buscar(2L);
		System.out.printf("%d: %s.\n", cidade.getId(), cidade.getNome());
	}
}
