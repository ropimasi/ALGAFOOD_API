package dev.ronaldomarques.algafood.domain.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.ronaldomarques.algafood.domain.model.entity.FormaPagamentoEntity;



@Repository
public abstract interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoEntity, Long> {
	/* Este ERA um repositório orientado à persistencia: 'POR'. */
	/* Quando um 'repository' é orientado à 'collection' ele tem como uma das abstrações imitar uma 'collection'. E os
	 * tipos 'collections' seguem padrões de nomenclatura próprios, por exemplo:
	 * public abstract CozinhaEntity guardar(CozinhaEntity cozinha);
	 * public abstract void eliminar(CozinhaEntity cozinha);
	 * public abstract CozinhaEntity porId(Long id);
	 * public abstract List<CozinhaEntity> todas(); */
	
	/* Métodos comentados para dar lugar ao modo JpaHibernate: */
	/* public abstract FormaPagamentoEntity gravar(FormaPagamentoEntity formaPagamentoEntity);
	 * public abstract void remover(FormaPagamentoEntity formaPagamentoEntity);
	 * public abstract FormaPagamentoEntity buscarId(Long id);
	 * public abstract List<FormaPagamentoEntity> listar(); */
	
}
