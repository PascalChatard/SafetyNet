package com.safetynet.apirest.apicontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.apirest.apiservice.FloodService;
import com.safetynet.apirest.model.ListHousholdsByFirestationAndAddress;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CtrlFlood, couche controleur du traitement de l'URL:
 * http://localhost:8080/flood/stations?stations=<a list of station_numbers>
 * 
 */

@Slf4j
@RestController
public class FloodController {

	/**
	 * injection de l'instance du bean de la couche service
	 * 
	 */
	@Autowired
	private FloodService service;

	/**
	 * Gestion de la requete de demande de la liste des foyers desservis par les
	 * casernes dont les numeros sont donnes.
	 * 
	 * @param request, requete a traiter
	 * @param stations, liste des numeros de station sur lesquelles doit se faire la
	 *        recherche
	 * @return la liste des foyers/personnes trouvees par caserne et par adresse
	 * 
	 */
	@GetMapping(value = "/flood/stations")
	public List<ListHousholdsByFirestationAndAddress> listOfHousholdServedByStation(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("stations") List<String> stations) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());

		List<ListHousholdsByFirestationAndAddress> households = service.listOfHousholdServedByStation(stations);

		log.debug("Liste des foyers/personnes par adresse et par caserne ({}) -->\n{}", stations,
				JsonUtils.indenteJson(households));

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", response.getStatus(), request.getMethod(),
				request.getRequestURI());

		return households;
	}
}
