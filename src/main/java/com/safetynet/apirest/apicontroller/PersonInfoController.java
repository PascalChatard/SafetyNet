package com.safetynet.apirest.apicontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.apirest.apiservice.PersonInfoService;
import com.safetynet.apirest.model.PersonInfo;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CtrlInfoPerson, couche controleur du traitement de l'URL:
 * http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
 * 
 */

@Slf4j
@RestController
public class PersonInfoController {

	/**
	 * injection de l'instance du bean de la couche service
	 * 
	 */
	@Autowired
	private PersonInfoService service;

	/**
	 * Gere la requete de demande de la liste des personnes dont le nom et prenom
	 * correspondent aux parametres donnes.
	 * 
	 * @param request, requete a traiter
	 * @param firstNameParam, prenom recherche
	 * @param lastNameParam, nom recherche
	 * @return la liste des personnes trouvees
	 * 
	 */
	@GetMapping(value = "/personInfo")
	public List<PersonInfo> getNameAdrAgeEmailAndMedicalRecord(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());

		List<PersonInfo> persons = service.getNameAdrAgeEmailAndMedicalRecord(firstName, lastName);

		log.debug("Liste des personnes dont l'identite est: {} {} -->:\n{}", firstName, lastName,
				JsonUtils.indenteJson(persons));

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", response.getStatus(), request.getMethod(),
				request.getRequestURI());

		return persons;
	}
}
