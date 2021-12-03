package com.safetynet.apirest.apiservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Firestation;
import com.safetynet.apirest.model.Household;
import com.safetynet.apirest.model.IdentityMedicalRecord;
import com.safetynet.apirest.model.ListHousholdsByFirestationAndAddress;
import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.utils.DateUtils;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ServiceFlood, couche service du traitement de l'URL:
 * http://localhost:8080/flood/stations?stations=<a list of station_numbers>
 * 
 */

@Slf4j
@Service
public class FloodService {

	/**
	 * reference sur la structure de la base de donnees en memoire
	 * 
	 */

	private DataSrc dataSrc;

	@Autowired
	public FloodService(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}

	/**
	 * Recherche et construit la liste des foyers desservis par les casernes dont
	 * les numeros sont passes en argument. Les informations retenues sont classees
	 * par station et adresse et sont: nom, age, telephone et dossier medical.
	 * 
	 * @param stations, liste des numeros de station sur lesquelles doit se faire la
	 *        recherche
	 * @return la liste des personnes trouvees par caserne et par adresse
	 * 
	 */
	public List<ListHousholdsByFirestationAndAddress> listOfHousholdServedByStation(List<String> stations) {

		log.debug("Debut methode listOfHousholdServedByStation, arg: {}", stations);
		List<ListHousholdsByFirestationAndAddress> result = new ArrayList<ListHousholdsByFirestationAndAddress>();

		for (String station : stations) {
			// pour chaque station_number demandes creation d'une liste de foyers
			ListHousholdsByFirestationAndAddress houseHoldsByFirestation = new ListHousholdsByFirestationAndAddress();
			houseHoldsByFirestation.setFirestationNumber(station);
			List<Household> households = new ArrayList<Household>();

			// construit la liste des Firestation dont le station_numbers est: station
			List<Firestation> firestations = dataSrc.getFirestations().stream()
					.filter(firestation -> isStationNumberOfFirestation(firestation, station))
					.collect(Collectors.toList());

			firestations.forEach(firestation -> {
				log.debug("Une adresse trouvee ({}) desservie par la caserne n°({})", firestation.getAddress(),
						station);

				// creation d'un foyer pour cette adresse
				Household household = new Household();
				household.setAddress(firestation.getAddress());
				// creation d'une liste d'habitants pour ce foyer
				List<IdentityMedicalRecord> habitants = new ArrayList<IdentityMedicalRecord>();

				// construit la liste des Person qui habitent l'adresse desservie par la caserne
				List<Person> persons = dataSrc.getPersons().stream()
						.filter(person -> isPersonLiveAtThisAddress(person, firestation.getAddress()))
						.collect(Collectors.toList());

				persons.forEach(person -> {
					log.debug("Une personne trouvee vivant a l'adresse({}) desservie par la caserne n°({}) --> \n{}",
							firestation.getAddress(), station, JsonUtils.indenteJson(person));

					// creation d'un habitant pour ce foyer a cette adresse
					IdentityMedicalRecord habitant = new IdentityMedicalRecord();

					// construit la liste le dossier medical de Person dont l'adresse est deservie
					// par la station_numbers: station
					List<MedicalRecord> medicalRecords = dataSrc.getMedicalrecords().stream()
							.filter(medicalRecord -> isMedicalRecordOfThisPerson(medicalRecord, person))
							.collect(Collectors.toList());

					medicalRecords.forEach(medicalRecord -> {
						log.debug("Le dossier medical trouvee --> \n{}", JsonUtils.indenteJson(medicalRecord));

						// calcul de l'age depuis la Birthdate
						long years = DateUtils.getAge(medicalRecord.getBirthdate());
						habitant.setFirstName(person.getFirstName());
						habitant.setLastName(person.getLastName());
						habitant.setPhone(person.getPhone());
						habitant.setAge(Long.valueOf(years).toString());
						habitant.setMedications(medicalRecord.getMedications());
						habitant.setAllergies(medicalRecord.getAllergies());

						log.debug("Ajout de l'habitant a la liste -->\n{}", JsonUtils.indenteJson(habitant));
						habitants.add(habitant); // alimentation de la liste d'habitants

					});

				});

				household.setHabitants(habitants);// affecte la liste d'habitants a ce foyer
				households.add(household);// ajoute ce foyer a la liste des foyers pour cette firestation

			});

			houseHoldsByFirestation.setHouseholds(households);// affecte la liste des foyers pour cette firestation
			result.add(houseHoldsByFirestation);// ajoute la liste des foyers pour cette firestation
		}
		log.debug("Liste des foyers & personnes par adresse et par caserne: {} -->:\n{}", stations,
				JsonUtils.indenteJson(result));
		log.debug("Fin methode listOfHousholdServedByStation");
		return result;
	}

	private boolean isStationNumberOfFirestation(final Firestation firestation, final String stationNumber) {

		return (StringUtils.isNotEmpty(firestation.getStation()) && StringUtils.isNotEmpty(stationNumber)
				&& StringUtils.compareIgnoreCase(firestation.getStation(), stationNumber) == 0);
	}

	private boolean isPersonLiveAtThisAddress(final Person person, final String address) {

		return (StringUtils.isNotEmpty(person.getAddress()) && StringUtils.isNotEmpty(address)
				&& StringUtils.compareIgnoreCase(person.getAddress(), address) == 0);
	}

	private boolean isMedicalRecordOfThisPerson(final MedicalRecord medicalRecord, final Person person) {

		return (StringUtils.isNotEmpty(medicalRecord.getFirstName()) && StringUtils.isNotEmpty(person.getFirstName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getFirstName(), person.getFirstName()) == 0
				&& StringUtils.isNotEmpty(medicalRecord.getLastName()) && StringUtils.isNotEmpty(person.getLastName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getLastName(), person.getLastName()) == 0);
	}

}
