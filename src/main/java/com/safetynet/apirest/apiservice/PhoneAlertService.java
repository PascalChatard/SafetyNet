package com.safetynet.apirest.apiservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Firestation;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ServicePhoneAlert, couche service du traitement de l'URL:
 * http://localhost:8080/phoneAlert?firestation=<firestation_number>.
 * 
 */

@Slf4j
@Service
public class PhoneAlertService {

	/**
	 * reference sur la structure de la base de donnees en memoire
	 * 
	 */
	private DataSrc dataSrc;

	@Autowired
	public PhoneAlertService(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}

	/**
	 * Construit la liste des numeros de telephone des personnes desservies par la
	 * caserne de pompier dont le numero est donnee en parametre.
	 * 
	 * @param station, le numero de la caserne de pompier
	 * @return la liste des numeros de telephone
	 * 
	 */
	public List<String> listPhoneOfPersonsCoverByFirestation(String station) {

		log.debug("Debut methode listPhoneOfPersonsCoverByFirestation, arg: {}", station);
		List<String> phones = new ArrayList<String>();

		// recherche les adresses desservies par la caserne "station"
		List<String> addresses = dataSrc.getFirestations().stream()
				.filter(f -> !f.getStation().isEmpty() && f.getStation().contentEquals(station))
				.map(Firestation::getAddress).collect(Collectors.toList());
		log.debug("Les adresses trouvees ({}) desservie par la caserne n°({})", addresses, station);

		// recherche les personnes residant a "address" et recupere le phone
		addresses.forEach((String address) -> {
			dataSrc.getPersons().stream()
					.filter(p -> !p.getAddress().isEmpty() && p.getAddress().contentEquals(address))
					.forEach(p -> phones.add(p.getPhone()));
		});

		log.debug("Liste des telephone des personne desservies par la caserne n° ({}) -->:\n{}", station,
				JsonUtils.indenteJson(phones));

		log.debug("Fin listPhoneOfPersonsCoverByFirestation");
		return phones;
	}
}
