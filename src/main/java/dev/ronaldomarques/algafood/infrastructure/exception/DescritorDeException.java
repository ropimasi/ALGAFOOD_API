package dev.ronaldomarques.algafood.infrastructure.exception;


public final class DescritorDeException {
	public static String descreverExcecao(Exception excep) {
		return "--------------------------------\n"
				+ "Exceção: " + excep.getClass().getName() + "\n"
				+ "--------------------------------\n"
				+ excep.getMessage() + "\n"
				+ "--------------------------------\n";
	}
	
	
	
	public static String descreverInesperadaException(Exception excep) {
		return "--------------------------------\n"
				+ "####  EXCEÇÃO  INESPERADA!  ####\n"
				+ descreverExcecao(excep);
	}
}
