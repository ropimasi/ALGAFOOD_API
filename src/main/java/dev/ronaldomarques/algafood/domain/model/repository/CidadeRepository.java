package dev.ronaldomarques.algafood.domain.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.ronaldomarques.algafood.domain.model.entity.CidadeEntity;



@Repository
public interface CidadeRepository extends JpaRepository<CidadeEntity, Long> {
	/* Este ERA um 'repositório orientado à persistencia': 'POR'.
	 * Quando um 'repository' é orientado à 'collection' ele tem como uma das abstrações imitar uma 'collection'. E os
	 * tipos 'collections' seguem padrões de nomenclatura próprios, por exemplo:
	 * public abstract RestauranteEntity guardar(RestauranteEntity restaurante);
	 * public abstract void eliminar(RestauranteEntity restaurante);
	 * public abstract RestauranteEntity porId(Long id);
	 * public abstract List<RestauranteEntity> todos(); */
	
	/* Métodos comentados para dar lugar ao modo JpaHibernate: */
	/* public abstract CidadeEntity gravar(CidadeEntity cidade);
	 * public abstract CidadeEntity remover(Long cidade);
	 * public abstract CidadeEntity buscar(Long id);
	 * public abstract List<CidadeEntity> listar(); */
	
}
