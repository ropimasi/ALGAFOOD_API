package dev.ronaldomarques.algafood.domain.exception;


public class EntidadeNaoPersistidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	
	public EntidadeNaoPersistidaException(String mensagem) {
		super("\nEntidade não Persistida:\n" + mensagem);
	}
}
