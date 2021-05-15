package dev.ronaldomarques.algafood.infrastructure.exception;


public class PercistenciaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	
	public PercistenciaException(String mensagem) {
		super("\nViolação de integridade com 'Entidade' ou 'Atributo' inadequados:\n" + mensagem);
	}
}
