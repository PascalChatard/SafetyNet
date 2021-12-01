package com.safetynet.apirest.model;

import java.util.List;

import lombok.Data;

/**
 * ListHousholdsByFirestationAndAddress, bean permettant de mapper le numero de
 * station de pompier et la liste des foyers desservis par la caserne de
 * pompier. Necessaire depuis l'URL
 * http://localhost:8080/flood/stations?stations=<a list of station_numbers>
 *
 */
//@NoArgsConstructor
@Data
public class ListHousholdsByFirestationAndAddress {

	/**
	 * le numero de la caserne de pompier
	 *
	 */
	private String firestationNumber;

	/**
	 * liste des foyers desservis par la caserne de pompier
	 *
	 */
	private List<Household> households;
}
