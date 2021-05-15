package dev.ronaldomarques.algafood.infrastructure.exception;


public class ArgumentoIlegalException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	
	public ArgumentoIlegalException(String mensagem) {
		super("\nEXCEÇÃO Argumento ilegal: " + mensagem);
	}
}
