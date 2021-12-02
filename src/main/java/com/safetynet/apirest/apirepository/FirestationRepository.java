package com.safetynet.apirest.apirepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.apirest.exception.NullDataSrcException;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Firestation;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * RepositoryFirestation, composant couche repository du traitement du endpoint:
 * http://localhost:8080/firestation.
 * 
 */

@Slf4j
@Repository
public class FirestationRepository implements IRepository<Firestation> {

	/**
	 * injection de l'instance du bean de la structure de la base de donnees
	 * "memoire"
	 * 
	 */
	private DataSrc dataSrc;

	@Autowired
	public FirestationRepository(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}

	@Override
	public Firestation deleteById(int id) throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		if (dataSrc == null) {
			log.error("Reference dataSrc null, pas d'acces aux donnees!!!!!!");
			// throw new NullDataSrcException("*** Reference dataSrc null ***");
			throw new NullDataSrcException();
		}

		Firestation firestation = dataSrc.getFirestations().remove(id);
		log.debug("Suppression d'une firestation, index ({}) -->\n{}", id, JsonUtils.indenteJson(firestation));

		return firestation;
	}

	@Override
	public Firestation save(Firestation firestation) throws NullPointerException, NullDataSrcException {

		if (dataSrc == null) {
			log.error("Reference dataSrc null, pas d'acces aux donnees!!!!!!");
			// throw new NullDataSrcException("*** Reference dataSrc null ***");
			throw new NullDataSrcException();
		}

		boolean status = dataSrc.getFirestations().add(firestation);
		if (status == false)
			log.error("Echec de l'ajout d'une firestation -->\n {}", JsonUtils.indenteJson(firestation));

		log.debug("Ajout d'une firestation :\n {}", JsonUtils.indenteJson(firestation));

		return firestation;
	}

	@Override
	public Firestation update(int id, Firestation firestationUp)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		if (dataSrc == null) {
			log.error("Reference dataSrc null, pas d'acces aux donnees!!!!!!");
			// throw new NullDataSrcException("*** Reference dataSrc null ***");
			throw new NullDataSrcException();
		}

		Firestation firestation = dataSrc.getFirestations().set(id, firestationUp);
		if (firestation == null)
			log.error("Echec lors de la mise à jour d'une firestation dans la liste, index :{}\n", id);

		log.debug("Mise à jour d'une personne avec succes, index ({}) -->\n{}", id,
				JsonUtils.indenteJson(firestationUp));

		return firestation;
	}
}
