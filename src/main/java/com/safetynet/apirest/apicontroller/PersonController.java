package com.safetynet.apirest.apicontroller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.service.PersonService;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CtrlPerson, couche controleur du traitement du endpoint:
 * http://localhost:8080/person.
 * 
 */

@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {


	/**
	 * injection de l'instance du bean du composant couche service
	 * 
	 */
	@Autowired
	private PersonService service;

	/**
	 * injection de l'instance du bean de la structure de la base de donnees
	 * "memoire"
	 * 
	 */
	@Autowired
	private DataSrc dataSrc;

	// *********************************
	// Traitement CRUD ressource Person
	// *********************************


	/**
	 * Gere la requete de suppression de l'objet Person positionne a l'index id,
	 * appelle la methode de la couche service.
	 * 
	 * @param request, la requete a traiter.
	 * @param id, le numero d'index de la personne a supprimer de la base de donnees
	 * @return une reference a l'objet supprime dans la dataSrc
	 * 
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Person> deletePerson(HttpServletRequest request,
			@PathVariable int id) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());
		Person person = service.serviceDeleteById(id);

		log.debug("Suppression d'une Person avec succes, index : {} Person:\n{}", id,
				JsonUtils.indenteJson(person));

		ResponseEntity<Person> responseEntity = ResponseEntity.ok(person);

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", responseEntity.getStatusCode(), request.getMethod(),
				request.getRequestURI());

		return responseEntity;
	}

	/**
	 * Gere la requete d'ajout de l'objet Person dans le DataSrc dataSrc, appelle la
	 * methode de la couche service.
	 * 
	 * @param request, la requete a traiter
	 * @param person, l'objet Person a ajouter dans la base de donnees
	 * @return une reference a l'objet ajouter dans dataSrc
	 * 
	 */
	@PostMapping()
	public ResponseEntity<Person> addPerson(HttpServletRequest request,
			@Valid @RequestBody Person person)
			throws JsonProcessingException {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());
		Person addPerson = service.serviceSave(person);

		int index = dataSrc.getPersons().indexOf(addPerson);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(index).toUri();

		log.debug("Ajout d'une Person avec succes, index : {} Person:\n{}", index,
				JsonUtils.indenteJson(addPerson));

		ResponseEntity<Person> responseEntity = ResponseEntity.created(location).body(addPerson);

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", responseEntity.getStatusCode(), request.getMethod(),
				request.getRequestURI());

		return responseEntity;
	}

	/**
	 * Gere la requete de mise a jour de l'objet Person positionne a l'index id dans
	 * le DataSrc dataSrc, appelle la methode de la couche service.
	 * 
	 * @param id, index de l'objet Person a modifier
	 * @param request, la requete a traiter
	 * @param person, l'objet Person a modifier dans la base de donnees
	 * @return une reference a l'objet modifier dans dataSrc
	 * 
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Person> updatePerson(HttpServletRequest request, 
			@PathVariable int id,
			@RequestBody Person person) throws JsonProcessingException {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());
		Person updatePerson = service.serviceUpdate(id, person);

		log.debug("Update d'une Person avec succes, index : {} Person:\n{}", id,
				JsonUtils.indenteJson(updatePerson));

		ResponseEntity<Person> responseEntity = ResponseEntity.ok(updatePerson);

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", responseEntity.getStatusCode(), request.getMethod(),
				request.getRequestURI());

		return responseEntity;
	}
}
