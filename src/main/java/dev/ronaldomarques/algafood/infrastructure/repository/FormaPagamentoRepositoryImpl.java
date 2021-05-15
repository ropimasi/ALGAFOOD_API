package dev.ronaldomarques.algafood.infrastructure.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.ronaldomarques.algafood.domain.model.entity.FormaPagamento;
import dev.ronaldomarques.algafood.domain.model.repository.FormaPagamentoRepository;



@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {
	@PersistenceContext
	EntityManager manager;
	
	
	@Transactional
	@Override
	public FormaPagamento gravar(FormaPagamento formaPagamento) {
		return manager.merge(formaPagamento);
	}
	
	
	@Transactional
	@Override
	public void remover(FormaPagamento formaPagamento) {
		manager.remove(buscarId(formaPagamento.getId()));
	}
	
	
	@Override
	public FormaPagamento buscarId(Long id) {
		return manager.find(FormaPagamento.class, id);
	}
	
	
	@Override
	public List<FormaPagamento> listar() {
		return manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}
}
