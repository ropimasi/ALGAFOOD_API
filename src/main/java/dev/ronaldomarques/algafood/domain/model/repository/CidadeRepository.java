package dev.ronaldomarques.algafood.domain.model.repository;

import java.util.List;

import dev.ronaldomarques.algafood.domain.model.entity.Cidade;

/* Este é um 'repositório orientado à persistencia': 'POR'. */

/* Quando um 'repository' é orientado à 'collection' ele tem como uma das abstrações imitar uma 'collection'. E os
 * tipos 'collections' seguem padrões de nomenclatura próprios, por exemplo:
 * public abstract Restaurante guardar(Restaurante restaurante);
 * public abstract void eliminar(Restaurante restaurante);
 * public abstract Restaurante porId(Long id);
 * public abstract List<Restaurante> todos(); */



public abstract interface CidadeRepository {
	public abstract Cidade gravar(Cidade cidade);
	public abstract Cidade remover(Long cidade);
	public abstract Cidade buscar(Long id);
	public abstract List<Cidade> listar();
}
