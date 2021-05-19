/**
 * DIREITOS, LICENSA E ISENÇÃO DE RESPONSABILIDADE:
 * Este arquivo é parte integrante, indivisível, inseparável de um projeto particular, o qual tem
 * seu uso expressamente exclusivo à seu autor, Ronaldo Marques (ronaldomarques@email.com ,
 * http://ronaldomarques.dev);
 * É vetado qualquer utilização, venda, aluguél, distribuição, em partes ou integral deste projeto;
 * Este projeto tem finalidade exclusiva de demonstração de conhecimento e habilidades no
 * desenvolvimento de software para apresentação de portfólio e processos de recrutamento;
 * Sendo assim, o autor deste projeto (Ronaldo Marques) não reconhece nem assume qualquer
 * responsabilidade pela utilização do mesmo, tão pouco por qualquer possível reflexos ou
 * consequência de tal utilização.
 * ---
 * RIGHTS, LICENSE AND DISCLAIMER:
 * This file is an integral, indivisible, inseparable part of a particular project, which has its
 * use expressly exclusive to its author, Ronaldo Marques (ronaldomarques@email.com ,
 * http://ronaldomarques.dev);
 * Any use, sale, rental, distribution, in parts or integral of this project is prohibited;
 * This project has the sole purpose of demonstrating knowledge and skills in software development
 * for portfolio presentations and recruitment processes;
 * Therefore, the author of this project (Ronaldo Marques) does not recognize or assume any
 * responsibility for the use of it, neither for any possible reflexes or consequence of such use.
 */
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
