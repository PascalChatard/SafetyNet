package com.safetynet.apirest.model;

import lombok.Builder;
import lombok.Data;

/**
 * ExceptionMessage, structure des messages retournes suite a la remontee d'une
 * exception depuis un controleur et une requete HTTP.
 * 
 */

@Data
@Builder
public class ExceptionMessage {

	/**
	 * date de la requete source de l'exception
	 */
	private String date;

	/**
	 * Status de la requete
	 */
	private String status;

	/**
	 * erreur
	 */
	private String error;

	/**
	 * inditule de l'exception
	 */
	private String exception;

	/**
	 * descriptif de l'exception
	 */
	private String message;

	/**
	 * chemin d'origine de la requete
	 */
	private String path;
}