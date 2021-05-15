package dev.ronaldomarques.algafood.infrastructure.service;


import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.util.ReflectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;




public final class MescladorAtributos {
	
	/* Método auxiliar para o método atualizarParcial(id, atributosValores); */
	public static void mesclarAtributos(Map<String, Object> mapAtributosValoresOrigem, Object entidadeDestino) {
		/* Didático: Aqui os valores dos atributos estão em formatos/tipo de ObjectMap, muito trabalhoso converter
		 * individualmente para os outros tipos definitivos dos atributos do objeto destino (ex: String BigDecimal,
		 * Record etc.). Então, Usarei conversão do objeto todo de uma vez com ajuda da LIB com.fastxml.jackson. */
		ObjectMapper objectMapper = new ObjectMapper();
		Object entidadeOrigem = objectMapper.convertValue(mapAtributosValoresOrigem, entidadeDestino.getClass());
		
		mapAtributosValoresOrigem.forEach((nomeDaPropriedade, valorDaPropriedade) -> {
			Field field = ReflectionUtils.findField(entidadeDestino.getClass(), nomeDaPropriedade);
			field.setAccessible(true); // Para tirá-lo do estado 'private' declarado em sua entity class.
			ReflectionUtils.setField(field, entidadeDestino, ReflectionUtils.getField(field, entidadeOrigem));
		});
	}
}
