package com.safetynet.apirest.apiservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Identity;
import com.safetynet.apirest.model.IdentityAge;
import com.safetynet.apirest.model.ListChildByAddress;
import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.utils.DateUtils;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ServiceCommunityEmail, couche service du traitement de l'URL:
 * http://localhost:8080/childAlert?address=<address>
 * 
 */

@Slf4j
@Service
public class ChildAlertService {

	/**
	 * reference sur la structure de la base de donnees en memoire
	 * 
	 */
	private DataSrc dataSrc;

	@Autowired
	public ChildAlertService(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}

	/**
	 * Recherche et construit la liste des enfants, age <= 18 ans, habitant a
	 * l'adresse donnee.Les informations retenues sont: nom, prenom et age, numero
	 * de telephone et dossier medical. Construit egalement la liste des adultes
	 * habitant cette adresse. Les informations retenue sont: nom et prenom.
	 * 
	 * @param address, adresse sur laquelle doit se faire la recherche
	 * @return la liste des enfants et la liste des adules trouvees. Si il n'y a pas
	 *         d'enfant retourne un objet vide.
	 * 
	 */
	public ListChildByAddress listOfChildrenLivingAtAddress(String address) {

		log.debug("Debut methode listOfChildrenLivingAtAddress, arg: {}", address);
		ListChildByAddress listChildren = new ListChildByAddress();
		List<IdentityAge> children = new ArrayList<IdentityAge>();
		List<Identity> adults = new ArrayList<Identity>();
		
		// recherche la/les Person(s) dont l'adresse est: address
		List<Person> persons = dataSrc.getPersons().stream()
				.filter(person -> !person.getAddress().isEmpty() && person.getAddress().contentEquals(address))
				.collect(Collectors.toList());
		log.debug("Liste des Person trouve pour cette adresse: ({}):\n{}", address, persons);

		persons.forEach((Person person) -> {
			dataSrc.getMedicalrecords().stream().filter(medicRcd -> isMedicalRecordOfThisPerson(medicRcd, person))
					.forEach(medicRcd -> {
				// calcul l'age depuis la Birthdate
				long years = DateUtils.getAge(medicRcd.getBirthdate());
				log.debug("Calcul de l'age de l'individu--> {}", years);

				if (DateUtils.isChild(medicRcd.getBirthdate())) {
					// C'est un enfant
					IdentityAge child = new IdentityAge();
					child.setFirstName(person.getFirstName());
					child.setLastName(person.getLastName());
					child.setAge(Long.valueOf(years).toString());
					children.add(child);
							log.debug("Cet individu est un enfant!");

				} else {
					// C'est un adulte
					Identity adult = new Identity();
					adult.setFirstName(person.getFirstName());
					adult.setLastName(person.getLastName());
					adults.add(adult);
							log.debug("Cet individu est un adulte!");
				}
			});

		});

		// affecte la liste des enfants et la liste des autres membres a l'objet
		// ListChildByAddress si il y a des enfants
		if (!children.isEmpty()) {
			listChildren.setChildren(children);
			listChildren.setAdults(adults);
		}
		else {
			listChildren.setChildren(new ArrayList<IdentityAge>());
			listChildren.setAdults(new ArrayList<Identity>());
		}
		log.debug("La liste des enfants vivant a l'adresse:{} sont -->\n{}", address,
				JsonUtils.indenteJson(listChildren));
		log.debug("Fin methode listOfChildrenLivingAtAddress");
		return listChildren;
	}

	private boolean isMedicalRecordOfThisPerson(final MedicalRecord medicalRecord, final Person person) {

		return (StringUtils.isNotEmpty(medicalRecord.getFirstName()) && StringUtils.isNotEmpty(person.getFirstName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getFirstName(), person.getFirstName()) == 0
				&& StringUtils.isNotEmpty(medicalRecord.getLastName()) && StringUtils.isNotEmpty(person.getLastName())
				&& StringUtils.compareIgnoreCase(medicalRecord.getLastName(), person.getLastName()) == 0);
	}
}
