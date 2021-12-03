package com.safetynet.apirest.apiservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.model.PersonInfo;
import com.safetynet.apirest.utils.DateUtils;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ServiceInfoPerson, couche service du traitement de l'URL:
 * http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
 * 
 */

@Slf4j
@Service
public class PersonInfoService {

	/**
	 * reference sur la structure de la base de donnees en memoire
	 * 
	 */

	private DataSrc dataSrc;

	@Autowired
	public PersonInfoService(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}

	/**
	 * Recherche et construit la liste des personnes dont le nom et prenom
	 * correspondent aux parametres donnes en argument. Les informations retenus
	 * sont: nom, age, adresse, email et dossier medical.
	 * 
	 * @param firstNameParam, prenom recherche
	 * @param lastNameParam, nom recherche
	 * @return la liste des personnes trouvees
	 * 
	 */
	public List<PersonInfo> getNameAdrAgeEmailAndMedicalRecord(String firsNameParam, String lastNameParam) {

		log.debug("Debut methode getNameAdrAgeEmailAndMedicalRecord, arg: {}, {}", firsNameParam, lastNameParam);

		List<PersonInfo> personInfos = new ArrayList<PersonInfo>();

		// recherche la/les Person(s) ayant l'identite firsNameParam et lastNameParam
		List<Person> persons = dataSrc.getPersons().stream()
				.filter(person -> isFirstNameAndLastNameOfThisPerson(firsNameParam, lastNameParam, person))
				.collect(Collectors.toList());
		log.debug("Liste des Person trouve avec prenom ({}) et nom ({}):\n{}", firsNameParam, lastNameParam, persons);

		// recherche le dossier medical de la/les Person(s) dont le firstName et le
		// lastName sont: firsNameParam et lastNameParam
		persons.forEach((Person person) -> {
			dataSrc.getMedicalrecords().stream().filter(medicRcd -> isMedicalRecordOfThisPerson(medicRcd, person))
					.forEach(medicRcd -> {
						log.debug("Le dossier medical est trouve --> \n{}", JsonUtils.indenteJson(medicRcd));

						// calcul de l'age depuis la Birthdate
						long years = DateUtils.getAge(medicRcd.getBirthdate());
						log.debug("Calcul de l'age de l'individu--> {}", years);

						// instancie resultat PersonInfo et ajout a la liste
						PersonInfo personInfo = PersonInfo.builder().lastName(lastNameParam)
								.address(person.getAddress()).age(Long.valueOf(years).toString())
								.email(person.getEmail()).medications(medicRcd.getMedications())
								.allergies(medicRcd.getAllergies()).build();
						personInfos.add(personInfo);
						log.debug("Ajout d'un PersonInfo la liste -->\n{}", JsonUtils.indenteJson(personInfo));

					});
		});

		log.debug("Liste des personnes ayant comme firstname et lastname ({}, {}) -->:\n{}", firsNameParam,
				lastNameParam, JsonUtils.indenteJson(personInfos));
		log.debug("Fin methode getNameAdrAgeEmailAndMedicalRecord");
		return personInfos;
	}

	private boolean isFirstNameAndLastNameOfThisPerson(final String firstNameParam, final String lastNameParam,
			final Person person) {

		return (StringUtils.isNotEmpty(person.getFirstName()) && StringUtils.isNotEmpty(firstNameParam)
				&& StringUtils.compareIgnoreCase(person.getFirstName(), firstNameParam) == 0
				&& StringUtils.isNotEmpty(person.getLastName()) && StringUtils.isNotEmpty(lastNameParam)
				&& StringUtils.compareIgnoreCase(person.getLastName(), lastNameParam) == 0);
	}

	private boolean isMedicalRecordOfThisPerson(final MedicalRecord medicalRecord, final Person person) {

		return (StringUtils.isNotEmpty(medicalRecord.getFirstName()) && StringUtils.isNotEmpty(person.getFirstName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getFirstName(), person.getFirstName()) == 0
				&& StringUtils.isNotEmpty(medicalRecord.getLastName()) && StringUtils.isNotEmpty(person.getLastName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getLastName(), person.getLastName()) == 0);
	}

}
