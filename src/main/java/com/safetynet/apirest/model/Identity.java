package com.safetynet.apirest.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identity, bean permettant de mapper l'identite d'une personne. Necessaire
 * pour la ressource Person.
 * 
 */

@NoArgsConstructor
@Data
public class Identity {

	/**
	 * prenom de la personne, ne peut etre nul, doit etre compose de 3 a 20
	 * carateres.
	 * 
	 */
	@NotNull
	@Length(min = 3, max = 20, message = "Le prénom doit avoir une longueur de 3 a 20 caracteres")
	private String firstName;

	/**
	 * nom de la personne, ne peut etre nul, doit etre compose de 3 a 20 carateres.
	 * 
	 */
	@NotNull
	@Length(min = 3, max = 20, message = "Le nom doit avoir une longueur de 3 a 20 caracteres")
	private String lastName;

}