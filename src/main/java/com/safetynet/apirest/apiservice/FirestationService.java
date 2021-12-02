package com.safetynet.apirest.apiservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.apirest.apirepository.FirestationRepository;
import com.safetynet.apirest.exception.NullDataSrcException;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Firestation;
import com.safetynet.apirest.model.IdentityAddrPhone;
import com.safetynet.apirest.model.ListPersonsByFirestation;
import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.utils.DateUtils;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ServicePerson, couche service du traitement du endpoint:
 * http://localhost:8080/firestation. Traitement de l'URL:
 * http://localhost:8080/firestation?stationNumber=<station_number>
 * 
 */

@Slf4j
@Service
public class FirestationService {

	/**
	 * injection de l'instance du bean du composant repository de traitement de la
	 * ressource Firestation
	 * 
	 */
	@Autowired
	private FirestationRepository repositoryFirestation;

	/**
	 * injection de l'instance du bean de la structure de la base de donnees
	 * "memoire"
	 * 
	 */

	private DataSrc dataSrc;

	@Autowired
	public FirestationService(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}

	/**
	 * Supprime l'objet Firestation positionne a l'index id, appelle la methode de
	 * la couche repository.
	 * 
	 * @param id, le numero d'index de la caserne de pompier a supprimer de la base
	 *        de donnees
	 * @return une reference a l'objet supprime dans dataSrc
	 * 
	 */
	public Firestation serviceDeleteById(int id)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		log.debug("Debut methode serviceDeleteByIdFirestation, arg: {}", id);

		Firestation firestation = repositoryFirestation.deleteById(id);
		log.debug("Succes suppression Firestation d'index ({})  Firestation --> \n{}", id,
				JsonUtils.indenteJson(firestation));

		log.debug("Fin methode serviceDeleteByIdFirestation");
		return firestation;
	}

	/**
	 * Ajoute l'objet Firestation dans le DataSrc dataSrc, appelle la methode de la
	 * couche repository.
	 * 
	 * @param firestation, l'objet Firestation a ajouter dans la base de donnees
	 * @return une reference a l'objet ajouter dans dataSrc
	 * 
	 */
	public Firestation serviceSave(Firestation firestation) throws NullPointerException, NullDataSrcException {

		log.debug("Debut methode serviceSaveFirestation, arg: {}", firestation);

		Firestation newFirestation = repositoryFirestation.save(firestation);
		log.debug("Succes ajout de la Firestation --> \n{}", JsonUtils.indenteJson(newFirestation));

		log.debug("Fin methode serviceSaveFirestation");
		return newFirestation;
	}

	/**
	 * Update l'objet Firestation positionne a l'index id dans le DataSrc dataSrc,
	 * appelle la methode de la couche repository.
	 * 
	 * @param id, index de l'objet Firestation a modifier
	 * @param firestation, l'objet Firestation a modifier dans la base de donnees
	 * @return une reference a l'objet modifier dans dataSrc
	 * 
	 */
	public Firestation serviceUpdate(int id, Firestation firestation)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		log.debug("Debut methode serviceUpdateFirestation, arg: {}", firestation);

		Firestation updateFirestation = repositoryFirestation.update(id, firestation);
		log.debug("Succes modification Firestation d'index({}) --> \n{}", id, JsonUtils.indenteJson(updateFirestation));

		log.debug("Fin methode serviceUpdateFirestation");
		return updateFirestation;
	}

	/**
	 * Recherche et construit la liste des personnes desservies par la caserne dont
	 * le numero est passe en argument. Les informations retenues sont: nom, prenom,
	 * adresse et numero de telephone, ainsi que le nombre d'enfants et le nombre
	 * d'adultes.
	 * 
	 * @param station, le numero de station sur laquelle doit se faire la recherche
	 * @return la liste des personnes trouvees et le decompte enfants/adultes
	 * 
	 */
	public ListPersonsByFirestation listOfPersonsCoverByFirestation(String station) {

		log.debug("Debut methode listOfPersonsCoverByFirestation, arg: {}", station);

		ListPersonsByFirestation listPersons = new ListPersonsByFirestation();
		listPersons.setPersons(new ArrayList<IdentityAddrPhone>());
		Integer numberOfChildren = 0;
		Integer numberOfAdults = 0;

		// construit la liste des adresses desservies par la caserne dont le
		// sation_number est station
		List<Firestation> firestations = dataSrc.getFirestations().stream()
				.filter(firestation -> isStationNumberOfFirestation(firestation, station)).collect(Collectors.toList());

		for (Firestation firestation : firestations) {

			log.debug("Adresse trouvee ({}) desservie par la caserne n°({})", firestation.getAddress(), station);

			// construit la liste des Person(s) dont l'adresse correspond a la Firestation
			// dont station_number = station
			List<Person> persons = dataSrc.getPersons().stream()
					.filter(person -> isPersonLiveAtThisAddress(person, firestation.getAddress()))
					.collect(Collectors.toList());

			for (Person person : persons) {

					// ajoute une Person couverte par la Firestation dont station_number = station
					IdentityAddrPhone personCover = new IdentityAddrPhone();
					personCover.setFirstName(person.getFirstName());
					personCover.setLastName(person.getLastName());
					personCover.setAddress(person.getAddress());
					personCover.setPhone(person.getPhone());
					listPersons.getPersons().add(personCover);
					log.debug("Une personne trouvee vivant a l'adresse ({}) -->\n{}", firestation.getAddress(),
							JsonUtils.indenteJson(personCover));

				// construit la liste de MedicalRecord de Person dont l'adresse est desservie
				// par la Firestation
				List<MedicalRecord> medicalRecords = dataSrc.getMedicalrecords().stream()
						.filter(medicalRecord -> isMedicalRecordOfThisPerson(medicalRecord, person))
						.collect(Collectors.toList());

				for (MedicalRecord medicalRecord : medicalRecords) {

					// comptabilise les enfants/adultes
					if (DateUtils.isChild(medicalRecord.getBirthdate()))
						numberOfChildren++;
					else
						numberOfAdults++;
					log.debug("Nombre d'enfant:{}, nombre d'adultes:{}", numberOfChildren, numberOfAdults);
				}
			}
		}

		listPersons.setChildrenNumber(Integer.valueOf(numberOfChildren).toString());
		listPersons.setAdultsNumber(Integer.valueOf(numberOfAdults).toString());

		log.debug("La liste des personnes desservies par la caserne n°{} est -->\n {}", station,
				JsonUtils.indenteJson(listPersons));
		log.debug("Fin methode listOfPersonsCoverByFirestation");

		return listPersons;
	}

	private boolean isPersonLiveAtThisAddress(Person person, String address) {

		return StringUtils.isNotEmpty(person.getAddress())
				&& StringUtils.compareIgnoreCase(person.getAddress(), address) == 0;
	}

	private boolean isStationNumberOfFirestation(Firestation firestation, String stationNumber) {

		return StringUtils.isNotEmpty(firestation.getStation())
				&& StringUtils.compareIgnoreCase(firestation.getStation(), stationNumber) == 0;
	}

	private boolean isMedicalRecordOfThisPerson(MedicalRecord medicalRecord, Person person) {

		return StringUtils.isNotEmpty(medicalRecord.getFirstName()) && StringUtils.isNotEmpty(person.getFirstName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getFirstName(), person.getFirstName()) == 0
				&& StringUtils.isNotEmpty(medicalRecord.getLastName()) && StringUtils.isNotEmpty(person.getLastName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getLastName(), person.getLastName()) == 0;

	}
}
