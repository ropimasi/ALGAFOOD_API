package dev.ronaldomarques.algafood.jpaexercicio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.CidadeEntity;
import dev.ronaldomarques.algafood.domain.model.repository.CidadeRepository;



public class AlteracaoCidadeMain {
	public static void main(String[] args) {
		ApplicationContext appContxt =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		CidadeRepository cidadeRepo = appContxt.getBean(CidadeRepository.class);
		
		System.out.println("\nListar primenramente, Cidade:");
		
		for (Cidade cidadeFor : cidadeRepo.listar()) {
			System.out.printf("\t%d: %s.\n", cidadeFor.getId(), cidadeFor.getNome());
		}
		
		System.out.println("Alterar Cidade:");
		/* Criar objetos exemplos para alterar. */
		Cidade cidadeAlt = new Cidade();
		cidadeAlt.setId(4L);
		cidadeAlt.setNome("JoinVille");
		/* Persistir o objeto exemplo com ID já pré-definido, ira sobrescrever o
		 * bjeto persistido anteriormente com este mesmo ID. */
		cidadeRepo.gravar(cidadeAlt);
		
		/* Printar os objetos persistidos com 'id' pra conferir. */
		System.out.println("\nListar depois, Cidades:");
		
		for (Cidade cidadeFor : cidadeRepo.listar()) {
			System.out.printf("\t%d: %s.\n", cidadeFor.getId(), cidadeFor.getNome());
		}
	}
}
