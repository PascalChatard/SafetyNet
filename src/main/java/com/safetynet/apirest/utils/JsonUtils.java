package com.safetynet.apirest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * DateUtil, fournit des methodes de type utilitaire autour d'une date sous la
 * forme d'un String et au format "MM/dd/yyyy".
 * 
 */

@Slf4j
public class JsonUtils {

	static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Formate l'objet Json passe en parametre sous la forme d'une String avec
	 * intentation.
	 * 
	 * @param json, objet Json a convertir.
	 * @return le Json sous forme de String avec indentation.
	 * 
	 */
	public static String indenteJson(final Object objJson) {
		String jsonIndente = new String();

		try {
			jsonIndente = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objJson);
		} catch (JsonProcessingException e) {

			log.error("JsonProcessingException, le format de date doit etre: \"MM/dd/yyyy\" \n" + e);
		}
		return jsonIndente;
	}

	/**
	 * Formate l'objet Json passe en parametre sous la forme d'une String sans
	 * intentation.
	 * 
	 * @param json, objet Json a convertir.
	 * @return le Json sous forme de String avec indentation.
	 * 
	 */
	public static String asJsonString(final Object objJson) {
		try {
			return objectMapper.writeValueAsString(objJson);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
