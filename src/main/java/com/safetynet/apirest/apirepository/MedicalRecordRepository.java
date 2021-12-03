package com.safetynet.apirest.apirepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.apirest.exception.NullDataSrcException;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.MedicalRecord;
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
		if (isMatchedIdentity(medicalRecord, medicalRecordUp)) {
			medicalRecord = dataSrc.getMedicalrecords().set(id, medicalRecord);

			log.debug("Mise à jour du dossier medical avec succes, index ({}) --> \n{}", id,
					JsonUtils.indenteJson(medicalRecord));
		} else
			log.error("Echec lors de la mise à jour d'un dossier medical dans la liste, index ({})\n", id);

		return medicalRecord;
	}

	/**
	 * isMatchedIdentity verifie la correspondance de l'identite, firstName et
	 * lastName, sur deux instances MedicalRecord.
	 * 
	 * @param medicalRecordA, premirere instance de l'objet MedicalRecord
	 * @param medicalRecordB, deuxieme instance de l'objet MedicalRecord
	 * @return true si l'identite correspond
	 * 
	 */
	private boolean isMatchedIdentity(MedicalRecord medicalRecordA, MedicalRecord medicalRecordB) {

		return StringUtils.isNotEmpty(medicalRecordA.getFirstName())
				&& StringUtils.isNotEmpty(medicalRecordB.getFirstName())
				&& StringUtils.compareIgnoreCase(medicalRecordA.getFirstName(), medicalRecordB.getFirstName()) == 0
				&& StringUtils.isNotEmpty(medicalRecordA.getLastName())
				&& StringUtils.isNotEmpty(medicalRecordB.getLastName())
				&& StringUtils.compareIgnoreCase(medicalRecordA.getLastName(), medicalRecordB.getLastName()) == 0;
	}

}
