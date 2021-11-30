package com.safetynet.apirest.apiservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ServiceCommunityEmail, couche service du traitement de l'URL:
 * http://localhost:8080/communityEmail?city=<city>
 * 
 */

@Slf4j
@Service
public class CommunityEmailService {

	/**
	 * reference sur la structure de la base de donnees en memoire
	 * 
	 */
	private DataSrc dataSrc;

	@Autowired
	public CommunityEmailService(DataSrc dataSrc) {
		this.dataSrc = dataSrc;

	}

	/**
	 * Recherche et construit la liste des adresses mail des habitants de la ville
	 * donnee.
	 * 
	 * @param city, ville sur laquelle doit se faire la recherche
	 * @return la liste des adresses mail trouvees
	 * 
	 */
	public List<String> emailListOfCityHabitants(String city) {

		log.debug("Debut methode emailListOfCityHabitants, arg: {}", city);

		List<Person> persons = dataSrc.getPersons();

		// recherche la/les Person(s) dont la city est: city
		// recupere et place l'email dans la liste emails
		List<String> emails = persons.stream().filter(p -> !p.getCity().isEmpty() && p.getCity().contentEquals(city))
				.map(Person::getEmail).collect(Collectors.toList());

		log.debug("La liste des emails des habitant de {} sont -->\n{}", city, JsonUtils.indenteJson(emails));
		log.debug("Fin methode emailListOfCityHabitants");
		return emails;
	}

}
