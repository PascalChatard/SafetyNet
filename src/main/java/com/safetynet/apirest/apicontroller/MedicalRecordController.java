package com.safetynet.apirest.apicontroller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

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

import com.safetynet.apirest.apiservice.MedicalRecordService;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CtrlPerson, couche controleur du traitement du endpoint:
 * http://localhost:8080/medicalRecord.
 * 
 */

@Slf4j
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {


	/**
	 * injection de l'instance du bean du composant couche service
	 * 
	 */
	@Autowired
	private MedicalRecordService service;

	/**
	 * injection de l'instance du bean de la structure de la base de donnees
	 * "memoire"
	 * 
	 */
	@Autowired
	private DataSrc dataSrc;

	// **********************************
	// Traitement CRUD de MedicalRecord
	// **********************************


	/**
	 * Gere la requete de suppression de l'objet MedicalRecord positionne a l'index
	 * id, appelle la methode de la couche service.
	 * 
	 * @param request, la requete a traiter.
	 * @param id, le numero d'index de du dossier medical a supprimer de la base de
	 *        donnees
	 * @return une reference a l'objet supprime dans la dataSrc
	 * 
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<MedicalRecord> deleteMedicalRecord(HttpServletRequest request,
			@PathVariable int id) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());
		MedicalRecord medicalRecord = service.serviceDeleteById(id);

		log.debug("Suppression d'un MedicalRecord avec succes, index : {} MedicalRecord:\n{}", id,
				JsonUtils.indenteJson(medicalRecord));

		ResponseEntity<MedicalRecord> responseEntity = ResponseEntity.ok(medicalRecord);

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", responseEntity.getStatusCode(), request.getMethod(),
				request.getRequestURI());

		return responseEntity;
	}

	/**
	 * Gere la requete d'ajout de l'objet MedicalRecord dans le DataSrc dataSrc,
	 * appelle la methode de la couche service.
	 * 
	 * @param request, la requete a traiter
	 * @param medicalRecord, l'objet MedicalRecord a ajouter dans la base de donnees
	 * @return une reference a l'objet ajouter dans dataSrc
	 * 
	 */
	@PostMapping()
	public ResponseEntity<MedicalRecord> addFirestation(HttpServletRequest request,
			@RequestBody MedicalRecord medicalRecord) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());
		MedicalRecord addMedicalRecord = service.serviceSave(medicalRecord);

		int index = dataSrc.getMedicalrecords().indexOf(addMedicalRecord);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(index).toUri();

		log.debug("Ajout d'un MedicalRecord avec succes, index : {} Person:\n{}",
				index, JsonUtils.indenteJson(addMedicalRecord));

		ResponseEntity<MedicalRecord> responseEntity = ResponseEntity.created(location).body(addMedicalRecord);

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", responseEntity.getStatusCode(), request.getMethod(),
				request.getRequestURI());

		return responseEntity;
	}

	/**
	 * Gere la requete de mise a jour de l'objet MedicalRecord positionne a l'index
	 * id dans le DataSrc dataSrc, appelle la methode de la couche service.
	 * 
	 * @param id, index de l'objet MedicalRecord a modifier
	 * @param request, la requete a traiter
	 * @param medicalRecordUp, l'objet MedicalRecord a modifier dans la base de
	 *        donnees
	 * @return une reference a l'objet modifier dans dataSrc
	 * 
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<MedicalRecord> updatePerson(HttpServletRequest request,
			@PathVariable int id,
			@RequestBody MedicalRecord medicalRecordUp) {

		log.info("Requete HTTP {}, Uri: {}", request.getMethod(), request.getRequestURI());

		final MedicalRecord updateMedicalRecord = service.serviceUpdate(id, medicalRecordUp);

		log.debug("Ajout d'un MedicalRecord avec succes, index : {} Person:\n{}", id,
				JsonUtils.indenteJson(updateMedicalRecord));

		ResponseEntity<MedicalRecord> responseEntity = ResponseEntity.ok(updateMedicalRecord);

		log.info("Reponse ({}) requete HTTP {}, Uri: {}", responseEntity.getStatusCode(), request.getMethod(),
				request.getRequestURI());

		return responseEntity;
	}
}
