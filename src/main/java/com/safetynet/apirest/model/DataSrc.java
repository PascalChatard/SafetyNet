package com.safetynet.apirest.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Structure des donnees sources alimentee depuis le fichier data.json. Une
 * instance unique est charges en memoire et sert de base de donnees.
 * 
 */

@NoArgsConstructor
@Data
public class DataSrc {

	/**
	 * liste des elements de la ressource Person
	 */
	private List<Person> persons;

	/**
	 * liste des elements de la ressource Firestation
	 */
	private List<Firestation> firestations;

	/**
	 * liste des elements de la ressource MedicalRecord
	 */
	private List<MedicalRecord> medicalrecords;

}
