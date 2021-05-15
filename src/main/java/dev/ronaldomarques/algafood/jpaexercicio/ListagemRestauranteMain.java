package dev.ronaldomarques.algafood.jpaexercicio;



import java.util.List;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Restaurante;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;



public class ListagemRestauranteMain {
	
	public static void main(String[] args) {
		
		ApplicationContext appCntxt =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		RestauranteRepository restauranteRepo = appCntxt.getBean(RestauranteRepository.class);
		List<Restaurante> restaurantes = restauranteRepo.listar();
		
		System.out.println("Lista de restaurantes:");
		
		for (Restaurante forRestaurante : restaurantes) {
			System.out.printf("\t%d: %s: (R$%.2f) %d.%s\n", forRestaurante.getId(), forRestaurante.getNome(),
					forRestaurante.getTaxaFrete(), forRestaurante.getCozinha().getId(),
					forRestaurante.getCozinha().getNome());
		}
		
	}
	
}
