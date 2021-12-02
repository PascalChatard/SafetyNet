package com.safetynet.apirest.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ListPersonsByFirestation, bean permettant de mapper la liste des personnes
 * couverte par la caserne de pompier dont le numero est donne ainsi que le
 * nombre d'enfants et le nombres d'adultes. Necessaire depuis l'URL
 * http://localhost:8080/firestation?stationNumber=<station_number>
 *
 */
@NoArgsConstructor
@Data
public class ListPersonsByFirestation {

	/**
	 * le nombre d'enfants
	 *
	 */
	private String childrenNumber;

	/**
	 * le nombre d'adultes
	 *
	 */
	private String adultsNumber;

	/**
	 * liste des personne couvertes par la caserne de pompier
	 *
	 */
	private List<IdentityAddrPhone> persons;
}
