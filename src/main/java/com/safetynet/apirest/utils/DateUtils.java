package com.safetynet.apirest.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * DateUtil, fournit des methodes de type utilitaire autour d'une date sous la
 * forme d'un String et au format "MM/dd/yyyy".
 * 
 */

@Slf4j
public final class DateUtils {

	/**
	 * Calcule l'age a partir d'une date de naissance.
	 * 
	 * @param birthDate la'date de naissance au format String "MM/dd/yyyy".
	 * @return le nombre d'annee sous la forme d'un entier long, 0 si le format de
	 *         la date est invalide.
	 * 
	 */
	public static long getAge(String birthDate) {
		long years = 0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		try {
			LocalDate birthDay = LocalDate.parse(birthDate, formatter);
			LocalDate now = LocalDate.now();
			years = ChronoUnit.YEARS.between(birthDay, now);
		} catch (DateTimeParseException e) {
			log.error("DateTimeParseException, le format de date doit etre: \"MM/dd/yyyy\"");
		}
		return years;
	}


	/**
	 * Determine a partir d'une date de naissance si la personne est un enfant,
	 * c'est a dire si son age est <= 18 ans.
	 * 
	 * @param birthDate la date de naissance au format String "MM/dd/yyyy".
	 * @return true si c'est un enfant false si c'est un adulte.
	 * 
	 */
	public static boolean isChild(String birthDate) {
		if (getAge(birthDate) <= 18)
			return true;
		else
			return false;
	}

}
