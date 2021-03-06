package com.safetynet.apirest.apirepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.apirest.exception.NullDataSrcException;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.utils.DataSrcUtils;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * RepositoryPerson, composant couche repository du traitement du endpoint:
 * http://localhost:8080/person.
 * 
 */

@Slf4j
@Repository
public class PersonRepository implements IRepository<Person> {

	/**
	 * injection de l'instance du bean de la structure de la base de donnees
	 * "memoire"
	 * 
	 */
	private DataSrc dataSrc;

	@Autowired
	public PersonRepository(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}

	@Override
	public Person deleteById(int id) throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		if (dataSrc == null) {
			log.error("Reference dataSrc null, pas d'acces aux donnees!!!!!!");
			throw new NullDataSrcException();
		}

		Person person = dataSrc.getPersons().remove(id);
		log.debug("Suppression de la personne d'index: {} -->\n{}", id, JsonUtils.indenteJson(person));

		return person;
	}

	@Override
	public Person save(Person person) throws NullPointerException, NullDataSrcException {

		if (dataSrc == null) {
			log.error("Reference dataSrc null, pas d'acces aux donnees!!!!!!");
			throw new NullDataSrcException();
		}

		boolean status = dataSrc.getPersons().add(person);
		if (status == false)
			log.error("Echec de l'ajout d'une personne -->:\n{}", JsonUtils.indenteJson(person));

		log.debug("Ajout d'une personne :\n {}", JsonUtils.indenteJson(person));

		return person;
	}

	@Override
	public Person update(int id, Person personUp)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		if (dataSrc == null) {
			log.error("Reference dataSrc null, pas d'acces aux donnees!!!!!!");
			throw new NullDataSrcException();
		}

		Person person = dataSrc.getPersons().get(id);
		// le nom et le prenom correspondent?
		if (DataSrcUtils.isMatchedIdentity(person, personUp)) {

			person = dataSrc.getPersons().set(id, personUp);
			log.debug("Mise ?? jour d'une personne avec succes, index: {} -->\n{}", id, JsonUtils.indenteJson(person));
		} else
			log.error("Echec lors de la mise ?? jour d'une personne dans la liste, index :{}\n", id);

		return person;
	}
}
