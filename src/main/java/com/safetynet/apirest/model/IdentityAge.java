package com.safetynet.apirest.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * IdentityAge, bean permettant de mapper l'age d'une personne. Necessaire pour
 * la ressource Person.
 * 
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class IdentityAge extends Identity {

	/**
	 * age de la personne.
	 * 
	 */
	@NotNull
	@NotEmpty
	private String age;
}
