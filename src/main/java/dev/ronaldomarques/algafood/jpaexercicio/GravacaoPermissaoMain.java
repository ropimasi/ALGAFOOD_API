package dev.ronaldomarques.algafood.jpaexercicio;



import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Permissao;
import dev.ronaldomarques.algafood.domain.model.repository.PermissaoRepository;



public class GravacaoPermissaoMain {
	
	public static void main(String[] args) {
		
		ApplicationContext appContxt =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		PermissaoRepository permissaoRepo = appContxt.getBean(PermissaoRepository.class);
		
		System.out.println("Lista inicial:");
		for (Permissao forPermissao : permissaoRepo.listar()) {
			System.out.printf("%d: %s: %d.\n", forPermissao.getId(), forPermissao.getNome(),
					forPermissao.getDescricao());
		}

		System.out.println("Adicionar Permissao1...");
		Permissao permissao1 = new Permissao();
		permissao1.setDescricao("Zelador");
		permissaoRepo.gravar(permissao1);
		
		System.out.println("Adicionar Permissao2...");
		Permissao permissao2 = new Permissao();
		permissao2.setDescricao("TRANSFERENCIA");
		permissaoRepo.gravar(permissao2);
		
		System.out.println("Lista final:");
		
		
	}
	
}
