package com.safetynet.apirest.apiservice;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.safetynet.apirest.model.PersonInfo;

class PersonInfoServiceTests {

	@Mock
	DataSrc dataSrc;

	@InjectMocks
	private PersonInfoService srvInfoPerson;

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
		srvInfoPerson = new PersonInfoService(dataSrc);
	}

	@Test
	final void testGetNameAdrAgeEmailAndMedicalRecord() {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setLastName("Boyd");
		personInfo.setAddress("1509 Culver St");
		personInfo.setAge("37");
		personInfo.setEmail("jaboyd@email.com");
		personInfo.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
		personInfo.setAllergies(Arrays.asList("nillacilan"));

		List<PersonInfo> persons = srvInfoPerson.getNameAdrAgeEmailAndMedicalRecord("John", "Boyd");
		assertThat(personInfo).isEqualTo(persons.get(0));

	}

	@Test
	final void testGetNameAdrAgeEmailAndMedicalRecord_withNoMatchLastName() {
		List<PersonInfo> persons = srvInfoPerson.getNameAdrAgeEmailAndMedicalRecord("John", "Travolta");
		assertThat(persons.isEmpty());

	}

	@Test
	final void testGetNameAdrAgeEmailAndMedicalRecord_withNoMatchFirstName() {
		List<PersonInfo> persons = srvInfoPerson.getNameAdrAgeEmailAndMedicalRecord("Peter", "Boyd");
		assertThat(persons.isEmpty());

	}

	@Test
	final void testGetNameAdrAgeEmailAndMedicalRecord_withEmptyLastName() {
		List<PersonInfo> persons = srvInfoPerson.getNameAdrAgeEmailAndMedicalRecord("John", "");
		assertThat(persons.isEmpty());

	}

	@Test
	final void testGetNameAdrAgeEmailAndMedicalRecord_withEmptyFirstName() {
		List<PersonInfo> persons = srvInfoPerson.getNameAdrAgeEmailAndMedicalRecord("", "Boyd");
		assertThat(persons.isEmpty());

	}

}
