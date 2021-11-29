package com.safetynet.apirest.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MedicalRecord, ressource de l'Api Rest. URI:/medicalRecord
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MedicalRecord {

	/**
	 * prenom de la personne, ne peut etre nul, doit etre compose de 3 a 20
	 * carateres.
	 * 
	 */
	@NotNull
	@Length(min = 3, max = 20, message = "Le prénom doit avoir une longueur de 3 a 20 caracteres")
	private String firstName;

	/**
	 * nom de la personne, ne peut etre nul, doit etre compose de 3 a 20 carateres.
	 * 
	 */
	@NotNull
	@Length(min = 3, max = 20, message = "Le nom doit avoir une longueur de 3 a 20 caracteres")
	private String lastName;

	/**
	 * date de naissance de la personne, format MM/dd/yyyy
	 * 
	 */
	@NotNull
	@NotEmpty
	private String birthdate;

	/**
	 * dossier medical, liste des medicaments et posologie
	 * 
	 */
	private List<String> medications;

	/**
	 * dossier medical, liste des allergies
	 * 
	 */
	private List<String> allergies;
}
