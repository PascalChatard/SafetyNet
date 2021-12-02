package com.safetynet.apirest.apicontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.apirest.apiservice.ChildAlertService;
import com.safetynet.apirest.model.ListChildByAddress;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CtrlChildAlert, couche controleur du traitement de l'URL:
 * http://localhost:8080/childAlert?address=<address>
 * 
 */

@Slf4j
@RestController
public class ChildAlertController {

	/**
	 * injection de l'instance du bean de la couche service
	 * 
	 */
	@Autowired
	private ChildAlertService service;

	/**
	 * Gere la requete demande de la liste des enfants, age <= 18 ans, habitant a
	 * l'adresse donnee.
	 * 
	 * @param address, adresse sur laquelle doit se faire la recherche
	 * @return la liste des enfants et la liste des adules trouvees. Si il n'y a pas
	 *         d'enfant retourne un objet vide.
	 * 
	 */
	@GetMapping(value = "/childAlert")
	public ListChildByAddress listOfChildrenLivingAtAddress(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("address") String address) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());

		ListChildByAddress childrenList = service.listOfChildrenLivingAtAddress(address);

		log.debug("Liste des enfants et des adultes habitant a l'adresse: {} -->:\n{}", address,
				JsonUtils.indenteJson(childrenList));

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", response.getStatus(), request.getMethod(),
				request.getRequestURI());

		return childrenList;
	}

}
