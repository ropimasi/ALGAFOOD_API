package dev.ronaldomarques.algafood.infrastructure.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.ronaldomarques.algafood.domain.model.entity.Permissao;
import dev.ronaldomarques.algafood.domain.model.repository.PermissaoRepository;



@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {
	@PersistenceContext
	private EntityManager manager;
	
	
	@Transactional
	@Override
	public Permissao gravar(Permissao permissao) {
		return manager.merge(permissao);
	}
	
	
	@Transactional
	@Override
	public void remover(Permissao permissao) {
		manager.remove(buscarId(permissao.getId()));
	}
	
	
	@Override
	public Permissao buscarId(Long id) {
		return manager.find(Permissao.class, id);
	}
	
	
	@Override
	public List<Permissao> listar() {
		return manager.createQuery("from Permissao", Permissao.class).getResultList();
	}
}
