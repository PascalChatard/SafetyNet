package com.safetynet.apirest.apicontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.apirest.apiservice.FireService;
import com.safetynet.apirest.model.ListHabitantsByStationNumber;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CtrlFire, couche controleur du traitement de l'URL:
 * http://localhost:8080/fire?address=<address>
 * 
 */

@Slf4j
@RestController
public class FireController {

	/**
	 * injection du bean de la couche service
	 * 
	 */
	@Autowired
	private FireService service;

	/**
	 * Gere la requete de demande de la liste des habitants vivant a l'adresse
	 * donnee ainsi que le numero de la caserne de pompier la desservant. Les
	 * informations retenues sont: nom, age, numero de telephone et dossier medical.
	 * 
	 * @param address, l'adresse sur laquelle doit se faire la recherche
	 * @return la liste des personnes trouvees et le numero de la caserne
	 * 
	 */
	@GetMapping(value = "/fire")
	public ListHabitantsByStationNumber listHabitantsOfAddress(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("address") String address) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());

		ListHabitantsByStationNumber habitants = service.listHabitantsOfAddress(address);

		log.debug("Liste des habitants vivant a l'adresses: {} -->:\n{}", address, JsonUtils.indenteJson(habitants));

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", response.getStatus(), request.getMethod(),
				request.getRequestURI());

		return habitants;
	}

}
