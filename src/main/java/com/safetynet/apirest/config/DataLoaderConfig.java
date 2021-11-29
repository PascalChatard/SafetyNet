package com.safetynet.apirest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.safetynet.apirest.model.DataSrc;

/**
 * DataLoaderConfig, bean de configuration qui permet de recuper le nom du
 * fichier JSON contenant les ressources de l'Api depuis application.properties,
 * de creer un bean DataSrc et de le remplir a partir de l'injection DataLoader
 * et du fichier JSON.
 * 
 */

@Configuration
public class DataLoaderConfig {

	/**
	 * le nom du fichier des ressources JSON
	 * 
	 */
	@Value("${data.source.json}")
	private String dataSourceFileName;

	/**
	 * composant permettant le chargement des ressources dans le DataSrc
	 * 
	 */
	@Autowired
	public DataLoader dataLoader;

	/**
	 * bean DataSrc qui mappe en memoire les ressources de l'Api.
	 * 
	 * @return la reference a dataSrc
	 */
	@Bean
	public DataSrc dataSrc() {
		dataLoader.setDataSourceFileName(dataSourceFileName);
		try {
			dataLoader.loadDataSrc();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataLoader.getDataSrc();
	}
}

