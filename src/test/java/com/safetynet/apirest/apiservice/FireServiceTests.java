package com.safetynet.apirest.apiservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.ListHabitantsByStationNumber;

class FireServiceTests {

	@Mock
	DataSrc dataSrc;

	@InjectMocks
	private FireService fireService;

	String dataSrcString = "{" + "    \"persons\": ["
			+ "        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" },"
			+ "        { \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"address\":\"951 LoneTree Rd\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7458\", \"email\":\"gramps@email.com\" }"
			+ "	], " + "    \"firestations\": [" + "	{ \"address\":\"1509 Culver St\", \"station\":\"3\" },"
			+ "        { \"address\":\"951 LoneTree Rd\", \"station\":\"2\" }" + "	]," + "    \"medicalrecords\": ["
			+ "        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] },"
			+ "        { \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }"
			+ "        ] " + "}";

	@BeforeEach
	public void init() throws JsonMappingException, JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		// mapping des donnees contenues dans la String dans le mock
		// DataSrc
		dataSrc = objectMapper.readValue(dataSrcString, DataSrc.class);
		fireService = new FireService(dataSrc);
	}

	@Test
	final void testListHabitantsOfAddress() {

		ListHabitantsByStationNumber listHabitant = fireService.listHabitantsOfAddress("1509 Culver St");
		assertThat(listHabitant.getHabitants().get(0).getFirstName()).isEqualTo("John");
		assertThat(listHabitant.getHabitants().get(0).getLastName()).isEqualTo("Boyd");
		assertThat(listHabitant.getHabitants().get(0).getAge()).isEqualTo("37");
		assertThat(listHabitant.getHabitants().get(0).getPhone()).isEqualTo("841-874-6512");
		assertThat(listHabitant.getHabitants().get(0).getMedications().size()).isEqualTo(2);
		assertThat(listHabitant.getHabitants().get(0).getMedications().get(0)).isEqualTo("aznol:350mg");
		assertThat(listHabitant.getHabitants().get(0).getMedications().get(1)).isEqualTo("hydrapermazol:100mg");
		assertThat(listHabitant.getHabitants().get(0).getAllergies().get(0)).isEqualTo("nillacilan");
	}

	@Test
	final void testListHabitantsOfAddress_forNotFoundAddress() {

		ListHabitantsByStationNumber listHabitant = fireService.listHabitantsOfAddress("45 Rue des  Champs");

		assertThat(listHabitant.getHabitants()).isEmpty();
		assertThat(listHabitant.getStationNumber()).isEmpty();
	}


	@Test
	final void testListHabitantsOfAddress_forEmptyAddress() {
		ListHabitantsByStationNumber listHabitant = fireService.listHabitantsOfAddress("");
		assertThat(listHabitant.getHabitants()).isEmpty();
		assertThat(listHabitant.getStationNumber()).isEmpty();

	}

}
