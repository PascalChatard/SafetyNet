package com.safetynet.apirest.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ListHabitantsByStationNumber, bean permettant de mapper la liste des
 * habitants d'une adresse donnee et le numero de la caseren de pompier qui
 * dessert cette adresse. Necessaire depuis l'URL
 * http://localhost:8080/fire?address=<address>
 *
 */
@NoArgsConstructor
@Data
public class ListHabitantsByStationNumber {

	/**
	 * numero de caserne de pompier qui dessert l'adresse donnee
	 *
	 */
	private String stationNumber;

	/**
	 * liste des habitants de l'adresse donnee
	 *
	 */
	private List<IdentityMedicalRecord> habitants;

}
