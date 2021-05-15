package dev.ronaldomarques.algafood.jpaexercicio;



import java.math.BigDecimal;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import dev.ronaldomarques.algafood.AlgafoodApiApplication;
import dev.ronaldomarques.algafood.domain.model.entity.Restaurante;
import dev.ronaldomarques.algafood.domain.model.repository.RestauranteRepository;



public class GravacaoRestauranteMain {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext =
				new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		
		// Erro intencional: RestauranteRepositoryImpl restauranteRepo = new RestauranteRepositoryImpl();
		// Teste ok: RestauranteRepository restauranteRepo =
		// applicationContext.getBean(RestauranteRepositoryImpl.class);
		// Correto Melhor:
		RestauranteRepository restauranteRepo = applicationContext.getBean(RestauranteRepository.class);
		
		System.out.println("Adicionar Restaurante:");
		
		Restaurante r1 = new Restaurante();
		r1.setNome("Susi's");
		r1.setTaxaFrete(new BigDecimal(7));
		
		Restaurante r2 = new Restaurante();
		r2.setNome("Sosso's");
		r2.setTaxaFrete(new BigDecimal(5.5));
		
		r1 = restauranteRepo.gravar(r1);
		r2 = restauranteRepo.gravar(r2);
		
		for (Restaurante forR : restauranteRepo.listar()) {
			System.out.printf("%d - %s : R$%.2f\n", forR.getId(), forR.getNome(), forR.getTaxaFrete());
		}
		
	}
	
}
