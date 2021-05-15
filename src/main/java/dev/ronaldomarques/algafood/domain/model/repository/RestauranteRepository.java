package dev.ronaldomarques.algafood.domain.model.repository;

import java.util.List;

import dev.ronaldomarques.algafood.domain.model.entity.Restaurante;

/* Este é um 'repositório orientado à persistencia': 'POR'. */

/* Quando um 'repository' é orientado à 'collection' ele tem como uma das abstrações imitar uma 'collection'. E os
 * tipos 'collections' seguem padrões de nomenclatura próprios, por exemplo:
 * public abstract Restaurante guardar(Restaurante restaurante);
 * public abstract void eliminar(Restaurante restaurante);
 * public abstract Restaurante porId(Long id);
 * public abstract List<Restaurante> todos(); */



public abstract interface RestauranteRepository {
	public abstract Restaurante gravar(Restaurante restaurante);
	public abstract Restaurante remover(Long id);
	public abstract Restaurante buscar(Long id);
	public abstract List<Restaurante> listar();
}
