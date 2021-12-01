package com.safetynet.apirest.apiservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.ListHousholdsByFirestationAndAddress;

class FloodServiceTests {

	@Mock
	DataSrc dataSrc;

	@InjectMocks
	private FloodService floodSrv;

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
		floodSrv = new FloodService(dataSrc);
	}

	@Test
	final void testListOfHousholdServedByStation_forOneStation() {
		List<String> stations = Arrays.asList("3");

		List<ListHousholdsByFirestationAndAddress> listHouseholds = floodSrv.listOfHousholdServedByStation(stations);
		assertThat(listHouseholds.get(0).getFirestationNumber()).isEqualTo("3");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getAddress()).isEqualTo("1509 Culver St");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getFirstName()).isEqualTo("John");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getLastName()).isEqualTo("Boyd");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getAge()).isEqualTo("37");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getPhone())
				.isEqualTo("841-874-6512");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getMedications().get(0))
				.isEqualTo("aznol:350mg");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getMedications().get(1))
				.isEqualTo("hydrapermazol:100mg");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getAllergies().get(0))
				.isEqualTo("nillacilan");
	}

	@Test
	final void testListOfHousholdServedByStation_forTooStations() {
		List<String> stations = Arrays.asList("3", "2");

		List<ListHousholdsByFirestationAndAddress> listHouseholds = floodSrv.listOfHousholdServedByStation(stations);

		assertThat(listHouseholds.get(0).getFirestationNumber()).isEqualTo("3");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getAddress()).isEqualTo("1509 Culver St");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getFirstName()).isEqualTo("John");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getLastName()).isEqualTo("Boyd");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getAge()).isEqualTo("37");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getPhone())
				.isEqualTo("841-874-6512");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getMedications().get(0))
				.isEqualTo("aznol:350mg");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getMedications().get(1))
				.isEqualTo("hydrapermazol:100mg");
		assertThat(listHouseholds.get(0).getHouseholds().get(0).getHabitants().get(0).getAllergies().get(0))
				.isEqualTo("nillacilan");

		assertThat(listHouseholds.get(1).getFirestationNumber()).isEqualTo("2");
		assertThat(listHouseholds.get(1).getHouseholds().get(0).getAddress()).isEqualTo("951 LoneTree Rd");
		assertThat(listHouseholds.get(1).getHouseholds().get(0).getHabitants().get(0).getFirstName()).isEqualTo("Eric");
		assertThat(listHouseholds.get(1).getHouseholds().get(0).getHabitants().get(0).getLastName())
				.isEqualTo("Cadigan");
		assertThat(listHouseholds.get(1).getHouseholds().get(0).getHabitants().get(0).getAge()).isEqualTo("76");
		assertThat(listHouseholds.get(1).getHouseholds().get(0).getHabitants().get(0).getPhone())
				.isEqualTo("841-874-7458");
		assertThat(listHouseholds.get(1).getHouseholds().get(0).getHabitants().get(0).getMedications().size())
				.isEqualTo(1);
		assertThat(listHouseholds.get(1).getHouseholds().get(0).getHabitants().get(0).getMedications().get(0))
				.isEqualTo("tradoxidine:400mg");
		assertThat(listHouseholds.get(1).getHouseholds().get(0).getHabitants().get(0).getAllergies().isEmpty());
	}


	@Test
	final void testListOfHousholdServedByStation_forEmptyStations() {
		List<String> stations = new ArrayList<String>();
		List<ListHousholdsByFirestationAndAddress> listHouseholds = floodSrv.listOfHousholdServedByStation(stations);
		assertThat(listHouseholds.isEmpty());

	}

}
