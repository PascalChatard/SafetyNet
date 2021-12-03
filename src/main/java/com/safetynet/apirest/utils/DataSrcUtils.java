package com.safetynet.apirest.utils;

import org.apache.commons.lang3.StringUtils;

import com.safetynet.apirest.model.Firestation;
import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.model.Person;

/**
 * DataSrcUtil, fournit des methodes de type utilitaire autour de la base de
 * donnees chargee en memoire dans une instance DatSrc.
 * 
 */

public class DataSrcUtils {

	/**
	 * isMatchedIdentity verifie la correspondance de l'identite, firstName et
	 * lastName, sur deux instances Person.
	 * 
	 * @param personA, premirere instance de l'objet Person
	 * @param personB, deuxieme instance de l'objet Person
	 * @return true si l'identite correspond
	 * 
	 */
	static public boolean isMatchedIdentity(final Person personA, final Person personB) {

		return StringUtils.isNotEmpty(personA.getFirstName()) && StringUtils.isNotEmpty(personB.getFirstName())
				&& StringUtils.compareIgnoreCase(personA.getFirstName(), personB.getFirstName()) == 0
				&& StringUtils.isNotEmpty(personA.getLastName()) && StringUtils.isNotEmpty(personB.getLastName())
				&& StringUtils.compareIgnoreCase(personA.getLastName(), personB.getLastName()) == 0;
	}

	/**
	 * isMatchedIdentity verifie la correspondance de l'identite, firstName et
	 * lastName, sur deux instances MedicalRecord.
	 * 
	 * @param medicalRecordA, premirere instance de l'objet MedicalRecord
	 * @param medicalRecordB, deuxieme instance de l'objet MedicalRecord
	 * @return true si l'identite correspond
	 * 
	 */
	static public boolean isMatchedIdentity(final MedicalRecord medicalRecordA, final MedicalRecord medicalRecordB) {

		return StringUtils.isNotEmpty(medicalRecordA.getFirstName())
				&& StringUtils.isNotEmpty(medicalRecordB.getFirstName())
				&& StringUtils.compareIgnoreCase(medicalRecordA.getFirstName(), medicalRecordB.getFirstName()) == 0
				&& StringUtils.isNotEmpty(medicalRecordA.getLastName())
				&& StringUtils.isNotEmpty(medicalRecordB.getLastName())
				&& StringUtils.compareIgnoreCase(medicalRecordA.getLastName(), medicalRecordB.getLastName()) == 0;
	}

	/**
	 * isStationNumberOfFirestation verifie la correspondance du numero de station
	 * sur l'instance Firestation.
	 * 
	 * @param firestation, instance de l'objet Firestation
	 * @param stationNumber, numero de station cible
	 * @return true si le numero de station correspond
	 * 
	 */
	static public boolean isStationNumberOfFirestation(final Firestation firestation, final String stationNumber) {

		return (StringUtils.isNotEmpty(firestation.getStation()) && StringUtils.isNotEmpty(stationNumber)
				&& StringUtils.compareIgnoreCase(firestation.getStation(), stationNumber) == 0);
	}

	/**
	 * isFirstNameAndLastNameOfThisPerson verifie la correspondance du nom et prenom
	 * sur l'instance Person.
	 * 
	 * @param firstNameParam, le prenom recherche
	 * @param lastNameParam, le nom recherche
	 * @param person, l'instance Person cible
	 * @return true si le nom et le prenom correspond
	 * 
	 */
	static public boolean isFirstNameAndLastNameOfThisPerson(final String firstNameParam, final String lastNameParam,
			final Person person) {

		return (StringUtils.isNotEmpty(person.getFirstName()) && StringUtils.isNotEmpty(firstNameParam)
				&& StringUtils.compareIgnoreCase(person.getFirstName(), firstNameParam) == 0
				&& StringUtils.isNotEmpty(person.getLastName()) && StringUtils.isNotEmpty(lastNameParam)
				&& StringUtils.compareIgnoreCase(person.getLastName(), lastNameParam) == 0);
	}

	/**
	 * isMedicalRecordOfThisPerson verifie que le MedicalRecord est le dossier
	 * medical de Person.
	 * 
	 * @param medicalRecord, l'instance de MedicalRecord a traiter
	 * @param person, l'instance de Person dont on cherche le MedicalRecord
	 * @return true si le dossier medical est celui de person
	 * 
	 */
	static public boolean isMedicalRecordOfThisPerson(final MedicalRecord medicalRecord, final Person person) {

		return (StringUtils.isNotEmpty(medicalRecord.getFirstName()) && StringUtils.isNotEmpty(person.getFirstName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getFirstName(), person.getFirstName()) == 0
				&& StringUtils.isNotEmpty(medicalRecord.getLastName()) && StringUtils.isNotEmpty(person.getLastName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getLastName(), person.getLastName()) == 0);
	}

	/**
	 * isPersonLiveAtThisAddress verifie que address correspond a l'adresse de
	 * Person.
	 * 
	 * @param person, l'instance de Person dont on veut verifier l'adresse
	 * @param address, l'adresse recherchee
	 * @return true si l'adresse de Person correspond
	 * 
	 */
	static public boolean isPersonLiveAtThisAddress(final Person person, final String address) {

		return (StringUtils.isNotEmpty(person.getAddress()) && StringUtils.isNotEmpty(address)
				&& StringUtils.compareIgnoreCase(person.getAddress(), address) == 0);
	}

	/**
	 * isFirestationServeThisAddress verifie que address correspond a l'adresse de
	 * Firestation.
	 * 
	 * @param firestation, l'instance de Firestation dont on veut verifier l'adresse
	 * @param address, l'adresse recherchee
	 * @return true si l'adresse de Firestation correspond
	 * 
	 */
	static public boolean isFirestationServeThisAddress(final Firestation firestation, final String address) {

		return (StringUtils.isNotEmpty(firestation.getAddress()) && StringUtils.isNotEmpty(address)
				&& StringUtils.compareIgnoreCase(firestation.getAddress(), address) == 0);
	}

	/**
	 * isCityOfThisPerson verifie que city correspond a la cite de l'instance de
	 * Person.
	 * 
	 * @param person, l'instance de Person dont on veut verifier la cite
	 * @param address, l'adresse recherchee
	 * @return true si la cite de Person correspond
	 * 
	 */
	static public boolean isCityOfThisPerson(Person person, String city) {

		return StringUtils.isNotEmpty(person.getCity()) && StringUtils.compareIgnoreCase(person.getCity(), city) == 0;
	}

}
