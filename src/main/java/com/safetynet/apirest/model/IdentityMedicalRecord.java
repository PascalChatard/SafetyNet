package com.safetynet.apirest.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * IdentityMedicalRecord, bean permettant de mapper le numero de telephone d'une
 * personne. Necessaire depuis l'URL
 * http://localhost:8080/fire?address=<address>
 *
 */

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class IdentityMedicalRecord extends IdentityAge {

	/**
	 * numero de telephone de la personnee
	 * 
	 */
	private String phone;

	/**
	 * dossier medical liste des medicaments et posologie
	 * 
	 */
	private List<String> medications;

	/**
	 * dossier medical liste des medicaments et posologie
	 * 
	 */
	private List<String> allergies;
}
