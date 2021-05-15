package dev.ronaldomarques.algafood.domain.model.repository;



import java.util.List;

import dev.ronaldomarques.algafood.domain.model.entity.FormaPagamento;



public abstract interface FormaPagamentoRepository {
	
	public abstract FormaPagamento gravar(FormaPagamento formaPagamento);
	public abstract void remover(FormaPagamento formaPagamento);
	public abstract FormaPagamento buscarId(Long id);
	public abstract List<FormaPagamento> listar();
	
}
