package com.safetynet.apirest.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Firestation, ressource de l'Api Rest. URI:/firestation
 * 
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Firestation {

	/**
	 * adresse deservie par la caserne de pompier dont le numero de station est:
	 * "station"
	 */
	@NotNull
	@NotEmpty
	private String address;

	/**
	 * numero de la caserne de pompier qui dessert l'adresse: "address"
	 */
	@NotNull
	@NotEmpty
	private String station;

}
