package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.CidadeEntity;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;
import dev.ronaldomarques.algafood.domain.model.repository.EstadoRepository;



public class GravacaoCidadeMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		CidadeRepository cidadeRepo = applicationContext.getBean(CidadeRepository.class);
		EstadoRepository estadoRepo = applicationContext.getBean(EstadoRepository.class);
		
		System.out.println("\nListar primeiramente, Cidades:");
		for (Cidade cidadeFor : cidadeRepo.listar()) {
			System.out.printf("\t%d: %s.\n", cidadeFor.getId(), cidadeFor.getNome());
		}
		
		System.out.println("Adicionar cidade:");
		Cidade cidade1, cidade2;
		cidade1 = new Cidade();
		cidade1.setNome("Campinas");
		cidade1.setEstado(estadoRepo.buscar(1L));
		/* Retorna atribui um objeto persistido/menaged pelo repository, e com id 'autoincremented'. */
		cidade1 = cidadeRepo.gravar(cidade1);
		
		cidade2 = new Cidade();
		cidade2.setNome("uma cidade");
		cidade2.setEstado(estadoRepo.buscar(2L));
		/* Retorna atribui um objeto persistido/menaged pelo repository, e com id 'autoincremented'. */
		cidade2 = cidadeRepo.gravar(cidade2);
		
		System.out.println("\nListar depois, Cidades:");
		for (Cidade cidadeFor : cidadeRepo.listar()) {
			System.out.printf("\t%d: %s.\n", cidadeFor.getId(), cidadeFor.getNome());
		}
	}
}
