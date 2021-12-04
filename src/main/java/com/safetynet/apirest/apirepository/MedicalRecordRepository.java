package com.safetynet.apirest.apirepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.apirest.exception.NullDataSrcException;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.utils.DataSrcUtils;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * RepositoryMedicalRecord, composant couche repository du traitement du
 * endpoint: http://localhost:8080/medicalRecord.
 * 
 */

@Slf4j
@Repository
public class MedicalRecordRepository implements IRepository<MedicalRecord> {

	/**
	 * injection de l'instance du bean de la structure de la base de donnees
	 * "memoire"
	 * 
	 */

	private DataSrc dataSrc;

	@Autowired
	public MedicalRecordRepository(DataSrc dataSrc) {
		this.dataSrc = dataSrc;
	}



	@Override
	public MedicalRecord deleteById(int id)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		if (dataSrc == null) {
			log.error("Reference dataSrc null, pas d'acces aux donnees!!!!!!");
			throw new NullDataSrcException();
		}

		MedicalRecord medicalRecord = dataSrc.getMedicalrecords().remove(id);
		log.debug("Suppression du dossier medical d'index ({}) -->\n{}", id, JsonUtils.indenteJson(medicalRecord));

		return medicalRecord;
	}


	@Override
	public MedicalRecord save(MedicalRecord medicalRecord) throws NullPointerException, NullDataSrcException {

		if (dataSrc == null) {
			log.error("Reference dataSrc null, pas d'acces aux donnees!!!!!!");
			throw new NullDataSrcException();
		}

		boolean status = dataSrc.getMedicalrecords().add(medicalRecord);
		if (status == false)
			log.error("Echec de l'ajout d'un medicalRcord -->\n {}", JsonUtils.indenteJson(medicalRecord));

		log.debug("Ajout d'un dossier medical --> \n {}", JsonUtils.indenteJson(medicalRecord));

		return medicalRecord;
	}


	@Override
	public MedicalRecord update(int id, MedicalRecord medicalRecordUp)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		if (dataSrc == null) {
			log.error("Reference dataSrc null, pas d'acces aux donnees!!!!!!");
			throw new NullDataSrcException();
		}

		MedicalRecord medicalRecord = dataSrc.getMedicalrecords().get(id);
		// le nom et le prenom correspondent?
		if (DataSrcUtils.isMatchedIdentity(medicalRecord, medicalRecordUp)) {
			medicalRecord = dataSrc.getMedicalrecords().set(id, medicalRecordUp);

			log.debug("Mise à jour du dossier medical avec succes, index ({}) --> \n{}", id,
					JsonUtils.indenteJson(medicalRecord));
		} else
			log.error("Echec lors de la mise à jour d'un dossier medical dans la liste, index ({})\n", id);

		return medicalRecord;
	}
}
