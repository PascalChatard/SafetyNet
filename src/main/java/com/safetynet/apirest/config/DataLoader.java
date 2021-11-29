package com.safetynet.apirest.config;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * DataLoader, composant charge de la lecture des donnees ressources contenues
 * dans le fichier JSON et stockage de celles-ci en memoire via le bean dataSrc.
 * 
 */

@Slf4j
@Component
public class DataLoader {

	/**
	 * le nom du fichier JSON contenant les donnees sources de l'Api.
	 * 
	 */
	private String dataSourceFileName;

	/**
	 * structure de donnees qui heberge les ressources de l'Api, c'est la "Bdd en
	 * memoire".
	 * 
	 */
	private DataSrc dataSrc;

	/**
	 * getter sur dataSrc l'instance des donnees sources en memoire de l'Api.
	 * 
	 */
	public DataSrc getDataSrc() {
		return dataSrc;
	}

	/**
	 * setter sur dataSourceFileName le nom du fichier JSON contenant les donnees
	 * sources de l'Api.
	 * 
	 */
	public void setDataSourceFileName(String dataSourceFileName) {
		this.dataSourceFileName = dataSourceFileName;
	}


	/**
	 * lit le fichier JSON, parse et mappe les donnees dans l'instance dataSrc.
	 * 
	 */
	public void loadDataSrc() throws Exception {
		// creation de la ressource d'acces au fichier JSON depuis le classpath
		Resource resourceFileSrc = new ClassPathResource(dataSourceFileName);

		// lecture d'un bloc du fichier JSON et stockage dans la String
		String fileAsString = Files.readString(resourceFileSrc.getFile().toPath(), StandardCharsets.UTF_8);
		log.debug("Convert DataSrc file to String:\n {}", fileAsString);

		ObjectMapper objectMapper = new ObjectMapper();

		// operation de mapping des donnees contenues dans la String dans une instance
		// DataSrc affectee a dataSrc
		dataSrc = objectMapper.readValue(fileAsString, DataSrc.class);
		log.debug("Construction de la data source depuis le fichier \"{}\":\n {}", dataSourceFileName,
				JsonUtils.indenteJson(dataSrc));
	}
}
