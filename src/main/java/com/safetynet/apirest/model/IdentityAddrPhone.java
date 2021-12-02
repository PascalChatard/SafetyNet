package com.safetynet.apirest.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IdentityAddrPhone, bean permettant de mapper l'adresse et le telephone d'une
 * personne. Necessaire pour la ressource Person.
 * 
 */
//@NoArgsConstructor
//@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class IdentityAddrPhone extends Identity {

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
}
