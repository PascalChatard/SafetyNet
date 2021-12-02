package com.safetynet.apirest.apicontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.apirest.apiservice.CommunityEmailService;

import lombok.extern.slf4j.Slf4j;

/**
 * CtrlCommunityEmail, couche controleur du traitement de l'URL:
 * http://localhost:8080/communityEmail?city=<city>
 * 
 */

@Slf4j
@RestController
public class CommunityEmailController {

	/**
	 * injection du bean de la couche service
	 * 
	 */
	@Autowired
	private CommunityEmailService service;

	/**
	 * Gere la requete de demande de la liste des adresses mail des habitants de la
	 * ville donnee.
	 * 
	 * @param city, ville sur laquelle doit se faire la recherche
	 * @return la liste des adresses mail trouvees
	 * 
	 */
	@GetMapping(value = "/communityEmail")
	public List<String> emailListOfCityHabitants(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("city") String city) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());

		List<String> persons = service.emailListOfCityHabitants(city);

		log.debug("Liste des adresses mail des habitants de ville: {} -->:\n{}", city, persons.toString());

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", response.getStatus(), request.getMethod(),
				request.getRequestURI());

		return persons;
	}
}
