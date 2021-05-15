package dev.ronaldomarques.algafood.domain.model.repository;

import java.util.List;

import dev.ronaldomarques.algafood.domain.model.entity.Cozinha;

/* Este é um repositório orientado à persistencia: 'POR'. */

/* Quando um 'repository' é orientado à 'collection' ele tem como uma das abstrações imitar uma 'collection'. E os
 * tipos 'collections' seguem padrões de nomenclatura próprios, por exemplo:
 * public abstract Cozinha guardar(Cozinha cozinha);
 * public abstract void eliminar(Cozinha cozinha);
 * public abstract Cozinha porId(Long id);
 * public abstract List<Cozinha> todas(); */



public interface CozinhaRepository {
	public abstract Cozinha gravar(Cozinha cozinha);
	public abstract Cozinha remover(Long id);
	public abstract Cozinha pegar(Long id);
	public abstract List<Cozinha> listar();
}
