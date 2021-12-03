package com.safetynet.apirest.apiservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Firestation;
import com.safetynet.apirest.model.IdentityMedicalRecord;
import com.safetynet.apirest.model.ListHabitantsByStationNumber;
import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.utils.DateUtils;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ServiceFlood, couche service du traitement de l'URL:
 * http://localhost:8080/fire?address=<address>
 * 
 */

@Slf4j
@Service
public class FireService {

	/**
	 * reference sur la structure de la base de donnees en memoire
	 * 
	 */

	private DataSrc dataSrc;

	@Autowired
	public FireService(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}

	/**
	 * Recherche et construit la liste des habitants vivant a l'adresse donnee ainsi
	 * que le numero de la caserne de pompier la desservant. Les informations
	 * retenues sont: nom, age, numero de telephone et dossier medical.
	 * 
	 * @param address, l'adresse sur laquelle doit se faire la recherche
	 * @return la liste des personnes trouvees et le numero de la caserne
	 * 
	 */
	public ListHabitantsByStationNumber listHabitantsOfAddress(String address) {

		log.debug("Debut methode listHabitantsOfAddress, arg: ({})", address.isEmpty() ? "vide" : address);

		ListHabitantsByStationNumber listHabitantsByAddress = new ListHabitantsByStationNumber();
		List<IdentityMedicalRecord> habitants = new ArrayList<IdentityMedicalRecord>();

		// recherche le numero de la firestation qui dessert cette adresse
		Optional<String> optionalStationNumber = dataSrc.getFirestations().stream()
				.filter(firestation -> isFirestationServeThisAddress(firestation, address)).map(Firestation::getStation)
				.findFirst();

		// recupere le numero de station ou affecte le String vide
		String stationNumber = optionalStationNumber.isPresent() ? optionalStationNumber.get() : "";

		log.debug("Une caserne trouvee ({}) desservant cette adresse: {}",
				stationNumber.isEmpty() ? "vide" : stationNumber, address.isEmpty() ? "vide" : address);

		// recherche les Person(s) habitant cette adresse
		List<Person> persons = dataSrc.getPersons().stream()
				.filter(person -> person.getAddress().contentEquals(address)).collect(Collectors.toList());

		// recherche le dossier medical de chaque personne habitant cette adresse
		persons.forEach(person -> {
			dataSrc.getMedicalrecords().stream()
					.filter(medicalRecord -> isMedicalRecordOfThisPerson(medicalRecord, person))
					.forEach(medicalRecord -> {
						log.debug("Dossier medical de la personne trouvee --> {}",
								JsonUtils.indenteJson(medicalRecord));

						// calcul de l'age depuis la Birthdate
						long years = DateUtils.getAge(medicalRecord.getBirthdate());
						log.debug("Calcul de l'age de l'individu--> {}", years);

						IdentityMedicalRecord habitant = new IdentityMedicalRecord();
						habitant.setFirstName(person.getFirstName());
						habitant.setLastName(person.getLastName());
						habitant.setPhone(person.getPhone());
						habitant.setAge(Long.valueOf(years).toString());
						habitant.setMedications(medicalRecord.getMedications());
						habitant.setAllergies(medicalRecord.getAllergies());
						habitants.add(habitant);
						log.debug("Creation d'un habitant vivant a l'adresse: {} --> \n{}", address,
								JsonUtils.indenteJson(habitant));
					});
		});

		listHabitantsByAddress.setStationNumber(stationNumber);
		listHabitantsByAddress.setHabitants(habitants);
		log.debug("Les habitants de l'adresse:({}) sont -->\n{}", address.isEmpty() ? "vide" : address,
				JsonUtils.indenteJson(listHabitantsByAddress));
		log.debug("Fin methode listHabitantsOfAddress");
		return listHabitantsByAddress;
	}

	private boolean isFirestationServeThisAddress(Firestation firestation, String address) {

		return (StringUtils.isNotEmpty(firestation.getAddress()) && StringUtils.isNotEmpty(address)
				&& StringUtils.compareIgnoreCase(firestation.getAddress(), address) == 0);
	}

	private boolean isMedicalRecordOfThisPerson(final MedicalRecord medicalRecord, final Person person) {

		return (StringUtils.isNotEmpty(medicalRecord.getFirstName()) && StringUtils.isNotEmpty(person.getFirstName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getFirstName(), person.getFirstName()) == 0
				&& StringUtils.isNotEmpty(medicalRecord.getLastName()) && StringUtils.isNotEmpty(person.getLastName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getLastName(), person.getLastName()) == 0);
	}
}
