package com.safetynet.apirest.apiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.apirest.apirepository.PersonRepository;
import com.safetynet.apirest.exception.NullDataSrcException;
import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ServicePerson, couche service du traitement du endpoint:
 * http://localhost:8080/person.
 * 
 */

@Slf4j
@Service
public class PersonService {

	/**
	 * reference sur le repository de traitement de la ressource Person
	 * 
	 */
	@Autowired
	private PersonRepository repositoryPerson;

	/**
	 * Supprime l'objet Person positionne a l'index id, appelle la methode de la
	 * couche repository.
	 * 
	 * @param id, le numero d'index de la personne a supprimer de la base de donnees
	 * @return une reference a l'objet supprime dans dataSrc
	 * 
	 */
	public Person serviceDeleteById(int id)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {
		log.debug("Debut methode serviceDeleteById, arg: {}", id);
		Person person = repositoryPerson.deleteById(id);
		log.debug("Succes suppression Person d'index({})  Person --> \n{}", id, JsonUtils.indenteJson(person));
		log.debug("Fin methode serviceDeleteById");
		return person;
	}

	/**
	 * Ajoute l'objet Person dans le DataSrc dataSrc, appelle la methode de la
	 * couche repository.
	 * 
	 * @param person, l'objet Person a ajouter dans la base de donnees
	 * @return une reference a l'objet ajouter dans dataSrc
	 * 
	 */
	public Person serviceSave(Person person) throws NullPointerException, NullDataSrcException {
		log.debug("Debut methode serviceSave, arg: {}", person);
		Person newPerson = repositoryPerson.save(person);
		log.debug("Succes ajout Person -->: \n{}", JsonUtils.indenteJson(newPerson));
		log.debug("Fin methode serviceSave");
		return newPerson;
	}

	/**
	 * Update l'objet Person positionne a l'index id dans le DataSrc dataSrc,
	 * appelle la methode de la couche repository.
	 * 
	 * @param id, index de l'objet Person a modifier
	 * @param person, l'objet Person a modifier dans la base de donnees
	 * @return une reference a l'objet modifier dans dataSrc
	 * 
	 */
	public Person serviceUpdate(int id, Person person)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		log.debug("Debut methode serviceUpdate, arg: {}", person);
		Person upPerson = repositoryPerson.update(id, person);
		log.debug("Succes modification Person d'index({}) --> \n{}", id, JsonUtils.indenteJson(upPerson));
		log.debug("Fin methode serviceUpdate");
		return upPerson;
	}
}
