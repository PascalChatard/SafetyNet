package com.safetynet.apirest.apiservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apirest.model.DataSrc;

class PhoneAlertServiceTests {


	@Mock
	DataSrc dataSrc;

	@InjectMocks
	private PhoneAlertService phoneAlertSrv;


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
		phoneAlertSrv = new PhoneAlertService(dataSrc);
	}

	@Test
	final void testListPhoneOfPersonsCoverByFirestation() {
		List<String> listPhones = phoneAlertSrv.listPhoneOfPersonsCoverByFirestation("3");
		assertThat("841-874-6512").isEqualTo(listPhones.get(0));

	}

	@Test
	final void testListPhoneOfPersonsCoverByFirestation_withStationNotFound() {
		List<String> listPhones = phoneAlertSrv.listPhoneOfPersonsCoverByFirestation("15");
		assertThat(listPhones.isEmpty());

	}

	@Test
	final void testListPhoneOfPersonsCoverByFirestation_withStationEmpty() {
		List<String> listPhones = phoneAlertSrv.listPhoneOfPersonsCoverByFirestation("");
		assertThat(listPhones.isEmpty());

	}

}
