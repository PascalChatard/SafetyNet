package com.safetynet.apirest.apicontroller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.apirest.apiservice.FirestationService;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Firestation;
import com.safetynet.apirest.model.ListPersonsByFirestation;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CtrlFirestation, couche controleur du traitement du endpoint:
 * http://localhost:8080/firestation. Traitement de l'URL:
 * http://localhost:8080/firestation?stationNumber=<station_number>
 * 
 */

@Slf4j
@RestController
@RequestMapping("/firestation")
public class FirestationController {


	/**
	 * injection de l'instance du bean du composant couche service
	 * 
	 */
	@Autowired
	private FirestationService service;

	/**
	 * injection de l'instance du bean de la structure de la base de donnees
	 * "memoire"
	 * 
	 */
	private DataSrc dataSrc;

	@Autowired
	public FirestationController(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}

	// *******************************
	// Traitement CRUD de FireStaion
	// *******************************


	/**
	 * Gere la requete de suppression de l'objet Firestation positionne a l'index
	 * id, appelle la methode de la couche service.
	 * 
	 * @param request, la requete a traiter.
	 * @param id, le numero d'index de la caserne de pompier a supprimer de la base
	 *        de donnees
	 * @return une reference a l'objet supprime dans la dataSrc
	 * 
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Firestation> deleteFirestation(HttpServletRequest request,
			@PathVariable int id) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());
		Firestation firestation = service.serviceDeleteById(id);

		log.debug("Suppression d'une Firestation avec succes, index : {} Firestation:\n{}", id,
				JsonUtils.indenteJson(firestation));

		ResponseEntity<Firestation> responseEntity = ResponseEntity.ok(firestation);

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", responseEntity.getStatusCode(), request.getMethod(),
				request.getRequestURI());

		return responseEntity;
	}

	/**
	 * Gere la requete d'ajout de l'objet Firestation dans le DataSrc dataSrc,
	 * appelle la methode de la couche service.
	 * 
	 * @param request, la requete a traiter
	 * @param firestation, l'objet Firestation a ajouter dans la base de donnees
	 * @return une reference a l'objet ajouter dans dataSrc
	 * 
	 */
	@PostMapping()
	public ResponseEntity<Firestation> addFirestation(HttpServletRequest request,
			@RequestBody Firestation firestation) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());
		Firestation addFirestation = service.serviceSave(firestation);

		int index = dataSrc.getFirestations().indexOf(addFirestation);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(index).toUri();

		log.debug("Ajout d'une Firestation avec succes, index : {} Firestation:\n{}",
				index, JsonUtils.indenteJson(addFirestation));

		ResponseEntity<Firestation> responseEntity = ResponseEntity.created(location).body(addFirestation);

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", responseEntity.getStatusCode(), request.getMethod(),
				request.getRequestURI());
		return responseEntity;
	}

	/**
	 * Gere la requete de mise a jour de l'objet Firestation positionne a l'index id
	 * dans le DataSrc dataSrc, appelle la methode de la couche service.
	 * 
	 * @param id, index de l'objet Firestation a modifier
	 * @param request, la requete a traiter
	 * @param firestation, l'objet Firestation a modifier dans la base de donnees
	 * @return une reference a l'objet modifier dans dataSrc
	 * 
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Firestation> updatePerson(HttpServletRequest request,
			@PathVariable int id,
			@RequestBody Firestation firestation) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());
		final Firestation updateFirestation = service.serviceUpdate(id, firestation);

		log.debug("Update d'une Firestation avec succes, index : {} Firestation:\n{}", id,
				JsonUtils.indenteJson(updateFirestation));

		ResponseEntity<Firestation> responseEntity = ResponseEntity.ok(updateFirestation);

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", responseEntity.getStatusCode(), request.getMethod(),
				request.getRequestURI());
		return responseEntity;
	}

	// *******************************
	// Traitement de l'URL
	// http://localhost:8080/firestation?stationNumber=<station_number>
	// *******************************

	/**
	 * Gere la requete de demande de la liste des personnes desservies par la
	 * caserne dont le numero est donne.
	 * 
	 * @param request, requete a traiter
	 * @param station, le numero de station sur laquelle doit se faire la recherche
	 * @return la liste des personnes trouvees et le decompte enfants/adultes
	 * 
	 */
	@GetMapping()
	public ListPersonsByFirestation listOfPersonsCoverByFirestation(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("stationNumber") String station) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());
		ListPersonsByFirestation listPersons = service.listOfPersonsCoverByFirestation(station);

		log.debug("Liste des personnes desservies par la caserne n°:{} -->:\n{}", station,
				JsonUtils.indenteJson(listPersons));
		log.info("Reponse ({}) requete HTTP {}, Uri: {}", response.getStatus(), request.getMethod(),
				request.getRequestURI());

		return listPersons;
	}

}
