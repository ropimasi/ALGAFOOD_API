package dev.ronaldomarques.algafood.domain.model.repository;



import java.util.List;

import dev.ronaldomarques.algafood.domain.model.entity.Permissao;



public abstract interface PermissaoRepository {
	
	public abstract Permissao gravar(Permissao permissao);
	public abstract void remover(Permissao permissao);
	public abstract Permissao buscarId(Long id);
	public abstract List<Permissao> listar();
	
}
