package dev.ronaldomarques.algafood.jpaexercicio;

import java.util.List;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Cidade;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;



public class ListagemCidadeMain {
	public static void main(String[] args) {
		ApplicationContext appContxt = new SpringApplicationBuilder(
				AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		CidadeRepository cidadeRepo = appContxt.getBean(CidadeRepository.class);
		List<Cidade> cidadeList = cidadeRepo.listar();
		
		System.out.println("Lista de Cidades:");
		
		for (Cidade cidadeFor : cidadeList) {
			System.out.println("\t" + cidadeFor.getId() + ": " + cidadeFor.getNome() + ".");
		}
	}
}
