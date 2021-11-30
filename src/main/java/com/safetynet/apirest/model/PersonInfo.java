package com.safetynet.apirest.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PersonInfo, bean permettant de mapper la liste des personnes et leur dossier
 * medical correspondant au nom et au prenom donnes. Necessaire depuis l'URL
 * http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonInfo {

	/**
	 * nom de la personne cherche
	 *
	 */
	private String lastName;

	/**
	 * adresse domicile de la personne trouve
	 *
	 */
	private String address;

	/**
	 * age de la personne trouve
	 *
	 */
	private String age;

	/**
	 * email de la personne trouve
	 *
	 */
	private String email;

	/**
	 * dossier medical medicaments et posologie de la personne trouve
	 *
	 */
	private List<String> medications;

	/**
	 * dossier medical alergies de la personne trouve
	 *
	 */
	private List<String> allergies;
}
