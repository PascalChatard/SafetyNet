package com.safetynet.apirest.apicontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.apirest.apiservice.PhoneAlertService;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CtrlPhoneAlert, couche controleur du traitement de l'URL:
 * http://localhost:8080/phoneAlert?firestation=<firestation_number>
 * 
 */

@Slf4j
@RestController
public class PhoneAlertController {

	/**
	 * injection de l'instance du bean de la couche service
	 * 
	 */
	@Autowired
	private PhoneAlertService service;

	/**
	 * Gere la requete de demande de la liste des numeros de telephone des personnes
	 * desservies par la caserne de pompier dont le numero est donnee en parametre.
	 * 
	 * @param request, requete a traiter
	 * @param firestationNumber, le numero de la caserne de pompier
	 * @return la liste des numeros de telephone
	 * 
	 */
	@GetMapping(value = "/phoneAlert")
	public List<String> listPhoneOfPersonsCoverByFirestation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("firestation") String firestationNumber) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());

		List<String> phones = service.listPhoneOfPersonsCoverByFirestation(firestationNumber);

		log.debug("Liste des numeros de telephones des personnes desservies par la caserne n°: {} -->:\n{}",
				firestationNumber, JsonUtils.indenteJson(phones));

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", response.getStatus(), request.getMethod(),
				request.getRequestURI());

		return phones;
	}
}
