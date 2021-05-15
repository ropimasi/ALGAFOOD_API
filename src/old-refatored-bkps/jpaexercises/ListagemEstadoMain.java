package dev.ronaldomarques.algafood.jpaexercicio;

import java.util.List;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.EstadoEntity;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;



public class ListagemEstadoMain {
	public static void main(String[] args) {
		ApplicationContext appContxt = new SpringApplicationBuilder(
				AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		EstadoRepository estadoRepo = appContxt.getBean(EstadoRepository.class);
		List<Estado> estados = estadoRepo.listar();
		
		System.out.println("Lista de Estados:");
		
		for (Estado forEstado : estados) {
			System.out.println("\t" + forEstado.getNome());
		}
	}
}
