package dev.ronaldomarques.algafood.jpaexercicio;



import java.util.List;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Permissao;
import dev.ronaldomarques.algafood.domain.model.repository.PermissaoRepository;



public class ListagemPermissaoMain {
	
	public static void main(String[] args) {
		
		ApplicationContext appContxt =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		PermissaoRepository permissaoRepo = appContxt.getBean(PermissaoRepository.class);
		List<Permissao> permissoes = permissaoRepo.listar();
		
		System.out.println("Lista de permissões:");
		
		for (Permissao forPermiss : permissoes) {
			System.out.printf("%d: %s: %s.\n", forPermiss.getId(), forPermiss.getNome(), forPermiss.getDescricao());
			
		}
		
	}
	
}
