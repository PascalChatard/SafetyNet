package com.safetynet.apirest.apiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.apirest.apirepository.MedicalRecordRepository;
import com.safetynet.apirest.exception.NullDataSrcException;
import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ServicePerson, couche service du traitement du endpoint:
 * http://localhost:8080/medicalReport.
 * 
 */

@Slf4j
@Service
public class MedicalRecordService {

	/**
	 * reference sur le repository de traitement de la ressource MedicalRecord
	 * 
	 */
	@Autowired
	private MedicalRecordRepository repositoryMedicalRecord;


	/**
	 * Supprime l'objet MedicalRecord positionne a l'index id, appelle la methode de
	 * la couche repository.
	 * 
	 * @param id, le numero d'index du dossier medical a supprimer de la base de
	 *        donnees
	 * @return une reference a l'objet supprime dans dataSrc
	 * 
	 */
	public MedicalRecord serviceDeleteById(int id)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		log.debug("Debut methode serviceDeleteByIdMedicalRecord, arg: {}", id);

		MedicalRecord medicalRecord = repositoryMedicalRecord.deleteById(id);

		log.debug("Succes suppression du MedicalRecord d'index ({})  MedicalRecord --> :\n{}", id,
				JsonUtils.indenteJson(medicalRecord));

		log.debug("Fin methode serviceDeleteByIdMedicalRecord");
		return medicalRecord;
	}

	/**
	 * Ajoute l'objet MedicalRecord dans le DataSrc dataSrc, appelle la methode de
	 * la couche repository.
	 * 
	 * @param medicalRecord, l'objet MedicalRecord a ajouter dans la base de donnees
	 * @return une reference a l'objet ajouter dans dataSrc
	 * 
	 */
	public MedicalRecord serviceSave(MedicalRecord medicalRecord) throws NullPointerException, NullDataSrcException {

		log.debug("Debut methode serviceSaveMedicalRecord, arg: {}", medicalRecord);

		MedicalRecord newMedicalRecord = repositoryMedicalRecord.save(medicalRecord);

		log.debug("Succes ajout du MedicalRecord --> \n{}", JsonUtils.indenteJson(newMedicalRecord));

		log.debug("Fin methode serviceSaveMedicalRecord");
		return newMedicalRecord;
	}

	/**
	 * Update l'objet MedicalRecord positionne a l'index id dans le DataSrc dataSrc,
	 * appelle la methode de la couche repository.
	 * 
	 * @param id, index de l'objet MedicalRecord a modifier
	 * @param medicalRecord, l'objet MedicalRecord a modifier dans la base de
	 *        donnees
	 * @return une reference a l'objet modifier dans dataSrc
	 * 
	 */
	public MedicalRecord serviceUpdate(int id, MedicalRecord medicalRecord)
			throws IndexOutOfBoundsException, NullPointerException, NullDataSrcException {

		log.debug("Debut methode serviceUpdateMedicalRecord, arg: {}", medicalRecord);

		MedicalRecord upMedicalRecord = repositoryMedicalRecord.update(id, medicalRecord);

		log.debug("Succes modification du MedicalRecord d'index ({}) --> \n{}", id,
				JsonUtils.indenteJson(upMedicalRecord));

		log.debug("Fin methode serviceUpdateMedicalRecord");
		return upMedicalRecord;
	}
}
