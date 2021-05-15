package dev.ronaldomarques.algafood.domain.exception;


public class EntidadeEmUsoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	
	public EntidadeEmUsoException(String mensagem) {
		super("\nViolação de integridade:\n" + mensagem);
	}
}
