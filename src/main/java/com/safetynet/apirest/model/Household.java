package com.safetynet.apirest.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Household, bean permettant de lister les personnes d'un foyer. Necessaire
 * depuis URL http://localhost:8080/flood/stations?stations=<a list of
 * station_numbers>
 * 
 */

@NoArgsConstructor
@Data
public class Household {

	/**
	 * adresse du foyer
	 */
	private String address;

	/**
	 * liste des personnes habitant a l'adresse de ce foyer
	 */
	private List<IdentityMedicalRecord> habitants;

}
