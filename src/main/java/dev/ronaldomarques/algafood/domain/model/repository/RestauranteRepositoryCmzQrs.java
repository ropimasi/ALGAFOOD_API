package dev.ronaldomarques.algafood.domain.model.repository;

import java.math.BigDecimal;
import java.util.List;
import dev.ronaldomarques.algafood.domain.model.entity.RestauranteEntity;



public interface RestauranteRepositoryCmzQrs {
	
	List<RestauranteEntity> buscarCozinhaFreteMenor(String cozinhaNome, BigDecimal taxaFreteMenor);
	
}
