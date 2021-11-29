package com.safetynet.apirest.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Person, ressource de l'Api Rest. URI:/person
 * 
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Person {

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

	/**
	 * adresse du domicile d'une personne.
	 * 
	 */
	@NotNull
	@NotEmpty
	private String address;

	/**
	 * numero de telephone d'une personne.
	 * 
	 */
	@NotNull
	@NotEmpty
	private String phone;

	/**
	 * ville de résidence de la personne
	 * 
	 */
	@NotNull
	@NotEmpty
	private String city;

	/**
	 * code postal de la residence
	 * 
	 */
	@NotNull
	@NotEmpty
	private String zip;

	/**
	 * adresse mail de la personne
	 * 
	 */
	@NotNull
	@NotEmpty
	@Email
	private String email;

}
